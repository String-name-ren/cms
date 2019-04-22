package com.waterelephant.common.entity.dto;

import java.io.Serializable;

public class RoleRuleDto implements Serializable{

	private static final long serialVersionUID = 280324480293323305L;

	/**
	 * 角色ID
	 */
	private Long roleId;
	/**
	 * 权限ID
	 */
	private Long ruleId;
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getRuleId() {
		return ruleId;
	}
	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}
	
	
	
}
