package com.waterelephant.controller.system;

import com.waterelephant.common.entity.SysCompany;
import com.waterelephant.common.entity.SysDepartment;
import com.waterelephant.common.entity.dto.BjuiDto;
import com.waterelephant.common.entity.dto.RemoteDto;
import com.waterelephant.common.utils.*;
import com.waterelephant.service.ManageSevice;
import com.waterelephant.service.SysCityService;
import com.waterelephant.service.SysDepartmentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门管理
 * @author wangzz
 */
@Controller
@RequestMapping("/system/dept")
public class DepartMentController {
	private Logger logger = Logger.getLogger(DepartMentController.class);
	@Autowired
	private SysDepartmentService deptService;
	@Autowired
	private ManageSevice manageSevice;
	@Autowired
	private SysCityService sysCityService;

	/**
	 * 获取架构信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getSysFrameworkPage.do")
	public String getSysFrameworkPage() throws Exception {
		return "system/sys_framework_list";
	}

	/**
	 * 获取公司page
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "getCompanyPage.do")
	public String getCompanyPage(HttpServletRequest request, ModelMap modelMap) {
		int[] pageParams = PageUtil.init(request);
		Page<SysCompany> page = new Page<>();
		page = deptService.findCompanyPage(pageParams[0], pageParams[1]);
		modelMap.put("page", page);
		return "system/sys_company";
	}

	/**
	 * 获取部门page
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "getDeptPage.do")
	public String getDeptPage(HttpServletRequest request, ModelMap modelMap) {
		String companyId= request.getParameter("companyId");
		if(null != companyId){
			modelMap.put("companyId", companyId);
		}
		int[] pageParams = PageUtil.init(request);
		Page<SysDepartment> page = new Page<>();
		page = deptService.findsByPage(companyId,pageParams[0], pageParams[1]);
		List<SysCompany> companyList = deptService.getAllCompanyInfo();
		if(!companyList.isEmpty()){
			modelMap.put("companyList", companyList);
		}
		modelMap.put("page", page);
		return "system/sys_dept";
	}


	/**
	 * 跳转到添加部门页面
	 *
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "toAdd.do")
	public String getDeptAddPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		// 获取所有的公司信息
		List<SysCompany> companyList = deptService.getAllCompanyInfo();
		if(!companyList.isEmpty()){
			modelMap.put("companyList", companyList);
		}
		return "system/sys_dept_add";
	}

	/**
	 * 跳转到添加公司页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "toAddCompany.do")
	public String getCompanyAddPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		return "system/sys_company_add";
	}

	/**
	 * 保存公司信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/saveCompany.do")
	public BjuiDto saveCompany(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BjuiDto bjuiDto = new BjuiDto();
		SysCompany sysCompany = new SysCompany();
		sysCompany.setOrgCode(request.getParameter("companyCode"));
		sysCompany.setOrgName(request.getParameter("companyName"));
		try {
			deptService.insertCompany(sysCompany);
			bjuiDto.setStatusCode("200");
			bjuiDto.setTabid("sys_department");
			bjuiDto.setCloseCurrent(true);
			// 保存到日志
			manageSevice.saveSysLog("添加公司", SystemConstant.SUCCESSFUL_OPERATION +",公司名为:"+sysCompany.getOrgName(), request);
		} catch (Exception e) {
			bjuiDto.setStatusCode("300");
			bjuiDto.setMessage("添加公司失败！");
			logger.error("添加公司失败！", e);
		}
		return bjuiDto;
	}


	/**
	 * 删除公司
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCompany.do")
	@ResponseBody
	public BjuiDto deleteCompany(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BjuiDto bjuiDto = new BjuiDto();
		Long id = Long.valueOf(request.getParameter("id"));
		try {
			deptService.deleteCompany(id);
			SysDepartment sysDepartment=deptService.findDeptByOrgId(id);
			deptService.deleteDept(sysDepartment.getId());
			bjuiDto.setStatusCode("200");
			bjuiDto.setTabid("sys_department");
			//保存log
			manageSevice.saveSysLog("删除公司", SystemConstant.SUCCESSFUL_OPERATION +",序号为:"+id, request);
		} catch (Exception e) {
			bjuiDto.setStatusCode("300");
			bjuiDto.setMessage("删除公司失败！");
			logger.error("删除公司失败！", e);
		}
		return bjuiDto;
	}

	/**
	 * 添加部门
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/saveDept.do")
	public BjuiDto saveDept(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BjuiDto bjuiDto = new BjuiDto();
		SysDepartment sysDepartment = new SysDepartment();
		sysDepartment.setDeptName(request.getParameter("deptName"));
		String companyId = request.getParameter("companyId");// 所选公司
		try {
			Long deptId = deptService.insertDept(sysDepartment);
			// 添加公司部门关系
			deptService.insertOrgDept(Long.valueOf(companyId), deptId);
			bjuiDto.setStatusCode("200");
			bjuiDto.setTabid("sys_department");
			bjuiDto.setCloseCurrent(true);
			// 保存到日志
			manageSevice.saveSysLog("添加部门", SystemConstant.SUCCESSFUL_OPERATION + sysDepartment.getDeptName(), request);
		} catch (Exception e) {
			bjuiDto.setStatusCode("300");
			bjuiDto.setMessage("添加失败！");
			logger.error("添加部门失败！", e);
		}
		return bjuiDto;
	}

	/**
	 * 删除部门
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete.do")
	@ResponseBody
	public BjuiDto deleteDepartment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BjuiDto bjuiDto = new BjuiDto();
		Long deptId = Long.valueOf(request.getParameter("id"));
		try {
			deptService.deleteDept(deptId);
			bjuiDto.setStatusCode("200");
			bjuiDto.setTabid("sys_department");
			manageSevice.saveSysLog("删除部门，ID：", SystemConstant.SUCCESSFUL_OPERATION + deptId, request);
		} catch (Exception e) {
			bjuiDto.setStatusCode("300");
			bjuiDto.setMessage("删除失败！");
			logger.error("删除部门失败！", e);
		}
		return bjuiDto;
	}

	/**
	 * 修改部门
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update.do")
	@ResponseBody
	public BjuiDto updateDepartment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BjuiDto bjuiDto = new BjuiDto();
		SysDepartment sysDepartment = new SysDepartment();
		sysDepartment.setDeptName(request.getParameter("deptName"));
		sysDepartment.setDeptCode(request.getParameter("deptCode"));
		sysDepartment.setDeptDesc(request.getParameter("deptDesc"));
		sysDepartment.setId(Long.valueOf(request.getParameter("deptId")));
		String orgId = request.getParameter("orgId");// 更新之前的所属机构ID
		String orgSelect = request.getParameter("orgSelect");// 更新之后的所属机构ID (0.表示未选择机构)
		try {
			// 修改机构部门关系
			deptService.updateOrgDept(sysDepartment, Long.valueOf(orgId), Long.valueOf(orgSelect));
			bjuiDto.setStatusCode("200");
			bjuiDto.setTabid("sys_department");
			bjuiDto.setCloseCurrent(true);
			// 保存到日志
			// saveSysLog(request, "修改部门" + sysDepartment.getDeptName()+"的信息");
			manageSevice.saveSysLog("修改部门", SystemConstant.SUCCESSFUL_OPERATION + sysDepartment.getDeptName(), request);
		} catch (Exception e) {
			bjuiDto.setStatusCode("300");
			bjuiDto.setMessage("修改失败！");
			logger.error("修改部门失败！", e);
		}
		return bjuiDto;

	}


	/**
	 * 查询所有部门
	 */
	@RequestMapping("toAll.do")
	@ResponseBody
	public List<SysDepartment> findDepartmentList() {
		return deptService.departmentAll();
	}

	/**
	 * 查询所有公司
	 * @return
	 */
	@RequestMapping("toAllCompany.do")
	@ResponseBody
	public List<SysCompany> toAllCompany(ModelMap modelMap) {
		List<SysCompany> companyList = deptService.getAllCompanyInfo();
		if(!companyList.isEmpty()){
			modelMap.put("companyList", companyList);
		}
		return companyList;
	}

	/**
	 *  跳转选择部门页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getDeptSelectPage.do")
	public String getRoleSelectPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		String companyId = null;
		String pageNo = request.getParameter("pageCurrent");
		String pageSize = request.getParameter("pageSize");
		Page<SysDepartment> page = new Page<>();
		if (pageNo == null || pageSize == null) {
			int[] pageParams = PageUtil.init(request);
			page = deptService.findsByPage(companyId,pageParams[0], pageParams[1]);
		} else {
			page = deptService.findsByPage(companyId,Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		}
		modelMap.put("page", page);
		return "system/sys_dept_select";
	}

	@RequestMapping(value = "getCompanySelectPage.do")
	public String getCompanySelectPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		String pageNo = request.getParameter("pageCurrent");
		String pageSize = request.getParameter("pageSize");
		Page<SysCompany> page = new Page<>();
		if (pageNo == null || pageSize == null) {
			int[] pageParams = PageUtil.init(request);
			page = deptService.findCompanyPage(pageParams[0], pageParams[1]);
		} else {
			page = deptService.findCompanyPage(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		}
		modelMap.put("page", page);
		return "system/sys_company_select";
	}

	/**
	 * 验证部门名称唯一性
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "validateDeptName.do")
	@ResponseBody
	public RemoteDto validateDeptName(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		RemoteDto remoteDto = new RemoteDto();
		try {
			String deptName = request.getParameter("deptName");
			SysDepartment sysDepartment = deptService.findSysDepartmentByDeptName(deptName);
			if (CommUtils.isNull(sysDepartment)) {
				remoteDto.setOk("");
			} else {
				remoteDto.setError("该部门名已使用");
			}
		} catch (Exception e) {
			e.printStackTrace();
			remoteDto.setError("系统异常，请稍后再试");
		}
		return remoteDto;
	}

	/**
	 * 验证公司名称唯一性
	 *
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "validateCompanyName.do")
	@ResponseBody
	public RemoteDto validateCompanyName(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		RemoteDto remoteDto = new RemoteDto();
		try {
			String companyName = request.getParameter("companyName");
			SysCompany sysCompany = deptService.findCompanyByCompanyName(companyName);
			if (CommUtils.isNull(sysCompany)) {
				remoteDto.setOk("");
			} else {
				remoteDto.setError("该公司名已使用");
			}
		} catch (Exception e) {
			e.printStackTrace();
			remoteDto.setError("系统异常，请稍后再试");
		}
		return remoteDto;
	}


	/**
	 * 查询指定公司所在的部门
	 * @return
	 */
	@RequestMapping("selectDeptInfo.do")
	@ResponseBody
	public List<Map<String,Object>> selectDeptInfo(HttpServletRequest request, ModelMap modelMap) {
		String companyId = request.getParameter("companyId");
		List<Map<String,Object>> mapList = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
        map1.put("label", "--请选择--");
        map1.put("value", "");
        mapList.add(map1);
        List<SysDepartment> allDeptList = deptService.getAllDeptList();
        if(!allDeptList.isEmpty() && StringUtil.isNotEmpty(companyId)){
            Page<SysDepartment> sysDepartmentPage = deptService.findsByPage(companyId, 1, allDeptList.size());
            if (sysDepartmentPage != null && sysDepartmentPage.getResult() != null) {
                for(SysDepartment sysDepartment : sysDepartmentPage.getResult()) {
                    Map<String,Object> map = new HashMap<>();
                    map.put("label",sysDepartment.getDeptName());
                    map.put("value",sysDepartment.getId());
                    mapList.add(map);
                }
            }
        }
		return mapList;
	}

}
