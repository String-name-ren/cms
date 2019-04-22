package com.waterelephant.controller.api;

import com.waterelephant.entity.CmsMenu;
import com.waterelephant.service.CmsMenuService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 描述
 *
 * @author: renpenghui
 * @date: 2019-04-19 14:30
 **/
@RestController
@RequestMapping("/menu/api")
@Slf4j
@Api(value = "menu/api", description = "栏目相关查询接口")
public class MenuApiController {

    @Autowired
    private CmsMenuService menuService;

    @RequestMapping(value = "get/{menuName}",method = RequestMethod.POST)
    public CmsMenu getMenuByName(@PathVariable("menuName") String menuName){
        return menuService.getMenuByName(menuName);
    }

}
