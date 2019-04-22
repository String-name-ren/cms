/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waterelephant.service.impl;

import java.util.List;
import java.util.Map;

import com.waterelephant.common.utils.SqlMapper;
import com.waterelephant.mapper.SysUserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waterelephant.common.base.BaseService;
import com.waterelephant.common.entity.SysRule;
import com.waterelephant.common.entity.SysUser;
import com.waterelephant.common.exception.BusException;
import com.waterelephant.common.utils.Page;
import com.waterelephant.common.vo.PageRequestVo;
import com.waterelephant.service.SysUserService;
/**
 *
 * @author Hui Wang
 */
@Service
public class SysUserServiceImpl extends BaseService<SysUser, Long>  implements SysUserService {
	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private SqlMapper sqlMapper;


    @Override
    public  List<SysUser> findUserByLoginName(String loginName){
        String hql = " select * from sys_user where login_name='"+loginName+"'";
        return sqlMapper.selectList(hql , SysUser.class);
    }
	/**
	 * 查询当前用户获得的rule
	 * @throws BusException 
	 */
	@Override
	public List<SysRule> queryRole(Long id) throws BusException {
		if(StringUtils.isEmpty(String.valueOf(id))){
			throw new BusException("用户ID为空");
		}
		//String split=sqlMapper.selectOne("select group_concat(role_id) role_id from sys_user_role where user_id='"+id+"'").get("role_id").toString();
		String split=sqlMapper.selectOne("select group_concat(role_id) role_id from sys_user_role where user_id='"+id+"'",String.class);
		String sql="select DISTINCT l.*  from sys_role_rule r LEFT JOIN sys_rule l on l.id=r.rule_id where r.role_id in ("+split+")  ";
		return sqlMapper.selectList(sql ,SysRule.class);
	}

	  
	@Override
	    public SysUser findUserByKey(Long id) {
		return sysUserMapper.selectByPrimaryKey(id);
	    }

	/**
	 * 获取用户的所有权限
	 * @param id 用户ID
	 * @return List<SysRule> 权限信息列表
	 */
	@Override
	public List<SysRule> getRuleById(Long id) {
		String sql = "SELECT "
				   + "a.*, c.* "
				   + "FROM "
				   + "sys_rule a "
				   + "LEFT JOIN sys_role_rule b ON a.id = b.rule_id "
				   + "LEFT JOIN sys_user_role c ON b.role_id = c.role_id "
				   + "WHERE c.user_id = #{id}";
		return sqlMapper.selectList(sql,id,SysRule.class);
	}
	@Override
	public void updateUser(SysUser user) {
		mapper.updateByPrimaryKeySelective(user);
		
	}
	@Override
	public Page<SysUser> findBorrowerPage(String orderId, int pageNo, int pageSize) throws BusException {
		if(StringUtils.isEmpty(orderId)){
			throw new BusException("工单Id为空");
		}
		String sql = "select org_id from bw_order where id='"+orderId+"'";//获得工单机构ID
		String orgId=sqlMapper.selectOne(sql,String.class);
		List<Map<String,Object>> listRole=sqlMapper.selectList("select id  from sys_role where role_code='ywy'");
		String account="";
		if(listRole.size()>0){
			account=listRole.get(0).get("id").toString();//查询业务员的角色Id
		}
		if(StringUtils.isEmpty(account)){
			throw new BusException("业务员角色ID为空");
		}
		String str="";
		if(StringUtils.isNoneBlank(orgId) && StringUtils.isNoneEmpty(account)){
			str="select u.* from sys_user u, sys_user_role r where u.id=r.user_id and concat(',',r.role_id,',') LIKE '%,"+account+",%'";
			str+=" and u.id in (select d.user_id from sys_org_dept o LEFT JOIN sys_dept_user d on o.dept_id=d.dept_id where o.org_id='"+orgId+"' and NOT ISNULL(d.user_id) ) and u.status='1'";
		}
		Page<SysUser> page = new Page<SysUser>();
		PageRequestVo pVo = new PageRequestVo((pageNo-1)*pageSize,pageSize);
		page.setPageNo((pageNo-1)*pageSize);
		page.setPageSize(pageSize);
		List<SysUser> list = sqlMapper.selectList(str,pVo,SysUser.class);
		List<SysUser> listPage = sqlMapper.selectList(str,SysUser.class);
		page.setResult(list);
		page.setTotalCount(listPage.size());
		return page;
	}
	@Override
	public String queryOrg(String username) {
		String sql="select role_id from sys_user_role s where s.user_id=(select id from sys_user u where u.login_name='"+username+"' and u.status='1' ) and s.role_id=(select id from sys_role where role_code='superadmin')";
		return sqlMapper.selectOne(sql,String.class);
	}
}
