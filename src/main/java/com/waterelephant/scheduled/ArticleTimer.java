package com.waterelephant.scheduled;

import com.waterelephant.common.utils.CommUtils;
import com.waterelephant.entity.CmsArticle;
import com.waterelephant.service.CmsArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 描述
 *
 * @author: renpenghui
 * @date: 2019-03-08 10:38
 **/
@Component
@Slf4j
public class ArticleTimer {

    @Autowired
    private CmsArticleService articleService;

    /**
     * 自动发布草稿箱中的文章
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void deploy(){
        //获取草稿箱中一条数据
        CmsArticle article = articleService.getOneArticle();
        if(article != null){
            articleService.deployArticle(article);
        }
        log.info("********自动发布草稿箱文章*********");
    }

}
