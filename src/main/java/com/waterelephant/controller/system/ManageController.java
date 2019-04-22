package com.waterelephant.controller.system;

import com.waterelephant.common.base.BaseVoController;
import com.waterelephant.common.entity.SysLog;
import com.waterelephant.common.utils.Page;
import com.waterelephant.common.utils.PageUtil;
import com.waterelephant.service.ManageSevice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/system/manage")
@Slf4j
public class ManageController extends BaseVoController {

	@Autowired
	protected ManageSevice manageSevice;

	@RequestMapping("/queryLogin.do")
	public String queryLogin(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {
		try {
			String pageNo = request.getParameter("pageCurrent");
			String pageSize = request.getParameter("pageSize");
			SysLog log = new SysLog();
			String operator=request.getParameter("operator");//登陆人
			String startTime=request.getParameter("startTime");//操作开始时间
			String endTime=request.getParameter("endTime");//操作开始时间
			log.setOperator(operator);
			Page<SysLog> page = new Page<>();
			if (pageNo == null || pageSize == null) {
				int[] pageParams = PageUtil.init(request);
				page = manageSevice.queryLogin(log,pageParams[0],pageParams[1] , startTime,endTime);
			}else{
				page = manageSevice.queryLogin(log,Integer.parseInt(pageNo),Integer.parseInt(pageSize),startTime,endTime);
			}
			modelMap.put("page", page);
			modelMap.put("log", log);
			modelMap.put("startTime", startTime);
			modelMap.put("endTime", endTime);
		} catch (Exception e) {
			log.error("操作日志error",e);
		}
		return "system/sys_log";
	}
}
