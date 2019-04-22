package com.waterelephant.service.impl;

import com.waterelephant.common.base.BaseService;
import com.waterelephant.common.entity.SysRole;
import com.waterelephant.common.entity.SysRoleRule;
import com.waterelephant.common.entity.SysRule;
import com.waterelephant.common.entity.dto.RoleRuleDto;
import com.waterelephant.common.exception.BusException;
import com.waterelephant.common.utils.Page;
import com.waterelephant.common.vo.PageRequestVo;
import com.waterelephant.service.SysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
* 角色接口
* @author WEIHU
*/
@Service
public class SysRoleServiceImpl extends BaseService<SysRole, Long> implements SysRoleService{

	@Override
	public Long insertRole(SysRole sysRole) {
		mapper.insert(sysRole);
	//	System.out.println(sysRole.getId());
		return sysRole.getId();
	}

	@Override
	public void deleteRole(Long roleId) {
		//mapper.deleteByPrimaryKey(roleId);
		String sql = "delete from sys_role where id=#{id}";
		sqlMapper.delete(sql,roleId);
	}

	@Override
	public void updateRole(SysRole sysRole) {
		String sql = "update sys_role set role_name=#{roleName},role_code=#{roleCode},role_desc=#{roleDesc} where id =#{id}";
		sqlMapper.update(sql,sysRole);
		//mapper.updateByPrimaryKey(sysRole);
		
	}

	@Override
	public Page<SysRole> findsSysRole(int pageNo,int pageSize) {
		String sql = "SELECT * FROM sys_role";
		int totalCount = getTotalCount();
		Page<SysRole> page = new Page<>();
		PageRequestVo pVo = new PageRequestVo((pageNo-1)*pageSize,pageSize);
		page.setPageNo((pageNo-1)*pageSize);
		page.setPageSize(pageSize);
		List<SysRole> list = sqlMapper.selectList(sql,pVo,SysRole.class);
		page.setResult(list);
		page.setTotalCount(totalCount);
		return page;
	}

	@Override
	public SysRole findById(Long id) {
		//mapper.selectByPrimaryKey(id);
		String sql = "SELECT id,role_code,role_name,role_desc FROM sys_role where id =#{id}";
		return sqlMapper.selectOne(sql,id,SysRole.class);
		//return mapper.selectByPrimaryKey(id);
	}

	@Override
	public int getTotalCount() {
		String sql = "select count(id) from sys_role";
		return sqlMapper.selectOne(sql, Integer.class);
	}

	@Override
	public int insertRoleRule(Long roleId,Set<SysRule> rules) {
		String sql = "insert into sys_role_rule(role_id,rule_id)values(#{roleId},#{ruleId})";
		int result = 0;
		for (SysRule sysRule : rules) {
			RoleRuleDto rDto = new RoleRuleDto();
			rDto.setRoleId(roleId);
			rDto.setRuleId(sysRule.getId());
			result = sqlMapper.insert(sql, rDto);
		}
		return result;
	}

	@Override
    public List<SysRole> sysRoleAll() {
		return sqlMapper.selectList("SELECT r.* from sys_role r",SysRole.class);
	}

	@Override
	public List<SysRole> findRoleByUserId(Long userID) throws BusException {
		if(StringUtils.isEmpty(String.valueOf(userID))){
			throw new BusException("用户Id为空");
		}
		String split=sqlMapper.selectOne("select group_concat(role_id) role_id from sys_user_role where user_id="+userID).get("role_id").toString();//对数据库数据进行分割
		String sql = "select r.* from sys_role r where id in ("+split+")";
		return sqlMapper.selectList(sql,userID,SysRole.class);
	}

	@Override
	public List<SysRule> roleIdByRules(Long id) {
		String sql = "select r.* from sys_rule r LEFT JOIN sys_role_rule l on r.id=l.rule_id LEFT JOIN sys_role e on "
				+ "e.id = l.role_id where l.role_id=#{id}";
		return sqlMapper.selectList(sql,id,SysRule.class);
	}

	@Override
	public int delete(Long roleIds) {
		String sql = "delete from sys_role_rule WHERE role_id=#{roleIds}";
		return sqlMapper.delete(sql , roleIds);
	}

	@Override
	public int insert(SysRoleRule sysRoleRule) {
		String sql = "insert into sys_role_rule(role_id,rule_id) VALUES(#{roleId},#{ruleId})";
		return sqlMapper.insert(sql,sysRoleRule);
	}

	
}
