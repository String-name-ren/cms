package com.waterelephant.controller.system;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waterelephant.common.entity.SysRole;
import com.waterelephant.common.entity.SysRoleRule;
import com.waterelephant.common.entity.SysRule;
import com.waterelephant.common.entity.dto.BjuiDto;
import com.waterelephant.common.utils.CommUtils;
import com.waterelephant.common.utils.Page;
import com.waterelephant.common.utils.PageUtil;
import com.waterelephant.common.utils.SystemConstant;
import com.waterelephant.common.utils.TreeModel;
import com.waterelephant.service.ManageSevice;
import com.waterelephant.service.SysRoleService;
import com.waterelephant.service.SysRuleService;

/**
 * 角色管理
 * 
 * @author WEIHU
 */
@Controller
@RequestMapping("/system/role")
public class SysRoleController {
	
	private Logger logger = Logger.getLogger(SysRoleController.class);
	
	@Autowired
	private SysRoleService roleService;

	@Autowired
	private ManageSevice manageSevice;
	
	@Autowired
	private SysRuleService sysRuleService;
	/**
	 * 添加角色
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/save.do")
	public BjuiDto saveDepartment(HttpServletRequest request, HttpServletResponse response)throws Exception{
		BjuiDto bjuiDto = new BjuiDto();
		SysRole sysRole = new SysRole();
		sysRole.setRoleName(request.getParameter("roleName"));
		sysRole.setRoleCode(request.getParameter("roleCode"));
		sysRole.setRoleDesc(request.getParameter("roleDesc"));
		String ruleIds = request.getParameter("ruleIds");
		try {
			Set<SysRule> ruleSet = new HashSet<SysRule>();
			if (!CommUtils.isNull(ruleIds)) {
				String[] ids = ruleIds.split(",");
				for (String ruleId : ids) {
					SysRule rule = new SysRule();
					rule.setId(Long.parseLong(ruleId));
					ruleSet.add(rule);
				}
				//sysRole.setRules(ruleSet);
			}
			Long roleId = roleService.insertRole(sysRole);
			roleService.insertRoleRule(roleId,ruleSet);
			//roleService.insertRole(sysRole);
			bjuiDto.setStatusCode("200");
			bjuiDto.setTabid("sys_role");
			bjuiDto.setCloseCurrent(true);
			//保存到日志
			manageSevice.saveSysLog("添加角色", SystemConstant.SUCCESSFUL_OPERATION+sysRole.getRoleName(), request);
		} catch (Exception e) {
			bjuiDto.setStatusCode("300");
			bjuiDto.setMessage("添加失败！");
			logger.error("添加角色失败！", e);
		}
		return bjuiDto;
	}
	/**
	 * 删除角色
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete.do")
	@ResponseBody
	public BjuiDto deleteDepartment(HttpServletRequest request, HttpServletResponse response) throws Exception{
		BjuiDto bjuiDto = new BjuiDto();
		Long roleId  = Long.valueOf(request.getParameter("id"));
		//Long roleId = 3l;
		try {
			roleService.deleteRole(roleId);
			bjuiDto.setStatusCode("200");
			bjuiDto.setTabid("sys_role");
			//保存到日志
			//saveSysLog(request, "删除角色，ID：" + roleId);
			manageSevice.saveSysLog("删除角色，ID：", SystemConstant.SUCCESSFUL_OPERATION+roleId, request);
		} catch (Exception e) {
			bjuiDto.setStatusCode("300");
			bjuiDto.setMessage("删除失败！");
			logger.error("删除角色失败！", e);
		}
		return bjuiDto;
	}

	/**
	 * 修改角色
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update.do")
	@ResponseBody
	public BjuiDto updateDepartment(HttpServletRequest request, HttpServletResponse response) throws Exception{
		BjuiDto bjuiDto = new BjuiDto();
		SysRole sysRole = new SysRole();
		sysRole.setRoleName(request.getParameter("roleName"));
		sysRole.setRoleCode(request.getParameter("roleCode"));
		sysRole.setRoleDesc(request.getParameter("roleDesc"));
		String roleId = request.getParameter("roleId");
		String ruleIds = request.getParameter("ruleIds");
		sysRole.setRoleDesc(request.getParameter("roleDesc"));
		sysRole.setId(Long.parseLong(roleId));
		try {
			roleService.updateRole(sysRole);
			//先清除角色的权限
			roleService.delete(Long.valueOf(roleId));
			//添加角色
			if (!CommUtils.isNull(ruleIds)) {
				String[] ids = ruleIds.split(",");
				SysRoleRule sysRoleRule = null;
				for (String ruleId : ids) {
					sysRoleRule = new SysRoleRule(Long.valueOf(roleId),Long.valueOf(ruleId));
					roleService.insert(sysRoleRule);
				}
			}
			bjuiDto.setStatusCode("200");
			bjuiDto.setTabid("sys_role");
			bjuiDto.setCloseCurrent(true);
			manageSevice.saveSysLog("修改角色", SystemConstant.SUCCESSFUL_OPERATION+sysRole.getRoleName(), request);
		} catch (Exception e) {
			bjuiDto.setStatusCode("300");
			bjuiDto.setMessage("修改失败！");
			logger.error("修改角色失败！", e);
		}
		return bjuiDto;	
	}
	/**
	 * 获取角色信息列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findsByPage.do")
	public String getDepartment(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap){
		String pageNo = request.getParameter("pageCurrent");
		String pageSize = request.getParameter("pageSize");
		Page<SysRole> page = new Page<>();
		if (pageNo == null || pageSize == null) {
			int[] pageParams = PageUtil.init(request);
			page = roleService.findsSysRole(pageParams[0],pageParams[1]); 
		}else{
			page = roleService.findsSysRole(Integer.parseInt(pageNo),Integer.parseInt(pageSize)); 
		}
		modelMap.put("page", page);
		//modelMap.put("ruleCodes", request.getSession().getAttribute("ruleCodes"));
		return "system/sys_role";
	}
	/**
	 * 跳转到添加角色页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "toAdd.do")
	public String getRoleAddPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		/**
		 * 获取权限（添加角色时使用）
		 */
	    List<TreeModel> treeList = sysRuleService.findRuleTree(false);
		modelMap.put("treeList", treeList);
		return "system/sys_role_add";
	}
	/**
	 * 跳转到编辑角色页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "toUpdate.do")
	public String getRoleUpdatePage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
//		String roleId = request.getParameter("id");
//		SysRole role = roleService.findById(Long.parseLong(roleId));
//		List<TreeModel> treeList = sysRuleService.findRuleTree(false);
//		//根据角色id查询该角色拥有的权限集合
//		List<SysRule> sysRules =  roleService.roleIdByRules(Long.valueOf(roleId));
//		modelMap.put("treeList", treeList);
//		modelMap.put("role", role);
//		return "system/sys_role_edit";
		String roleId = request.getParameter("id");
		SysRole role = roleService.findById(Long.parseLong(roleId));
		modelMap.put("role", role);
		Set<Long> ruleIdSet = null;
		List<SysRule> ruleSet =  roleService.roleIdByRules(Long.valueOf(roleId));
		if (!CommUtils.isNull(ruleSet)) {
			ruleIdSet = new HashSet<Long>();
			for (SysRule rule : ruleSet) {
				ruleIdSet.add(rule.getId());
			}
		}
		List<TreeModel> treeList = sysRuleService.findRuleTree(ruleIdSet);
		modelMap.put("treeList", treeList);
		return "system/sys_role_edit";
	}
	/**
	 * 添加操作日志
	 * 
	 * @param request
	 * @param description
	 */
	/*private void saveSysLog(HttpServletRequest request, String description) {
		SysDepartment sysDepartment = (SysDepartment) request.getSession().getAttribute(SystemConstant.SESSION_USER);
		SysLog log = new SysLog();
		log.setOperateTime(new Date());
		log.setOperator(user.getLoginName());
		log.setDescription(description);
		log.setOperateResult("操作成功");
		log.setIp(CommUtils.getIpAddr(request));
		sysLogService.saveLog(log);
	}*/
	
	
	@RequestMapping("/findAll.do")
	@ResponseBody
	public List<SysRole> getRoles(){
		return roleService.sysRoleAll();
	}	
	
	/**
	 * 跳转选择角色页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getRoleSelectPage.do")
	public String getRoleSelectPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		String pageNo = request.getParameter("pageCurrent");
		String pageSize = request.getParameter("pageSize");
		Page<SysRole> page = new Page<>();
		if (pageNo == null || pageSize == null) {
			int[] pageParams = PageUtil.init(request);
			page = roleService.findsSysRole(pageParams[0],pageParams[1]); 
		}else{
			page = roleService.findsSysRole(Integer.parseInt(pageNo),Integer.parseInt(pageSize)); 
		}
		modelMap.put("page", page);
		return "system/sys_role_select";
	}
}
