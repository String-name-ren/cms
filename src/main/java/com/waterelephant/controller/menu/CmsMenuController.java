package com.waterelephant.controller.menu;

import com.waterelephant.common.entity.dto.BjuiDto;
import com.waterelephant.common.utils.*;
import com.waterelephant.entity.CmsGlobalField;
import com.waterelephant.entity.CmsMenu;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @program: cms
 * @Description:
 * @Date: 2019/3/13 9:35
 * @Author: 齐腾杰
 */

@Slf4j
@Controller
@RequestMapping("/menu")
public class CmsMenuController {

    @Value("${server_protocol}")
    private String serverProtocol;
    @Value("${server_ip}")
    private String serverIp;
    @Value("${server_port:80}")
    private String serverPort;

    @Autowired
    CmsMenuService cmsMenuService;

    @Autowired
    private ManageSevice manageSevice;

    @Autowired
    private CmsGlobalFieldService globalFieldService;
    /**
     *  跳转到栏目页 并查询数据
     * @param modelMap
     * @return
     */
    @RequestMapping("/findMenuList.do")
    public String findMenuList(ModelMap modelMap){
        //查询树集合
        List<TreeModel> treeList = cmsMenuService.findMenuTree(false);
        modelMap.put("treeList",treeList);
        return "menu/cms_menu";
    }

    /**
     * 根据父ID查询栏目列表
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("/findMenuListByParentId.do")
    public String findMenuListByParentId(HttpServletRequest request, ModelMap modelMap){
        //查询父对象
        String parentId = request.getParameter("parentId");
        CmsMenu parent = cmsMenuService.selectByPrimaryKey(Integer.valueOf(parentId));
        //查询子列表数据
        List<CmsMenu> menuList = cmsMenuService.findMenuListByParentId(Integer.valueOf(parentId));
        if (!CommUtils.isNull(menuList)){
            for (CmsMenu menu:menuList){
                menu.setParent(parent);
            }
        }
        modelMap.put("menuList",menuList);
        modelMap.put("parentId",parentId);
        modelMap.put("serverProtocol", serverProtocol);
        modelMap.put("serverIp", serverIp);
        modelMap.put("serverPort", serverPort);
        return "menu/cms_menu_list";
    }

    /**
     * 跳转添加栏目页面
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "getMenuAddPage.do")
    public String getMenuAddPage(HttpServletRequest request, ModelMap modelMap){
        //父栏目ID
        String parentId = request.getParameter("parentId");
        modelMap.put("parentId", parentId);
        if ("0".equals(parentId)){
            modelMap.put("parentName","根目录");
        }else{
            CmsMenu menu = cmsMenuService.selectByPrimaryKey(Integer.valueOf(parentId));
            modelMap.put("parentName",menu.getName());
        }
        return "menu/cms_menu_add";
    }

    /**
     * 跳转编辑栏目页面
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "getMenuEditPage.do")
    public String getMenuEditPage(HttpServletRequest request, ModelMap modelMap){
        //查询出栏目对象
        String id = request.getParameter("id");
        String parentId = request.getParameter("pId");
        CmsMenu menu = cmsMenuService.selectByPrimaryKey(Integer.valueOf(id));
        CmsMenu parent = cmsMenuService.selectByPrimaryKey(Integer.valueOf(parentId));
        if ("0".equals(parentId)){
            modelMap.put("parentName","根目录");
        }else{
            modelMap.put("parentName",parent.getName());
        }
        modelMap.put("menu", menu);
        return "menu/cms_menu_edit";
    }

    /**
     * 新增栏目
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "save.do")
    @ResponseBody
    public BjuiDto saveMenu(HttpServletRequest request, CmsMenu cmsMenu) throws Exception {
        BjuiDto bjuiDto = new BjuiDto();
        this.setDefaultValue(cmsMenu);
        try{
            //保存
            cmsMenu.setCreateTime(new Date());
            cmsMenu.setUpdateTime(new Date());
            cmsMenuService.insertSelective(cmsMenu);
            bjuiDto.setStatusCode("200");
            bjuiDto.setMessage("添加成功！");
            bjuiDto.setTabid("cms_menu");
            bjuiDto.setCloseCurrent(true);
            //保存到日志
            manageSevice.saveSysLog("添加栏目", SystemConstant.SUCCESSFUL_OPERATION+",添加栏目名为:"+cmsMenu.getName(), request);
        }catch (Exception e){
            bjuiDto.setStatusCode("300");
            bjuiDto.setMessage("添加栏目异常");
            log.error("添加栏目,异常信息：{}",e);
        }

        return bjuiDto;
    }

    public void setDefaultValue(CmsMenu cmsMenu){
        if(CommUtils.isNull(cmsMenu.getTitle())){
            cmsMenu.setTitle(globalFieldService.getGlobalTitle());
        }
        if(CommUtils.isNull(cmsMenu.getKeywords())){
            cmsMenu.setKeywords(globalFieldService.getGlobalKeyword());
        }
        if(CommUtils.isNull(cmsMenu.getDescription())){
            cmsMenu.setDescription(globalFieldService.getGlobalDescription());
        }
        if(CommUtils.isNull(cmsMenu.getUrl())){
            cmsMenu.setUrl(ArticleConstant.menu_url+"/"+ PinYinUtils.getPinYinHeadChar(cmsMenu.getName()));
        }
    }

    /**
     * 编辑栏目
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "update.do")
    @ResponseBody
    public BjuiDto updateMenu(HttpServletRequest request, CmsMenu cmsMenu) throws Exception {
        BjuiDto bjuiDto = new BjuiDto();
        this.setDefaultValue(cmsMenu);
        try{
            //更新
            cmsMenu.setUpdateTime(new Date());
            int result = cmsMenuService.updateByPrimaryKeySelective(cmsMenu);
            if (result>0){
                bjuiDto.setStatusCode("200");
                bjuiDto.setCloseCurrent(true);
                bjuiDto.setTabid("cms_menu");
                //保存到日志
                manageSevice.saveSysLog("更新栏目", SystemConstant.SUCCESSFUL_OPERATION+",栏目编号为："+cmsMenu.getId(), request);
            }else{
                bjuiDto.setStatusCode("300");
                bjuiDto.setMessage("编辑栏目失败");
            }
        }catch (Exception e){
            bjuiDto.setStatusCode("300");
            bjuiDto.setMessage("更新栏目异常");
            log.error("更新栏目异常,异常信息：{}",e);
        }
        return bjuiDto;
    }

    /**
     * 删除栏目
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "delete.do")
    @ResponseBody
    public BjuiDto deleteMenu(HttpServletRequest request){
        BjuiDto bjuiDto = new BjuiDto();
        String id = request.getParameter("id");
        try{
            if (StringUtils.isNotEmpty(id)){
                int result = cmsMenuService.deleteMenuByLogic(Integer.valueOf(id));
                if (result > 0){
                    bjuiDto.setStatusCode("200");
                    bjuiDto.setCloseCurrent(true);
                    bjuiDto.setTabid("cms_menu");
                    //保存到日志
                    manageSevice.saveSysLog("删除栏目", SystemConstant.SUCCESSFUL_OPERATION+",栏目编号为："+id, request);
                }else {
                    bjuiDto.setStatusCode("300");
                    bjuiDto.setMessage("删除栏目失败");
                }
            }else {
                bjuiDto.setStatusCode("300");
                bjuiDto.setMessage("栏目ID不能为空");
            }
        }catch (Exception e){
            bjuiDto.setStatusCode("300");
            bjuiDto.setMessage("删除栏目异常");
            log.error("删除栏目异常,异常信息：{}",e);
        }
        return bjuiDto;
    }
}
