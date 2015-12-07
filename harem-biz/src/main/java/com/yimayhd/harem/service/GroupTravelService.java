package com.yimayhd.harem.service;

import com.yimayhd.harem.model.travel.groupTravel.GroupTravel;

/**
 * 跟团游服务
 * 
 * @author yebin
 *
 */
public interface GroupTravelService {
	/**
	 * 保存或更新
	 * 
	 * @param groupTravel
	 * @return
	 */
	public void saveOrUpdate(GroupTravel groupTravel) throws Exception;

	/**
	 * 查询跟团游产品
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public GroupTravel getById(long id) throws Exception;
}
