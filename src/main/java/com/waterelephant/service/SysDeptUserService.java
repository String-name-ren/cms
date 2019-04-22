package com.waterelephant.service;

import com.waterelephant.common.entity.SysDeptUser;

public interface SysDeptUserService {
	
	int insert(SysDeptUser sysDeptUser);
	
	
	int delete(Long userId);
	
	
	int update(SysDeptUser sysDeptUser);
	
	/**
	 * 批量删除
	 * */
	int batchDeleteDept(String userIds);
}
