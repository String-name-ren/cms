package com.waterelephant.service;

import java.util.List;
import java.util.Map;

import com.waterelephant.common.entity.SysCity;
import com.waterelephant.common.exception.BusException;
import com.waterelephant.common.utils.Page;
import com.waterelephant.common.utils.TreeModel;

public interface SysCityService {

	Page<SysCity> query(int[] pageParams, String cityName,String orgId ,String lft, String rgt);

	List<TreeModel> findChannelTree();

	void add(String cityName, String cityDesc, String orgId) throws BusException;

	void deleteCity(String id) throws BusException;

	SysCity queryCity(String id);

	void updateSave(String id, String orgId, String cityName, String cityDesc);

	Map<String, Object> queryOrg(String id);

}
