package com.waterelephant.common.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="sys_user_role")
public class SysUserRole implements Serializable{
		
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		/**
		 * 用户编号
		 * */
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long userId;
		
		/**
		 * 角色编号
		 * */
		private Long roleId;

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public Long getRoleId() {
			return roleId;
		}

		public void setRoleId(Long roleId) {
			this.roleId = roleId;
		}

		public SysUserRole() {
		}

		public SysUserRole(Long userId, Long roleId) {
			this.userId = userId;
			this.roleId = roleId;
		}		
		
}
