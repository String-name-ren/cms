/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waterelephant.common.entity.dto;

import java.io.Serializable;

/**
 *
 * @author HWang
 */
public class RemoteDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ok;
	private String error;

	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}