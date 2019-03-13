package com.mapscience.modular.system.controller.excel;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.mapscience.core.common.ResponseVal;
import com.mapscience.core.util.excel.EasyPOIExcelUtile;
import com.mapscience.modular.system.dto.EmployeeDTO;
import com.mapscience.modular.system.model.Company;
import com.mapscience.modular.system.model.Education;
import com.mapscience.modular.system.model.Employee;
import com.mapscience.modular.system.service.ICompanyService;
import com.mapscience.modular.system.service.IEducationService;
import com.mapscience.modular.system.service.IEmployeeService;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 操作表格
 */
@Controller
@RequestMapping("/excel")
public class ExcelController {

    /**
     * 人员基本信息
     */
    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IEducationService educationService;

    @Autowired
    private ICompanyService companyService;

    /**
     * 获取导出模板 /数据
     *
     * @param request
     * @param method  0 模板 1 数据
     * @param type    导出模板类型 1 员工信息 2 教育经历 3 证书证件 4 工作经历
     * @param ids     导出员工的ID
     */
    @RequestMapping("getModelExcel")
    @ResponseBody
    public ResponseVal exportEmploy(HttpServletRequest request, Integer method, Integer type, String ids,
                                    HttpServletResponse response) {

        List<Employee> list = employeeService.getList();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("员工信息表", "员工信息"), Employee.class, list);



        return new ResponseVal(200, "测试", new Date());
    }

    /**
     * 导出空表格
     *
     * @param response
     * @return
     */
    @RequestMapping("export")
    @ResponseBody
    public ResponseVal export(HttpServletResponse response) {
        EasyPOIExcelUtile.exportExcel(new ArrayList<EmployeeDTO>(), "员工基本信息", "基本信息", EmployeeDTO.class, "员工基本信息.xls",
                response);


        return new ResponseVal(200, "测试", null);
    }

    /**
     * 导入excel信息
     *
     * @param request
     * @param files   (标题0行，表头1行)
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/importExcelEmp", method = RequestMethod.POST)
    public ResponseVal importExcelEmp(HttpServletRequest request,
                                      @RequestParam(value = "file", required = false) MultipartFile files) {
        try {
            // 获取workbook
            Workbook workBook = EasyPOIExcelUtile.getWorkBook(files);

            ImportParams params = new ImportParams();
            // 表头在第几行
            params.setTitleRows(0);
            params.setHeadRows(2);
            Sheet sh = null;


            // 循环工作表获取sheet
            for (int n = 0; n < workBook.getNumberOfSheets(); n++) {

                params.setStartSheetIndex(n);
                //ExcelImportResult<Object> result = null;
                // 循环表获取页
                sh = workBook.getSheetAt(n);
                String sheetName = sh.getSheetName();

                if ("基本信息".equals(sheetName)) {
                    //List<Employee> list = EasyPOIExcelUtile.importExcel(files, params.getTitleRows(), params.getHeadRows(), Employee.class);
                    List<EmployeeDTO> list = ExcelImportUtil.importExcel(files.getInputStream(), EmployeeDTO.class, params);
                    for (EmployeeDTO employeeDTO : list) {
                        employeeDTO.setEmployeeStateId("1");
                        employeeDTO.setCrateTime(new Date());
                        employeeDTO.setUpdateTime(new Date());
                        employeeService.add(employeeDTO);
                    }

                    //result = ExcelImportUtil.importExcelMore(files.getInputStream(), EmployeeDTO.class, params);

                } else if ("教育".equals(sheetName)) {
                    List<Education> list = ExcelImportUtil.importExcel(files.getInputStream(), Education.class, params);
                    for (Education education : list) {
                        education.setCreateTime(new Date());
                        education.setUpdateTime(new Date());

                    }

                } else if ("工作经历".equals(sheetName)) {
                    List<Employee> list = ExcelImportUtil.importExcel(files.getInputStream(), EmployeeDTO.class, params);
                    for (Employee employee : list) {
                        employeeService.add(employee);

                    }

                } else if ("家庭成员社会关系".equals(sheetName)) {
                    // List<Employee> list = EasyPOIExcelUtile.importExcel(files, 0, 1, Employee.class);
                    List<EmployeeDTO> list = ExcelImportUtil.importExcel(files.getInputStream(), EmployeeDTO.class, params);
                    for (EmployeeDTO employeeDTO : list) {
                        employeeService.add(employeeDTO);

                    }

                }

            }

            return new ResponseVal(200, "上传成功");

        } catch (Exception e) {
            return new ResponseVal(500, "上传失败！");
        }
    }


    /**
     * 导入excel公司
     *
     * @param files(标题0行，表头1行)
     */
    @PostMapping("/importExcelCompany")
    @ResponseBody
    public ResponseVal importExcelCompany(@RequestParam(value = "file", required = false) MultipartFile files) {
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(0);
            params.setHeadRows(1);
            List<Company> list = ExcelImportUtil.importExcel(files.getInputStream(), Company.class, params);
            for (Company company : list) {
                company.setStatus(1);
                company.setCrateTime(new Date());
                company.setUpdateTime(new Date());
            }
            boolean insertBatch = companyService.insertBatch(list);
            if (insertBatch) {
                return new ResponseVal(200, "success");
            } else {
                return new ResponseVal(500, "fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseVal(500, "erro");
        }
    }


}
