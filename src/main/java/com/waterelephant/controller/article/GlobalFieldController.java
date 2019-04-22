package com.waterelephant.controller.article;

import com.waterelephant.common.entity.dto.BjuiDto;
import com.waterelephant.entity.CmsGlobalField;
import com.waterelephant.service.CmsGlobalFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 描述
 *
 * @author: renpenghui
 * @date: 2019-03-25 09:20
 **/
@Controller
@RequestMapping("global/field")
public class GlobalFieldController {

    @Autowired
    private CmsGlobalFieldService globalFieldService;

    @RequestMapping("get")
    public String getGlobalField(ModelMap model){
        model.put("globalField",globalFieldService.get());
        return "article/global_field";
    }

    @RequestMapping("update")
    @ResponseBody
    public BjuiDto updateGlobalField(CmsGlobalField globalField){
        globalFieldService.update(globalField);
        BjuiDto bjuiDto = new BjuiDto();
        bjuiDto.setStatusCode("200");
        bjuiDto.setMessage("更新成功！");
        bjuiDto.setTabid("global_field");
        return bjuiDto;
    }

}
