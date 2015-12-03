package com.yimayhd.harem.service;

import com.yimayhd.harem.model.travel.selfServiceTravel.SelfServiceTravel;

/**
 * 自由行服务
 * 
 * @author yebin
 *
 */
public interface SelfServiceTravelService {
	/**
	 * 保存
	 * 
	 * @param selfServiceTravel
	 * @return
	 */
	public void save(SelfServiceTravel selfServiceTravel) throws Exception;

	/**
	 * 更新
	 * 
	 * @param selfServiceTravel
	 * @return
	 */
	public void update(SelfServiceTravel selfServiceTravel) throws Exception;

	/**
	 * 查询自由行产品
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public SelfServiceTravel getById(long id) throws Exception;
}
