package com.waterelephant.listener;

import com.waterelephant.dto.CmsArticleDto;
import com.waterelephant.entity.CmsArticle;
import com.waterelephant.enums.ArticleStatusEnum;
import com.waterelephant.enums.PublishTypeEnum;
import com.waterelephant.service.CmsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

/**
 * 描述
 *
 * @author: renpenghui
 * @date: 2019-03-13 17:31
 **/
@Component
public class ArticleListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private CmsArticleService articleService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() == null){
            //查询所有需要定时发布的定时文章
            CmsArticleDto articleDto = new CmsArticleDto();
            articleDto.setStatus(ArticleStatusEnum.UNPUBLISHED.getId());
            articleDto.setPublishType(PublishTypeEnum.TIMING.getId());
            List<CmsArticle> articleList = articleService.getArticleList(articleDto);
            if(articleList != null && articleList.size()>0){
                for (CmsArticle article : articleList){
                    articleService.execute(article);
                }
            }
        }
    }
}
