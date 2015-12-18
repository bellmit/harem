package com.yimayhd.harem.base;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yimayhd.user.client.domain.AreaDO;
import com.yimayhd.user.client.domain.CityDO;
import com.yimayhd.user.client.domain.ProvinceDO;
import com.yimayhd.user.client.result.ProvinceCityAreaResult;
import com.yimayhd.user.client.service.UserAddressService;

public class AreaService {

	private static AreaService instance = null;
	
	//private ProvinceCityAreaResult provinceCityAreaResult;
	
	private static List<ProvinceDO> listProvinceDO = null;
	
	private static List<CityDO> listCityDO = null;
	
	private static List<AreaDO> listAreaDO = null;
	
	private static com.yimayhd.user.client.service.UserAddressService userAddressService = null;
	
	private AreaService() {
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();
		ApplicationContext appCtx = WebApplicationContextUtils.getWebApplicationContext(servletContext);		
		
/*		ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext(
        "classpath*:spring-consumer-singleton.xml");*/
		userAddressService = (UserAddressService) appCtx.getBean("userAddressServiceRef");
		
		listProvinceDO = userAddressService.getProvinceCityArea().getProvinceDOList();
		listCityDO = userAddressService.getProvinceCityArea().getCityDOList();
		listAreaDO = userAddressService.getProvinceCityArea().getAreaDOList();
		
	}
	
	public static AreaService getInstance() {
		
		if (instance == null) {
			
			synchronized(AreaService.class) {
				
				if (instance == null) {
					instance = new AreaService();
				}
				
			}
			
		}
		
		return instance;
		
	}

	public List<ProvinceDO> getListProvinceDO() {
		return listProvinceDO;
	}

	public List<CityDO> getListCityDO() {
		return listCityDO;
	}

	public List<AreaDO> getListAreaDO() {
		return listAreaDO;
	}
	
}
