package com.waterelephant.mapper;

import com.waterelephant.entity.CmsArticle;

public interface CmsArticleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CmsArticle record);

    int insertSelective(CmsArticle record);

    CmsArticle selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CmsArticle record);

    int updateByPrimaryKeyWithBLOBs(CmsArticle record);

    int updateByPrimaryKey(CmsArticle record);
}