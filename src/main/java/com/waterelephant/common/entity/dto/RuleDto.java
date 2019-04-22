/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waterelephant.common.entity.dto;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author HWang
 */
public class RuleDto implements Comparable<Object>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long parentId;
	private String tabid;
	private String name;
	private String icon;
	private String url;
	private Integer priority;
	private List<RuleDto> children;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTabid() {
		return tabid;
	}

	public void setTabid(String tabid) {
		this.tabid = tabid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<RuleDto> getChildren() {
		return children;
	}

	public void setChildren(List<RuleDto> children) {
		this.children = children;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Override
	public int compareTo(Object obj) {
		RuleDto dto = (RuleDto) obj;
		return this.priority.compareTo(dto.getPriority());
	}

}