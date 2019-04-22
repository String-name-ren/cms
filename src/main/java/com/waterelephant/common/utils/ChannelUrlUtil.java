package com.waterelephant.common.utils;

import javax.servlet.http.HttpServletRequest;

public class ChannelUrlUtil {
	
	
	public static String getUrl(HttpServletRequest request,String cid){
	       String channelUrl="http://" + request.getServerName() //服务器地址  
           + ":"   
           + request.getServerPort()           //端口号  
           + request.getContextPath()      //项目名称  
           + "/channelStatistics/getChannelStatistics.do"    +  //请求页面或其他地址    第三方 请求 action 地址
       "?cid="+cid  ;   //参数  
		
		return channelUrl;
	}

}
