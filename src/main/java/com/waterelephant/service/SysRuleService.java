
package com.waterelephant.service;

import java.util.List;
import java.util.Set;

import com.waterelephant.common.entity.SysRule;
import com.waterelephant.common.exception.BusException;
import com.waterelephant.common.utils.TreeModel;

/**
 * 权限服务接口
 *
 * @author HWang
 */
public interface SysRuleService {

	List<SysRule> findRuleListByParentId(Long parentId);

	/**
	 * 
	 * @param isRecursion 是否使用递归
	 * @return
	 */
	List<TreeModel> findRuleTree(Boolean isRecursion);

	List<TreeModel> findRuleTree(Set<Long> ruleIdSet);

	List<SysRule> findAllRuleList();

	List findRuleByCode(String ruleCode);

	SysRule findRuleById(Long id) throws BusException;

	void saveRule(SysRule rule);

	void updateRule(SysRule rule);

	void deleteRule(Long id) throws BusException;

	/**
	 * 获取总页数
	 */
	Long getTotal();
}
