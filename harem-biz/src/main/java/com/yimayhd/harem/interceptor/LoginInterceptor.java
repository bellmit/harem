package com.yimayhd.harem.interceptor;

import com.yimayhd.harem.model.HaMenuDO;
import com.yimayhd.harem.model.Menu;
import com.yimayhd.harem.service.HaMenuService;

import net.pocrd.responseEntity.KeyValuePair;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/10/26.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
	// 0:不限;1：GET;2:POST;3:PUT;4:DELETE 
	
	public static Map<String,Integer> map = new HashMap<String, Integer>();
	static {
		map.put("ALL", 0);
		map.put("GET", 1);
		map.put("POST", 2);
		map.put("PUT", 3);
		map.put("DELETE", 4);
	}
	
    @Autowired
    private HaMenuService haMenuService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        int reqType= map.get(request.getMethod());
        if(!url.equals("/user/noPower")){
            long userId = 10;
            //List<HaMenuDO> haMenuList = haMenuService.getUrlListByUserId(userId);
            List<HaMenuDO> haMenuList = haMenuService.getUrlListByUserId(userId);
            if(null !=haMenuList && haMenuList.size()>0){
            	for (HaMenuDO haMenuDO : haMenuList) {
            		
					String dbURL =haMenuDO.getUrl();
					int dbReqType=haMenuDO.getReqType();
					
					if(0 !=dbReqType && reqType !=dbReqType){
						continue;
					}
					if(hasBrackets(dbURL)){
						dbURL=turnBrackets(dbURL);
					}
					if(matchURL(url, dbURL)){
					  System.out.println("OK");
					  return true;
					}
				}
            }
        }
        return false;
    }
    
    /**
    * @Title: hasBrackets 
    * @Description:(判断数据库中的配置url有没有包含"{") 
    * @author create by yushengwei @ 2015年12月3日 下午7:08:36 
    * @param @param str
    * @param @return 
    * @return boolean 返回类型 
    * @throws
     */
	public static boolean hasBrackets(String str){
		boolean flag=str.contains("{");
	    System.out.println(flag);
		return flag;
	}
    
    /**
    * @Title: turnBrackets 
    * @Description:(把数据库中配置的url带{的转换成通配符，匹配{}内的所有内容) 
    * @author create by yushengwei @ 2015年12月3日 下午7:10:38 
    * @param @param str
    * @param @return 
    * @return String 返回类型 
    * @throws
     */
    public static String turnBrackets(String str){
		String interval  =str.substring(str.indexOf("{"), str.indexOf("}")+1);
		System.out.println("old="+str +"    ,interval="+interval+"  ,str="+str);
		str=str.replace(interval,"[^/]+?");
		System.out.println(str);
		return str;
	}
    
    /**
    * @Title: matchURL 
    * @Description:(判断两个url是否相匹配) 
    * @author create by yushengwei @ 2015年12月3日 下午7:11:32 
    * @param @param str
    * @param @param match
    * @param @return 
    * @return boolean 返回类型 
    * @throws
     */
    public static boolean matchURL(String str,String match){
		String regEx=match;
		Pattern pattern = Pattern.compile(regEx);
	    Matcher matcher = pattern.matcher(str);
	    boolean rs = matcher.matches();
	    System.out.println(rs);
		return rs;
	}
    
    }
