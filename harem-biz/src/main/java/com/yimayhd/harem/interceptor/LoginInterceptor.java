package com.yimayhd.harem.interceptor;

import com.yimayhd.harem.model.HaMenuDO;
import com.yimayhd.harem.model.Menu;
import com.yimayhd.harem.service.HaMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/26.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private HaMenuService haMenuService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		/*
		 * String url = request.getRequestURI();
		 * if(!url.equals("/user/noPower")){ long userId = 1; List<HaMenuDO>
		 * haMenuList = haMenuService.getUrlListByUserId(userId); for (HaMenuDO
		 * haMenu:haMenuList){ if(url.equals(haMenu.getLinkUrl())){ return true;
		 * } } return true; //response.sendRedirect("/user/noPower?urlName=" +
		 * url); //return false;
		 * 
		 * }else{ return true; }
		 */
		return true;
	}

}
