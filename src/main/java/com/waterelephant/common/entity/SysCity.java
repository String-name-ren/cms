package com.waterelephant.common.entity;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="sys_city")
public class SysCity
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  private String cityName;
  private String cityDesc;
  private String orgName;
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long id)
  {
    this.id = id;
  }
  
  public String getCityName()
  {
    return this.cityName;
  }
  
  public void setCityName(String cityName)
  {
    this.cityName = cityName;
  }
  
  public String getCityDesc()
  {
    return this.cityDesc;
  }
  
  public void setCityDesc(String cityDesc)
  {
    this.cityDesc = cityDesc;
  }

public String getOrgName() {
	return orgName;
}

public void setOrgName(String orgName) {
	this.orgName = orgName;
}
  
}
