package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.waterelephant.common.base.BaseService;
import com.waterelephant.common.entity.SysCity;
import com.waterelephant.common.entity.SysOrganization;
import com.waterelephant.common.exception.BusException;
import com.waterelephant.common.utils.CommUtils;
import com.waterelephant.common.utils.Page;
import com.waterelephant.common.utils.TreeModel;
import com.waterelephant.common.vo.PageRequestVo;
import com.waterelephant.service.SysCityService;

@Service
public class SysCityServiceImpl extends BaseService<SysCity, Long> implements SysCityService {

	@Override
	public Page<SysCity> query(int[] pageParams, String cityName, String orgId, String lft, String rgt) {
		String hql = "select c.id,c.city_name,c.city_desc, o.org_name From sys_city c LEFT JOIN sys_orgCity oc on oc.city_id=c.id LEFT JOIN sys_organization o on oc.org_id=o.id  where 1=1";
		if (!CommUtils.isNull(cityName)) {
			hql += " and c.city_name=" + cityName + "";
		}
		if (!CommUtils.isNull(orgId)) {

			hql += " and EXISTS(select id from sys_organization where lft>='" + lft + "' and rgt<='" + rgt
					+ "' and o.id=id )";
		}
		String sql = hql.substring(hql.indexOf("From"));
		hql += "  limit #{pageNum},#{pageSize}";
		Long totalCount = sqlMapper.selectOne("select count(c.id) " + sql, Long.class);
		Page<SysCity> page = new Page<>();
		PageRequestVo pVo = new PageRequestVo((pageParams[0] - 1) * pageParams[1], pageParams[1]);
		page.setPageNo((pageParams[0] - 1) * pageParams[1]);
		page.setPageSize(pageParams[1]);
		page.setResult(sqlMapper.selectList(hql, pVo, SysCity.class));
		page.setTotalCount(totalCount);
		return page;
	}

	@Override
	public List<TreeModel> findChannelTree() {
		List<TreeModel> channelTree = null;
		String hql = "select * from sys_organization ";
		channelTree = new ArrayList<TreeModel>();
		List<SysOrganization> list = sqlMapper.selectList(hql, SysOrganization.class);
		for (SysOrganization channel : list) {
			TreeModel tree = new TreeModel();
			tree.setId(String.valueOf(channel.getId()));
			tree.setParentId(String.valueOf(channel.getFatherId()));
			tree.setName(channel.getOrgName());
			tree.setImgPath(String.valueOf(channel.getLft()));
			tree.setUrl(String.valueOf(channel.getRgt()));
			channelTree.add(tree);
		}
		return channelTree;
	}

	@Override
	public void add(String cityName, String cityDesc, String orgId) throws BusException {
		if (CommUtils.isNull(orgId)) {
			throw new BusException("机构选择异常");
		}
		if (CommUtils.isNull(cityDesc)) {
			throw new BusException(" 备注为空");
		}
		if (CommUtils.isNull(cityName)) {
			throw new BusException("地域名为空");
		}
		SysCity sysCity = new SysCity();
		sysCity.setCityName(cityName);
		sysCity.setCityDesc(cityDesc);
		mapper.insert(sysCity);
		sqlMapper.insert("insert into sys_orgCity (org_id,city_id) values(" + orgId + "," + sysCity.getId() + ")");
	}

	@Override
	public void deleteCity(String id) throws BusException {
		if (CommUtils.isNull(id)) {
			throw new BusException("地域ID为空");
		}
		sqlMapper.delete("delete from sys_city where id=" + id + "");
		sqlMapper.delete("delete from  sys_orgCity where city_id=" + id);

	}

	@Override
	public SysCity queryCity(String id) {
		return sqlMapper.selectOne("select id,city_name,city_desc from sys_city where id='" + id + "'", SysCity.class);
	}

	@Override
	public void updateSave(String id, String orgId, String cityName, String cityDesc) {
		sqlMapper
				.update("update sys_city set city_name='" + cityName + "',city_desc='" + cityDesc + "' where id=" + id);
		Long cou = sqlMapper.selectOne("select count(id) from sys_orgCity where city_id='" + id + "'", Long.class);
		if (cou == 0) {
			sqlMapper.insert("insert into sys_orgCity (org_id,city_id) values(" + orgId + "," + id + ")");
		} else {
			sqlMapper.update("update sys_orgCity set org_id=" + orgId + " where city_id=" + id);
		}
	}

	@Override
	public Map<String, Object> queryOrg(String id) {
		String sql = "select o.id,o.org_name from sys_orgCity c LEFT JOIN sys_organization o on c.org_id=o.id where c.city_id='"
				+ id + "'";
		return sqlMapper.selectOne(sql);
	}

}
