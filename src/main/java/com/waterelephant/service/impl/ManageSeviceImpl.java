package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.waterelephant.common.base.BaseService;
import com.waterelephant.common.entity.SysCity;
import com.waterelephant.common.entity.SysLog;
import com.waterelephant.common.entity.SysOrganization;
import com.waterelephant.common.entity.SysUser;
import com.waterelephant.common.exception.BusException;
import com.waterelephant.common.utils.CommUtils;
import com.waterelephant.common.utils.Page;
import com.waterelephant.common.utils.SystemConstant;
import com.waterelephant.common.utils.TreeModel;
import com.waterelephant.common.vo.PageRequestVo;
import com.waterelephant.service.ManageSevice;

@Service
public class ManageSeviceImpl extends BaseService<SysOrganization, Long> implements ManageSevice {

	// 获得机构数据
	@Override
	public Page<SysOrganization> query(SysOrganization org, int pageNo, int pageSize, String pid) throws BusException {
		Page<SysOrganization> page = new Page<>();
		String hql = "";
		if ("0".equals(pid)) {
			hql = " select * from sys_organization where father_id=(select id from sys_organization where father_id='0')";
			if (!CommUtils.isNull(org.getId())) {
				hql += " and   lft>(select lft from sys_organization where id=" + org.getId()
						+ ") and rgt < (select rgt from sys_organization where id=" + org.getId() + ")";
			}
			if (!CommUtils.isNull(org.getOrgName())) {
				hql += " and org_name='" + org.getOrgName() + "'";
			}
			hql += " limit #{pageNum},#{pageSize} ";
		} else {
			hql = " select * from sys_organization where 1=1";
			if (!CommUtils.isNull(org.getId())) {
				hql += " and   lft>(select lft from sys_organization where id=" + org.getId()
						+ ") and rgt < (select rgt from sys_organization where id=" + org.getId() + ")";
			}
			if (!CommUtils.isNull(org.getOrgName())) {
				hql += " and org_name='" + org.getOrgName() + "'";
			}
			hql += " limit #{pageNum},#{pageSize} ";
		}
		int totalCount = getTotalCount(org, pid);
		PageRequestVo pVo = new PageRequestVo((pageNo - 1) * pageSize, pageSize);
		page.setPageNo((pageNo - 1) * pageSize);
		page.setPageSize(pageSize);
		page.setResult(sqlMapper.selectList(hql, pVo, SysOrganization.class));
		page.setTotalCount(totalCount);
		return page;
	}

	/* 获得总行数 */
	@Override
	public int getTotalCount(SysOrganization org, String pid) {
		String hql = "";
		if ("0".equals(pid)) {
			hql = " select count(id) from sys_organization where father_id=(select id from sys_organization where father_id='0')";
			if (!CommUtils.isNull(org.getId())) {
				hql += " and   lft>(select lft from sys_organization where id=" + org.getId()
						+ ") and rgt < (select rgt from sys_organization where id=" + org.getId() + ")";
			}
			if (!CommUtils.isNull(org.getOrgName())) {
				hql += " and org_name='" + org.getOrgName() + "'";
			}
		} else {
			hql = " select count(id) from sys_organization where 1=1";
			if (!CommUtils.isNull(org.getId())) {
				hql += " and   lft>(select lft from sys_organization where id=" + org.getId()
						+ ") and rgt < (select rgt from sys_organization where id=" + org.getId() + ")";
			}
			if (!CommUtils.isNull(org.getOrgName())) {
				hql += " and org_name='" + org.getOrgName() + "'";
			}
		}
		return sqlMapper.selectOne(hql, Integer.class);
	}

	// 判断机构名是否存在
	@Override
	public Map<String, Object> findUserByLoginName(String orgName, HttpServletRequest request) throws BusException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isBlank(orgName)) {
			throw new BusException("机构名不能为空");
		} else {
			String hql = "select org_name from sys_organization where org_name='" + orgName + "'";
			map = sqlMapper.selectOne(hql);
		}
		return map;
	}

	// 添加机构信息
	@Override
	public Long save(String orgName, String pid, String orgDesc) throws BusException {
		Long id = sqlMapper.selectOne(
				"select id from sys_organization where father_id='1' and org_name='" + orgName + "'", Long.class);
		if (!CommUtils.isNull(id)) {
			throw new BusException("该机构名在父机构中已存在");
		}
		String sql = "select tree_org(" + Integer.valueOf(pid) + ",'" + orgName + "','" + orgDesc + "')";
		int lft = sqlMapper.selectOne(sql, Integer.class);
		SysOrganization sysOrganization = new SysOrganization();
		sysOrganization.setOrgName(orgName);
		sysOrganization.setFatherId(Long.valueOf(pid));
		sysOrganization.setOrgDesc(orgDesc);
		sysOrganization.setOrgCode("pinyin(" + orgName + ")");
		sysOrganization.setLft((lft + 1));
		sysOrganization.setRgt((lft + 2));
		mapper.insert(sysOrganization);
		sqlMapper.update("update sys_organization set org_code=pinyin('" + orgName + "') where id='"
				+ sysOrganization.getId() + "'");
		return sysOrganization.getId();
	}

	// 删除机构
	@Override
	public int delete(String ids) throws BusException {
		String sql = "select delete_org(" + Integer.valueOf(ids) + ") ";
		return sqlMapper.selectOne(sql, Integer.class);
	}

	// 查询索要更新的数据
	@Override
	public Map<String, Object> update(String id) throws BusException {
		String hql = "";
		if (StringUtils.isNotBlank(id)) {
			hql += "select * from sys_organization where id='" + id + "'";
		} else {
			throw new BusException("更新数据ID为空");
		}

		return sqlMapper.selectOne(hql);
	}

	// 更新数据信息
	@Override
	public void updateIns(String pid, String id, String orgName, String orgDesc) throws BusException {
		if (CommUtils.isNull(id)) {
			throw new BusException("机构ID为空");
		}
		if (CommUtils.isNull(orgName)) {
			throw new BusException("机构名字为空");
		}
		if (CommUtils.isNull(orgDesc)) {
			throw new BusException("机构描述为空");
		}
		if (CommUtils.isNull(pid)) {
			sqlMapper.update("update sys_organization set org_name='" + orgName + "',org_desc='" + orgDesc
					+ "' where id='" + id + "'");
		} else {
			int de_org = sqlMapper.selectOne("select delete_org(" + Integer.valueOf(id) + ")", Integer.class);
			if (de_org == 0) {
				int lft = sqlMapper.selectOne(
						"select tree_org(" + Integer.valueOf(pid) + ",'" + orgName + "','" + orgDesc + "')",
						Integer.class);
				SysOrganization sysOrganization = new SysOrganization();
				sysOrganization.setOrgName(orgName);
				sysOrganization.setFatherId(Long.valueOf(pid));
				sysOrganization.setOrgCode(orgName);
				sysOrganization.setOrgDesc(orgDesc);
				sysOrganization.setLft((lft + 1));
				sysOrganization.setRgt((lft + 2));
				mapper.insert(sysOrganization);
				sqlMapper.update("update sys_organization set org_code=pinyin('" + orgName + "') where id='"
						+ sysOrganization.getId() + "'");
			}
		}
	}

	// 查询操作日志
	@Override
	public Page<SysLog> queryLogin(SysLog log, int pageNo, int pageSize, String startTime, String endTime)
			throws BusException {
		String hql = "select * from sys_log where 1=1";
		if (StringUtils.isNotBlank(log.getOperator())) {
			hql += " and operator like '%" + log.getOperator() + "%'";
		}
		if (!CommUtils.isNull(startTime)) {
			hql += " and left(operate_time,10)>='" + startTime + "'";
		}
		if (!CommUtils.isNull(endTime)) {
			hql += " and left(operate_time,10)<='" + endTime + "'";
		}
		hql += " ORDER BY operate_time DESC limit #{pageNum},#{pageSize}";
		Page<SysLog> page = new Page<>();
		PageRequestVo pVo = new PageRequestVo((pageNo - 1) * pageSize, pageSize);
		int totalCount = TotalLogin(log, startTime, endTime);
		page.setPageNo((pageNo - 1) * pageSize);
		page.setPageSize(pageSize);
		page.setResult(sqlMapper.selectList(hql, pVo, SysLog.class));
		page.setTotalCount(totalCount);
		return page;
	}

	// 查询日志总行数
	@Override
	public int TotalLogin(SysLog log, String startTime, String endTime) {
		String hql = "select count(id) from sys_log where 1=1";
		if (StringUtils.isNotBlank(log.getOperator())) {
			hql += " and operator like '%" + log.getOperator() + "%'";
		}
		if (StringUtils.isNotBlank(log.getDescription())) {
			hql += " and description  like '%" + log.getDescription() + "%'";
		}
		if (!CommUtils.isNull(startTime)) {
			hql += " and left(operate_time,10)>='" + startTime + "'";
		}
		if (!CommUtils.isNull(endTime)) {
			hql += " and left(operate_time,10)<='" + endTime + "'";
		}
		return sqlMapper.selectOne(hql, Integer.class);
	}

	/**
	 * 添加操作日志
	 * 
	 * @param matter 内容
	 * @param result 状态
	 */
	@Override
	public int saveSysLog(String matter, String result, HttpServletRequest request) {
		SysUser user = (SysUser) request.getSession().getAttribute(SystemConstant.SESSION_USER);
		SysLog log = new SysLog();
		log.setOperateTime(new Date());
		log.setOperator(user.getLoginName());
		log.setDescription(matter);
		log.setOperateResult(result);
		log.setIp(CommUtils.getIpAddr(request));
		String hql = "insert into sys_log (operator,operate_time,description,operate_result,ip) values" + " ('"
				+ log.getOperator() + "',NOW(),'" + log.getDescription() + "','" + log.getOperateResult() + "','"
				+ log.getIp() + "')";
		return sqlMapper.insert(hql);

	}

	@Override
	public SysOrganization findByDeptId(Long dId) {
		String sql = "select o.* from sys_organization o where  o.id IN (select org_id from sys_org_"
				+ "dept where dept_id=" + dId + ") LIMIT 1";
		return sqlMapper.selectOne(sql, SysOrganization.class);
	}

	@Override
	public List<SysCity> findByOrgIdAndCity(String orgId) {
		String sql = "";
		if (CommUtils.isNull(orgId)) {
			sql = "select * from sys_city";
		} else {
			sql = "select c.* from sys_orgCity o LEFT JOIN sys_city c on o.city_id=c.id where  o.org_id='" + orgId
					+ "'";
		}
		return sqlMapper.selectList(sql, SysCity.class);
	}

	@Override
	public List<TreeModel> findRuleTree(boolean isRecursion) {
		List<TreeModel> ruleTree = null;
		if (!isRecursion) {
			String hql = "select * from sys_organization";
			List<SysOrganization> ruleList = sqlMapper.selectList(hql, SysOrganization.class);
			if (!CommUtils.isNull(ruleList)) {
				ruleTree = new ArrayList<TreeModel>();
				for (SysOrganization rule : ruleList) {
					TreeModel tree = new TreeModel();
					tree.setId(String.valueOf(rule.getId()));
					tree.setParentId(rule.getFatherId().toString());
					tree.setName(rule.getOrgName());
					ruleTree.add(tree);
				}
			}
		}
		return ruleTree;
	}

}
