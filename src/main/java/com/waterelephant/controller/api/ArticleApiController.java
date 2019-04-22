package com.waterelephant.controller.api;

import com.github.pagehelper.PageInfo;
import com.waterelephant.common.utils.CommUtils;
import com.waterelephant.dto.CmsArticleDto;
import com.waterelephant.entity.CmsArticle;
import com.waterelephant.service.CmsArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 描述 提供给外部接口
 *
 * @author: renpenghui
 * @date: 2019-03-12 10:07
 **/
@RestController
@RequestMapping("/article/api")
@Slf4j
@Api(value = "article/api", description = "文章相关查询接口")
public class ArticleApiController {
    @Autowired
    private CmsArticleService cmsArticleService;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 文章列表
     *
     * @return
     */
    @ApiOperation(value = "文章列表分页", notes = "文章列表分页", produces = "application/json; charset=utf-8")
    @RequestMapping(value = "articleList", method = RequestMethod.POST)
    public PageInfo<CmsArticle> articleList(@ApiParam(name = "articleDto", value = "文章内容查询条件", required = true) @RequestBody CmsArticleDto articleDto) {
        if (CommUtils.isNull(articleDto.getPageNum())) {
            articleDto.setPageNum(1);
        }
        if (CommUtils.isNull(articleDto.getPageSize())) {
            articleDto.setPageSize(10);
        }
        //状态为已经发布的
        articleDto.setStatus(3);
        PageInfo<CmsArticle> pageInfo = cmsArticleService.selectPage(articleDto.getPageNum(), articleDto.getPageSize(), articleDto);
        return pageInfo;
    }

    /**
     * 最新文章
     *
     * @return
     */
    @ApiOperation(value = "最新文章分页", notes = "最新文章分页", produces = "application/json; charset=utf-8")
    @RequestMapping(value = "newArticleList", method = RequestMethod.POST)
    public PageInfo<CmsArticle> newArticleList(@ApiParam(name = "articleDto", value = "文章内容查询条件", required = true) @RequestBody CmsArticleDto articleDto) {
        if (CommUtils.isNull(articleDto.getPageNum())) {
            articleDto.setPageNum(1);
        }
        if (CommUtils.isNull(articleDto.getPageSize())) {
            articleDto.setPageSize(10);
        }
        PageInfo<CmsArticle> cmsArticles = cmsArticleService.newArticleList(articleDto.getPageNum(), articleDto.getPageSize());
        return cmsArticles;
    }

    /**
     * 相关文章
     *
     * @return
     */
    @ApiOperation(value = "相关文章分页", notes = "相关文章分页", produces = "application/json; charset=utf-8")
    @RequestMapping(value = "relevantArticle", method = RequestMethod.POST)
    public PageInfo<CmsArticle> relevantArticle(@ApiParam(name = "articleDto", value = "文章内容查询条件", required = true) @RequestBody CmsArticleDto articleDto) {
        if (CommUtils.isNull(articleDto.getPageNum())) {
            articleDto.setPageNum(1);
        }
        if (CommUtils.isNull(articleDto.getPageSize())) {
            articleDto.setPageSize(10);
        }
        PageInfo<CmsArticle> cmsArticles = cmsArticleService.relevantArticleList(articleDto.getPageNum(), articleDto.getPageSize(), articleDto.getKeyWord());
        return cmsArticles;
    }

    /**
     * 上一篇文章
     *
     * @return
     */
    @ApiOperation(value = "上一篇文章", notes = "上一篇文章", produces = "application/json; charset=utf-8")
    @RequestMapping(value = "lastArticle/{deployTime}", method = RequestMethod.POST)
    public CmsArticle lastArticle(@ApiParam(name = "deployTime", value = "时间", required = true) @PathVariable Long deployTime) {
        if (CommUtils.isNull(deployTime)) {
            return null;
        }
        CmsArticle cmsArticles = cmsArticleService.lastArticle(sdf.format(new Date(deployTime)));
        return cmsArticles;
    }

    /**
     * 下一篇文章
     *
     * @return
     */
    @ApiOperation(value = "下一篇文章", notes = "下一篇文章", produces = "application/json; charset=utf-8")
    @RequestMapping(value = "nextArticle/{deployTime}", method = RequestMethod.POST)
    public CmsArticle nextArticle(@ApiParam(name = "deployTime", value = "时间", required = true) @PathVariable Long deployTime) {
        if (CommUtils.isNull(deployTime)) {
            return null;
        }
        CmsArticle cmsArticles = cmsArticleService.nextArticle(sdf.format(new Date(deployTime)));
        return cmsArticles;
    }

    /**
     * 获取文章详情
     *
     * @return
     */
    @ApiOperation(value = "获取文章详情", notes = "获取文章详情", produces = "application/json; charset=utf-8")
    @RequestMapping(value = "getArticleLines/{menuName}/{articleId}", method = RequestMethod.POST)
    public CmsArticle getArticleLines(@ApiParam(name = "menuName", value = "menuName", required = true) @PathVariable("menuName") String menuName,@ApiParam(name = "articleId", value = "articleId", required = true) @PathVariable("articleId") Integer articleId) {
        if (CommUtils.isNull(menuName) || CommUtils.isNull(articleId)) {
            return null;
        }
        CmsArticle cmsArticles = cmsArticleService.getArticleLines(menuName,articleId);
        return cmsArticles;
    }

    /**
     * 浏览次数
     *
     * @return
     */
    @ApiOperation(value = "浏览次数", notes = "浏览次数", produces = "application/json; charset=utf-8")
    @RequestMapping(value = "addBrowse/{id}", method = RequestMethod.POST)
    public void addBrowse(@ApiParam(name = "id", value = "ID", required = true) @PathVariable Long id) {
        if (!CommUtils.isNull(id)) {
            cmsArticleService.addBrowse(id);
        }
    }
}
