/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waterelephant.common.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色
 *
 * @author WEIHU
 */
@Table(name="sys_role")
public class SysRole  implements   Serializable{

	private static final long serialVersionUID = 5372412796906257355L;
	/**
     * 主键ID
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 角色代码
     */
    private String roleCode;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色描述
     */
    private String roleDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }
}
