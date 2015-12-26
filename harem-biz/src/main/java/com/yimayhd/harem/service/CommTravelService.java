package com.yimayhd.harem.service;

import com.yimayhd.harem.model.travel.BaseTravel;

/**
 * 自由行服务
 * 
 * @author yebin
 *
 */
public interface CommTravelService {
	/**
	 * 发布线路
	 * 
	 * @param selfServiceTravel
	 * @return
	 * @throws Exception
	 */
	long publishLine(BaseTravel travel);

	/**
	 * 查询线路
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	<T extends BaseTravel> T getById(long id, Class<T> clazz);
}
