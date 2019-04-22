package com.waterelephant.common.interceptor;

import com.waterelephant.common.entity.SysUser;
import com.waterelephant.common.entity.dto.BjuiDto;
import com.waterelephant.common.utils.RedisUtils;
import com.waterelephant.common.utils.SystemConstant;
import com.waterelephant.service.SysUuidService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录拦截器
 *
 * @author Administrator
 */
public class SecurityInterceptor implements HandlerInterceptor {
	
	private Logger logger = Logger.getLogger(SecurityInterceptor.class);

	@Autowired
	private SysUuidService uuidService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		//对于前端页面api请求不拦截
		if(uri.indexOf("/article/api") > 0 || uri.indexOf("/menu/api") > 0){
			return true;
		}
		if (uri.indexOf("/system/user/login.do") > 0 || uri.indexOf("/system/user/index.do") > 0
				|| uri.indexOf("/system/user/getVerifyCode.do") > 0
				|| uri.indexOf("/system/user/loginTimeOut.do") > 0
				|| uri.indexOf("/system/user/indexTimeOut.do") > 0
				) {
			return true;
		} else {
			HttpSession session = request.getSession();
			SysUser user = (SysUser) session.getAttribute(SystemConstant.SESSION_USER);
			if (user == null) {
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				BjuiDto bjuiDto = new BjuiDto();
				bjuiDto.setStatusCode("301");
				bjuiDto.setMessage("会话超时，请重新登录！");
				JSONObject jsonObject = JSONObject.fromObject(bjuiDto);
				response.getWriter().write(jsonObject.toString());
				return false;
			} else {
				String user_uuid = (String) session.getAttribute("user_uuid");
//				String userUUID = RedisUtils.get("user:uuid:"+user.getId());
				String userUUID = uuidService.getUuid(user.getId());
				if(!user_uuid.equals(userUUID)){
					session.removeAttribute(SystemConstant.SESSION_USER);
					session.removeAttribute("user_uuid");
					session.removeAttribute(SystemConstant.SESSION_ORG);
					session.removeAttribute(SystemConstant.SESSION_DEPT);
					session.removeAttribute("ruleCodes");
					session.removeAttribute("rules");
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					BjuiDto bjuiDto = new BjuiDto();
					bjuiDto.setStatusCode("301");
					bjuiDto.setMessage("您的账号已在别处登录，您被迫退出！");
					JSONObject jsonObject = JSONObject.fromObject(bjuiDto);
					response.getWriter().write(jsonObject.toString());
					return false;
				}
				return true;
			}
		}
	}

	@Override
	public void postHandle(HttpServletRequest hsr, HttpServletResponse hsr1, Object o, ModelAndView mav)
			throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest hsr, HttpServletResponse hsr1, Object o, Exception excptn)
			throws Exception {
	}
}
