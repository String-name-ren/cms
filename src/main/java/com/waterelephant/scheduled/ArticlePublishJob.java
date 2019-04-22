package com.waterelephant.scheduled;

import com.waterelephant.common.utils.StringUtil;
import com.waterelephant.entity.CmsArticle;
import com.waterelephant.enums.ArticleStatusEnum;
import com.waterelephant.service.CmsArticleService;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 描述：定时发布文章job
 *
 * @author: renpenghui
 * @date: 2019-03-13 10:55
 **/
@Slf4j
public class ArticlePublishJob implements Runnable {

    //任务缓存,key为文章id
    private static volatile ConcurrentHashMap<Long, ArticlePublishJob> cache = new ConcurrentHashMap<>();

    //false未取消true已取消
    private volatile AtomicBoolean canceled = new AtomicBoolean(false);//任务取消状态

    private CmsArticle article;

    public ArticlePublishJob(CmsArticle article) {
        this.article=article;
        //取消并清除上一次任务
        cancelAndClearLastJobIfExist();
        //缓存本次任务
        cacheThisJob();
    }

    /**
     * 取消并清除上一次任务
     */
    private void cancelAndClearLastJobIfExist(){
        if (StringUtil.isNotEmpty(article) && StringUtil.isNotEmpty(cache.get(article.getId()))) {
            cache.get(article.getId()).canceled.set(true);
            cache.remove(article.getId());
        }
    }

    /**
     * 缓存本次任务
     */
    private void cacheThisJob(){
        //文章发布任务缓存
        cache.put(article.getId(), this);
    }

    /**
     * 取消任务
     */
    public void cancelJob() {
        this.canceled.set(true);
    }

    /**
     * 清理缓存
     */
    public void clear() {
        cache.remove(article.getId());
    }

    @Override
    public void run() {
        //判断任务是否被取消
        if (!canceled.get()) {
            //获取articleService
            CmsArticleService articleService = SpringContextHolder.getApplicationContext().getBean(CmsArticleService.class);
            try {
                //发布文章
                log.info("任务情况："+cache);
                articleService.deployArticle(article);
                //更新文章状态
                article.setStatus(ArticleStatusEnum.PUBLISHED.getId());
                articleService.updateSelective(article);
                //从缓存中清理本任务
                log.info("定时发布文章成功，文章id："+article.getId()+",cancel状态："+cache.get(article.getId()).canceled);
                clear();
                log.info("任务情况："+cache);
            } catch (Exception e) {
                log.error("文章发布错误：文章id：{}，错误信息：{}" ,article.getId(),e.getMessage());
            }

        }else{
            log.info("文章取消了，文章id："+article.getId()+",cancel状态："+cache.get(article.getId()).canceled);
        }
    }
}
