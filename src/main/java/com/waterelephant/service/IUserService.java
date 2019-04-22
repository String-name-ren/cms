package com.waterelephant.service;

import java.util.List;

import com.waterelephant.common.entity.SysUser;
import com.waterelephant.common.utils.Page;

public interface IUserService {

	List<SysUser> findAll();

	Page<SysUser> findSysUser(SysUser user, int pageNo, int pageSize, String beginTime, String endTime);

	Long getRowCount(SysUser user);

	/**
	 * 添加用户，同时插入用户所属部门和角色
	 */

	boolean insertUser(SysUser user, String deptId, String roleId, String passStr);

	SysUser findUserByLoginName(String loginName);

	SysUser findUserByKey(Long id);

	/**
	 * 更新用户信息，以及用户的角色、部门
	 */
	boolean updateUser(SysUser user, String roleId, String deptId);

	boolean delete(Long id);

	/**
	 * 批量删除
	 */
	boolean bulkDeleteUser(String ids);

	Integer TotalCount(SysUser user, String beginTime, String endTime);

}
