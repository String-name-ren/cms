package com.waterelephant.common.entity.dto;

/**
 * @Description:手动分单dto
 * @author liven
 * @date 2017年2月27日 上午9:21:13
 */
public class LoanManualDto {

	private String orderNo;
	private String idCard;
	private String customName;
	private String mobile;
	private String startTime;
	private String endTime;
	private String overdueDays;
	private String stayDays;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getCustomName() {
		return customName;
	}
	public void setCustomName(String customName) {
		this.customName = customName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStayDays() {
		return stayDays;
	}
	public void setStayDays(String stayDays) {
		this.stayDays = stayDays;
	}
	public String getOverdueDays() {
		return overdueDays;
	}
	public void setOverdueDays(String overdueDays) {
		this.overdueDays = overdueDays;
	}
	
}
