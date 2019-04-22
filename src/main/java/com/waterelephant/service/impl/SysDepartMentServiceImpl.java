package com.waterelephant.service.impl;

import java.util.List;

import com.waterelephant.common.entity.SysCompany;
import org.springframework.stereotype.Service;

import com.waterelephant.common.base.BaseService;
import com.waterelephant.common.entity.SysDepartment;
import com.waterelephant.common.entity.SysOrganization;
import com.waterelephant.common.entity.dto.OrgDeptDto;
import com.waterelephant.common.utils.CommUtils;
import com.waterelephant.common.utils.Page;
import com.waterelephant.common.vo.PageRequestVo;
import com.waterelephant.service.SysDepartmentService;

/**
 * 部门接口
 * 
 * @author WEIHU
 */
@Service
public class SysDepartMentServiceImpl extends BaseService<SysDepartment, Long> implements SysDepartmentService {

	/**
	 * 新增部门
	 */
	@Override
	public Long insertDept(SysDepartment sysDepartment) {
		mapper.insert(sysDepartment);
		return sysDepartment.getId();
	}

	/**
	 * 删除部门
	 */
	@Override
	public void deleteDept(Long deptId) {
		// mapper.deleteByPrimaryKey(deptId);
		String sql = "delete from sys_department " + "where id = #{id}";
		String hql = "delete from sys_org_dept " + "where dept_id = #{id}";
		String str = "delete from sys_dept_user " + "where dept_id = #{id}";
		sqlMapper.delete(sql, deptId);
		sqlMapper.delete(hql, deptId);
		sqlMapper.delete(str, deptId);
	}

	/**
	 * 更新部门
	 */
	@Override
	public void updateDept(SysDepartment sysDepartment) {
		// mapper.updateByPrimaryKey(sysDepartment);
		String sql = "UPDATE sys_department " + "SET dept_name = #{deptName} , " + "dept_code = #{deptCode} , "
				+ "dept_desc = #{deptDesc} " + "WHERE id = #{id} ";
		sqlMapper.update(sql, sysDepartment);
	}

	/**
	 * 获取部门信息列表
	 */
	@Override
	public Page<SysDepartment> findsByPage(String companyId, int pageNo, int pageSize) {
		String sql;
		if (companyId == null || "999999999".equals(companyId)) {
			//sql = "select d.* from sys_department d where d.id in(select dept_id from sys_org_dept where org_id=null) limit #{pageNum},#{pageSize}";
			sql = "select d.* from sys_department d limit #{pageNum},#{pageSize}";
		} else {
			sql = "select d.* from sys_department d where d.id in(select dept_id from sys_org_dept where org_id=" + companyId + ") limit #{pageNum},#{pageSize}";
		}
		int totalCount = getTotalCount();
		Page<SysDepartment> page = new Page<>();
		PageRequestVo pVo = new PageRequestVo((pageNo - 1) * pageSize, pageSize);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		List<SysDepartment> list = sqlMapper.selectList(sql, pVo, SysDepartment.class);
		page.setResult(list);
		page.setTotalCount(totalCount);
		return page;
	}

	@Override
	public SysDepartment findById(Long id) {
		// return mapper.selectByPrimaryKey(id);
		String sql = "select * from sys_department where id = #{id} ";
		return sqlMapper.selectOne(sql, id, SysDepartment.class);
	}

	@Override
	public int getTotalCount() {
		String sql = "select count(id) from sys_department";
		return sqlMapper.selectOne(sql, Integer.class);
	}

	/**
	 * 获取所有的机构
	 */
	@Override
	public List<SysOrganization> getAllOrgList() {
		String sql = "select * from sys_organization";
		return sqlMapper.selectList(sql, SysOrganization.class);
	}

	/**
	 * 获取所有的部门
	 */
	@Override
	public List<SysDepartment> getAllDeptList() {
		String sql = "select * from sys_department";
		return sqlMapper.selectList(sql, SysDepartment.class);
	}

	/**
	 * 添加公司部门关系
	 * 
	 * @param orgId 机构ID,deptId 部门ID
	 */
	@Override
	public int insertOrgDept(Long orgId, Long deptId) {
		String sql = " insert into sys_org_dept(org_id,dept_id)values(#{orgId},#{deptId})";
		OrgDeptDto orgDeptDto = new OrgDeptDto();
		orgDeptDto.setDeptId(deptId);
		orgDeptDto.setOrgId(orgId);
		return sqlMapper.insert(sql, orgDeptDto);
	}

	@Override
	public List<SysDepartment> departmentAll() {
		return sqlMapper.selectList("select d.* from sys_department d", SysDepartment.class);
	}

	@Override
	public SysDepartment findDeptByUserId(Long userId) {
		String sql = "select d.* from sys_department d where d.id=(select dept_id from sys_dept_user "
				+ "where user_id=#{userId})";
		return sqlMapper.selectOne(sql, userId, SysDepartment.class);
	}

	/**
	 * 根据部门ID获取所属机构信息
	 */
	@Override
	public SysOrganization findOrgById(Long deptId) {
		String sql = "SELECT * FROM sys_organization a LEFT JOIN sys_org_dept b ON a.id = b.org_id WHERE b.dept_id = #{id}";
		return sqlMapper.selectOne(sql, deptId, SysOrganization.class);
	}

	/**
	 * 修改部门所属机构信息
	 */
	@Override
	public int updateOrgDept(Long deptId, Long oldOrgId, Long newOrgId) {
		String sql = "update sys_org_dept set org_id=#{newOrgId} where dept_id=#{deptId} and org_id =#{orgId} ";
		OrgDeptDto orgDeptDto = new OrgDeptDto();
		orgDeptDto.setDeptId(deptId);
		orgDeptDto.setOrgId(oldOrgId);
		orgDeptDto.setNewOrgId(newOrgId);
		return sqlMapper.update(sql, orgDeptDto);
	}

	/**
	 * 修改部门所属机构信息
	 */
	@Override
	public int updateOrgDept(SysDepartment sysDepartment, Long oldOrgId, Long newOrgId) {
		this.updateDept(sysDepartment);
		String sql = "update sys_org_dept set org_id=#{newOrgId} where dept_id=#{deptId} and org_id =#{orgId} ";
		OrgDeptDto orgDeptDto = new OrgDeptDto();
		orgDeptDto.setDeptId(sysDepartment.getId());
		orgDeptDto.setOrgId(oldOrgId);
		orgDeptDto.setNewOrgId(newOrgId);
		return sqlMapper.update(sql, orgDeptDto);
	}

	/**
	 * 
	 * 根据部门名称查询部门信息
	 */
	@Override
	public SysDepartment findSysDepartmentByDeptName(String deptName) {
		String sql = "select u.* from sys_department u where u.dept_name=#{deptName}";
		List<SysDepartment> sysDepartmentList = sqlMapper.selectList(sql, deptName, SysDepartment.class);
		return CommUtils.isNull(sysDepartmentList) ? null : sysDepartmentList.get(0);
	}

	/**
	 * 查询所有的公司
	 * @return
	 */
	@Override
	public List<SysCompany> getAllCompanyInfo() {
		String sql = "select * from sys_company";
		return sqlMapper.selectList(sql, SysCompany.class);
	}

	/**
	 * 新增公司
	 * @param sysCompany
	 * @return
	 */
	@Override
	public int insertCompany(SysCompany sysCompany) {
		String sql = " insert into sys_company(org_Name,org_code)values(#{orgName},#{orgCode})";
		return sqlMapper.insert(sql, sysCompany);
	}

	/**
	 * 获取公司list
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Override
	public Page<SysCompany> findCompanyPage(int pageNo, int pageSize) {
		String sql = "select c.* from sys_company c limit #{pageNum},#{pageSize}";
		int totalCount = getTotalCount();
		Page<SysCompany> page = new Page<>();
		PageRequestVo pVo = new PageRequestVo((pageNo - 1) * pageSize, pageSize);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		List<SysCompany> list = sqlMapper.selectList(sql, pVo, SysCompany.class);
		page.setResult(list);
		page.setTotalCount(totalCount);
		return page;
	}

	/**
	 * 删除公司信息
	 * @param id
	 */
	@Override
	public void deleteCompany(Long id) {
		String sql = "delete from sys_company where id = #{id}";
//		String sql1 = "delete from sys_org_dept " + "where org_id = #{id}";
		sqlMapper.delete(sql, id);
//		sqlMapper.delete(sql1, id);
	}

	@Override
	public SysCompany findOrgByDeptId(Long deptId) {
		String sql = "select c.* from sys_company c where c.id=(select org_id from sys_org_dept "
				+ "where dept_id=#{deptId})";
		return sqlMapper.selectOne(sql, deptId, SysCompany.class);
	}

	@Override
	public SysCompany findByOrgId(Long orgId) {
		String sql = "select * from sys_company where id = #{orgId} ";
		return sqlMapper.selectOne(sql, orgId, SysCompany.class);
	}

	@Override
	public SysCompany findCompanyByCompanyName(String companyName) {
		String sql = "select c.* from sys_company c where c.org_name=#{companyName}";
		List<SysCompany> sysCompanyList = sqlMapper.selectList(sql, companyName, SysCompany.class);
		return CommUtils.isNull(sysCompanyList) ? null : sysCompanyList.get(0);
	}

	@Override
	public SysDepartment findDeptByOrgId(Long OrgId) {
		String sql = "select c.* from sys_department c where c.id=(select dept_id from sys_org_dept "
				+ "where org_id=#{OrgId})";
		return sqlMapper.selectOne(sql, OrgId, SysDepartment.class);
	}

}
