package com.mapscience.modular.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.mapscience.core.common.ResponseVal;
import com.mapscience.modular.system.model.Menu;

/**
 * <p>
 * 菜单 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-01-18
 */
public interface IMenuService extends IService<Menu> {


    /**
     * 递归查找菜单
     * @return
     */
    ResponseVal findmenuChildren();

    /**
     * 增加菜单
     * @param t
     */
    ResponseVal saveMenu(Menu t);
}