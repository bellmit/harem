package com.yimayhd.harem.service.impl;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.dto.TagRelationInfoDTO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.harem.base.BaseException;
import com.yimayhd.harem.constant.B2CConstant;
import com.yimayhd.harem.model.CommScenicVO;
import com.yimayhd.harem.service.CommScenicService;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemFeature;
import com.yimayhd.ic.client.model.enums.ItemFeatureKey;
import com.yimayhd.ic.client.model.enums.ItemPicUrlsKey;
import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.ic.client.model.enums.ReduceType;
import com.yimayhd.ic.client.model.enums.ResourceType;
import com.yimayhd.ic.client.model.param.item.ScenicPublishDTO;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.ic.client.service.item.ItemPublishService;

/**
 * Created by Administrator on 2015/11/18.
 */
public class CommScenicServiceImpl implements CommScenicService {
	private static final Logger log = LoggerFactory.getLogger(CommScenicServiceImpl.class);
	@Autowired
	private ItemPublishService itemPublishService;
	@Resource
	protected ComCenterService comCenterServiceRef;

	@Override
	public ItemPubResult save(CommScenicVO commScenic) throws Exception {
		ScenicPublishDTO dto = new ScenicPublishDTO();
		ItemDO itemDO = commScenic.getItemDO();
		
		
		ScenicDO scenicDO= new ScenicDO();
		scenicDO.setId(itemDO.getOutId());
		//scenicPublishDTO.getScenicDO().setId(scenicPublishDTO.getItemDO().getOutId());
		//scenicPublishDTO.getScenicDO().setItemStatus(ItemStatus.valid.getValue());
		
		itemDO.addPicUrls(ItemPicUrlsKey.SMALL_LIST_PIC, commScenic.getSmallListPic());
		itemDO.addPicUrls(ItemPicUrlsKey.BIG_LIST_PIC, commScenic.getBigListPic());
		itemDO.addPicUrls(ItemPicUrlsKey.COVER_PICS, commScenic.getCoverPics());
		itemDO.setPrice((long) (commScenic.getPriceF() * 100));
		ItemFeature itemFeature = new ItemFeature(null);
		 //减库存方式
        itemFeature.put(ItemFeatureKey.REDUCE_TYPE,ReduceType.NONE.getBizType());
        //未付款超时时间
        //itemFeature.put(ItemFeatureKey.NOT_PAY_TIMEOUT,3 * 24 * 3600 * 1000L);
        //商品星级
        itemFeature.put(ItemFeatureKey.GRADE,5);
        //可预订时间，秒
        itemFeature.put(ItemFeatureKey.END_BOOK_TIME_LIMIT,commScenic.getEndTime()* 24 * 3600 );
        //需要提前多久预订，秒startDayTime*24 * 3600 * 1000L+startHourTime* 3600 * 1000L
        itemFeature.put(ItemFeatureKey.START_BOOK_TIME_LIMIT,commScenic.getStartDayTime()*24+(24-commScenic.getStartHourTime()));
        itemDO.setItemFeature(itemFeature);
		itemDO.setSellerId(B2CConstant.YIMAY_OFFICIAL_ID);
		itemDO.setOutType(ResourceType.SCENIC.getType());
		itemDO.setCredit(0);
		itemDO.setPoint(0);
		itemDO.setOriginalCredit(0);
		itemDO.setOriginalPoint(0);
		itemDO.setOriginalPrice(0);
		itemDO.setStockNum(9999);//默认库存
		dto.setItemDO(itemDO);
		dto.setScenicDO(scenicDO);
		ItemPubResult publicScenic = itemPublishService.publishScenic(dto);
		if (publicScenic != null && publicScenic.isSuccess()) {
			TagRelationInfoDTO tagRelationInfoDTO = new TagRelationInfoDTO();
			tagRelationInfoDTO.setTagType(TagType.VIEWTAG.getType());
			tagRelationInfoDTO.setOutId(itemDO.getOutId());
			tagRelationInfoDTO.setOrderTime(new Date());
			tagRelationInfoDTO.setList(Arrays.asList(commScenic.getCheck()));
			BaseResult<Boolean> addTagRelationInfo = comCenterServiceRef.addTagRelationInfo(tagRelationInfoDTO);
			if (!addTagRelationInfo.isSuccess()) {
				log.error("保存景区主题失败：" + addTagRelationInfo.getResultMsg());
				log.error(MessageFormat.format("保存景区主题失败：tagRelationInfo={0}", JSON.toJSONString(tagRelationInfoDTO)));
				log.error(MessageFormat.format("保存景区主题失败：tagResult={0}", JSON.toJSONString(addTagRelationInfo)));
				throw new BaseException("保存景区主题失败");
			}

		} else {
			log.error("保存景区失败：" + publicScenic.getResultMsg());
			log.error(MessageFormat.format("保存景区失败：line={0}", JSON.toJSONString(publicScenic)));
			throw new BaseException("保存景区失败");
		}
		return publicScenic;
	}
	
	
	
	

}
