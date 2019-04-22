
package com.waterelephant.controller.system;

import com.waterelephant.common.entity.SysRule;
import com.waterelephant.common.entity.dto.BjuiDto;
import com.waterelephant.common.entity.dto.RemoteDto;
import com.waterelephant.common.utils.CommUtils;
import com.waterelephant.common.utils.SystemConstant;
import com.waterelephant.common.utils.TreeModel;
import com.waterelephant.service.ManageSevice;
import com.waterelephant.service.SysRuleService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 权限管理
 *
 * @author HWang
 */
@Controller
@RequestMapping("/system/rule/")
public class SysRuleController {

	private Logger logger = Logger.getLogger(SysRuleController.class);
	
	@Autowired
	private SysRuleService sysRuleService;
	
	@Autowired
	private ManageSevice manageSevice;
	
	/**
	 * 权限页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getRulePage.do")
	public String getRulePage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		List<TreeModel> treeList = sysRuleService.findRuleTree(false);
		modelMap.put("treeList", treeList);
		return "system/sys_rule";
	}

	/**
	 * 根据父ID查询权限列表
	 *
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "findRuleListByParentId.do")
	public String findRuleListByParentId(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		String parentId = request.getParameter("pId");
		SysRule parent = sysRuleService.findRuleById(Long.valueOf(parentId));
		List<SysRule> ruleList = sysRuleService.findRuleListByParentId(Long.parseLong(parentId));
		for (SysRule sysRule : ruleList) {
			sysRule.setParent(parent);
		}
		modelMap.put("ruleList", ruleList);
		modelMap.put("pId", parentId);
		return "system/sys_rule_list";
	}
/*
	/**
	 * 跳转添加权限页面
	 *
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getRuleAddPage.do")
	public String getRuleAddPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		String parentId = request.getParameter("pId");
		if ("0".equals(parentId)) {
			modelMap.put("parentName", "根目录");
		} else {
			SysRule rule = sysRuleService.findRuleById(Long.parseLong(parentId));
			modelMap.put("parentName", rule.getRuleName());
		}
		modelMap.put("pId", parentId);
		return "system/sys_rule_add";
	}

	/**
	 * 跳转编辑权限页面
	 *
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getRuleEditPage.do")
	public String getRuleEditPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		String id = request.getParameter("id");
		String parentId = request.getParameter("pId");
		SysRule rule = sysRuleService.findRuleById(Long.parseLong(id));
		//SysRule parent = rule.getParent();
		if ("0".equals(parentId)) {
			modelMap.put("parentName", "根目录");
		} else {
			modelMap.put("parentName", rule.getRuleName());
		}
		modelMap.put("rule", rule);
		return "system/sys_rule_edit";
	}

	/**
	 * 验证权限代码唯一性
	 *
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "validateRuleCode.do")
	@ResponseBody
	public RemoteDto validateRuleCode(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		String ruleCode = request.getParameter("ruleCode");
		List rule = sysRuleService.findRuleByCode(ruleCode);
		RemoteDto remoteDto = new RemoteDto();
		if (rule.size()>0) {
			remoteDto.setOk("");
		} else {
			remoteDto.setError("该权限代码已使用");
		}
		return remoteDto;
	}

	/**
	 * 新增权限
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "save.do")
	@ResponseBody
	public BjuiDto saveRule(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BjuiDto bjuiDto = new BjuiDto();
		try {
			String ruleCode = request.getParameter("ruleCode");
			String ruleName = request.getParameter("ruleName");
			String ruleType = request.getParameter("ruleType");
			String parentId = request.getParameter("pId");
			String ruleDesc = request.getParameter("ruleDesc");
			String rulePicture = request.getParameter("rulePicture");
			String ruleExt = request.getParameter("ruleExt");
			String ruleUrl = request.getParameter("ruleUrl");
			String ruleOrder = request.getParameter("ruleOrder");
			//SysRule parent = sysRuleService.findRuleById(Long.parseLong(parentId));
			SysRule rule = new SysRule();
			rule.setParentId(Long.valueOf(parentId));
			rule.setRuleCode(ruleCode);
			rule.setRuleDesc(ruleDesc);
			rule.setRuleExt(ruleExt);
			rule.setRuleName(ruleName);
			rule.setRuleOrder(Integer.parseInt(ruleOrder));
			rule.setRulePicture(rulePicture);
			rule.setRuleType(StringUtils.isBlank(ruleType) ? 1 : Integer.parseInt(ruleType));
			rule.setRuleUrl(ruleUrl);
			sysRuleService.saveRule(rule);
			bjuiDto.setStatusCode("200");
			bjuiDto.setMessage("添加成功！");
			bjuiDto.setTabid("sys_rule");
			bjuiDto.setCloseCurrent(true);
			//保存到日志
			manageSevice.saveSysLog("添加权限", SystemConstant.SUCCESSFUL_OPERATION+",添加权限名为:"+ruleName, request);
		} catch (Exception e) {
			bjuiDto.setStatusCode("300");
			bjuiDto.setMessage("添加失败！");
			logger.error("添加权限失败！", e);
		}
		return bjuiDto;
	}

	/**
	 * 编辑权限
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "update.do")
	@ResponseBody
	public BjuiDto updateRule(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BjuiDto bjuiDto = new BjuiDto();
		try {
			String ruleId = request.getParameter("ruleId");
			String ruleCode = request.getParameter("ruleCode");
			String ruleName = request.getParameter("ruleName");
			String ruleType = request.getParameter("ruleType");
			String ruleDesc = request.getParameter("ruleDesc");
			String rulePicture = request.getParameter("rulePicture");
			String ruleExt = request.getParameter("ruleExt");
			String ruleUrl = request.getParameter("ruleUrl");
			String ruleOrder = request.getParameter("ruleOrder");
			//SysRule rule = sysRuleService.findRuleById(Long.parseLong(ruleId));
			SysRule rule = new SysRule();
			rule.setParentId(Long.valueOf(request.getParameter("parentId")));
			rule.setId(Long.valueOf(ruleId));
			rule.setRuleCode(ruleCode);
			rule.setRuleDesc(ruleDesc);
			rule.setRuleExt(ruleExt);
			rule.setRuleName(ruleName);
			rule.setRuleOrder(Integer.parseInt(ruleOrder));
			rule.setRulePicture(rulePicture);
			rule.setRuleType(StringUtils.isBlank(ruleType) ? 1 : Integer.parseInt(ruleType));
			rule.setRuleUrl(ruleUrl);
			sysRuleService.updateRule(rule);
			bjuiDto.setStatusCode("200");
			bjuiDto.setCloseCurrent(true);
			bjuiDto.setTabid("sys_rule");
			//保存到日志
			manageSevice.saveSysLog("更新权限", SystemConstant.SUCCESSFUL_OPERATION+",权限编号为："+ruleId, request);
		} catch (Exception e) {
			bjuiDto.setStatusCode("300");
			bjuiDto.setMessage("编辑失败！");
		}
		return bjuiDto;
	}

	/**
	 * 删除权限
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteRule.do")
	@ResponseBody
	public BjuiDto deleteRule(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BjuiDto bjuiDto = new BjuiDto();
		try {
			String id = request.getParameter("id");
			List<SysRule> ruleList = sysRuleService.findRuleListByParentId(Long.parseLong(id));
			if (!CommUtils.isNull(ruleList)) {
				bjuiDto.setStatusCode("300");
				bjuiDto.setMessage("请先删除子权限！");
			} else {
				sysRuleService.deleteRule(Long.parseLong(id));
				bjuiDto.setStatusCode("200");
				bjuiDto.setTabid("sys_rule");
				//保存到日志
				manageSevice.saveSysLog("删除权限", SystemConstant.SUCCESSFUL_OPERATION+",权限编号为："+id, request);
			}
		} catch (Exception e) {
			bjuiDto.setStatusCode("300");
			bjuiDto.setMessage("删除失败！");
		}
		return bjuiDto;
	}

}
