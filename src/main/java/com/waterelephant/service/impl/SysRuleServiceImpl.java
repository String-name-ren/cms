package com.waterelephant.service.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import com.waterelephant.common.base.BaseService;
import com.waterelephant.common.entity.SysRule;
import com.waterelephant.common.utils.CommUtils;
import com.waterelephant.common.utils.TreeModel;
import com.waterelephant.service.SysRuleService;

@Service
public class SysRuleServiceImpl extends BaseService<SysRule, Long> implements SysRuleService {


	@Override
	public List<SysRule> findRuleListByParentId(Long parentId) {
		String sql = "select * from sys_rule where parent_id = #{parent_id} order by rule_order";
		return sqlMapper.selectList(sql, parentId, SysRule.class);
	}

	@Override
	public List findAllRuleList() {
		String hql = "select * from sys_rule";
		List ruleList = sqlMapper.selectList(hql);
		return ruleList;
	}

	@Override
	public List findRuleByCode(String ruleCode) {
		String hql = "select * from sys_rule where rule_code = ?";
		List ruleList = sqlMapper.selectList(hql, new Object[] { ruleCode });
		return ruleList;
	}

	@Override
	public SysRule findRuleById(Long id) {
		String sql = "select * from sys_rule where id =#{id}";
		
		return sqlMapper.selectOne(sql, id, SysRule.class);
	}

	@Override
	public void saveRule(SysRule rule) {
		String sql = "insert into sys_rule(parent_id,rule_code,rule_name,rule_type,rule_desc,rule_url,rule_order,rule_picture,rule_ext"
				+ ")values(#{parentId},#{ruleCode},#{ruleName},#{ruleType},#{ruleDesc},#{ruleUrl},#{ruleOrder},#{rulePicture},#{ruleExt})";
		sqlMapper.insert(sql, rule);
	}

	@Override
	public void updateRule(SysRule rule) {
		String sql = "update sys_rule set rule_code=#{ruleCode},rule_name=#{ruleName},rule_type=#{ruleType},rule_desc=#{ruleDesc},rule_url=#{ruleUrl},"
				   + "rule_order=#{ruleOrder},rule_picture=#{rulePicture},rule_ext=#{ruleExt} where id =#{id}";
		sqlMapper.update(sql, rule);
	}

	@Override
	public void deleteRule(Long id) {
		mapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<TreeModel> findRuleTree(Boolean isRecursion) {
		List<TreeModel> ruleTree = null;
		if (isRecursion) {
			ruleTree = findRuleTreeByPId(0L);
		} else {
			String hql = "select * from sys_rule";
			List<SysRule> ruleList = sqlMapper.selectList(hql,SysRule.class);
			if (!CommUtils.isNull(ruleList)) {
				ruleTree = new ArrayList<TreeModel>();
				for (SysRule rule : ruleList) {
					TreeModel tree = new TreeModel();
					tree.setId(String.valueOf(rule.getId()));
					tree.setParentId(rule.getParentId().toString());
					tree.setName(rule.getRuleName());
					tree.setImgPath(rule.getRulePicture());
					ruleTree.add(tree);
				}
			}
		}
		return ruleTree;
	}

	private List<TreeModel> findRuleTreeByPId(Long parentId) {
		List<SysRule> ruleList = this.findRuleListByPId(parentId);
		List<TreeModel> treeList = null;
		if (!CommUtils.isNull(ruleList)) {
			treeList = new ArrayList<TreeModel>();
			for (SysRule rule : ruleList) {
				List<TreeModel> children = findRuleTreeByPId(rule.getId());
				TreeModel tree = new TreeModel();
				tree.setId(String.valueOf(rule.getId()));
				tree.setParentId(String.valueOf(parentId));
				tree.setName(rule.getRuleName());
				tree.setImgPath(rule.getRulePicture());
				tree.setChildren(children);
				treeList.add(tree);
			}
		}
		return treeList;
	}

	private List<SysRule> findRuleListByPId(Long parentId) {
		StringBuilder hql = new StringBuilder("select * from sys_rule r where 1=1 ");
		hql.append("and r.parent_id = ").append(parentId).append(" ");
		hql.append("order by r.rule_order");
		List<SysRule> ruleList = sqlMapper.selectList(hql.toString(),SysRule.class);
		return ruleList;
	}

	@Override
	public List<TreeModel> findRuleTree(Set<Long> ruleIdSet) {
		List<TreeModel> ruleTree = null;
		String hql = "select * from sys_rule";
		List<SysRule> ruleList = sqlMapper.selectList(hql,SysRule.class);
		if (!CommUtils.isNull(ruleList)) {
			ruleTree = new ArrayList<TreeModel>();
			for (SysRule rule : ruleList) {
				TreeModel tree = new TreeModel();
				tree.setId(String.valueOf(rule.getId()));
				//tree.setParentId(String.valueOf(rule.getParent().getId()));
				tree.setParentId(String.valueOf(rule.getParentId()));
				tree.setName(rule.getRuleName());
				tree.setImgPath(rule.getRulePicture());
				if (!CommUtils.isNull(ruleIdSet) && ruleIdSet.contains(rule.getId())) {
					tree.setChecked("true");
				}
				ruleTree.add(tree);
			}
		}
		return ruleTree;
	}

	@Override
	public Long getTotal() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
