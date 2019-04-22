/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waterelephant.common.entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 权限
 *
 * @author HWang
 */
@Table(name="sys_rule")
public class SysRule implements   Serializable {

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
	 * 父权限
	 */
	private SysRule parent;
	/**
	 * 权限代码
	 */
	private String ruleCode;
	/**
	 * 权限名称
	 */
	private String ruleName;
	/**
	 * 权限描述
	 */
	private String ruleDesc;
	/**
	 * 权限URL
	 */
	private String ruleUrl;
	/**
	 * 权限排序
	 */
	private Integer ruleOrder;
	/**
	 * 权限类型
	 */
	private Integer ruleType;
	/**
	 * 权限样式
	 */
	private String rulePicture;
	/**
	 * 权限扩展
	 */
	private String ruleExt;
	/**
	 * 树节点左边值
	 */
	private Long lft;
	/**
	 * 树节点左边值
	 */
	private Long parentId;
	
	private Long rgt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SysRule getParent() {
		return parent;
	}

	public void setParent(SysRule parent) {
		this.parent = parent;
	}

	public Long getLft() {
		return lft;
	}

	public void setLft(Long lft) {
		this.lft = lft;
	}

	public Long getRgt() {
		return rgt;
	}

	public void setRgt(Long rgt) {
		this.rgt = rgt;
	}

	public String getRuleCode() {
		return ruleCode;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleDesc() {
		return ruleDesc;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

	public String getRuleUrl() {
		return ruleUrl;
	}

	public void setRuleUrl(String ruleUrl) {
		this.ruleUrl = ruleUrl;
	}

	public Integer getRuleType() {
		return ruleType;
	}

	public void setRuleType(Integer ruleType) {
		this.ruleType = ruleType;
	}

	public Integer getRuleOrder() {
		return ruleOrder;
	}

	public void setRuleOrder(Integer ruleOrder) {
		this.ruleOrder = ruleOrder;
	}

	public String getRulePicture() {
		return rulePicture;
	}

	public void setRulePicture(String rulePicture) {
		this.rulePicture = rulePicture;
	}

	public String getRuleExt() {
		return ruleExt;
	}

	public void setRuleExt(String ruleExt) {
		this.ruleExt = ruleExt;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}


}
