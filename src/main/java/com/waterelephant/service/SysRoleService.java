package com.waterelephant.service;

import java.util.List;
import java.util.Set;

import com.waterelephant.common.entity.SysRole;
import com.waterelephant.common.entity.SysRoleRule;
import com.waterelephant.common.entity.SysRule;
import com.waterelephant.common.exception.BusException;
import com.waterelephant.common.utils.Page;

public interface SysRoleService {

	//增
	Long insertRole(SysRole sysRole);
	//删
	void deleteRole(Long roleId);
	//改
	void updateRole(SysRole sysRole);
	//查
	Page<SysRole> findsSysRole(int pageNo,int pageSize);
	
	//根据ID查询角色信息
	SysRole findById(Long id);
	/**
	 * 获取角色信息总行数
	 */
	int getTotalCount();

	
	List<SysRole> sysRoleAll();
	
	
	/**
	 * 根据用户的id查询改用户拥有的角色
	 * @throws BusException 
	 * */
	List<SysRole> findRoleByUserId(Long userID) throws BusException;
	

	/**
	 * 新增角色权限关系记录
	 */
	int insertRoleRule(Long roleId,Set<SysRule> rules);
	
	
	/**
	 * 根据角色编号查询权限集合
	 * */
	List<SysRule> roleIdByRules(Long id);
	
	/**
	 * 根据id批量删除
	 * */
	public int delete(Long roleIds);
	
	/**
	 * 根据id批量插入权限
	 * */
	int insert(SysRoleRule sysRoleRule);
	
}
