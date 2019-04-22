package com.waterelephant.service.impl;
import org.springframework.stereotype.Service;

import com.waterelephant.common.base.BaseService;
import com.waterelephant.common.entity.SysUserRole;
import com.waterelephant.service.SysUserRoleService;
/**
 * 
 * */
@Service
public class SysUserRoleServiceImpl extends BaseService<SysUserRole,Long> implements SysUserRoleService {

	@Override
	public int insert(SysUserRole sysUserRole) {
		return mapper.insert(sysUserRole);
	}

	@Override
	public int delete(Long userId) {
		String sql = "delete from sys_user_role where user_id=#{userId}";
		return sqlMapper.delete(sql,userId);
	}

	@Override
	public void update(SysUserRole sysUserRole) {
		mapper.insert(sysUserRole);
	}

	@Override
	public int batchDeleteRole(String deptIds) {
		String sql = "delete from sys_user_role where user_id in ("+deptIds+")";
		return sqlMapper.delete(sql);
	}	
}
