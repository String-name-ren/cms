package com.waterelephant.controller.system;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waterelephant.common.entity.SysCity;
import com.waterelephant.common.entity.dto.BjuiDto;
import com.waterelephant.common.utils.Page;
import com.waterelephant.common.utils.PageUtil;
import com.waterelephant.common.utils.TreeModel;
import com.waterelephant.service.SysCityService;

@Controller
@RequestMapping("/system/city")
public class SysCityController {
	private Logger logger = Logger.getLogger(SysCityController.class);

	@Autowired
	private SysCityService sysCityService;
	
	/**
	 * 跳转部门详细页面展示数据
	 */
	@RequestMapping(value = "selectCity.do")
	public String selectCity(HttpServletRequest request, ModelMap modelMap) {
		try {
			String lft=request.getParameter("lft");
			String rgt=request.getParameter("rgt");
			String cityName=request.getParameter("cityName");
			String orgId=request.getParameter("orgId");
			String orgName=request.getParameter("orgName");
			Page<SysCity> page = new Page<>();
			int[] pageParams = PageUtil.init(request);
			page = sysCityService.query(pageParams,cityName,orgId,lft,rgt); 
			List<TreeModel> treeList = sysCityService.findChannelTree();
			modelMap.put("treeList", treeList);
			modelMap.put("page", page);
			modelMap.put("orgName", orgName);
			modelMap.put("cityName", cityName);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "system/sysCity";
	}
	
	/**
	 * 添加地域跳转
	 */
	@RequestMapping(value = "addCity.do")
	public String addCity(ModelMap modelMap) {
		List<TreeModel> treeList = sysCityService.findChannelTree();
		modelMap.put("treeList", treeList);
		return "system/sysCity_add";
	}
	
	/**
	 * 添加到地域表
	 */
	@RequestMapping(value = "save.do")
	@ResponseBody
	public BjuiDto save(HttpServletRequest request,ModelMap modelMap) {
		BjuiDto bjuiDto = new BjuiDto();
		try {
			String cityName=request.getParameter("cityName");
			String cityDesc=request.getParameter("cityDesc");
			String orgId=request.getParameter("orgId");
			sysCityService.add(cityName,cityDesc,orgId);
			bjuiDto.setStatusCode("200");
		} catch (Exception e) {
			bjuiDto.setStatusCode("300");
			bjuiDto.setMessage(e.getMessage());
		}
		return bjuiDto;
	}
	
	/**
	 * 删除地域
	 */
	@RequestMapping(value = "deleteCity.do")
	@ResponseBody
	public BjuiDto deleteCity(HttpServletRequest request,ModelMap modelMap) {
		BjuiDto bjuiDto = new BjuiDto();
		try {
			String id=request.getParameter("id");
			sysCityService.deleteCity(id);
			bjuiDto.setStatusCode("200");
		} catch (Exception e) {
			bjuiDto.setStatusCode("300");
			bjuiDto.setMessage(e.getMessage());
		}
		return bjuiDto;
	}
	/**
	 *修改地域
	 */
	@RequestMapping(value = "updateCity.do")
	public String updateCity(HttpServletRequest request,ModelMap modelMap) {
		try {
			String id=request.getParameter("id");
			SysCity city=sysCityService.queryCity(id);
			Map<String,Object> map=sysCityService.queryOrg(id);
			List<TreeModel> treeList = sysCityService.findChannelTree();
			modelMap.put("treeList", treeList);
			modelMap.put("city", city);
			modelMap.put("map", map);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "system/sysCity_update";
	}
	
	/**
	 * 更新地域
	 */
	@RequestMapping(value = "updateSave.do")
	@ResponseBody
	public BjuiDto updateSave(HttpServletRequest request,ModelMap modelMap) {
		BjuiDto bjuiDto = new BjuiDto();
		try {
			String orgId=request.getParameter("orgId");
			String id=request.getParameter("id");
			String cityName=request.getParameter("cityName");
			String cityDesc=request.getParameter("cityDesc");
			sysCityService.updateSave(id,orgId,cityName,cityDesc);
			bjuiDto.setStatusCode("200");
			bjuiDto.setTabid("city");
			bjuiDto.setCloseCurrent(true);
		} catch (Exception e) {
			bjuiDto.setStatusCode("300");
			bjuiDto.setMessage(e.getMessage());
		}
		return bjuiDto;
	}
}
