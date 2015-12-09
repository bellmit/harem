package com.yimayhd.harem.service;

import java.util.List;

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
	 * 添加景区资源
	 * 
	 * @param scenicDO
	 * @return
	 * @throws Exception
	 */
	ScenicDO add(ScenicDO scenicDO) throws Exception;

	/**
	 * 修改景区资源
	 * 
	 * @param scenicDO
	 * @throws Exception
	 */
	void modify(ScenicDO scenicDO) throws Exception;

	/**
	 * 批量修改景区状态
	 * 
	 * @param idList
	 * @param scenicStatus
	 * @throws Exception
	 */
	void setScenicStatusList(List<Integer> idList, int scenicStatus) throws Exception;

	/**
	 * 修改景区状态
	 * 
	 * @param id
	 * @param scenicStatus
	 * @throws Exception
	 */
	void setScenicStatus(long id, int scenicStatus) throws Exception;

}
