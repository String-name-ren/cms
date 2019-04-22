/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waterelephant.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 操作日志
 *
 * @author HWang
 */
@Table(name="sys_log")
public class SysLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键ID
	 */
	@Id
	private Long id;
	/**
	 * 操作人
	 */
	private String operator;
	/**
	 * 操作时间
	 */
	private Date operateTime;
	/**
	 * 操作描述
	 */
	private String description;
	/**
	 * 操作结果
	 */
	private String operateResult;
	/**
	 * 操作IP
	 */
	private String ip;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOperateResult() {
		return operateResult;
	}

	public void setOperateResult(String operateResult) {
		this.operateResult = operateResult;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}


}
