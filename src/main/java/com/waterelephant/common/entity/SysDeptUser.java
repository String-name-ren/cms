package com.waterelephant.common.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="sys_dept_user")
public class SysDeptUser implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 部门编号
	 * */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long deptId;
	
	/**
	 * 用户编号
	 * */
	private Long userId;

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
	public SysDeptUser(Long deptId, Long userId) {
		this.deptId = deptId;
		this.userId = userId;
	}

	
	public SysDeptUser() {
	
	}
	
	
}
