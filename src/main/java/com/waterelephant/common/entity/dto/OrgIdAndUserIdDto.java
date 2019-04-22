package com.waterelephant.common.entity.dto;

import java.io.Serializable;

/**
 * 
 * @author song
 *
 */
public class OrgIdAndUserIdDto implements Serializable{

	
	private static final long serialVersionUID = 7560876315155676978L;
	
	private Long orgId;
	
	private Long userId;

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "OrgIdAndUserIdDto [orgId=" + orgId + ", userId=" + userId + "]";
	}
	
	

}
