/**
 * 用户详情对话框
 * @type {{UserEditData: {}, validateFields: {account: {validators: {notEmpty: {message: string}}}, name: {validators: {notEmpty: {message: string}}}, citySel: {validators: {notEmpty: {message: string}}}, password: {validators: {notEmpty: {message: string}, identical: {field: string, message: string}}}, rePassword: {validators: {notEmpty: {message: string}, identical: {field: string, message: string}}}}}}
 */
var UserEdit = {
    UserEditData: {},
    validateFields: {
        account: {
            validators: {
                notEmpty: {
                    message: '账户不能为空'
                }
            }
        },
        name: {
            validators: {
                notEmpty: {
                    message: '姓名不能为空'
                }
            }
        },
        citySel: {
            validators: {
                notEmpty: {
                    message: '部门不能为空'
                }
            }
        },
        password: {
            validators: {
                notEmpty: {
                    message: '密码不能为空'
                },
                identical: {
                    field: 'rePassword',
                    message: '两次密码不一致'
                },
            }
        },
        rePassword: {
            validators: {
                notEmpty: {
                    message: '密码不能为空'
                },
                identical: {
                    field: 'password',
                    message: '两次密码不一致'
                },
            }
        }
    }
};

layui.use(['form','jquery'], function () {
    var form = layui.form;
    form.on('submit(adduser)', function (data) {
        var ajax = new $ax(MyObject.ctxPath + "/user/update", function (data) {
            MyObject.success("修改成功！");
        }, function (data) {
            MyObject.error("修改失败!" + data.responseJSON.msg + "!");
        });
        ajax.setData(data.field);
        ajax.start();
        //console.info(data.field)
        return false;
    })
    //性别赋值
    var sex = $("#sexVal").val();
    $("#sex").val(sex);
    form.render('select');
})

//头像上传
layui.use('upload', function(){
    var $ = layui.jquery,upload = layui.upload;
    var uploadInst = upload.render({
        elem: '#imgbtn,#headimg'
        ,url: '/file/fileUpload'
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                $('#headimg').attr('src', result); //图片链接（base64）
            });
        }
        ,done: function(res){
            if(res.code == 200) {//上传成功
                var imgId = res.data;
                $("#avatar").val(imgId);
                layer.msg('上传成功', {
                    icon: 1
                });
                $('#demoText').empty();
            } else {//上传失败
                layer.msg('上传失败', {
                    icon: 1
                });
            }
        }
        ,error: function(){
            //失败状态，并实现重传
            var demoText = $('#demoText');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function(){
                uploadInst.upload();
            });
        }
    });
})

/**
 * 设置对话框中的数据
 * @returns {UserEdit}
 */
UserEdit.set = function (key, value) {
    if (typeof value == "undefined") {
        if (typeof $("#" + key).val() == "undefined") {
            var str = "";
            var ids = "";
            $("input[name='" + key + "']:checkbox").each(function () {
                if (true == $(this).is(':checked')) {
                    str += $(this).val() + ",";
                }
            });
            if (str) {
                if (str.substr(str.length - 1) == ',') {
                    ids = str.substr(0, str.length - 1);
                }
            } else {
                $("input[name='" + key + "']:radio").each(function () {
                    if (true == $(this).is(':checked')) {
                        ids = $(this).val()
                    }
                });
            }
            this.UserEditData[key] = ids;
        } else {
            this.UserEditData[key] = $("#" + key).val();
        }
    }
    return this;
}

/**
 * 获取对话框中的数据
 * @param key
 * @returns {jQuery}
 */
UserEdit.get = function (key) {
    return $("#" + key).val();
}

$(function () {
    //点击页面时关闭下拉窗
    $("body").bind("mousedown", onBodyDown);

    //部门选择值
    var ztree = new $ZTree("tree", "/dept/tree");
    ztree.bindOnClick(UserEdit.onClickDept);
    ztree.init();
    instance = ztree;

    //角色选择值
    var id = $("#id").val();
    var ztreeRole = new $ZTree("roletree", "/role/roleTreeListByUserId/"+id);
    var setting = {
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: { "Y": "", "N": "" }
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onCheck: function(event, treeId, treeNode){
                var ids = MyObject.zTreeCheckedNodes("roletree");
                $("#roleid").val(ids);
                var names = MyObject.zTreeCheckedNodesName("roletree");
                $("#roleSel").val(names);
                //如果角色为环卫工、司机时，不需要填写账户信息
                UserEdit.hideAccount(names);
            }
        }
    };
    ztreeRole.setSettings(setting);
    ztreeRole.init();
    instanceRole = ztreeRole;

    //驾照类型选择值
    var ztreeVehicleType = new $ZTree("vehicleTypeTree", "/dict/getDictList/vehicle_type");
    var settingVehicleType = {
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: { "Y": "", "N": "" }
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onCheck: function(event, treeId, treeNode){
                var ids = MyObject.zTreeCheckedNodes("vehicleTypeTree");
                $("#vehicleType").val(ids);
                var names = MyObject.zTreeCheckedNodesName("vehicleTypeTree");
                $("#vehicleTypeSel").val(names);
            }
        }
    };
    ztreeVehicleType.setSettings(settingVehicleType);
    ztreeVehicleType.init();
    instanceVehicleType = ztreeVehicleType;

    //部门赋值
    var deptid = $("#deptid").val();
    if(deptid != undefined && deptid != null && deptid.length > 0) {
        var zTree = $.fn.zTree.getZTreeObj("tree");
        var node = zTree.getNodeByParam("id",deptid);
        $("#citySel").val(node.name);
        zTree.selectNode(node);
    }
    //角色赋值
    var ids = MyObject.zTreeCheckedNodes("roletree");
    $("#roleid").val(ids);
    var names = MyObject.zTreeCheckedNodesName("roletree");
    $("#roleSel").val(names);
    UserEdit.hideAccount(names);
    //驾照类型赋值
    var vehicleTypes = $("#vehicleType").val();
    if(vehicleTypes != undefined && vehicleTypes != null && vehicleTypes.length > 0) {
        var zTreeVT = $.fn.zTree.getZTreeObj("vehicleTypeTree");
        var type = vehicleTypes.split(",");
        $.each(type,function(i,value){
            var node = zTreeVT.getNodeByParam("id",value);
            node.checked = true;
            zTreeVT.updateNode(node);
        });
        var ids = MyObject.zTreeCheckedNodes("vehicleTypeTree");
        $("#vehicleType").val(ids);
        var names = MyObject.zTreeCheckedNodesName("vehicleTypeTree");
        $("#vehicleTypeSel").val(names);
    }
})

/**
 * 选中部门时
 */
UserEdit.onClickDept = function (e, treeId, treeNode) {
    $("#citySel").attr("value", instance.getSelectedVal());
    $("#deptid").attr("value", treeNode.id);
};

//选中角色时如果角色为环卫工、司机时，不需要填写账户信息;为司机时需填写司机信息
UserEdit.hideAccount = function(names){
    var list = names.split(",");
    var hidden = false;
    var driver = false;
    for (var i = 0; i < list.length; i++) {
        if(list[i] == "环卫工") {
            hidden = true;
        }
        if(list[i] == "司机") {
            hidden = true;
            driver = true;
        }
    }
    if (hidden) {
        //隐藏
        $(".account").attr("lay-verify", "");
        $(".password").attr("lay-verify", "");
        $(".account").val("");
        $(".password").val("");
        $(".acctitle").hide();
    } else {
        //不隐藏
        $(".account").attr("lay-verify", "required");
        $(".password").attr("lay-verify", "password|required");
        $(".acctitle").show();
    }
    if (driver) {
        $(".driver").show();
        $(".driver-required").attr("lay-verify", "required");
        $(".driver-number").attr("lay-verify", "number");
        $(".driver-vehicleType").attr("lay-verify", "required");
    } else {
        $(".driver").hide();
        $(".driver-required").attr("lay-verify", "");
        $(".driver-number").attr("lay-verify", "");
        $(".driver-vehicleType").attr("lay-verify", "");
        $(".driver-required").val("");
        $(".driver-number").val("");
        $("#vehicleType").val("");
    }
}

/**
 * 绑定单击事件
 * @param event
 */
function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0
        || event.target.id == "roleContent" || $(event.target).parents("#roleContent").length > 0
        || event.target.id == "vehicleTypeContent" || $(event.target).parents("#vehicleTypeContent").length > 0)) {
        $("#roleContent").fadeOut("fast");
        $("#menuContent").fadeOut("fast");
        $("#vehicleTypeContent").fadeOut("fast");
    }
}

/**
 * 显示角色树
 */
UserEdit.showRoleSelectTree = function () {
    $("#roleContent").css("display", "block");
}

/**
 * 显示部门树
 */
UserEdit.showDeptSelectTree = function () {
    $("#menuContent").css("display", "block");
}

/**
 * 显示驾照类型树
 */
UserEdit.showVehicleTypeSelectTree = function () {
    $("#vehicleTypeContent").css("display", "block");
}

