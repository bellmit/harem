package com.yimayhd.harem.service;

import java.util.ArrayList;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
public interface ScenicService {
	/**
	 * 获取景区资源列表(可带查询条件)
	 * 
	 * @return 景区资源列表
	 */
	PageVO<ScenicDO> getList(ScenicPageQuery scenicPageQuery) throws Exception;

	/**
	 * 获取景区资源详情
	 * 
	 * @return 景区资源详情
	 */
	ScenicDO getById(long id) throws Exception;


	/**
	 * 修改景区状态
	 * 
	 * @param id
	 * @param scenicStatus
	 * @throws Exception
	 */
	boolean updateStatus(int id, int scenicStatus) throws Exception;
	

	/**
	 * 批量修改景区状态
	 * 
	 * @param id
	 * @param scenicStatus
	 * @throws Exception
	 */
	boolean batchupdateStatus(ArrayList<Integer> scenicIdList, int scenicStatus);

}
