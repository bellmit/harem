package com.yimayhd.palace.util;


import org.springframework.beans.BeansException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class SpringContextModel {
	
	public static Object getBean(String name) throws BeansException {
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		return wac.getBean(name);
    } 
	
}
		
