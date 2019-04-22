package com.waterelephant.service;


import com.github.pagehelper.PageInfo;
import com.waterelephant.dto.CmsArticleDto;
import com.waterelephant.entity.CmsArticle;

import java.util.List;

public interface CmsArticleService {

    /**
     * 查询分页
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<CmsArticle> selectPage(int pageNum, int pageSize, CmsArticleDto articleBo);

    /**
     * 逻辑删除
     *
     * @param id
     */
    void deleteArticle(Long id);

    /**
     * 添加
     *
     * @param article
     * @return
     */
    int insertSelective(CmsArticle article);

    CmsArticle getOneArticle();

    void deployArticle(CmsArticle article);

    /**
     * 查询单个对象
     *
     * @param id
     * @return
     */
    CmsArticle selectCmsArticle(Long id);

    /**
     * 更新文章
     * @param article
     * @return
     */
    int updateSelective(CmsArticle article);

    /**
     * 对象生成HTML页面返回路径
     *
     * @param cmsArticle
     */
    String generateHtmlByTemplate(CmsArticle cmsArticle);

    /**
     * 最新文章
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<CmsArticle> newArticleList(int pageNum, int pageSize);

    void execute(CmsArticle article);

    /**
     * 相关文章
     *
     * @param pageNum
     * @param pageSize
     * @param keyWord
     * @return
     */
    PageInfo<CmsArticle> relevantArticleList(int pageNum, int pageSize, String keyWord);

    /**
     * 上一篇文章
     *
     * @param deployTime
     * @return
     */
    CmsArticle lastArticle(String deployTime);

    /**
     * 下一篇文章
     *
     * @param deployTime
     * @return
     */
    CmsArticle nextArticle(String deployTime);

    /**
     * 根据ID获取文章详情
     * @param articleId
     * @return
     */
    CmsArticle getArticleLines(String menuName,Integer articleId);

    //根据条件查询
    List<CmsArticle> getArticleList(CmsArticleDto articleBo);

    /**
     * 增加浏览次数
     * @param id
     */
    void addBrowse(Long id);


    int toBaidu(String address,int type);
}
