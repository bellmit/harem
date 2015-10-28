package com.yimayhd.interceptor;

import com.yimayhd.model.Menu;
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
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long userId = 1;
        List<Menu> menuList = new ArrayList<Menu>();

        Menu menu = new Menu(1,"交易管理","/tradeManage",1,0);
        List<Menu> menuListSub = new ArrayList<Menu>();
        menuListSub.add(new Menu(7,"交易流水","/tradeManage/list",2,1));
        menu.setMenuList(menuListSub);

        Menu menu2 = new Menu(1,"会员管理","/userManage",1,0);
        menuList.add(menu);
        menuList.add(menu2);
        modelAndView.addObject("menuList",menuList);

    }

}
