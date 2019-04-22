package com.waterelephant.controller.system;

import com.waterelephant.common.entity.*;
import com.waterelephant.common.entity.dto.BjuiDto;
import com.waterelephant.common.entity.dto.RuleDto;
import com.waterelephant.common.exception.BusException;
import com.waterelephant.common.utils.*;
import com.waterelephant.service.ManageSevice;
import com.waterelephant.service.SysDepartmentService;
import com.waterelephant.service.SysUserService;
import com.waterelephant.service.SysUuidService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/system/user/")
public class RegisterController {
	private Logger logger = Logger.getLogger(RegisterController.class);
	@Autowired
	protected SysUserService sysUserService;
	@Autowired
	protected ManageSevice manageSevice;
	@Autowired
	private SysDepartmentService deptService;
	@Autowired
	private SysUuidService uuidService;

	/**
	 * 跳转到登录页面
	 *
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "login.do")
	public String login(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String uri = request.getRequestURI();
		if (uri.indexOf("/system/user/login.do") > 0) {
			request.getSession().removeAttribute(SystemConstant.SESSION_USER);
			request.getSession().removeAttribute("lydsh");
			request.getSession().removeAttribute(SystemConstant.SESSION_ORG);
			request.getSession().removeAttribute(SystemConstant.SESSION_DEPT);
			request.getSession().removeAttribute("ruleCodes");
			request.getSession().removeAttribute("rules");
			request.getSession().removeAttribute("user_uuid");
		}
		return "login";
	}

	@RequestMapping(value = "loginTimeOut.do")
	public String loginTimeout(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		HttpSession session = request.getSession();
		session.removeAttribute(SystemConstant.SESSION_USER);
		session.removeAttribute("user_uuid");
		session.removeAttribute(SystemConstant.SESSION_ORG);
		session.removeAttribute(SystemConstant.SESSION_DEPT);
		session.removeAttribute("ruleCodes");
		session.removeAttribute("rules");
		session.removeAttribute("lydsh");
		return "login_timeout";
	}

	/**
	 * 首页信息
	 *
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "index.do")
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		try {
			String username = new String(request.getParameter("username").getBytes("iso-8859-1"), "utf-8");
			String password = request.getParameter("password");
			String verify = request.getParameter("verify");
			String phoneNote = request.getParameter("phoneNote");
			String message = request.getParameter("message");
			String phoneCode = request.getParameter("phone");
			String verifyCode = request.getParameter("verifyCode");
			String ip = CommUtils.getIpAddr(request);//获得客户端Ip
			if (verifyCode == null) {
				SysUser sysUser = (SysUser) request.getSession().getAttribute(SystemConstant.SESSION_USER);
				if (sysUser == null) {
					//判断Ip是否为白名单如果为白名单则跳转到login页面，如果不是则跳转到phoneLogin
					return "login";
				}
				return "index";
			}
			boolean checkVc = verifyCode
					.equalsIgnoreCase((String) request.getSession().getAttribute(SystemConstant.SESSION_VC));
			if ("0".equals(verify) && phoneNote.length() > 0 && "null".equals(message)) {
				boolean phoneVerdict = phoneCode.equalsIgnoreCase((String) request.getSession().getAttribute("logIn"));
				if (!phoneVerdict) {
					modelMap.put("errorMsg", "手机验证码错误！");
					return "login";
				}
				request.getSession().removeAttribute("logIn");
			}
			if (!checkVc) {
				modelMap.put("errorMsg", "验证码错误！");
				return "login";
			}
			List<SysUser> user = sysUserService.findUserByLoginName(username);
			boolean checkPassword = (!CommUtils.isNull(user))
					&& user.get(0).getPassword().equals(CommUtils.getMD5(password.getBytes()));
			if (checkPassword) {
				if (!user.get(0).getStatus().equals(1)) {
					modelMap.put("errorMsg", "该用户已被禁用！");
					return "login";
				}
				SysUser su = user.get(0);
				HttpSession session = request.getSession();
				session.setAttribute(SystemConstant.SESSION_USER, su);
				String uuid = CommUtils.getUUID();
				session.setAttribute("user_uuid", uuid);
//				RedisUtils.set("user:uuid:" + su.getId(), uuid);
				uuidService.insert(su.getId(),uuid);
				manageSevice.saveSysLog("用户登录", SystemConstant.SUCCESSFUL_OPERATION + ",登陆的用户为:" + username, request);
				String superadmin = sysUserService.queryOrg(username);
				logger.info("------用户角色的Id为" + superadmin + "-----");
				if (superadmin == null) {
					// 根据用户编号查询用户所属部门
					SysDepartment sysDepartment = deptService.findDeptByUserId(user.get(0).getId());
					// request.setAttribute(SystemConstant.SESSION_DEPT,
					// sysDepartment.getDeptName());
					session.setAttribute(SystemConstant.SESSION_DEPT, sysDepartment);
					// 根据部门查询所属机构
					if (sysDepartment == null) {
						session.removeAttribute(SystemConstant.SESSION_ORG);
					} else {
						SysOrganization sysOrganization = manageSevice.findByDeptId(sysDepartment.getId());
						// request.setAttribute(SystemConstant.SESSION_ORG,
						// sysOrganization.getOrgName());
						session.setAttribute(SystemConstant.SESSION_ORG, sysOrganization);
					}

				} else {
					session.removeAttribute(SystemConstant.SESSION_DEPT);
					session.removeAttribute(SystemConstant.SESSION_ORG);
				}
				List<SysRule> roles = null;
				try {
					roles = sysUserService.queryRole(user.get(0).getId());
				} catch (BusException e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				}
				Set<RuleDto> rules = new HashSet<RuleDto>();
				List<RuleDto> firstRules = new ArrayList<RuleDto>();
				Set<String> ruleCodes = new HashSet<String>();
				if (!CommUtils.isNull(roles)) {
					for (int i = 0; i < roles.size(); i++) {
						if (roles.get(i) != null) {
							if (roles.get(i).getRuleType() == 2) {
								if ("lydsh".equals(roles.get(i).getRuleCode())) {
									request.getSession().setAttribute("lydsh", 1);
								}
								ruleCodes.add(roles.get(i).getRuleCode());
							} else if (roles.get(i).getRuleType().equals(1)) {
								RuleDto ruleDto = new RuleDto();
								ruleDto.setId(roles.get(i).getId());
								ruleDto.setParentId(roles.get(i).getParentId());
								ruleDto.setName(roles.get(i).getRuleName());
								ruleDto.setIcon(roles.get(i).getRulePicture());
								ruleDto.setPriority(roles.get(i).getRuleOrder());
								ruleDto.setTabid(roles.get(i).getRuleExt());
								ruleDto.setUrl(roles.get(i).getRuleUrl());
								rules.add(ruleDto);
								if (roles.get(i).getParentId().equals(0L)) {
									firstRules.add(ruleDto);
								}
							}
						}
					}
				}
				for (Iterator<RuleDto> it = firstRules.iterator(); it.hasNext(); ) {
					RuleDto r = it.next();
					List<RuleDto> children = new ArrayList<RuleDto>();
					for (RuleDto rule : rules) {
						if (rule.getParentId().equals(r.getId())) {
							children.add(rule);
						}
					}
					Collections.sort(children);
					r.setChildren(children);
				}
				Collections.sort(firstRules);
				session.setAttribute("ruleCodes", ruleCodes);
				session.setAttribute("rules", firstRules);
				modelMap.addAttribute("user", user.get(0));
				modelMap.addAttribute("rules", firstRules);
				// saveSysLog(request, "登录系统");

				return "index";
			} else {
				modelMap.put("errorMsg", "用户名或者密码错误！");
				return "login";
			}
		} catch (Exception e) {
			logger.info("登录失败！", e);
		}
		return "login";
	}

	/**
	 * 首页信息
	 *
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "indexTimeOut.do", method = RequestMethod.POST)
	@ResponseBody
	public BjuiDto indexTimeOut(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		BjuiDto bjuiDto = new BjuiDto();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		List<SysUser> user = sysUserService.findUserByLoginName(username);
		boolean checkPassword = (!CommUtils.isNull(user))
				&& user.get(0).getPassword().equals(CommUtils.getMD5(password.getBytes()));
		if (checkPassword) {
			if (!user.get(0).getStatus().equals(1)) {
				bjuiDto.setStatusCode("300");
				bjuiDto.setMessage("该用户已被禁用！");
				return bjuiDto;
			}
			SysUser su = user.get(0);
			HttpSession session = request.getSession();
			session.setAttribute(SystemConstant.SESSION_USER, su);
			String uuid = CommUtils.getUUID();
			session.setAttribute("user_uuid", uuid);
//			RedisUtils.set("user:uuid:" + su.getId(), uuid);
			uuidService.insert(su.getId(),uuid);
			manageSevice.saveSysLog("用户登录", SystemConstant.SUCCESSFUL_OPERATION + ",登陆的用户为:" + username, request);
			String superadmin = sysUserService.queryOrg(username);
			logger.info("------用户角色的Id为" + superadmin + "-----");
			if (superadmin == null) {
				// 根据用户编号查询用户所属部门
				SysDepartment sysDepartment = deptService.findDeptByUserId(user.get(0).getId());
				// request.setAttribute(SystemConstant.SESSION_DEPT,
				// sysDepartment.getDeptName());
				session.setAttribute(SystemConstant.SESSION_DEPT, sysDepartment);
				// 根据部门查询所属机构
				if (sysDepartment == null) {
					session.removeAttribute(SystemConstant.SESSION_ORG);
				} else {
					SysOrganization sysOrganization = manageSevice.findByDeptId(sysDepartment.getId());
					// request.setAttribute(SystemConstant.SESSION_ORG,
					// sysOrganization.getOrgName());
					session.setAttribute(SystemConstant.SESSION_ORG, sysOrganization);
				}

			} else {
				session.removeAttribute(SystemConstant.SESSION_DEPT);
				session.removeAttribute(SystemConstant.SESSION_ORG);
			}
			List<SysRule> roles = null;
			try {
				roles = sysUserService.queryRole(user.get(0).getId());
			} catch (BusException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			Set<RuleDto> rules = new HashSet<RuleDto>();
			List<RuleDto> firstRules = new ArrayList<RuleDto>();
			Set<String> ruleCodes = new HashSet<String>();
			if (!CommUtils.isNull(roles)) {
				for (int i = 0; i < roles.size(); i++) {
					if (roles.get(i) != null) {
						if (roles.get(i).getRuleType() == 2) {
							ruleCodes.add(roles.get(i).getRuleCode());
						} else if (roles.get(i).getRuleType().equals(1)) {
							RuleDto ruleDto = new RuleDto();
							ruleDto.setId(roles.get(i).getId());
							ruleDto.setParentId(roles.get(i).getParentId());
							ruleDto.setName(roles.get(i).getRuleName());
							ruleDto.setIcon(roles.get(i).getRulePicture());
							ruleDto.setPriority(roles.get(i).getRuleOrder());
							ruleDto.setTabid(roles.get(i).getRuleExt());
							ruleDto.setUrl(roles.get(i).getRuleUrl());
							rules.add(ruleDto);
							if (roles.get(i).getParentId().equals(0L)) {
								firstRules.add(ruleDto);
							}
						}
					}
				}
			}
			for (Iterator<RuleDto> it = firstRules.iterator(); it.hasNext();) {
				RuleDto r = it.next();
				List<RuleDto> children = new ArrayList<RuleDto>();
				for (RuleDto rule : rules) {
					if (rule.getParentId().equals(r.getId())) {
						children.add(rule);
					}
				}
				Collections.sort(children);
				r.setChildren(children);
			}
			Collections.sort(firstRules);
			session.setAttribute("ruleCodes", ruleCodes);
			session.setAttribute("rules", firstRules);
			bjuiDto.setStatusCode("200");
			bjuiDto.setMessage("登录成功！");
			bjuiDto.setCloseCurrent(true);
			// saveSysLog(request, "登录系统");
		} else {
			bjuiDto.setStatusCode("300");
			bjuiDto.setMessage("用户名或者密码错误！");
		}
		return bjuiDto;
	}

	/**
	 * 登陆验证码生成
	 */
	@RequestMapping(value = "getVerifyCode.do")
	public void getVerifyCode(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		request.getSession().setAttribute(SystemConstant.SESSION_VC, verifyCode);
		ServletOutputStream os = response.getOutputStream();
		VerifyCodeUtils.outputImage(150, 35, os, verifyCode);
		os.flush();
		os.close();
	}

	@RequestMapping(value = "forwardMainPage.do")
	public String forwardMainPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		return "index_main";
	}

	/**
	 * 获取主菜单树
	 *
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getMenuTree.do")
	@ResponseBody
	public List<TreeModel> getMenuTree(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		Set<Long> ruleIds = (Set<Long>) request.getSession().getAttribute("ruleIds");
		// List<TreeModel> treeList = sysRuleService.findRuleTree(false);
		request.getSession().removeAttribute("ruleIds");
		return null;
	}

	/**
	 * 修改密码
	 *
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = " getChangePwdPage.do")
	public String getChangePwdPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		return "changepwd";
	}

	/**
	 * 实现修改密码
	 *
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "changePwd.do")
	@ResponseBody
	public BjuiDto changePwd(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		BjuiDto bjuiDto = new BjuiDto();
		try {
			String id = request.getParameter("userId");
			String oldPwd = request.getParameter("oldPassword");
			SysUser user = sysUserService.findUserByKey(Long.parseLong(id));
			if (CommUtils.getMD5(oldPwd.getBytes()).equals(user.getPassword())) {
				String newPwd = request.getParameter("newPassword");
				user.setPassword(CommUtils.getMD5(newPwd.getBytes()));
				sysUserService.updateUser(user);
				bjuiDto.setStatusCode("200");
				bjuiDto.setMessage("修改成功！");
				bjuiDto.setCloseCurrent(true);
				manageSevice.saveSysLog("修改密码", SystemConstant.SUCCESSFUL_OPERATION, request);
			} else {
				bjuiDto.setStatusCode("300");
				bjuiDto.setMessage("旧密码错误！");
			}
		} catch (Exception e) {
			manageSevice.saveSysLog("修改密码", SystemConstant.OPERATION_FAILURE + e.getMessage(), request);
			bjuiDto.setStatusCode("300");
			bjuiDto.setMessage("修改失败！");
		}
		return bjuiDto;

	}

}