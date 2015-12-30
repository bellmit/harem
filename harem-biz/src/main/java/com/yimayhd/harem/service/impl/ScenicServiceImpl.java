package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yimayhd.harem.base.BaseException;
import com.yimayhd.harem.model.ScenicVO;
import com.yimayhd.harem.model.query.HotelListQuery;
import com.yimayhd.harem.model.query.ScenicListQuery;
import com.yimayhd.harem.model.PictureVO;
import com.yimayhd.ic.client.model.domain.PicturesDO;
import com.yimayhd.ic.client.model.enums.PictureOutType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.service.ScenicService;
import com.yimayhd.harem.service.TfsService;
import com.yimayhd.harem.util.DateUtil;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.domain.share_json.NeedKnow;
import com.yimayhd.ic.client.model.domain.share_json.TextItem;
import com.yimayhd.ic.client.model.param.item.ScenicAddNewDTO;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.ic.client.service.item.ResourcePublishService;

/**
 * Created by Administrator on 2015/11/18.
 */
public class ScenicServiceImpl implements ScenicService {
	private static final Logger log = LoggerFactory.getLogger(ScenicServiceImpl.class);
	@Autowired
	private ItemQueryService itemQueryService;
	@Autowired
	private ResourcePublishService resourcePublishServiceRef;
	@Autowired
	private TfsService tfsService;

	@Override
	public PageVO<ScenicDO> getList(ScenicListQuery scenicListQuery) throws Exception {
		int totalCount = 0;
		ScenicPageQuery pageQuery = new ScenicPageQuery();
		pageQuery.setNeedCount(true);
		if(scenicListQuery.getPageNumber()!=null){
			int pageNumber =scenicListQuery.getPageNumber();
			int pageSize = scenicListQuery.getPageSize();
			pageQuery.setPageNo(pageNumber);
			pageQuery.setPageSize(pageSize);
		}
		//景区名称
		if (!StringUtils.isBlank(scenicListQuery.getTags())) {
			pageQuery.setTags(scenicListQuery.getTags());			
		}
		//景区状态
		if (scenicListQuery.getItemStatus() != null) {			
			pageQuery.setItemStatus(scenicListQuery.getItemStatus());
		}
		//开始时间
		if (!StringUtils.isBlank(scenicListQuery.getStartTime())) {
			Date startTime = DateUtil.parseDate(scenicListQuery.getStartTime());
			pageQuery.setStartTime(startTime);
		}
				
		//结束时间
		if (!StringUtils.isBlank(scenicListQuery.getEndTime())) {
			Date endTime = DateUtil.parseDate(scenicListQuery.getEndTime());
			pageQuery.setEndTime(DateUtil.add23Hours(endTime));
		}
		//景区等级
		if (scenicListQuery.getLevel() != null) {			
			pageQuery.setLevel(scenicListQuery.getLevel() );
		}
		List<ScenicDO> itemList = new ArrayList<ScenicDO>();
		
		ICPageResult<ScenicDO> pageResult = itemQueryService.pageQueryScenic(pageQuery);
		if (pageResult != null && pageResult.isSuccess()) {
			totalCount = pageResult.getTotalCount();
			if (CollectionUtils.isNotEmpty(pageResult.getList())) {
				itemList.addAll(pageResult.getList());
			}
		} else {
			log.error("itemQueryService.pageQueryScenic return value is null !returnValue :"
					+ JSON.toJSONString(pageResult));
		}
		return new PageVO<ScenicDO>(pageQuery.getPageNo(), pageQuery.getPageSize(), totalCount, itemList);
	}

	@Override
	public ScenicAddNewDTO getById(long id) throws Exception {

		ICResult<ScenicDO> scenic = itemQueryService.getScenic(id);
		if (scenic.isSuccess()) {
			ScenicAddNewDTO dto = new ScenicAddNewDTO();
			ScenicDO scenicDO = scenic.getModule();
			NeedKnow needKnow = scenicDO.getNeedKnow();
			String extraInfoUrl = "";
			if (needKnow != null && StringUtils.isNotBlank(needKnow.getExtraInfoUrl())) {
				extraInfoUrl = tfsService.readHtml5(needKnow.getExtraInfoUrl());
				needKnow.setExtraInfoUrl(extraInfoUrl);
			}
			List<String> pictures = scenicDO.getPictures();
			if(pictures !=null && pictures.size()!=0){
				scenicDO.setCoverUrl(StringUtils.join(pictures.toArray(),"|"));
			}
			
			
			dto.setNeedKnow(needKnow);
			dto.setScenic(scenicDO);
			return dto;
		} else {
			log.error("itemQueryService.getScenic return value is null !returnValue :" + JSON.toJSONString(scenic));
		}
		return null;
	}

	public boolean enableScenicItem(long id) throws Exception {
		
		ItemPubResult result = resourcePublishServiceRef.enableScenicItem(id);
		if(!result.isSuccess()){
			log.error("enableScenicItem return value is null !returnValue :"
					+ JSON.toJSONString(result));
		}
		return result.isSuccess();
		
	}
	
	
	public boolean disableScenicItem(int id) throws Exception {
		ItemPubResult result = resourcePublishServiceRef.disableScenicItem(id);
		if(!result.isSuccess()){
			log.error("disableScenicItem return value is null !returnValue :"
					+ JSON.toJSONString(result));
		}
		return result.isSuccess();
	}
	
	/*@Override
	public boolean updateStatus(int id, int scenicStatus) throws Exception {
		ArrayList<ScenicDO> scenicDOList = new ArrayList<ScenicDO>();
		ScenicDO scenic = new ScenicDO();
		scenic.setId(id);
		scenic.setStatus(scenicStatus);
		scenicDOList.add(scenic);
		ICResult<ScenicDO> result = resourcePublishServiceRef.updateScenic(scenicDOList);
		if (!result.isSuccess()) {
			log.error("resourcePublishServiceRef.updateScenic return value is null !returnValue :"
					+ JSON.toJSONString(result));
		}
		
		resourcePublishServiceRef.enableScenicItem(arg0)
		
		return result.isSuccess();
	}
*/
/*	@Override
	public boolean batchupdateStatus(ArrayList<Integer> scenicIdList, int scenicStatus) {
		if (!scenicIdList.isEmpty()) {
			ArrayList<ScenicDO> scenicDOList = new ArrayList<ScenicDO>();
			for (Integer id : scenicIdList) {
				ScenicDO scenic = new ScenicDO();
				scenic.setId(id);
				scenic.setStatus(scenicStatus);
				scenicDOList.add(scenic);
			}
			ICResult<ScenicDO> result = resourcePublishServiceRef.updateScenic(scenicDOList);
			if (!result.isSuccess()) {
				log.error("resourcePublishServiceRef.updateScenic return value is null !returnValue :"
						+ JSON.toJSONString(result));
			}
		}
		return false;

	}
*/
	@Override
	public ICResult<ScenicDO> save(ScenicVO scenicVO) throws Exception {

		ICResult<ScenicDO> addScenicNew = null;

		if (0 == scenicVO.getId()) {
			ScenicAddNewDTO addNewDTO = new ScenicAddNewDTO();

			ScenicDO scenicDO = ScenicVO.getScenicDO(scenicVO);
			addNewDTO.setScenic(scenicDO);
			scenicDO.setMemberPrice(scenicDO.getPrice());
			//NeedKnowOb
			List<TextItem> frontNeedKnow = scenicVO.getNeedKnowOb().getFrontNeedKnow();
			List<TextItem> newFrontNeedKnow =new ArrayList<TextItem>();
			if(frontNeedKnow!=null&&!frontNeedKnow.isEmpty()){
				for (int i = 0; i < frontNeedKnow.size(); i++) {
					if(StringUtils.isNotBlank(frontNeedKnow.get(i).getTitle())||StringUtils.isNotBlank(frontNeedKnow.get(i).getContent())){
						newFrontNeedKnow.add(frontNeedKnow.get(i));
					}
				}
				scenicVO.getNeedKnowOb().setFrontNeedKnow(newFrontNeedKnow);
			}
			
			
			addNewDTO.setNeedKnow(scenicVO.getNeedKnowOb());
			scenicDO.setRecommend(scenicVO.getMasterRecommend());
			//购买须知存tfs
			if(org.apache.commons.lang.StringUtils.isNotBlank(addNewDTO.getNeedKnow().getExtraInfoUrl())) {
				addNewDTO.getNeedKnow().setExtraInfoUrl(tfsService.publishHtml5(addNewDTO.getNeedKnow().getExtraInfoUrl()));
			}
			addScenicNew = resourcePublishServiceRef.addScenicNew(addNewDTO);
			if(null == addScenicNew){
				log.error("ScenicServiceImpl.save-ResourcePublishService.addScenicNew result is null and parame: " + JSON.toJSONString(addNewDTO));
				throw new BaseException("修改返回结果为空,修改失败");
			} else if(!addScenicNew.isSuccess()){
				log.error("ScenicServiceImpl.save-ResourcePublishService.addScenicNew error:" + JSON.toJSONString(addScenicNew) + "and parame: " + JSON.toJSONString(addNewDTO) + "and scenicVO:" + JSON.toJSONString(scenicVO));
				throw new BaseException(addScenicNew.getResultMsg());
			}
			
			//图片集insert
			if(org.apache.commons.lang.StringUtils.isNotBlank(scenicVO.getPicListStr())){
				List<PictureVO> pictureVOList = JSON.parseArray(scenicVO.getPicListStr(),PictureVO.class);
				List<PicturesDO> picList = new ArrayList<PicturesDO>();
				for (PictureVO pictureVO:pictureVOList){
					PicturesDO picturesDO = new PicturesDO();
					picturesDO.setPath(pictureVO.getValue());
					picturesDO.setName(pictureVO.getName());
					picturesDO.setOutId(addScenicNew.getModule().getId());
					picturesDO.setOutType(PictureOutType.SCENIC.getValue());
					picturesDO.setOrderNum(pictureVO.getIndex());
					picturesDO.setIsTop(pictureVO.isTop());
					picList.add(picturesDO);
				}
				ICResult<Boolean> icResult =  resourcePublishServiceRef.addPictures(picList);
				if(null == icResult){
					log.error("ScenicServiceImpl.save-ResourcePublishService.addScenicNew result is null and parame: " + JSON.toJSONString(picList));
					throw new BaseException("景区资源保存成功，图片集保存返回结果为空，保存失败");
				} else if(!icResult.isSuccess()){
					log.error("ScenicServiceImpl.save-ResourcePublishService.addScenicNew error:" + JSON.toJSONString(icResult) + "and parame: " + JSON.toJSONString(picList) + "and scenicVO:" + JSON.toJSONString(scenicVO));
					throw new BaseException("景区资源保存成功，图片集保存失败" + icResult.getResultMsg());
				}
			}
		} else {
			ICResult<ScenicDO> icResult = itemQueryService.getScenic(scenicVO.getId());
			if(null == icResult){
				log.error("ScenicServiceImpl.save-itemQueryService.getScenic result is null and parame: " + scenicVO.getId());
				throw new BaseException("查询结果为空,修改失败 ");
			} else if(!icResult.isSuccess()){
				log.error("ScenicServiceImpl.save-itemQueryService.getScenic error:" + JSON.toJSONString(icResult) + "and parame: " + scenicVO.getId());
				throw new BaseException(icResult.getResultMsg());
			}
			ScenicDO scenicDB = icResult.getModule();
			if(scenicDB == null){
				log.error("ScenicServiceImpl.save-itemQueryService.getScenic result.getModule is null and parame: " + scenicVO.getId());
			}
			ScenicAddNewDTO addNewDTO = new ScenicAddNewDTO();
			ScenicDO scenicDO = ScenicVO.getScenicDO(scenicVO);
			addNewDTO.setScenic(scenicDO);
			scenicDO.setMemberPrice(scenicDO.getPrice());
			//NeedKnowOb
			addNewDTO.setNeedKnow(scenicVO.getNeedKnowOb());
			scenicDO.setRecommend(scenicVO.getMasterRecommend());
			//购买须知存tfs
			if(org.apache.commons.lang.StringUtils.isNotBlank(addNewDTO.getNeedKnow().getExtraInfoUrl())) {
				addNewDTO.getNeedKnow().setExtraInfoUrl(tfsService.publishHtml5(addNewDTO.getNeedKnow().getExtraInfoUrl()));
			}
			//TODO 修改项处理
			addScenicNew = resourcePublishServiceRef.updateScenicNew(addNewDTO);
			if(null == addScenicNew){
				log.error("ScenicServiceImpl.save-ResourcePublishService.updateScenicNew result is null and parame: " + JSON.toJSONString(addNewDTO));
				throw new BaseException("修改返回结果为空,修改失败");
			} else if(!addScenicNew.isSuccess()){
				log.error("ScenicServiceImpl.save-ResourcePublishService.updateScenicNew error:" + JSON.toJSONString(addScenicNew) + "and parame: " + JSON.toJSONString(addNewDTO) + "and scenicVO:" + JSON.toJSONString(scenicVO));
				throw new BaseException(addScenicNew.getResultMsg());
			}
		}
		return addScenicNew;
	}

	@Override
	public boolean batchEnableStatus(ArrayList<Integer> scenicIdList) {
		ItemPubResult result = new ItemPubResult();
		for (Integer id : scenicIdList) {
			result = resourcePublishServiceRef.enableScenicItem(id);
		}
		
		if(!result.isSuccess()){
			log.error("disableScenicItem return value is null !returnValue :"
					+ JSON.toJSONString(result));
		}
		return result.isSuccess();
	}

	@Override
	public boolean batchDisableStatus(ArrayList<Integer> scenicIdList) {
		ItemPubResult result = new ItemPubResult();
		for (Integer id : scenicIdList) {
			result = resourcePublishServiceRef.disableScenicItem(id);
		}
		
		if(!result.isSuccess()){
			log.error("disableScenicItem return value is null !returnValue :"
					+ JSON.toJSONString(result));
		}
		return result.isSuccess();
	}

	
	
}
