package com.yimayhd.harem.service;

import java.util.List;

import com.yimayhd.harem.model.TripBo;

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
	int saveTrip(TripBo tripBo);
	
	/**
	* @Title: delTrip 
	* @Description:(删除出发地目的地) 
	* @author create by yushengwei @ 2015年12月10日 下午8:41:58 
	* @param @param tripBo
	* @param @return 
	* @return boolean 返回类型 
	* @throws
	 */
	boolean delTrip(TripBo tripBo);
	
	/**
	* @Title: getTripBo 
	* @Description:(根据id查询TripBo) 
	* @author create by yushengwei @ 2015年12月10日 下午8:43:57 
	* @param @param id
	* @param @return 
	* @return TripBo 返回类型 
	* @throws
	 */
	TripBo getTripBo(int id);
	
	/**
	* @Title: getTripBo 
	* @Description:(获取TripBo列表) 
	* @author create by yushengwei @ 2015年12月10日 下午8:44:34 
	* @param @return 
	* @return List<TripBo> 返回类型 
	* @throws
	 */
	List<TripBo> getTripBo();
	
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

}
