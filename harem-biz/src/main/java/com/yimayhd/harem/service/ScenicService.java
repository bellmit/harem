package com.yimayhd.harem.service;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.ScenicVO;
import com.yimayhd.harem.model.query.ScenicListQuery;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.param.item.ScenicAddNewDTO;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.ic.client.model.result.ICResult;

import java.util.ArrayList;
public interface ScenicService {
	/**
	 * 获取景区资源列表(可带查询条件)
	 * 
	 * @return 景区资源列表
	 */
	PageVO<ScenicDO> getList(ScenicListQuery scenicListQuery) throws Exception;

	/**
	 * 获取景区资源详情
	 * 
	 * @return 景区资源详情
	 */
	ScenicAddNewDTO getById(long id) throws Exception;


	/**
	 * 修改景区状态
	 * 
	 * @param id
	 * @param scenicStatus
	 * @throws Exception
	 */
	boolean enableScenicItem(long id) throws Exception;
	

	/**
	 * 修改景区状态
	 * 
	 * @param id
	 * @param scenicStatus
	 * @throws Exception
	 */
	boolean disableScenicItem(int id)throws Exception;

	ICResult<ScenicDO> save(ScenicVO scenicVO) throws Exception;

	boolean batchEnableStatus(ArrayList<Integer> scenicIdList);

	boolean batchDisableStatus(ArrayList<Integer> scenicIdList);
	

}
