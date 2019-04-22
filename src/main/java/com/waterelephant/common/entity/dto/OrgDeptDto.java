package com.waterelephant.common.entity.dto;

import java.io.Serializable;

/**
* 机构部门关系Dto
* @author WEIHU
*/
public class OrgDeptDto implements Serializable{

	private static final long serialVersionUID = -1821988380892827523L;
	/**
	 * 机构ID
	 */
	private Long orgId;
	/**
	 * 部门ID
	 */
	private Long newOrgId;
	/**
	 * 部门ID
	 */
	private Long deptId;
	
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public Long getNewOrgId() {
		return newOrgId;
	}
	public void setNewOrgId(Long newOrgId) {
		this.newOrgId = newOrgId;
	}
	
}
