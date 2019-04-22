package com.waterelephant.common.base;

import java.util.List;

import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.waterelephant.common.utils.SqlMapper;
import com.waterelephant.common.vo.PageRequestVo;
import com.waterelephant.common.vo.PageResponseVo;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

public class BaseService<T, PK> {

	@Autowired
	protected Mapper<T> mapper;

	@Autowired
	protected SqlMapper sqlMapper;

	public T selectOne(T record) {
		return mapper.selectOne(record);
	}

	public List<T> select(T record) {
		return mapper.select(record);
	}

	public int selectCount(T record) {
		return mapper.selectCount(record);
	}

	public T selectByPrimaryKey(PK key) {
		return mapper.selectByPrimaryKey(key);
	}

	@SelectKey(before = false, keyProperty = "id", resultType = Long.class, statementType = StatementType.STATEMENT, statement = "SELECT LAST_INSERT_ID() AS record.id")
	public int insert(T record) {

		return mapper.insert(record);
	}

	public int insertSelective(T record) {
		return mapper.insertSelective(record);
	}

	public int delete(T record) {
		return mapper.delete(record);
	}

	public int deleteByPrimaryKey(PK key) {
		return mapper.deleteByPrimaryKey(key);
	}

	public int updateByPrimaryKey(T record) {
		return mapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(T record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	public int selectCountByExample(Example example) {
		return mapper.selectCountByExample(example);
	}

	public int deleteByExample(Example example) {
		return mapper.deleteByExample(example);
	}

	public List<T> selectByExample(Example example) {
		return mapper.selectByExample(example);
	}

	public int updateByExampleSelective(T record, Example example) {
		return mapper.updateByExampleSelective(record, example);
	}

	public int updateByExample(T record, Example example) {
		return mapper.updateByExample(record, example);
	}

	public PageResponseVo<T> page(PageRequestVo page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<T> list = mapper.selectByExample(page.getExample());
		PageInfo<T> pageInfo = new PageInfo(list);
		return new PageResponseVo(pageInfo.getTotal(), pageInfo.getList());
	}


	}


