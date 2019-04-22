package com.waterelephant.service;

import java.util.List;

import com.waterelephant.common.entity.SysCompany;
import com.waterelephant.common.entity.SysDepartment;
import com.waterelephant.common.entity.SysOrganization;
import com.waterelephant.common.utils.Page;

public interface SysDepartmentService {

	/**
	 * 添加部门
	 */
	Long insertDept(SysDepartment sysDepartment);

	/**
	 * 删除部门
	 */
	void deleteDept(Long deptId);

	/**
	 * 修改部门信息
	 */
	void updateDept(SysDepartment sysDepartment);

	/**
	 * 分页查询部门信息
	 */
	Page<SysDepartment> findsByPage(String companyId,int pageNo, int pageSize);

	/**
	 * 根据id获取部门信息
	 */
	SysDepartment findById(Long id);

	/**
	 * 获取所有的机构
	 */
	List<SysOrganization> getAllOrgList();

	/**
	 * 获取所有的部门
	 */
	List<SysDepartment> getAllDeptList();

	/**
	 * 添加机构部门关系
	 * 
	 * @param orgId 机构ID,deptId 部门ID
	 */
	int insertOrgDept(Long orgId, Long deptId);

	/**
	 * 获取总行数
	 */
	int getTotalCount();

	/**
	 * 获取所有的部门信息
	 */
	List<SysDepartment> departmentAll();

	/**
	 * 根据用户编号查询相应的部门
	 */
	SysDepartment findDeptByUserId(Long userId);

	/**
	 * 根据部门ID获取所属机构信息
	 */
	SysOrganization findOrgById(Long deptId);

	/**
	 * 修改部门所属机构信息
	 */
	int updateOrgDept(Long deptId, Long oldOrgId, Long newOrgId);

	/**
	 * 修改部门所属机构信息
	 */
	int updateOrgDept(SysDepartment sysDepartment, Long oldOrgId, Long newOrgId);

	/**
	 *
	 * 根据部门名称查询部门
	 *
	 * @return
	 */
	SysDepartment findSysDepartmentByDeptName(String deptName);

	/**
	 * 查询所有的公司
	 *
	 * @return
	 */
	List<SysCompany> getAllCompanyInfo();

	/**
	 * 添加公司
	 * @param sysCompany
	 * @return
	 */
	int insertCompany(SysCompany sysCompany);

	/**
	 * 分页查询公司信息
	 */
	Page<SysCompany> findCompanyPage(int pageNo, int pageSize);

	/**
	 * 删除公司
	 * @param id
	 */
	void deleteCompany(Long id);

	/**
	 * 根据部门id查询公司
	 * @param deptId
	 * @return
	 */
	SysCompany findOrgByDeptId(Long deptId);

	/**
	 * 根据公司ID查询公司
	 * @param orgId
	 * @return
	 */
	SysCompany findByOrgId(Long orgId);


	/**
	 * 根据公司名称查询公司
	 * @param companyName
	 * @return
	 */
	SysCompany findCompanyByCompanyName(String companyName);


	/**
	 * 根据公司OrgId查询部门信息
	 * @param OrgId
	 * @return
	 */
	SysDepartment findDeptByOrgId(Long OrgId);



}
