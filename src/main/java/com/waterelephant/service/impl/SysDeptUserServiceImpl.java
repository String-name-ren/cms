package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.common.base.BaseService;
import com.waterelephant.common.entity.SysDeptUser;
import com.waterelephant.service.SysDeptUserService;

@Service
public class SysDeptUserServiceImpl extends BaseService<SysDeptUser, Long> implements SysDeptUserService {

	@Override
	public int insert(SysDeptUser sysDeptUser) {
		return mapper.insert(sysDeptUser);
	}

	@Override
	public int delete(Long userId) {
		String sql = "delete from sys_dept_user where user_id=#{userId}";
		return sqlMapper.delete(sql,userId);
	}

	@Override
	public int update(SysDeptUser sysDeptUser) {
		String sql = "UPDATE sys_dept_user SET dept_id=#{deptId} where user_id=#{userId}";
		return sqlMapper.update(sql, sysDeptUser);
	}

	@Override
	public int batchDeleteDept(String userIds) {
		String sql = "delete from sys_dept_user where user_id in ("+userIds+")";
		return sqlMapper.delete(sql);
	}

		
}