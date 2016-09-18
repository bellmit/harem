package com.yimayhd.palace.service;

import java.util.ArrayList;
import java.util.List;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.ScenicAddVO;
import com.yimayhd.palace.model.ScenicVO;
import com.yimayhd.palace.model.line.pictxt.PictureTextVO;
import com.yimayhd.palace.model.query.ScenicListQuery;
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
	ScenicVO getById(long id) throws Exception;
	
	/**
	 * 获取景区资源详情
	 * 
	 * @return 景区资源详情
	 */
	ScenicAddVO getDetailById(long id) throws Exception;


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
	boolean enableScenic(long id) throws Exception;
	

	/**
	 * 修改景区状态
	 * 
	 * @param id
	 * @param scenicStatus
	 * @throws Exception
	 */
	boolean disableScenicItem(int id)throws Exception;

	ICResult<ScenicDO> save(ScenicVO scenicVO) throws Exception;
	
	ICResult<ScenicDO> save(ScenicAddVO scenicAddVO) throws Exception;

	boolean batchEnableStatus(ArrayList<Integer> scenicIdList);

	boolean batchDisableStatus(ArrayList<Integer> scenicIdList);
	
	/**
	 * 获取景区全部主题
	 * 
	 * @return
	 */
	List<ComTagDO> getAllLineThemes();
	
	/**
	 * 保存景区图文详情（资源）
	 * 
	 * @return
	 * @throws
	 */
	void savePictureText(long id, PictureTextVO pictureTextVO) throws Exception;
	
	/**
	 * 读取图文详情
	 */
	PictureTextVO getPictureText(long id) throws Exception;
}
