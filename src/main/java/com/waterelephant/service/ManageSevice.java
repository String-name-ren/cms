package com.waterelephant.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.waterelephant.common.entity.SysCity;
import com.waterelephant.common.entity.SysLog;
import com.waterelephant.common.entity.SysOrganization;
import com.waterelephant.common.exception.BusException;
import com.waterelephant.common.utils.Page;
import com.waterelephant.common.utils.TreeModel;

/**
 * 机构日志业务层
 * @author DOY
 *
 */
public interface ManageSevice {
	
	Page<SysOrganization> query(SysOrganization org, int i, int j,String pid) throws BusException ;
	
	Map<String, Object> findUserByLoginName(String orgName,HttpServletRequest request) throws BusException;

	Long save(String orgName, String pid,String orgDesc)throws BusException;

	int delete(String ids) throws BusException;

	Map<String, Object> update(String id) throws BusException;

	void updateIns(String pid, String id,String orgName,String orgDesc) throws BusException;

	Page<SysLog> queryLogin(SysLog log, int i, int j, String startTime , String endTime) throws BusException;
	
	int saveSysLog(String string, String oPERATION_FAILURE, HttpServletRequest request);

	int getTotalCount(SysOrganization org,String status);

	int TotalLogin(SysLog log , String startTime , String endTime);
	
	/**
	 * 根据部门id查询所属机构
	 * */
	SysOrganization findByDeptId(Long dId);
	
	
	List<SysCity> findByOrgIdAndCity(String orgId);

	List<TreeModel> findRuleTree(boolean b);
}
