package com.waterelephant.controller.system;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.waterelephant.common.entity.SysDepartment;
import com.waterelephant.common.entity.SysRole;
import com.waterelephant.common.entity.SysUser;
import com.waterelephant.common.entity.dto.BjuiDto;
import com.waterelephant.common.entity.dto.RemoteDto;
import com.waterelephant.common.utils.CommUtils;
import com.waterelephant.common.utils.Page;
import com.waterelephant.common.utils.PageUtil;
import com.waterelephant.common.utils.SystemConstant;
import com.waterelephant.service.IUserService;
import com.waterelephant.service.ManageSevice;
import com.waterelephant.service.SysDepartmentService;
import com.waterelephant.service.SysRoleService;

@Controller
@RequestMapping("/system/user")
public class UserController {

    private Logger logger = Logger.getLogger(UserController.class);
    @Autowired
    private SysDepartmentService deptService;

    @Autowired
    private IUserService userService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private ManageSevice manageSevice;

    @Autowired
    private SysDepartmentService DepartmentService;

    /**
     * 分页查询用户列表
     *
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "findUserByCriteriaAndPage.do")
    public String findUserByCriteriaAndPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
            throws Exception {
        String realName = request.getParameter("realName");
        String status = request.getParameter("status");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        SysUser user = new SysUser();
        user.setRealName(realName);
        if (!StringUtils.isBlank(status)) {
            user.setStatus(Integer.parseInt(status));
        }
        if (!StringUtils.isBlank(beginTime)) {
            user.setCreateTime(CommUtils.convertStringToDate(beginTime, SystemConstant.YMD_HMS));
        }
        if (!StringUtils.isBlank(endTime)) {
            user.setUpdateTime(CommUtils.convertStringToDate(endTime, SystemConstant.YMD_HMS));
        }
        int[] pageParams = PageUtil.init(request);
        Page<SysUser> page = userService.findSysUser(user, pageParams[0], pageParams[1], beginTime, endTime);
        modelMap.put("page", page);
        modelMap.put("u", user);
        modelMap.put("beginTime", beginTime);
        modelMap.put("endTime", endTime);
        return "system/sys_user";
    }

    /**
     * 跳转到添加用户页面
     *
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getUserAddPage.do")
    public String getUserAddPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
            throws Exception {
//        List<SysCompany> companyList = deptService.getAllCompanyInfo();
//        if(!companyList.isEmpty()){
//            modelMap.put("companyList", companyList);
//        }
        return "system/sys_user_add";
    }

    /**
     * 跳转到用户编辑页面
     *
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getUserEditPage.do")
    public String getUserEditPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
            throws Exception {
        String id = request.getParameter("id");
        SysUser user = userService.findUserByKey(Long.parseLong(id));
        //查询用户拥有的角色
        List<SysRole> roleSet = sysRoleService.findRoleByUserId(user.getId());
        //查询用户拥有的部门
        SysDepartment sysDepartment = DepartmentService.findDeptByUserId(user.getId());
        //查询用户拥有的公司
//        SysCompany sysCompany = DepartmentService.findOrgByDeptId(sysDepartment.getId());
        String deptName = "";
        if (sysDepartment == null) {
            deptName = "武汉总部";
        } else {
            deptName = sysDepartment.getDeptName();
        }
        StringBuilder roleIds = null;
        StringBuilder roleNames = null;
        if (!CommUtils.isNull(roleSet)) {
            roleIds = new StringBuilder();
            roleNames = new StringBuilder();
            for (SysRole role : roleSet) {
                roleIds.append(role.getId()).append(",");
                roleNames.append(role.getRoleName()).append(",");
            }
            String roleId = roleIds.toString();
            roleId = roleId.substring(0, roleId.lastIndexOf(","));
            String roleName = roleNames.toString();
            roleName = roleName.substring(0, roleName.lastIndexOf(","));
            modelMap.put("roleId", roleId);
            modelMap.put("roleName", roleName);
            modelMap.put("sysDepartment", sysDepartment);
            modelMap.put("deptName", deptName);
//            modelMap.put("sysCompany",sysCompany);
        }
//        if (sysDepartment != null) {
//            List<SysCompany> allCompanyInfo = DepartmentService.getAllCompanyInfo();
//            modelMap.put("allCompanyInfo", allCompanyInfo);
//        }
        modelMap.put("user", user);
        return "system/sys_user_edit";
    }

    /**
     * 批量删除用户
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "bulkDeleteUser.do")
    @ResponseBody
    public BjuiDto bulkDeleteUser(HttpServletRequest request, HttpServletResponse response) {
        BjuiDto bjuiDto = new BjuiDto();
        try {
            String delids = request.getParameter("delids");
            //批量删除
            userService.bulkDeleteUser(delids);
            bjuiDto.setStatusCode("200");
            bjuiDto.setTabid("sys_user");
            manageSevice.saveSysLog("批量删除用户：", SystemConstant.SUCCESSFUL_OPERATION + ",删除的用户id为:" + delids, request);
        } catch (Exception e) {
            bjuiDto.setStatusCode("300");
            bjuiDto.setMessage("删除失败！");
            logger.error("删除用户失败！", e);
        }
        return bjuiDto;
    }

    /**
     * 删除用户
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "deleteUser.do")
    @ResponseBody
    public BjuiDto deleteUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BjuiDto bjuiDto = new BjuiDto();
        try {
            String id = request.getParameter("id");
            boolean success = userService.delete(Long.valueOf(id));
            if (success) {
                bjuiDto.setStatusCode("200");
                bjuiDto.setTabid("sys_user");
                manageSevice.saveSysLog("删除用户", SystemConstant.SUCCESSFUL_OPERATION + ",删除的用户编号为:" + id, request);
            }
        } catch (Exception e) {
            bjuiDto.setStatusCode("300");
            bjuiDto.setMessage("删除失败！");
            logger.error("删除用户失败！", e);
        }
        return bjuiDto;
    }

    /**
     * 验证用户登录名唯一性
     *
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "validateUserLoginName.do")
    @ResponseBody
    public RemoteDto validateUserLoginName(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
            throws Exception {
        String loginName = request.getParameter("loginName");
        SysUser user = userService.findUserByLoginName(loginName);
        RemoteDto remoteDto = new RemoteDto();
        if (CommUtils.isNull(user)) {
            remoteDto.setOk("");
        } else {
            remoteDto.setError("该登录用户名已使用");
        }
        return remoteDto;
    }

    /**
     * 添加用户
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "saveUser.do")
    @ResponseBody
    public BjuiDto saveUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BjuiDto bjuiDto = new BjuiDto();
        String passStr = "123456";//VerifyCodeUtils.generateVerifyCode(6);
        try {
            String loginName = request.getParameter("loginName");
            String realName = request.getParameter("realName");
            String email = request.getParameter("email");
            String sex = request.getParameter("sex");
            String status = request.getParameter("status");
            String phone = request.getParameter("phone");
            //角色id
            String roleIds = request.getParameter("role.id");
            //获取部门编号
            String departmentId = request.getParameter("dept.id");
            SysUser user = new SysUser();
            user.setLoginName(loginName);
            user.setRealName(realName);
            user.setPassword(CommUtils.getMD5(passStr.getBytes()));
            user.setEmail(email);
            user.setPhone(phone);
            user.setSex(StringUtils.isBlank(sex) ? 1 : Integer.parseInt(sex));
            user.setStatus(StringUtils.isBlank(status) ? 1 : Integer.parseInt(status));
            Date curDate = new Date();
            user.setCreateTime(curDate);
            //user.setUpdateTime(curDate);
            //添加用户操作
            boolean ch = userService.insertUser(user, departmentId, roleIds, passStr);
            if (ch) {
                //添加用户角色记录
                bjuiDto.setStatusCode("200");
                bjuiDto.setTabid("sys_user");
                bjuiDto.setCloseCurrent(true);
                manageSevice.saveSysLog("添加用户", SystemConstant.SUCCESSFUL_OPERATION + ",用户登录名为:" + user.getLoginName(), request);
            } else {
                bjuiDto.setStatusCode("300");
                bjuiDto.setMessage("添加失败,发送短信SDK异常！");
            }
        } catch (Exception e) {
            bjuiDto.setStatusCode("300");
            bjuiDto.setMessage("添加失败！");
            logger.error("添加用户失败！", e);
        }
        return bjuiDto;
    }

    /**
     * 编辑用户
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "updateUser.do")
    @ResponseBody
    public BjuiDto updateUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BjuiDto bjuiDto = new BjuiDto();
        try {
            String id = request.getParameter("id");
            String loginName = request.getParameter("loginName");
            String realName = request.getParameter("realName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String sex = request.getParameter("sex");
            String status = request.getParameter("status");
            //用户所属角色编号
            String roleIds = request.getParameter("role.id");
            //用户所属部门
            String departmentId = request.getParameter("dept.id");
            SysUser user = new SysUser();
            user.setId(Long.valueOf(id));
            user.setLoginName(loginName);
            user.setRealName(realName);
            user.setEmail(email);
            user.setPhone(phone);
            user.setSex(StringUtils.isBlank(sex) ? 1 : Integer.parseInt(sex));
            user.setStatus(StringUtils.isBlank(status) ? 1 : Integer.parseInt(status));
            user.setUpdateTime(new Date());
            //更新用户信息以及用户的所属部门
            System.out.println(roleIds);
            userService.updateUser(user, roleIds.toString(), departmentId);
            bjuiDto.setStatusCode("200");
            bjuiDto.setTabid("sys_user");
            bjuiDto.setCloseCurrent(true);
            manageSevice.saveSysLog("编辑用户", SystemConstant.SUCCESSFUL_OPERATION + ",编辑的用户名为:" + user.getLoginName(), request);
        } catch (Exception e) {
            bjuiDto.setStatusCode("300");
            bjuiDto.setMessage("编辑失败！");
            logger.error("编辑用户信息失败！", e);
        }
        return bjuiDto;
    }

    /**
     * 添加操作日志
     *
     * @param request
     * @param description
     */
//	private void saveSysLog(HttpServletRequest request, String description) {
//		SysUser user = (SysUser) request.getSession().getAttribute(SystemConstant.SESSION_USER);
//		SysLog log = new SysLog();
//		log.setOperateTime(new Date());
//		log.setOperator(user.getLoginName());
//		log.setDescription(description);
//		log.setOperateResult("操作成功");
//		log.setIp(CommUtils.getIpAddr(request));
//		sysLogService.saveLog(log);
//	}

}
