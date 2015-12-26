package com.yimayhd.harem.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.base.BaseException;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.HotelFacilityVO;
import com.yimayhd.harem.model.HotelVO;
import com.yimayhd.harem.model.PictureVO;
import com.yimayhd.harem.model.query.HotelListQuery;
import com.yimayhd.harem.service.HotelRPCService;
import com.yimayhd.harem.util.DateUtil;
import com.yimayhd.ic.client.model.domain.FacilityIconDO;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.PicturesDO;
import com.yimayhd.ic.client.model.enums.PictureOutType;
import com.yimayhd.ic.client.model.query.HotelPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.service.item.HotelService;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.ic.client.service.item.ResourcePublishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class HotelRPCServiceImpl implements HotelRPCService {

    @Autowired
    private ItemQueryService itemQueryService;
	
    @Autowired
    private HotelService hotelService;

	@Autowired
	private ResourcePublishService resourcePublishServiceRef;

	private static final Logger log = LoggerFactory.getLogger(HotelRPCServiceImpl.class);
	@Override
	public PageVO<HotelDO> pageQueryHotel(HotelListQuery hotelListQuery)throws Exception {
		
		HotelPageQuery hotelPageQuery = new HotelPageQuery();
    	hotelPageQuery.setNeedCount(true);
		
		if (hotelListQuery.getPageNumber() != null) {
			int pageNumber = hotelListQuery.getPageNumber();
			int pageSize = hotelListQuery.getPageSize();
			hotelPageQuery.setPageNo(pageNumber);
			hotelPageQuery.setPageSize(pageSize);
		}
		
		//酒店名称
		if (!StringUtils.isBlank(hotelListQuery.getName())) {
			hotelPageQuery.setTags(hotelListQuery.getName());			
		}
		
		//酒店状态
		if (hotelListQuery.getHotelStatus() != 0) {			
			hotelPageQuery.setItemStatus(hotelListQuery.getHotelStatus());
		}
		
		//开始时间
		if (!StringUtils.isBlank(hotelListQuery.getBeginDate())) {
			Date startTime = DateUtil.parseDate(hotelListQuery.getBeginDate());
			hotelPageQuery.setStartTime(startTime);
		}
		
		//结束时间
		if (!StringUtils.isBlank(hotelListQuery.getEndDate())) {
			Date endTime = DateUtil.parseDate(hotelListQuery.getEndDate());
			hotelPageQuery.setEndTime(DateUtil.add23Hours(endTime));
		}
		
		ICPageResult<HotelDO> icPageResult = itemQueryService.pageQueryHotel(hotelPageQuery);
    	List<HotelDO> hotelDOList = icPageResult.getList();
    	
    	PageVO<HotelDO> pageVo = new PageVO<HotelDO>(hotelPageQuery.getPageNo(), hotelPageQuery.getPageSize(), icPageResult.getTotalCount(), hotelDOList);
		
		return pageVo;
	}

	@Override
	public ICResult<Boolean> updateHotelStatus(HotelDO hotelDO) {

		try {
			return hotelService.updateHotelStatus(hotelDO);
		} catch (Exception e) {
			log.error("hotelService.updateHotelStatus(hotelDO) exception, hotelDO:" + hotelDO,e);
		}
		return null;
	}

	@Override
	public ICResult<Boolean> addHotel(HotelVO hotelVO)throws Exception{


		ICResult<Boolean> result = new ICResult<Boolean>();

		HotelDO hotelDO = HotelVO.getHotelDO(hotelVO);

		ICResult<HotelDO> icResult = hotelService.addHotel(hotelDO);
		if(icResult == null){
			log.error("HotelRPCServiceImpl.addHotel-hotelService.addHotel result is null and parame: " + JSON.toJSONString(hotelDO));
			throw new BaseException("返回结果为空，酒店资源新增失败");
		}else if(!icResult.isSuccess()){
			log.error("HotelRPCServiceImpl.addHotel-hotelService.addHotel error:" + JSON.toJSONString(icResult) + "and parame: " + JSON.toJSONString(hotelDO) + "and hotelVO:" + JSON.toJSONString(hotelVO));
			throw new BaseException("返回结果错误，图片集保存失败，" + icResult.getResultMsg());
		}
		//图片集insert
		if(org.apache.commons.lang.StringUtils.isNotBlank(hotelVO.getPicListStr())){
			List<PictureVO> pictureVOList = JSON.parseArray(hotelVO.getPicListStr(),PictureVO.class);
			List<PicturesDO> picList = new ArrayList<PicturesDO>();
			for (PictureVO pictureVO:pictureVOList){
				PicturesDO picturesDO = new PicturesDO();
				picturesDO.setPath(pictureVO.getValue());
				picturesDO.setName(pictureVO.getName());
				picturesDO.setOutId(icResult.getModule().getId());
				picturesDO.setOutType(PictureOutType.HOTEL.getValue());
				picturesDO.setOrderNum(pictureVO.getIndex());
				picturesDO.setIsTop(pictureVO.isTop());
				picList.add(picturesDO);
			}
			ICResult<Boolean> icResultPic =  resourcePublishServiceRef.addPictures(picList);
			if(null == icResultPic){
				log.error("ScenicServiceImpl.save-ResourcePublishService.addScenicNew result is null and parame: " + JSON.toJSONString(picList));
				throw new BaseException("景区资源保存成功，图片集保存返回结果为空，保存失败");
			} else if(!icResultPic.isSuccess()){
				log.error("ScenicServiceImpl.save-ResourcePublishService.addScenicNew error:" + JSON.toJSONString(icResultPic) + "and parame: " + JSON.toJSONString(picList) + "and hotelVO:" + JSON.toJSONString(hotelVO));
				throw new BaseException("景区资源保存成功，图片集保存失败" + icResultPic.getResultMsg());
			}
		}
		result.setModule(icResult.isSuccess());
		return result;
	}

	@Override
	public ICResult<Boolean> updateHotel(HotelVO hotelVO) {
		
		ICResult<Boolean> result = new ICResult<Boolean>();
		HotelDO hotelDO = null;
		ICResult<Boolean> icResult = new ICResult<Boolean>();
		
		//数据转换
		try {
			hotelDO = HotelVO.getHotelDO(hotelVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (hotelDO != null) {
			icResult = hotelService.updateHotel(hotelDO);
		}
		
		result.setModule(icResult.isSuccess());		
		return result;
	}

	@Override
	public HotelVO getHotel(long id) {
		
		HotelVO hotelVO = null;
		ICResult<HotelDO> icResult = itemQueryService.getHotel(id);
		HotelDO hotelDO = icResult.getModule();
		
		try {
			hotelVO = HotelVO.getHotelVO(hotelDO);
		} catch (Exception e) {
			log.error("hotelVO = HotelVO.getHotelVO(hotelDO); exception,"+hotelDO,e);
		}
		
		return hotelVO;
	}

	@Override
	public List<HotelFacilityVO> queryFacilities(int type) {
		
		ICPageResult<FacilityIconDO> icPageResult = itemQueryService.queryFacilities(type);
		List<FacilityIconDO> list = icPageResult.getList();		
		List<HotelFacilityVO> resultList = new ArrayList<HotelFacilityVO>();
		Iterator<FacilityIconDO> it = list.iterator();
		
		while (it.hasNext()) {			
			FacilityIconDO temp = it.next();
			HotelFacilityVO vTemp = new HotelFacilityVO();
			vTemp.setName(temp.getName());
			vTemp.setNumber(temp.getNumber());
			resultList.add(vTemp);
		}
		
		return resultList;
	}

	@Override
	public void setHotelStatusList(List<Long> idList, int hotelStatus) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHotelStatus(long id, int hotelStatus) throws Exception {
		// TODO Auto-generated method stub
		
	}	
	
}
