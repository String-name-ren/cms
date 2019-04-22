package com.waterelephant.service.impl;

import com.waterelephant.common.base.BaseService;
import com.waterelephant.common.entity.SysDeptUser;
import com.waterelephant.common.entity.SysUser;
import com.waterelephant.common.entity.SysUserRole;
import com.waterelephant.common.utils.CommUtils;
import com.waterelephant.common.utils.Page;
import com.waterelephant.common.utils.SystemConstant;
import com.waterelephant.common.vo.PageRequestVo;
import com.waterelephant.mapper.SysUserMapper;
import com.waterelephant.service.IUserService;
import com.waterelephant.service.SysDeptUserService;
import com.waterelephant.service.SysUserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends BaseService<SysUser, Long> implements IUserService {
	private Logger logger = Logger.getLogger(UserServiceImpl.class);
	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Autowired
	private SysDeptUserService sysDeptUserService;

	@Autowired
	private SysUserMapper userMapper;
	

	/*
	 * 查询所有user
	 */
	@Override
	public List<SysUser> findAll() {
		return sqlMapper.selectList("select u.* from sys_user u", SysUser.class);
	}

	/**
	 * 查询分页
	 */
	@Override
	public Page<SysUser> findSysUser(SysUser user, int pageNo, int pageSize, String beginTime, String endTime) {
		Page<SysUser> page = new Page<SysUser>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		StringBuffer sql = new StringBuffer(
				"select u.id,u.login_name,u.real_name,u.sex,u.email,u.phone,u.status,u.create_time,u.update_time,(select group_concat(role_id) from sys_user_role where user_id=u.id) password from sys_user u ");
		if (!CommUtils.isNull(user.getRealName())) {
			sql.append(" where u.real_name LIKE '%" + user.getRealName() + "%'");
		} else {
			sql.append(" where u.real_name LIKE '%%'");
		}
		if (!CommUtils.isNull(user.getStatus())) {
			sql.append(" and u.`status`=" + user.getStatus());
		}
		if (!CommUtils.isNull(user.getSex())) {
			sql.append(" and u.sex =" + user.getSex());
		}
		if (!CommUtils.isNull(beginTime)) {
			sql.append(" and left(u.create_time,10) >='" + beginTime + "'");
		}
		if (!CommUtils.isNull(endTime)) {
			sql.append(" and left(u.create_time,10) <='" + endTime + "'");
		}
		sql.append(" and u.login_name not in ('" + SystemConstant.SUPER_ADMIN + "')");
		sql.append(" order by u.create_time desc 	limit #{pageNum},#{pageSize}");
		page.setTotalCount(TotalCount(user, beginTime, endTime) - 1);
		PageRequestVo pVo = new PageRequestVo((pageNo - 1) * pageSize, pageSize);
		page.setPageNo((pageNo - 1) * pageSize);
		page.setPageSize(pageSize);
		List<SysUser> rows = sqlMapper.selectList(sql.toString(), pVo, SysUser.class);
		page.setResult(rows);
		return page;
	}

	@Override
	public Long getRowCount(SysUser user) {
		return Integer.toUnsignedLong(super.selectCount(user));
	}

	/**
	 * 添加用户
	 */
	@Override
	public boolean insertUser(SysUser user, String deptId, String roleId, String passStr) {
		// String sql = "insert into sys_user"
		// + "(login_name,real_name,password,sex,email,phone,status,create_time) VALUES"
		// + "(#{loginName},#{realName},#{password},#{sex},#{email},#{phone},#{status},#{createTime})";
		// int userResult = sqlMapper.insert(sql,user);
		// 发送短信
//		MsgReqData msgReqData = new MsgReqData();
//		msgReqData.setPhone(user.getPhone());
//		msgReqData.setMsg("【水象分期】尊敬的" + user.getLoginName() + "您在水象分期后台审核系统添加用户的密码为" + passStr + "！");
//		// 短信的环境 0 真是环境 1 测试环境
//		msgReqData.setType(String.valueOf(SystemConstant.MESSAGE_CIRCUMSTANCE));
//		Response<Object> rsp = BeadWalletSendMsgService.sendMsg(msgReqData);
//		logger.info("添加用户发送短信" + rsp.getRequestMsg());
		String message = "尊敬的" + user.getLoginName() + "您在水象分期后台审核系统添加用户的密码为" + passStr + "！";
//		boolean bo = sendMessageCommonService.commonSendMessage("1", user.getPhone(), message);
	/*	MessageDto messageDto = new MessageDto();
		messageDto.setBusinessScenario("1");
		messageDto.setPhone(user.getPhone());
		messageDto.setMsg(message);
		messageDto.setType("1");
		RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));*/
		boolean bo = true;
		boolean success = false;
		if (bo) {
//		if (rsp.getRequestCode().equals("200")) {
			// 添加用户
			int successNum1 = userMapper.insert(user);
			// 添加用户角色
			String[] splitRole = roleId.split(",");
			for (int i = 0; i < splitRole.length; i++) {
				SysUserRole role = new SysUserRole(user.getId(), Long.valueOf(splitRole[i]));
				sysUserRoleService.insert(role);
			}
			// 添加用户部门
			if (StringUtils.isNotEmpty(deptId)) {
				SysDeptUser sysDeptUser = new SysDeptUser(Long.valueOf(deptId), user.getId());
				sysDeptUserService.insert(sysDeptUser);
			} else {
				SysDeptUser sysDeptUser = new SysDeptUser(0L, user.getId());
				sysDeptUserService.insert(sysDeptUser);
			}

			if (successNum1 > 0) {
				success = true;
			}
		}

		return success;

	}

	@Override
	public SysUser findUserByLoginName(String loginName) {
		String sql = "select u.* from sys_user u where u.login_name=#{loginName}";
		List<SysUser> userList = sqlMapper.selectList(sql, loginName, SysUser.class);
		return CommUtils.isNull(userList) ? null : userList.get(0);
	}

	@Override
	public SysUser findUserByKey(Long id) {
		String sql = "select u.id,u.login_name,u.real_name,u.sex,u.phone,u.email,u.`password`,u.`status`,u.create_time"
				+ ",u.update_time from sys_user u where u.id=#{id}";
		return sqlMapper.selectOne(sql, id, SysUser.class);
	}

	public int findSysUser(SysUser user) {
		return sqlMapper.insert("insert into sys_user(login_name) values(#{loginName})", user);

	}

	@Override
	public boolean updateUser(SysUser user, String roleId, String deptId) {
		// 更新用户信息
		String sql = "update sys_user u set u.login_name=#{loginName},u.real_name=#{realName},u.email=#{email},"
				+ "u.phone=#{phone},u.sex=#{sex},u.`status`=#{status},u.update_time=#{updateTime} "
				+ "where u.id=#{id}";
		int userUpdate = sqlMapper.update(sql, user);
		// 更新用户角色信息
		String str = "delete from sys_user_role where user_id='" + user.getId() + "'";
		sqlMapper.delete(str);
		String[] splitRole = roleId.split(",");
		for (int i = 0; i < splitRole.length; i++) {
			SysUserRole sysUserRole = new SysUserRole(user.getId(), Long.valueOf(splitRole[i]));
			sysUserRoleService.update(sysUserRole);
		}

		// 更新用户部门信息
		if (StringUtils.isEmpty(deptId)) {
			deptId = "0";
		}
		SysDeptUser sysDeptUser = new SysDeptUser(Long.valueOf(deptId), user.getId());
		sysDeptUserService.update(sysDeptUser);
		if (userUpdate > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(Long id) {
		String sql = "delete from sys_user where id=#{id}";
		// 删除用户记录
		int deleteId = sqlMapper.delete(sql, id);
		// 删除用户拥有的角色
		int roleId = sysUserRoleService.delete(id);
		// 删除用户拥有的部门
		int deprId = sysDeptUserService.delete(id);
		if (deleteId > 0 && roleId > 0 && deprId > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 批量删除
	 */
	@Override
	public boolean bulkDeleteUser(String ids) {
		// 批量删除用户
		String sql = "DELETE FROM sys_user WHERE id in (" + ids + ") ";
		int result1 = sqlMapper.delete(sql);
		// 批量删除用户拥有的角色
		int result2 = sysUserRoleService.batchDeleteRole(ids);
		// 批量删除用户所属的部门信息
		int result3 = sysDeptUserService.batchDeleteDept(ids);
		if (result1 > 0 && result2 > 0 && result3 > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Integer TotalCount(SysUser user, String beginTime, String endTime) {
		StringBuffer sql = new StringBuffer("select count(id) from sys_user u ");
		if (!CommUtils.isNull(user.getRealName())) {
			sql.append(" where u.real_name LIKE '%" + user.getRealName() + "%'");
		} else {
			sql.append(" where u.real_name LIKE '%%'");
		}
		if (!CommUtils.isNull(user.getStatus())) {
			sql.append(" and u.`status`=" + user.getStatus());
		}
		if (!CommUtils.isNull(user.getSex())) {
			sql.append(" and u.sex =" + user.getSex());
		}
		if (!CommUtils.isNull(beginTime)) {
			sql.append(" and left(u.create_time,10) >='" + beginTime + "'");
		}
		if (!CommUtils.isNull(endTime)) {
			sql.append(" and left(u.create_time,10) <='" + endTime + "'");
		}
		return sqlMapper.selectOne(sql.toString(), Integer.class);
	}


}
