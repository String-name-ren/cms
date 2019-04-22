package com.waterelephant.mapper.custom;

import com.waterelephant.dto.CmsArticleDto;
import com.waterelephant.entity.CmsArticle;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: cms
 * @Description:文章自定义方法
 * @Date: 2019/3/12 14:00
 * @Author: 齐腾杰
 */
public interface CmsArticleCustomMapper {
    /**
     * 模糊搜索查询
     * @param article
     * @return
     */
    List<CmsArticle> selectArticleByCond(CmsArticleDto article);

    /**
     * 获取草稿箱里面的一条数据
     * @return
     */
    CmsArticle getOneArticle();

    /**
     * 最新文章
     *
     * @return
     */
    List<CmsArticle> getNewArticle();

    /**
     * 关键字文章查询
     *
     * @param keyWord
     * @return
     */
    List<CmsArticle> getRelevantArticle(@Param("keyWord") String keyWord);

    /**
     * 上一篇
     *
     * @param deployTime
     * @return
     */
    CmsArticle lastArticle(@Param("deployTime") String deployTime);

    /**
     * 下一篇
     *
     * @param deployTime
     * @return
     */
    CmsArticle nextArticle(@Param("deployTime") String deployTime);

    /**
     * 增加浏览次数
     *
     * @param id
     */
    void addBrowse(@Param("id") Long id);

    CmsArticle selectByMenuNamAndId(@Param("menuName") String menuName,@Param("articleId") Integer articleId);

}
