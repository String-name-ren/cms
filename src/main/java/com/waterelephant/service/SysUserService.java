/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waterelephant.service;

import java.util.List;

import com.waterelephant.common.entity.SysRule;
import com.waterelephant.common.entity.SysUser;
import com.waterelephant.common.exception.BusException;
import com.waterelephant.common.utils.Page;

/**
 *
 * @author HWang
 */
public interface SysUserService {

	List<SysUser> findUserByLoginName(String loginName);

	List<SysRule> queryRole(Long id) throws BusException;

	SysUser findUserByKey(Long id);

	/**
	 * 获取用户的所有权限
	 * 
	 * @param id 用户ID
	 */
	List<SysRule> getRuleById(Long id);

	void updateUser(SysUser user);

	Page<SysUser> findBorrowerPage(String orderId, int i, int j) throws BusException;

	String queryOrg(String username);

}
