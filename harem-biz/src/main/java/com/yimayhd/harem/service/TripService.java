package com.yimayhd.harem.service;

import java.util.List;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.TripBo;
import com.yimayhd.harem.model.query.HotelListQuery;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.resourcecenter.domain.RegionDO;
import com.yimayhd.resourcecenter.model.result.ShowCaseResult;

/** 
* @ClassName: TripAService 
* @Description: (出发地 目的地) 
* @author create by yushengwei @ 2015年12月10日 下午7:58:28 
*/
public interface TripService {
	/**
	* @Title: saveTrip 
	* @Description:(保存出发地目的地) 
	* @author create by yushengwei @ 2015年12月10日 下午8:41:35 
	* @param @param tripBo
	* @param @return 
	* @return boolean 返回类型 
	* @throws
	 */
	long saveTrip(TripBo tripBo);
	
	/**
	* @Title: delTrip 
	* @Description:(删除出发地目的地) 
	* @author create by yushengwei @ 2015年12月10日 下午8:41:58 
	* @param @param long id
	* @param @return 
	* @return boolean 返回类型 
	* @throws
	 */
	boolean delTrip(long id);
	
	/**
	* @Title: RegionDO 
	* @Description:(根据id查询TripBo) 
	* @author create by yushengwei @ 2015年12月10日 下午8:43:57 
	* @param @param id
	* @param @return 
	* @return TripBo 返回类型 
	* @throws
	 */
	RegionDO getTripBo(int id);
	
	/**
	* @Title: getTripBo 
	* @Description:(获取TripBo列表) 
	* @author create by yushengwei @ 2015年12月10日 下午8:44:34 
	* @param @return 
	* @return List<TripBo> 返回类型 
	* @throws
	 */
	List<RegionDO> getTripBo();
	
	/**
	* @Title: editTripBo 
	* @Description:(修改出发地目的地) 
	* @author create by yushengwei @ 2015年12月10日 下午8:43:06 
	* @param @param tripBo
	* @param @return 
	* @return TripBo 返回类型 
	* @throws
	 */
	boolean editTripBo(TripBo tripBo);
	
	/**
	* @Title: relevanceRecommended 
	* @Description:(出发地目的地关联各种推荐) 
	* @author create by yushengwei @ 2015年12月10日 下午8:42:14 
	* @param @param type
	* @param @param destinationId
	* @param @param showCaseId
	* @param @return 
	* @return boolean 返回类型 
	* @throws
	 */
	boolean relevanceRecommended(int type,int destinationId,int showCaseId[]);
	
	/**
	* @Title: selectRegion 
	* @Description:(获取地区列表，用户用户创建出发地目的地的时候选择对应的城市名称) 
	* @author create by yushengwei @ 2015年12月16日 上午10:07:23 
	* @param @param type
	* @param @return 
	* @return List<RegionDO> 返回类型 
	* @throws
	 */
	List<RegionDO> selectRegion(int type);
	
	/**
	* @Title: selectScenicDO 
	* @Description:(获取景区列表) 
	* @author create by yushengwei @ 2015年12月16日 上午10:28:57 
	* @param @param scenicPageQuery
	* @param @return
	* @param @throws Exception 
	* @return PageVO<ScenicDO> 返回类型 
	* @throws
	 */
	PageVO<ScenicDO> selectScenicDO(ScenicPageQuery scenicPageQuery) throws Exception;
	
	/**
	* @Title: getListShowCaseResult 
	* @Description:(获取showcase列表) 
	* @author create by yushengwei @ 2015年12月16日 上午10:33:32 
	* @param @param type
	* @param @return 
	* @return List<ShowCaseResult> 返回类型 
	* @throws
	 */
	List<ShowCaseResult> getListShowCaseResult(int type);
	
	/**
	* @Title: selecthotelDO 
	* @Description:(获取酒店列表) 
	* @author create by yushengwei @ 2015年12月16日 上午10:46:21 
	* @param @param hotelListQuery
	* @param @return 
	* @return List<HotelDO> 返回类型 
	* @throws
	 */
	List<HotelDO> selecthotelDO(HotelListQuery hotelListQuery)throws Exception;
}
