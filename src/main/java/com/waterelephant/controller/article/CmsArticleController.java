package com.waterelephant.controller.article;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.waterelephant.common.entity.SysUser;
import com.waterelephant.common.entity.dto.BjuiDto;
import com.waterelephant.common.utils.*;
import com.waterelephant.dto.CmsArticleDto;
import com.waterelephant.entity.CmsArticle;
import com.waterelephant.entity.CmsMenu;
import com.waterelephant.enums.ArticleStatusEnum;
import com.waterelephant.enums.BaiduTypeEnum;
import com.waterelephant.enums.DelFlagEnum;
import com.waterelephant.enums.PublishTypeEnum;
import com.waterelephant.mapper.CmsGlobalFieldMapper;
import com.waterelephant.service.CmsArticleService;
import com.waterelephant.service.CmsGlobalFieldService;
import com.waterelephant.service.CmsMenuService;
import com.waterelephant.service.ManageSevice;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/article")
@Slf4j
public class CmsArticleController {
    @Autowired
    private CmsArticleService cmsArticleService;
    @Autowired
    private ManageSevice manageSevice;
    @Autowired
    CmsMenuService cmsMenuService;
    @Autowired
    CmsGlobalFieldService globalFieldService;

    @Value("${server_protocol}")
    private String serverProtocol;
    @Value("${server_ip}")
    private String serverIp;
    @Value("${server_port:80}")
    private String serverPort;

    /**
     * 查询文章
     *
     * @param request
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findArticleList.do")
    public String findArticleList(HttpServletRequest request, CmsArticleDto articleDto, ModelMap modelMap) {
        try{
            int[] pageParams = PageUtil.init(request);
            PageInfo<CmsArticle> pageInfo = cmsArticleService.selectPage(pageParams[0],pageParams[1], articleDto);
            modelMap.put("page",pageInfo);
            modelMap.put("cond", articleDto);
            modelMap.put("serverProtocol", serverProtocol);
            modelMap.put("serverIp", serverIp);
            modelMap.put("serverPort", serverPort);
        }catch (Exception e){
            log.error("查询文章异常，详情:",e);
        }
        return "article/article_list";
    }

    /**
     * 删除文章
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/deleteArticle.do")
    @ResponseBody
    public BjuiDto deleteArticle(HttpServletRequest request) {
        BjuiDto bjuiDto = new BjuiDto();
        try {
            String delids = request.getParameter("delids");
            if (StringUtils.isNotEmpty(delids)) {
                String[] ids = delids.split(",");
                for (String id : ids) {
                    cmsArticleService.deleteArticle(Long.parseLong(id));
                }
            }
            String id = request.getParameter("id");
            if (StringUtils.isNotEmpty(id)) {
                cmsArticleService.deleteArticle(Long.parseLong(id));
            }
            bjuiDto.setTabid("cms_article");
            bjuiDto.setStatusCode("200");
            bjuiDto.setMessage("删除成功！");
        } catch (Exception e) {
            bjuiDto.setStatusCode("300");
            bjuiDto.setMessage("删除失败！");
            log.error("删除文章失败！", e);
        }
        return bjuiDto;
    }

    /**
     * 跳转新增/修改页面
     * @return
     */
    @RequestMapping(value = "/getArticleAddOrEditPage.do")
    public String getArticleAddPage(HttpServletRequest request,ModelMap modelMap) {
        String id = request.getParameter("id");
        if (StringUtils.isNotEmpty(id)){
            CmsArticle cmsArticle = cmsArticleService.selectCmsArticle(Long.valueOf(id));
            modelMap.put("article", cmsArticle);
        }

        //树结构数据
        List<Tree> treeList = cmsMenuService.getMenuTree(0);
        String treeJsonStr= JSON.toJSONString(treeList);
        modelMap.put("treeJsonStr",treeJsonStr);
        return "article/article_addOrEdit";
    }


    /**
     * 保存或者更新
     * @param request
     * @return
     */
    @RequestMapping(value = "/saveOrUpdate.do")
    @ResponseBody
    public BjuiDto saveOrUpdate(HttpServletRequest request,CmsArticle article){
        BjuiDto bjuiDto = new BjuiDto();
        //某些字段为空，做些默认处理
        if(CommUtils.isNull(article.getAuthor())){
            SysUser user = (SysUser) request.getSession().getAttribute(SystemConstant.SESSION_USER);
            article.setAuthor(user.getRealName());
        }
        if(CommUtils.isNull(article.getAbstracts())){
            article.setAbstracts(HtmlUtil.getText(article.getContent()));
        }
        if(CommUtils.isNull(article.getImgurl())){
            article.setImgurl(HtmlUtil.getImageUrl(article.getContent()));
        }
        if(CommUtils.isNull(article.getSource())){
            article.setSource(article.getMenuName());
        }
        if(CommUtils.isNull(article.getKeyWord())){
            article.setKeyWord(article.getTitle()+","+article.getMenuName()+","+globalFieldService.getGlobalKeyword());
        }
        if(CommUtils.isNull(article.getSeoTitle())){
            article.setSeoTitle(article.getTitle());
        }
        if(CommUtils.isNull(article.getSeoDescription())){
            article.setSeoDescription(article.getAbstracts());
        }
        if(!CommUtils.isNull(article.getContent())){
            article.setContent(HtmlUtil.addAttr(article.getContent(),article.getKeyWord()));
        }
        try{
            String id = request.getParameter("id");
            if(article.getPublishType().intValue() == PublishTypeEnum.AUTOMATIC.getId().intValue()){
                article.setStatus(ArticleStatusEnum.DRAFT.getId());
            }else {
                article.setStatus(ArticleStatusEnum.UNPUBLISHED.getId());
            }
            if (StringUtils.isNotEmpty(id)){
                //如果修改了栏目，相应修改article_id，修改文章地址
                CmsArticle oldArticle = cmsArticleService.selectCmsArticle(Long.parseLong(id));
                if(!Objects.equals(article.getMenuId(),oldArticle.getMenuId())){
                    article.setArticleId(cmsMenuService.updateMenuNum(article.getMenuId()));
                    article.setArticleAddress(serverIp+":"+serverPort+"/"+article.getMenuName()+"/"+article.getArticleId()+".html");
                }
                int result = cmsArticleService.updateSelective(article);
                if(result > 0 && article.getPublishType() ==  ArticleStatusEnum.PUBLISHED.getId()){
                    //将文章推送给百度
                    cmsArticleService.toBaidu(article.getArticleAddress(), BaiduTypeEnum.update.getId());
                }
                bjuiDto.setStatusCode("200");
                bjuiDto.setMessage("文章修改成功");
                bjuiDto.setTabid("cms_article");
                bjuiDto.setCloseCurrent(true);
                //保存到日志
                manageSevice.saveSysLog("修改文章", SystemConstant.SUCCESSFUL_OPERATION + article.getTitle(), request);
            }else{
                article.setArticleId(cmsMenuService.updateMenuNum(article.getMenuId()));
                article.setArticleAddress(serverIp+":"+serverPort+"/"+article.getMenuName()+"/"+article.getArticleId()+".html");
                article.setCreateTime(new Date());
                int result = cmsArticleService.insertSelective(article);
                if(result > 0){
                    //将文章推送给百度

                }
                bjuiDto.setStatusCode("200");
                bjuiDto.setMessage("文章保存成功");
                bjuiDto.setTabid("cms_article");
                bjuiDto.setCloseCurrent(true);
                //保存到日志
                manageSevice.saveSysLog("添加文章", SystemConstant.SUCCESSFUL_OPERATION+article.getTitle(), request);
            }
        }catch (Exception e){
            log.error("文章保存或者更新失败,异常信息:{}",e);
            bjuiDto.setStatusCode("300");
            bjuiDto.setMessage("文章保存或者更新失败");
        }
        return bjuiDto;
    }

    /**
     * 操作文章状态 发布3 移至草稿箱2
     * @param request
     * @param article
     * @return
     */
    @ResponseBody
    @RequestMapping("/operationAticleStatus.do")
    public BjuiDto operationAticleStatus(HttpServletRequest request,CmsArticle newArticle){
        BjuiDto bjuiDto = new BjuiDto();
        CmsArticle article = cmsArticleService.selectCmsArticle(newArticle.getId());
        article.setStatus(newArticle.getStatus());
        try{
            if (null != article && null != article.getStatus() && article.getStatus()>0
                    && null != article.getId() && article.getId()>0){
                //发布时更新发布时间
                if (Objects.equals(ArticleStatusEnum.PUBLISHED.getId(), article.getStatus())){
                    article.setDeployTime(new Date());
                }
                int result = cmsArticleService.updateSelective(article);
                //发布文章 生成静态页
                if (Objects.equals(ArticleStatusEnum.PUBLISHED.getId(), article.getStatus())){
                    cmsArticleService.deployArticle(article);
                }
                if (result>0){
                    bjuiDto.setStatusCode("200");
                    bjuiDto.setMessage("更新文章状态成功");
                    //保存到日志
                    manageSevice.saveSysLog("更新文章状态", SystemConstant.SUCCESSFUL_OPERATION+article.getTitle(), request);
                }else{
                    bjuiDto.setStatusCode("300");
                    bjuiDto.setMessage("更新文章状态失败");
                }
            }else {
                bjuiDto.setStatusCode("300");
                bjuiDto.setMessage("更新文章状态失败,参数错误");
            }
        }catch (Exception e){
            log.error("更新文章状态失败,异常信息:{} "+e);
            bjuiDto.setStatusCode("300");
            bjuiDto.setMessage("更新文章状态失败,出现异常");
        }
        return bjuiDto;
    }


    //下载上传模板
    @RequestMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
        //获取classpath路径
        String path = this.getClass().getResource("/").getPath();
        download(response,path,"muban.xls");
    }

    /**
     * @param resp
     * @param path         文件路径
     * @param downloadName 文件下载时名字
     */
    public static void download(HttpServletResponse resp, String path, String downloadName) {
        String fileName = null;
        try {
            fileName = new String(downloadName.getBytes("GBK"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        File file = new File(path+File.separator+downloadName);
        resp.reset();
        resp.setContentType("application/octet-stream");
        resp.setCharacterEncoding("utf-8");
        resp.setContentLength((int) file.length());
        resp.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = resp.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(bis != null){
                    bis.close();
                }
                if(os != null){
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 跳转上传页面
     * @return
     */
    @RequestMapping(value = "/uploadPage.do")
    public String uploadTBBoutiqueList(){
        return "article/articleUpload";
    }

    /**
     * 导入文章信息
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/uploadArticle")
    @ResponseBody
    public BjuiDto uploadArticle(@RequestParam(value = "excelFile") MultipartFile file, HttpServletRequest request) throws IOException {
        BjuiDto bjuiDto = new BjuiDto();
        int successCount = 0;
        int errCount = 0;

        if (file == null || file.getSize() == 0) {
            bjuiDto.setStatusCode("300");
            bjuiDto.setMessage("导入失败，文件为空！");
            return bjuiDto;
        }
        String name = file.getOriginalFilename();
        if (name == null || ExcelUtil.EMPTY.equals(name)) {
            bjuiDto.setStatusCode("300");
            bjuiDto.setMessage("导入失败，文件内容为空！");
            return bjuiDto;
        }
        if (!name.toUpperCase().endsWith("XLS") && !name.toUpperCase().endsWith("XLSX")) {
            bjuiDto.setStatusCode("300");
            bjuiDto.setMessage("必须上传excel文件");
            return bjuiDto;
        }
        //读取Excel数据到List中
        List<ArrayList<String>> list = null;
        try {
            list = new ExcelRead().readExcel(file);
        } catch (IOException e1) {
            e1.printStackTrace();
            bjuiDto.setStatusCode("300");
            bjuiDto.setMessage("导入失败，读取Excel出错！");
            return bjuiDto;
        }
        int i = 0;
        if (list != null && list.size() > 0) {
            for (ArrayList<String> arr : list) {
                log.info("开始导入第"+i+"条数据");
                i++;
                try {
                    CmsArticle article = new CmsArticle();
                    if (StringUtils.isEmpty(arr.get(0)) || StringUtils.isEmpty(arr.get(1)) || StringUtils.isEmpty(arr.get(2)) || StringUtils.isEmpty(arr.get(3))) {
                        throw new RuntimeException("该行信息不完整");
                    }
                    article.setTitle(arr.get(0));
                    article.setContent(arr.get(1));
                    Integer menuId = Integer.parseInt(arr.get(2));
                    CmsMenu cmsMenu = cmsMenuService.selectByPrimaryKey(menuId);
                    if(cmsMenu != null){
                        article.setMenuName(cmsMenu.getDirectory());
                    }
                    article.setMenuId(menuId);
                    article.setMenuNameCn(arr.get(3));
                    article.setAuthor(arr.get(4));
                    article.setAbstracts(arr.get(5));
                    article.setSource(arr.get(6));
                    article.setKeyWord(arr.get(7));
                    article.setSeoTitle(arr.get(8));
                    article.setSeoDescription(arr.get(9));
                    article.setStatus(ArticleStatusEnum.DRAFT.getId());
                    article.setDelFlag(DelFlagEnum.NO.getId());
                    //某些字段为空，做些默认处理
                    if(CommUtils.isNull(article.getAuthor())){
                        SysUser user = (SysUser) request.getSession().getAttribute(SystemConstant.SESSION_USER);
                        article.setAuthor(user.getRealName());
                    }
                    if(CommUtils.isNull(article.getAbstracts())){
                        article.setAbstracts(HtmlUtil.getText(article.getContent()));
                    }
                    if(CommUtils.isNull(article.getImgurl())){
                        article.setImgurl(HtmlUtil.getImageUrl(article.getContent()));
                    }
                    if(CommUtils.isNull(article.getSource())){
                        article.setSource(article.getMenuName());
                    }
                    if(CommUtils.isNull(article.getKeyWord())){
                        article.setKeyWord(article.getTitle()+","+article.getMenuName()+","+globalFieldService.getGlobalKeyword());
                    }
                    if(CommUtils.isNull(article.getSeoTitle())){
                        article.setSeoTitle(article.getTitle());
                    }
                    if(CommUtils.isNull(article.getSeoDescription())){
                        article.setSeoDescription(article.getAbstracts());
                    }
                    if(!CommUtils.isNull(article.getContent())){
                        article.setContent(HtmlUtil.addAttr(article.getContent(),article.getKeyWord()));
                    }
                    article.setArticleId(cmsMenuService.updateMenuNum(article.getMenuId()));
                    //TODO 文章地址
                    article.setArticleAddress(serverIp+":"+serverPort+"/"+article.getMenuName()+"/"+article.getArticleId()+".html");
                    Date date = new Date();
                    article.setCreateTime(date);
                    article.setUpdateTime(date);
                    article.setPublishType(PublishTypeEnum.AUTOMATIC.getId());
                    //插入数据
                    cmsArticleService.insertSelective(article);
                    //记录成功数
                    successCount++;
                    log.info("导入第"+i+"条数据结束");
                } catch (Exception e) {
                    log.error("文章信息当前数据导入失败：" + arr + "  " + e);
                    e.printStackTrace();
                    //记录失败数
                    errCount++;
                }
            }
        }
        //保存到日志
        manageSevice.saveSysLog("批量导入文章", "导入文章信息成功!" + "成功条数：" + successCount + ",失败条数" + errCount, request);
        bjuiDto.setStatusCode("200");
        bjuiDto.setMessage("导入文章信息成功!" + "成功条数：" + successCount + ",失败条数" + errCount);
        bjuiDto.setTabid("cms_article");
        return bjuiDto;
    }

}

