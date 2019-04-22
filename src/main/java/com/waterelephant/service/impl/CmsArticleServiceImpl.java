package com.waterelephant.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.waterelephant.common.utils.ArticleConstant;
import com.waterelephant.common.utils.BeanUtils;
import com.waterelephant.common.utils.CommUtils;
import com.waterelephant.common.utils.HttpClientUtil;
import com.waterelephant.dto.CmsArticleDto;
import com.waterelephant.entity.CmsArticle;
import com.waterelephant.enums.ArticleStatusEnum;
import com.waterelephant.enums.BaiduTypeEnum;
import com.waterelephant.enums.PublishTypeEnum;
import com.waterelephant.mapper.CmsArticleMapper;
import com.waterelephant.mapper.custom.CmsArticleCustomMapper;

import com.waterelephant.scheduled.ArticlePublishJob;
import com.waterelephant.service.CmsArticleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class CmsArticleServiceImpl implements CmsArticleService {

    @Value("${baidu_urls}")
    private String  baidu_urls;

    @Value("${baidu_update}")
    private String  baidu_update;

    @Value("${baidu_del}")
    private String  baidu_del;

    @Autowired
    CmsArticleMapper cmsArticleMapper;

    @Autowired
    CmsArticleCustomMapper cmsArticleCustomMapper;

    /**
     * 创建线程池
     */
    public static ScheduledExecutorService service = Executors.newScheduledThreadPool(20);

    /**
     * 定时发布文章
     */
    @Override
    public void execute(CmsArticle article) {
        /**新建任务，并设定执行时间*/
        ArticlePublishJob job = new ArticlePublishJob(article);
        if (Objects.equals(article.getPublishType(), PublishTypeEnum.TIMING.getId()) && article.getDeployTime() != null) {
            System.out.println(article.getDeployTime().getTime());
            System.out.println(System.currentTimeMillis());
            long delay = article.getDeployTime().getTime()/1000 - System.currentTimeMillis()/1000;
            if (delay <= 0) {
                Random ra = new Random();
                delay = ra.nextInt(10) + 1;
            }
            service.schedule(job, delay, TimeUnit.SECONDS);
            log.info("文章:" + article + "已添加到定时发布任务！");
        }
    }


    @Override
    public PageInfo<CmsArticle> selectPage(int pageNum, int pageSize, CmsArticleDto articleBo) {
        //使用PageHelper
        PageHelper.startPage(pageNum, pageSize);
        try {
            List<CmsArticle> list = cmsArticleCustomMapper.selectArticleByCond(articleBo);
            PageInfo<CmsArticle> pageInfo = new PageInfo<>(list);
            return pageInfo;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }

    }


    @Override
    public List<CmsArticle> getArticleList(CmsArticleDto articleBo) {
        List<CmsArticle> list = null;
        try{
            list = cmsArticleCustomMapper.selectArticleByCond(articleBo);
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
        return list;
    }

    @Override
    public void deleteArticle(Long id) {
        try{
            CmsArticle record = new CmsArticle();
            record.setId(id);
            record.setDelFlag(1);
            int result = cmsArticleMapper.updateByPrimaryKeySelective(record);
            if(result>0){
                CmsArticle article = cmsArticleMapper.selectByPrimaryKey(id);
                this.toBaidu(article.getArticleAddress(),BaiduTypeEnum.del.getId());
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @Override
    public int insertSelective(CmsArticle article) {
        int count = cmsArticleMapper.insertSelective(article);
        if(count > 0 && Objects.equals(article.getPublishType(), PublishTypeEnum.TIMING.getId()) && article.getDeployTime() != null){
            this.execute(article);
        }
        return count;
    }

    @Override
    public CmsArticle getOneArticle() {
        return cmsArticleCustomMapper.getOneArticle();
    }

    @Override
    public void deployArticle(CmsArticle article) {
        if(CommUtils.isNull(article.getDeployTime())){
            article.setDeployTime(new Date());
        }
        article.setStatus(ArticleStatusEnum.PUBLISHED.getId());
        int tobaidu = this.toBaidu(article.getArticleAddress(),BaiduTypeEnum.save.getId());
        if(tobaidu>0){
            article.setSuccessed(1);
            log.info("推送百度成功：id={},address={}",article.getId(),article.getArticleAddress());
        }else{
            article.setSuccessed(0);
            log.info("推送百度失败：id={},address={}",article.getId(),article.getArticleAddress());
        }
        cmsArticleMapper.updateByPrimaryKeySelective(article);
        System.out.println("文章发布了。。。。。。。。。。。。。。");
    }

    @Override
    public CmsArticle selectCmsArticle(Long id) {
        return cmsArticleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateSelective(CmsArticle article) {
        int count = 0;
        article.setUpdateTime(new Date());
        try{
            count = cmsArticleMapper.updateByPrimaryKeySelective(article);
            if(count > 0 && Objects.equals(article.getPublishType(), PublishTypeEnum.TIMING.getId()) && article.getDeployTime() != null && !Objects.equals(article.getStatus(), ArticleStatusEnum.PUBLISHED.getId())){
                this.execute(article);
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return count;
    }

    /**
     * 使用模板生成HTML页面
     *
     * @param cmsArticle
     * @return 返回生成的页面地址
     */
    @Override
    public String generateHtmlByTemplate(CmsArticle cmsArticle) {
        if (CommUtils.isNull(cmsArticle)) {
            return "";
        }
        try {
            Map<String, Object> map = BeanUtils.object2Map(cmsArticle);
            //获取classpath路径
            String path = this.getClass().getResource("/").getPath();
            File file = new File(path + File.separator + "article-muban.html");
            //读取内容
            String content = FileUtils.readFileToString(file, "utf-8");

            //替换内容
            StrSubstitutor strSubstitutor = new StrSubstitutor(map);
            String newContent = strSubstitutor.replace(content);

            //生成文件
            File srcFile = new File(ArticleConstant.html_address + cmsArticle.getId() + ".html");
            FileUtils.write(srcFile, newContent, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("生成html发生错误：" + e.getMessage());
        }
        return "";
    }


    @Override
    public PageInfo<CmsArticle> newArticleList(int pageNum, int pageSize) {
        //使用PageHelper
        PageHelper.startPage(pageNum, pageSize);
        try {
            List<CmsArticle> list = cmsArticleCustomMapper.getNewArticle();
            PageInfo<CmsArticle> pageInfo = new PageInfo<>(list);
            return pageInfo;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }

    }

    @Override
    public PageInfo<CmsArticle> relevantArticleList(int pageNum, int pageSize, String keyWord) {
        //使用PageHelper
        PageHelper.startPage(pageNum, pageSize);
        try {
            List<CmsArticle> list = cmsArticleCustomMapper.getRelevantArticle(keyWord);
            PageInfo<CmsArticle> pageInfo = new PageInfo<>(list);
            return pageInfo;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }

    }

    @Override
    public CmsArticle lastArticle(String deployTime) {
        try {
            CmsArticle list = cmsArticleCustomMapper.lastArticle(deployTime);
            return list;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public CmsArticle nextArticle(String deployTime) {
        try {
            CmsArticle list = cmsArticleCustomMapper.nextArticle(deployTime);
            return list;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public CmsArticle getArticleLines(String menuName,Integer articleId) {
        try {
            CmsArticle article = cmsArticleCustomMapper.selectByMenuNamAndId(menuName,articleId);
            return article;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public void addBrowse(Long id) {
        try {
            cmsArticleCustomMapper.addBrowse(id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public int toBaidu(String address, int type) {
        String url = null;
        if(BaiduTypeEnum.save.getId()==type){
            url = baidu_urls ;
        }
        if(BaiduTypeEnum.update.getId()==type){
            url = baidu_update ;
        }
        if(BaiduTypeEnum.del.getId()==type){
            url = baidu_del ;
        }
        return HttpClientUtil.post(url,"utf-8",address);
    }
}
