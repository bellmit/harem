package com.yimayhd.palace.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.yimayhd.ic.client.model.param.item.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.dto.ComentEditDTO;
import com.yimayhd.commentcenter.client.enums.PictureText;
import com.yimayhd.commentcenter.client.result.PicTextResult;
import com.yimayhd.commission.convert.PictureTextConverter;
import com.yimayhd.ic.client.model.domain.FacilityIconDO;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.PictureFeature;
import com.yimayhd.ic.client.model.domain.PicturesDO;
import com.yimayhd.ic.client.model.domain.RoomDO;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.enums.BaseStatus;
import com.yimayhd.ic.client.model.enums.PictureOutType;
import com.yimayhd.ic.client.model.enums.PictureTag;
import com.yimayhd.ic.client.model.query.HotelPageQuery;
import com.yimayhd.ic.client.model.query.PicturesPageQuery;
import com.yimayhd.ic.client.model.query.RoomQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.model.result.ICResultSupport;
import com.yimayhd.ic.client.service.item.HotelService;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.ic.client.service.item.ResourcePublishService;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.model.HotelFacilityVO;
import com.yimayhd.palace.model.HotelVO;
import com.yimayhd.palace.model.PictureVO;
import com.yimayhd.palace.model.RoomVO;
import com.yimayhd.palace.model.line.pictxt.PictureTextVO;
import com.yimayhd.palace.model.query.HotelListQuery;
import com.yimayhd.palace.repo.PictureTextRepo;
import com.yimayhd.palace.repo.ResourceRepo;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.palace.service.HotelRPCService;
import com.yimayhd.palace.service.TfsService;
import com.yimayhd.palace.util.DateUtil;

public class HotelRPCServiceImpl implements HotelRPCService {

	private final static int PIC_PAGE_SIZE = 500;
	private final static int PIC_PAGE_NO = 1;
	private static final Logger log = LoggerFactory.getLogger(HotelRPCServiceImpl.class);

    @Autowired
    private ItemQueryService itemQueryServiceRef;
	
    @Autowired
    private HotelService hotelServiceRef;

	@Autowired
	private ResourcePublishService resourcePublishServiceRef;

	@Autowired
	private TfsService tfsService;
	
	@Autowired
	private PictureTextRepo	pictureTextRepo;
	@Autowired
	private ResourceRepo resourceRepo;

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
			hotelPageQuery.setTags(hotelListQuery.getName().trim());			
		}
		
		//酒店状态
		if (hotelListQuery.getHotelStatus() != null && hotelListQuery.getHotelStatus() != 0) {			
			hotelPageQuery.setStatus(hotelListQuery.getHotelStatus());
		}
		
		//酒店类型
		if(hotelListQuery.getType() != null && hotelListQuery.getType() != 0){
			hotelPageQuery.setType(hotelListQuery.getType());
		}
		
		//区域-省
		if(hotelListQuery.getLocationProvinceId() != null && hotelListQuery.getLocationProvinceId() != 0){
			hotelPageQuery.setLocationProvinceId(hotelListQuery.getLocationProvinceId());
		}
		
		//区域-市
		if(hotelListQuery.getLocationCityId() != null && hotelListQuery.getLocationCityId() != 0){
			hotelPageQuery.setLocationCityId(hotelListQuery.getLocationCityId());
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
		
		if (hotelListQuery.getDomain() != 0) {			
			hotelPageQuery.setDomain(hotelListQuery.getDomain());
		}
		
		ICPageResult<HotelDO> icPageResult = itemQueryServiceRef.pageQueryHotel(hotelPageQuery);
    	List<HotelDO> hotelDOList = icPageResult.getList();
    	
		return new PageVO<HotelDO>(hotelPageQuery.getPageNo(), hotelPageQuery.getPageSize(), icPageResult.getTotalCount(), hotelDOList);
	}

	@Override
	public ICResult<Boolean> updateHotelStatus(HotelDO hotelDO)throws Exception {
		ICResult<Boolean> result = hotelServiceRef.updateHotelStatus(hotelDO);
		if(result == null){
			log.error("HotelRPCServiceImpl.updateHotelStatus-hotelService.updateHotelStatus result is null and parame: " + JSON.toJSONString(hotelDO));
			throw new BaseException("酒店状态修改失败");
		}else if(!result.isSuccess()){
			log.error("HotelRPCServiceImpl.updateHotelStatus-hotelService.updateHotelStatus error:" + JSON.toJSONString(result) + "and parame: " + JSON.toJSONString(hotelDO));
			throw new BaseException("酒店状态修改失败，" + result.getResultMsg());
		}
		return result;
	}

	@Override
	public ICResult<Boolean> addHotel(HotelVO hotelVO)throws Exception{


		ICResult<Boolean> result = new ICResult<Boolean>();

		HotelDO hotelDO = HotelVO.getHotelDO(hotelVO);
		//needKonw中的extraInfoUrl(富文本编辑)
		if(StringUtils.isNotBlank(hotelDO.getNeedKnow().getExtraInfoUrl())){
			hotelDO.getNeedKnow().setExtraInfoUrl(tfsService.publishHtml5(hotelDO.getNeedKnow().getExtraInfoUrl()));
		}

		ICResult<HotelDO> icResult = hotelServiceRef.addHotel(hotelDO);
		if(icResult == null){
			log.error("HotelRPCServiceImpl.addHotel-hotelService.addHotel result is null and parame: " + JSON.toJSONString(hotelDO));
			throw new BaseException("返回结果为空，酒店资源新增失败");
		}else if(!icResult.isSuccess()){
			log.error("HotelRPCServiceImpl.addHotel-hotelService.addHotel error:" + JSON.toJSONString(icResult) + "and parame: " + JSON.toJSONString(hotelDO) + "and hotelVO:" + JSON.toJSONString(hotelVO));
			throw new BaseException("返回结果错误，酒店资源新增失败，" + icResult.getResultMsg());
		}
		//图片集insert
		if(StringUtils.isNotBlank(hotelVO.getPicListStr())){
			List<PictureVO> pictureVOList = JSON.parseArray(hotelVO.getPicListStr(),PictureVO.class);
			List<PicturesDO> picList = new ArrayList<PicturesDO>();
			for (PictureVO pictureVO:pictureVOList){
				PicturesDO picturesDO = new PicturesDO();
				picturesDO.setPath(pictureVO.getValue());
				picturesDO.setName(pictureVO.getName());
				picturesDO.setOutId(icResult.getModule().getId());
				picturesDO.setOutType(PictureOutType.HOTEL.getValue());
				//TODO picturesDO.setOrderNum(pictureVO.getIndex());
				picturesDO.setIsTop(pictureVO.isTop());
				picList.add(picturesDO);
			}
			ICResult<Boolean> icResultPic =  resourcePublishServiceRef.addPictures(picList);
			if(null == icResultPic){
				log.error("ScenicServiceImpl.save-ResourcePublishService.addPictures result is null and parame: " + JSON.toJSONString(picList));
				throw new BaseException("酒店资源保存成功，图片集保存返回结果为空，保存失败");
			} else if(!icResultPic.isSuccess()){
				log.error("ScenicServiceImpl.save-ResourcePublishService.addPictures error:" + JSON.toJSONString(icResultPic) + "and parame: " + JSON.toJSONString(picList) + "and hotelVO:" + JSON.toJSONString(hotelVO));
				throw new BaseException("酒店资源保存成功，图片集保存失败" + icResultPic.getResultMsg());
			}
		}
		result.setModule(icResult.isSuccess());
		return result;
	}
	
	@Override
	public ICResult<HotelVO> addHotelV2(HotelVO hotelVO)throws Exception{
		
		//判断酒店是否存在
		this.judgeExist(hotelVO);
		
		HotelDO hotelDO = HotelVO.getHotelDOV2(hotelVO);
		ICResult<HotelDO> icResult = hotelServiceRef.addHotel(hotelDO);
		if(icResult == null){
			log.error("HotelRPCServiceImpl.addHotel-hotelService.addHotel result is null and parame: " + JSON.toJSONString(hotelDO));
			throw new BaseException("返回结果为空，酒店资源新增失败");
		}else if(!icResult.isSuccess()){
			log.error("HotelRPCServiceImpl.addHotel-hotelService.addHotel error:" + JSON.toJSONString(icResult) + "and parame: " + JSON.toJSONString(hotelDO) + "and hotelVO:" + JSON.toJSONString(hotelVO));
			throw new BaseException("返回结果错误，酒店资源新增失败，" + icResult.getResultMsg());
		}
		
		//图片集insert
		List<PictureVO> pictureVOList = HotelVO.transFromToPicturesList(hotelVO);
		if(CollectionUtils.isNotEmpty(pictureVOList)){
			List<PicturesDO> picList = new ArrayList<PicturesDO>();
			for (PictureVO pictureVO:pictureVOList){
				PicturesDO picturesDO = new PicturesDO();
				picturesDO.setPath(pictureVO.getValue());
				picturesDO.setName(pictureVO.getName());
				picturesDO.setOutId(icResult.getModule().getId());
				picturesDO.setOutType(PictureOutType.HOTEL.getValue());
				picturesDO.setDomain(Constant.DOMAIN_JIUXIU);
				
				PictureFeature feature = new PictureFeature(null);
				feature.setPictureTag(pictureVO.getTag());
				picturesDO.setPictureFeature(feature);
				
				picList.add(picturesDO);
			}
			ICResult<Boolean> icResultPic =  resourcePublishServiceRef.addPictures(picList);
			if(null == icResultPic){
				log.error("ScenicServiceImpl.save-ResourcePublishService.addPictures result is null and parame: " + JSON.toJSONString(picList));
				throw new BaseException("酒店资源保存成功，图片集保存返回结果为空，保存失败");
			} else if(!icResultPic.isSuccess()){
				log.error("ScenicServiceImpl.save-ResourcePublishService.addPictures error:" + JSON.toJSONString(icResultPic) + "and parame: " + JSON.toJSONString(picList) + "and hotelVO:" + JSON.toJSONString(hotelVO));
				throw new BaseException("酒店资源保存成功，图片集保存失败" + icResultPic.getResultMsg());
			}
		}
		
		ICResult<HotelVO> result = new ICResult<HotelVO>();
		result.setModule(HotelVO.getHotelVO(icResult.getModule()));
		return result;
	}

	@Override
	public ICResult<Boolean> updateHotel(HotelVO hotelVO)throws Exception {
		//获取酒店资源
		ICResult<HotelDO> icResultDB = itemQueryServiceRef.getHotel(hotelVO.getId());
		if(icResultDB == null){
			log.error("HotelRPCServiceImpl.updateHotel-hotelService.getHotel result is null and parame: " + hotelVO.getId());
			throw new BaseException("返回结果为空，酒店资源修改失败");
		}else if(!icResultDB.isSuccess()){
			log.error("HotelRPCServiceImpl.updateHotel-hotelService.getHotel error:" + JSON.toJSONString(icResultDB) + "and parame: " + hotelVO.getId());
			throw new BaseException("返回结果错误，酒店资源修改失败，" + icResultDB.getResultMsg());
		}
		HotelDO hotelDB = icResultDB.getModule();

		//数据转换
		HotelDO hotelDO = HotelVO.getHotelDO(hotelVO);
		//对接

		//酒店名称
		hotelDB.setName(hotelDO.getName());
		//选择地址
		hotelDB.setLocationProvinceId(hotelDO.getLocationProvinceId());
		hotelDB.setLocationProvinceName(hotelDO.getLocationProvinceName());
		hotelDB.setLocationCityId(hotelDO.getLocationCityId());
		hotelDB.setLocationCityName(hotelDO.getLocationCityName());
		hotelDB.setLocationTownId(hotelDO.getLocationTownId());
		hotelDB.setLocationTownName(hotelDO.getLocationTownName());
		//星级
		hotelDB.setLevel(hotelDO.getLevel());
		//地址经纬度
		hotelDB.setLocationX(hotelDO.getLocationX());
		hotelDB.setLocationY(hotelDO.getLocationY());
		//酒店电话
		hotelDB.setPhoneNum(hotelDO.getPhoneNum());
		//酒店简介
		hotelDB.setDescription(hotelDO.getDescription());
		//特色描述
		hotelDB.setOneword(hotelDO.getOneword());
		//基础价格
		hotelDB.setPrice(hotelDO.getPrice());
		//酒店联系人
		hotelDB.setContactPerson(hotelDO.getContactPerson());
		hotelDB.setContactPhone(hotelDO.getContactPhone());
		//房间设施
		hotelDB.setRoomFacility(hotelDO.getRoomFacility());
		//特色服务
		hotelDB.setRoomService(hotelDO.getRoomService());
		//酒店设施
		hotelDB.setHotelFacility(hotelDO.getHotelFacility());
		//最晚到店时间
		hotelDB.setOpenTime(hotelDO.getOpenTime());
		//入住须知
		hotelDB.getNeedKnow().setExtraInfoUrl(tfsService.publishHtml5(hotelDO.getNeedKnow().getExtraInfoUrl()));
		hotelDB.getNeedKnow().setFrontNeedKnow(hotelDO.getNeedKnow().getFrontNeedKnow());
		//推荐理由
		hotelDB.setRecommend(hotelDO.getRecommend());

		//列表页展示图
		hotelDB.setLogoUrl(hotelDO.getLogoUrl());
		//详情页展示图
		hotelDB.setPicturesString(hotelVO.getPicturesStr());
		ICResult<Boolean> icResultUpdate = hotelServiceRef.updateHotel(hotelDO);
		if(icResultUpdate == null){
			log.error("HotelRPCServiceImpl.updateHotel-hotelService.updateHotel result is null and parame: " + JSON.toJSONString(hotelDB));
			throw new BaseException("返回结果为空，酒店资源修改失败");
		}else if(!icResultUpdate.isSuccess()){
			log.error("HotelRPCServiceImpl.updateHotel-hotelService.updateHotel error:" + JSON.toJSONString(icResultUpdate) + "and parame: " + JSON.toJSONString(hotelDB) + "and hotelVO:" + JSON.toJSONString(hotelVO));
			throw new BaseException("返回结果错误，酒店资源修改失败，" + icResultUpdate.getResultMsg());
		}
		if(StringUtils.isNotBlank(hotelVO.getPicListStr())){
			hotelVO.setPictureList(JSON.parseArray(hotelVO.getPicListStr(),PictureVO.class));
		}
		if(CollectionUtils.isNotEmpty(hotelVO.getPictureList())) {
			//获取图片
			PicturesPageQuery picturesPageQuery = new PicturesPageQuery();
			picturesPageQuery.setOutId(hotelVO.getId());
			picturesPageQuery.setPageNo(PIC_PAGE_NO);
			picturesPageQuery.setPageSize(PIC_PAGE_SIZE);
			picturesPageQuery.setStatus(BaseStatus.AVAILABLE.getType());
			picturesPageQuery.setOutType(PictureOutType.HOTEL.getValue());
			picturesPageQuery.setDomain(Constant.DOMAIN_JIUXIU);
			ICPageResult<PicturesDO> icPageResult = itemQueryServiceRef.queryPictures(picturesPageQuery);
			if (icPageResult == null) {
				log.error("HotelRPCServiceImpl.updateHotel-itemQueryService.queryPictures result is null and parame: " + JSON.toJSONString(picturesPageQuery));
				throw new BaseException("返回结果为空，获取酒店资源图片失败");
			} else if (!icPageResult.isSuccess()) {
				log.error("HotelRPCServiceImpl.updateHotel-itemQueryService.queryPictures error:" + JSON.toJSONString(icPageResult) + "and parame: " + JSON.toJSONString(picturesPageQuery) + "and id:" + hotelVO.getId());
				throw new BaseException("返回结果错误，获取酒店资源图片失败，" + icPageResult.getResultMsg());
			}
			List<PicturesDO> picturesDOList = icPageResult.getList();
			//图片集合处理
			PictureUpdateDTO pictureUpdateDTO = new PictureUpdateDTO();
			if(PictureVO.setPictureListPictureUpdateDTO(hotelVO.getId(),PictureOutType.HOTEL,pictureUpdateDTO, picturesDOList,hotelVO.getPictureList()) != null){
				ICResult<Boolean> updatePictrueResult = resourcePublishServiceRef.updatePictures(pictureUpdateDTO);
				if(null == updatePictrueResult){
					log.error("HotelRPCServiceImpl.save-ResourcePublishService.updatePictures result is null and parame: " + JSON.toJSONString(pictureUpdateDTO));
					throw new BaseException("酒店资源保存成功，图片集保存返回结果为空，保存失败");
				} else if(!updatePictrueResult.isSuccess()){
					log.error("HotelRPCServiceImpl.save-ResourcePublishService.updatePictures error:" + JSON.toJSONString(updatePictrueResult) + "and parame: " + JSON.toJSONString(pictureUpdateDTO) + "and hotelVO:" + JSON.toJSONString(hotelVO));
					throw new BaseException("酒店资源保存成功，图片集保存失败" + updatePictrueResult.getResultMsg());
				}
			}
		}
		return null;
	}
	
	@Override
	public ICResult<Boolean> updateHotelV2(HotelVO hotelVO)throws Exception {
		
		//判断酒店是否存在
		this.judgeExist(hotelVO);
		
		//获取酒店资源
		ICResult<HotelDO> icResultDB = itemQueryServiceRef.getHotel(hotelVO.getId());
		if(icResultDB == null){
			log.error("HotelRPCServiceImpl.updateHotel-hotelService.getHotel result is null and parame: " + hotelVO.getId());
			throw new BaseException("返回结果为空，酒店资源修改失败");
		}else if(!icResultDB.isSuccess()){
			log.error("HotelRPCServiceImpl.updateHotel-hotelService.getHotel error:" + JSON.toJSONString(icResultDB) + "and parame: " + hotelVO.getId());
			throw new BaseException("返回结果错误，酒店资源修改失败，" + icResultDB.getResultMsg());
		}
		
		//数据转换
		HotelDTO hotelDTO = HotelVO.getHotelDTO(hotelVO);
		
		ICResultSupport icResultUpdate = hotelServiceRef.updateHotelV2(hotelDTO);
		if(icResultUpdate == null){
			log.error("HotelRPCServiceImpl.updateHotel-hotelService.updateHotelV2 result is null and parame: " + JSON.toJSONString(hotelVO));
			throw new BaseException("返回结果为空，酒店资源修改失败");
		}else if(!icResultUpdate.isSuccess()){
			log.error("HotelRPCServiceImpl.updateHotel-hotelService.updateHotelV2 error:" + JSON.toJSONString(icResultUpdate) + "and parame: " + JSON.toJSONString(hotelVO));
			throw new BaseException("返回结果错误，酒店资源修改失败，" + icResultUpdate.getResultMsg());
		}
		
		List<PictureVO> pictureVOList = HotelVO.transFromToPicturesList(hotelVO);
		hotelVO.setPictureList(pictureVOList);		
		
		if(CollectionUtils.isNotEmpty(hotelVO.getPictureList())) {
			//获取图片
			PicturesPageQuery picturesPageQuery = new PicturesPageQuery();
			picturesPageQuery.setOutId(hotelVO.getId());
			picturesPageQuery.setPageNo(PIC_PAGE_NO);
			picturesPageQuery.setPageSize(PIC_PAGE_SIZE);
			picturesPageQuery.setStatus(BaseStatus.AVAILABLE.getType());
			picturesPageQuery.setOutType(PictureOutType.HOTEL.getValue());
			picturesPageQuery.setDomain(Constant.DOMAIN_JIUXIU);
			ICPageResult<PicturesDO> icPageResult = itemQueryServiceRef.queryPictures(picturesPageQuery);
			if (icPageResult == null) {
				log.error("HotelRPCServiceImpl.updateHotel-itemQueryService.queryPictures result is null and parame: " + JSON.toJSONString(picturesPageQuery));
				throw new BaseException("返回结果为空，获取酒店资源图片失败");
			} else if (!icPageResult.isSuccess()) {
				log.error("HotelRPCServiceImpl.updateHotel-itemQueryService.queryPictures error:" + JSON.toJSONString(icPageResult) + "and parame: " + JSON.toJSONString(picturesPageQuery) + "and id:" + hotelVO.getId());
				throw new BaseException("返回结果错误，获取酒店资源图片失败，" + icPageResult.getResultMsg());
			}
			List<PicturesDO> picturesDOList = icPageResult.getList();
			//图片集合处理
			PictureUpdateDTO pictureUpdateDTO = new PictureUpdateDTO();
			if(PictureVO.setPictureListPictureUpdateDTO(hotelVO.getId(),PictureOutType.HOTEL,pictureUpdateDTO, picturesDOList,hotelVO.getPictureList()) != null){
				
//				ICResult<Boolean> updatePictrueResult = resourcePublishServiceRef.updatePictures(pictureUpdateDTO);
//				if(null == updatePictrueResult){
//					log.error("HotelRPCServiceImpl.save-ResourcePublishService.updatePictures result is null and parame: " + JSON.toJSONString(pictureUpdateDTO));
//					throw new BaseException("酒店资源保存成功，图片集保存返回结果为空，保存失败");
//				} else if(!updatePictrueResult.isSuccess()){
//					log.error("HotelRPCServiceImpl.save-ResourcePublishService.updatePictures error:" + JSON.toJSONString(updatePictrueResult) + "and parame: " + JSON.toJSONString(pictureUpdateDTO) + "and hotelVO:" + JSON.toJSONString(hotelVO));
//					throw new BaseException("酒店资源保存成功，图片集保存失败" + updatePictrueResult.getResultMsg());
//				}
				boolean rs = resourceRepo.updatePictures(pictureUpdateDTO) ;
				if(!rs){
					log.error("HotelRPCServiceImpl.save-ResourcePublishService.updatePictures error:" + rs + "and parame: " + JSON.toJSONString(pictureUpdateDTO) + "and hotelVO:" + JSON.toJSONString(hotelVO));
					throw new BaseException("酒店资源保存成功，图片集保存失败");
				}
			}
		}
		return null;
	}

	@Override
	public HotelVO getHotel(long id)throws Exception {
		
		ICResult<HotelDO> icResult = itemQueryServiceRef.getHotel(id);
		if(icResult == null){
			log.error("HotelRPCServiceImpl.getHotel-hotelService.getHotel result is null and parame: " + id);
			throw new BaseException("返回结果为空，获取酒店资源失败");
		}else if(!icResult.isSuccess()){
			log.error("HotelRPCServiceImpl.getHotel-hotelService.getHotel error:" + JSON.toJSONString(icResult) + "and parame: " + id);
			throw new BaseException("返回结果错误，获取酒店资源失败，" + icResult.getResultMsg());
		}
		HotelDO hotelDO = icResult.getModule();
		//获取图片
		PicturesPageQuery picturesPageQuery = new PicturesPageQuery();
		picturesPageQuery.setOutId(id);
		picturesPageQuery.setPageNo(PIC_PAGE_NO);
		picturesPageQuery.setPageSize(PIC_PAGE_SIZE);
		picturesPageQuery.setStatus(BaseStatus.AVAILABLE.getType());
		picturesPageQuery.setOutType(PictureOutType.HOTEL.getValue());
		picturesPageQuery.setDomain(Constant.DOMAIN_JIUXIU);
		ICPageResult<PicturesDO> icPageResult = itemQueryServiceRef.queryPictures(picturesPageQuery);
		if(icPageResult == null){
			log.error("HotelRPCServiceImpl.getHotel-itemQueryService.queryPictures result is null and parame: " + JSON.toJSONString(picturesPageQuery));
			throw new BaseException("返回结果为空，获取酒店资源图片失败");
		}else if(!icPageResult.isSuccess()){
			log.error("HotelRPCServiceImpl.getHotel-itemQueryService.queryPictures error:" + JSON.toJSONString(icPageResult) + "and parame: " + JSON.toJSONString(picturesPageQuery) + "and id:" + id);
			throw new BaseException("返回结果错误，获取酒店资源图片失败，" + icPageResult.getResultMsg());
		}
		List<PicturesDO> picturesDOList = icPageResult.getList();
		List<PictureVO> pictureVOList = new ArrayList<PictureVO>();
		if(CollectionUtils.isNotEmpty(picturesDOList)){
			for(PicturesDO picturesDO : picturesDOList){
				PictureVO pictureVO = new PictureVO();
				pictureVO.setId(picturesDO.getId());
				pictureVO.setName(picturesDO.getName());
				pictureVO.setValue(picturesDO.getPath());
				pictureVO.setIsTop(picturesDO.isTop());
				pictureVOList.add(pictureVO);

			}
		}
		HotelVO hotelVO = HotelVO.getHotelVO(hotelDO);
		hotelVO.setPictureList(pictureVOList);
		
		List<PictureVO> picVOList = getPictureVOListByTag(picturesDOList, PictureTag.OUTLOOK.getValue());
		hotelVO.setOutlookPicList(picVOList);
		
		picVOList = getPictureVOListByTag(picturesDOList, PictureTag.INDOOR.getValue());
		hotelVO.setInDoorPicList(picVOList);
		
		picVOList = getPictureVOListByTag(picturesDOList, PictureTag.ROOM.getValue());
		hotelVO.setRoomPicList(picVOList);
		
		picVOList = getPictureVOListByTag(picturesDOList, PictureTag.OTHER.getValue());
		hotelVO.setOtherPicList(picVOList);
		
		return hotelVO;
	}
	
	@Override
	public HotelVO getHotelV2(long id)throws Exception {
		
		ICResult<HotelDO> icResult = itemQueryServiceRef.getHotel(id);
		if(icResult == null){
			log.error("HotelRPCServiceImpl.getHotel-hotelService.getHotel result is null and parame: " + id);
			throw new BaseException("返回结果为空，获取酒店资源失败");
		}else if(!icResult.isSuccess()){
			log.error("HotelRPCServiceImpl.getHotel-hotelService.getHotel error:" + JSON.toJSONString(icResult) + "and parame: " + id);
			throw new BaseException("返回结果错误，获取酒店资源失败，" + icResult.getResultMsg());
		}
		HotelDO hotelDO = icResult.getModule();
		//获取图片
		PicturesPageQuery picturesPageQuery = new PicturesPageQuery();
		picturesPageQuery.setOutId(id);
		picturesPageQuery.setPageNo(PIC_PAGE_NO);
		picturesPageQuery.setPageSize(PIC_PAGE_SIZE);
		picturesPageQuery.setStatus(BaseStatus.AVAILABLE.getType());
		picturesPageQuery.setOutType(PictureOutType.HOTEL.getValue());
		picturesPageQuery.setDomain(Constant.DOMAIN_JIUXIU);
		ICPageResult<PicturesDO> icPageResult = itemQueryServiceRef.queryPictures(picturesPageQuery);
		if(icPageResult == null){
			log.error("HotelRPCServiceImpl.getHotel-itemQueryService.queryPictures result is null and parame: " + JSON.toJSONString(picturesPageQuery));
			throw new BaseException("返回结果为空，获取酒店资源图片失败");
		}else if(!icPageResult.isSuccess()){
			log.error("HotelRPCServiceImpl.getHotel-itemQueryService.queryPictures error:" + JSON.toJSONString(icPageResult) + "and parame: " + JSON.toJSONString(picturesPageQuery) + "and id:" + id);
			throw new BaseException("返回结果错误，获取酒店资源图片失败，" + icPageResult.getResultMsg());
		}
		List<PicturesDO> picturesDOList = icPageResult.getList();
		List<PictureVO> pictureVOList = new ArrayList<PictureVO>();
		
		HotelVO hotelVO = HotelVO.getHotelVO(hotelDO);
		hotelVO.setPictureList(pictureVOList);
		
		List<PictureVO> picVOList = getPictureVOListByTag(picturesDOList, PictureTag.OUTLOOK.getValue());
		hotelVO.setOutlookPicList(picVOList);
		
		picVOList = getPictureVOListByTag(picturesDOList, PictureTag.INDOOR.getValue());
		hotelVO.setInDoorPicList(picVOList);
		
		picVOList = getPictureVOListByTag(picturesDOList, PictureTag.ROOM.getValue());
		hotelVO.setRoomPicList(picVOList);
		
		picVOList = getPictureVOListByTag(picturesDOList, PictureTag.OTHER.getValue());
		hotelVO.setOtherPicList(picVOList);
		
		// 图文详情
		hotelVO.setPictureText(getPictureText(id));;
		
		return hotelVO;
	}

	@Override
	public List<HotelFacilityVO> queryFacilities(int type) throws Exception{
		
		ICPageResult<FacilityIconDO> icPageResult = itemQueryServiceRef.queryFacilities(type);
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
	public List<HotelFacilityVO> queryFacilitiesV2(int type) throws Exception{
		
		FacilityQueryDTO queryDTO = new FacilityQueryDTO();
		queryDTO.setDomain(Constant.DOMAIN_JIUXIU);
		queryDTO.setStatus(BaseStatus.AVAILABLE.getType());
		queryDTO.setType(type);
		
		ICPageResult<FacilityIconDO> icPageResult = itemQueryServiceRef.queryFacilities(queryDTO);
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
	
	public List<PictureVO> getPictureVOListByTag(List<PicturesDO> picturesDOList, int pictureTagValue){
		
		List<PictureVO> pictureVOList = new ArrayList<PictureVO>();
		if(CollectionUtils.isEmpty(picturesDOList)){
			return pictureVOList;
		}
		
		PictureFeature feature = null;
		for(PicturesDO picturesDO : picturesDOList){
			
			feature = picturesDO.getPictureFeature();
			if(feature == null){
				continue;
			}
			
			if(feature.getPictureTag() != pictureTagValue){
				continue;
			}
			
			PictureVO pictureVO = new PictureVO();
			pictureVO.setId(picturesDO.getId());
			pictureVO.setName(picturesDO.getName());
			pictureVO.setValue(picturesDO.getPath());
			pictureVO.setIsTop(picturesDO.isTop());
			pictureVOList.add(pictureVO);

		}
		return pictureVOList;
	}
	
	/**
	 * 获取酒店所属的房型列表
	 */
	public ICResult<List<RoomDO>> queryAllRoom(long hotelId) throws Exception{
		
		RoomQuery roomQuery = new RoomQuery();
		roomQuery.setHotelId(hotelId);
		ICResult<List<RoomDO>> icResult = itemQueryServiceRef.queryAllRoom(roomQuery);
		return icResult;
	}

	@Override
	public ICResult<RoomDO> addRoom(RoomVO roomVO) throws Exception{		
		RoomDO roomDO = RoomVO.getRoomDO(roomVO);
		ICResult<RoomDO> result = hotelServiceRef.addRoom(roomDO);
		return result;
	}

	@Override
	public ICResultSupport updateRoom(RoomVO roomVO) throws Exception{
		
		long id = roomVO.getId();
		ICResult<RoomDO> roomDB = itemQueryServiceRef.getRoom(id);
		
		if(roomDB == null || roomDB.getModule() == null){
			log.error("HotelRPCServiceImpl.updateRoom-itemQueryServic.getRoom result is null and parame: " + id);
			throw new BaseException("返回结果为空，获取酒店资源失败");
		}else if(!roomDB.isSuccess()){
			log.error("HotelRPCServiceImpl.getHotel-itemQueryServic.getRoom error:" + JSON.toJSONString(roomDB) + "and parame: " + id);
			throw new BaseException("返回结果错误，获取酒店资源失败，" + roomDB.getResultMsg());
		}
		
		RoomDO roomDO = RoomVO.getRoomDO(roomVO);
		
		RoomDTO roomDTO = new RoomDTO();
		roomDTO.setId(roomDO.getId());
		roomDTO.setName(roomDO.getName());
		roomDTO.setFeature(roomDO.getFeature());
		roomDTO.setPics(roomDO.getPics());
		
		ICResultSupport result = hotelServiceRef.updateRoom(roomDTO);
		return result;
	}

	@Override
	public ICResult<RoomVO> getRoom(long id) throws Exception{
		ICResult<RoomDO> roomDB = itemQueryServiceRef.getRoom(id);
		if(roomDB == null || roomDB.getModule() == null){
			log.error("HotelRPCServiceImpl.updateRoom-itemQueryServic.getRoom result is null and parame: " + id);
			throw new BaseException("返回结果为空，获取酒店资源失败");
		}else if(!roomDB.isSuccess()){
			log.error("HotelRPCServiceImpl.getHotel-itemQueryServic.getRoom error:" + JSON.toJSONString(roomDB) + "and parame: " + id);
			throw new BaseException("返回结果错误，获取酒店资源失败，" + roomDB.getResultMsg());
		}
		RoomVO roomVO = RoomVO.getRoomVO(roomDB.getModule());
		
		ICResult<RoomVO> result = new ICResult<RoomVO>();
		result.setModule(roomVO);
		result.setSuccess(roomDB.isSuccess());
		result.setResultMsg(roomDB.getResultMsg());
		return result;
		
	}

	@Override
	public ICResultSupport updateRoomStatus(RoomVO roomVO) throws Exception {
		
		RoomStatusDTO roomStatusDTO = new RoomStatusDTO();
		roomStatusDTO.setId(roomVO.getId());
		roomStatusDTO.setStatus(roomVO.getStatus());
		ICResultSupport result = hotelServiceRef.updateRoomStatus(roomStatusDTO);
		return result;
	}

	@Override
	public void savePictureText(long id, PictureTextVO pictureTextVO) throws Exception {
		ComentEditDTO comentEditDTO = PictureTextConverter.toComentEditDTO(id, PictureText.HOTEL, pictureTextVO);
		pictureTextRepo.editPictureText(comentEditDTO);
	}
	
	@Override
	public PictureTextVO getPictureText(long id) throws Exception {
		if(id == 0){
			return null;
		}
		
		// 图文详情
		PicTextResult picTextResult = pictureTextRepo.getPictureText(id, PictureText.HOTEL);
		PictureTextVO pictureTextVO = PictureTextConverter.toPictureTextVO(picTextResult);
		return pictureTextVO;
	}
	
	public void judgeExist(HotelVO hotelVO) throws Exception{
		
		if(hotelVO == null){
			return;
		}
		
		HotelListQuery query = new HotelListQuery();
		query.setDomain(Constant.DOMAIN_JIUXIU);
		query.setName(hotelVO.getName());
		PageVO<HotelDO> pageVo = this.pageQueryHotel(query);
		if(pageVo == null){
			return;
		}
		
		List<HotelDO> list = pageVo.getItemList();
		if(list == null || list.size() == 0){
			return;
		}
		
		String hotelName = hotelVO.getName().trim();

		HotelDO hotelDO = null;
		for(int i = 0; i < list.size(); i++){
			hotelDO = list.get(i);
			if(!hotelName.equals(hotelDO.getName().trim())){
				continue;
			}
			
			if(hotelVO.getId() != hotelDO.getId()){
				throw new BaseException("该酒店已存在，不需要重复录入！");
			}
		}
	}

	@Override
	public BizResult<Boolean> setHotelWeight(long id, int weight) {
		
		BizResult<Boolean> result = new BizResult<Boolean>();
	//	HotelPublishDTO dto = new HotelPublishDTO();
		SetHotelWeightDTO setHotelWeightDTO = new SetHotelWeightDTO();
		setHotelWeightDTO.setId(id);
		setHotelWeightDTO.setOrderNum(weight);
	/*	dto.setSort(weight);
		ItemDO itemDO = new ItemDO();
		itemDO.setId(id);
		dto.setItemDO(itemDO);*/
		boolean setResult = hotelServiceRef.setHotelWeight(setHotelWeightDTO);
		if (!setResult) {
			result.setPalaceReturnCode(PalaceReturnCode.UPDATE_WEIGHT_FAILED);
			return result;
		}
		return result;
	}

	
}
