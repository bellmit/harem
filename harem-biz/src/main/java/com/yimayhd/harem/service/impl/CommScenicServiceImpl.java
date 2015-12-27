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
import com.yimayhd.harem.service.CommScenicService;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.enums.ItemStatus;
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
	public ItemPubResult save(ScenicPublishDTO scenicPublishDTO, Long[] check) throws Exception {

		ItemDO itemDO = scenicPublishDTO.getItemDO();
		 
		itemDO.setSellerId(B2CConstant.YIMAY_OFFICIAL_ID);
		itemDO.setOutType(ResourceType.SCENIC.getType());
		scenicPublishDTO.getScenicDO().setId(scenicPublishDTO.getItemDO().getOutId());
		scenicPublishDTO.getScenicDO().setItemStatus(ItemStatus.valid.getValue());
		itemDO.setCredit(0);
		itemDO.setPoint(0);
		itemDO.setOriginalCredit(0);
		itemDO.setOriginalPoint(0);
		itemDO.setOriginalPrice(0);
		itemDO.setStockNum(9999);//默认库存
		ItemPubResult publicScenic = itemPublishService.publishScenic(scenicPublishDTO);
		if (publicScenic != null && publicScenic.isSuccess()) {
			TagRelationInfoDTO tagRelationInfoDTO = new TagRelationInfoDTO();
			tagRelationInfoDTO.setTagType(TagType.VIEWTAG.getType());
			tagRelationInfoDTO.setOutId(itemDO.getOutId());
			tagRelationInfoDTO.setOrderTime(new Date());
			tagRelationInfoDTO.setList(Arrays.asList(check));
			BaseResult<Boolean> addTagRelationInfo = comCenterServiceRef.addTagRelationInfo(tagRelationInfoDTO);
			if (addTagRelationInfo != null && addTagRelationInfo.isSuccess()) {

			} else {
				log.error("保存景区主题失败：" + addTagRelationInfo.getResultMsg());
				log.error(MessageFormat.format("保存景区主题失败：tagRelationInfo={0}", JSON.toJSONString(tagRelationInfoDTO)));
				log.error(MessageFormat.format("保存景区主题失败：tagResult={0}", JSON.toJSONString(addTagRelationInfo)));
				throw new BaseException("保存景区主题失败");
			}

		} else {
			log.error("保存线路失败：" + publicScenic.getResultMsg());
			log.error(MessageFormat.format("保存景区失败：line={0}", JSON.toJSONString(publicScenic)));
			throw new BaseException("保存景区失败");
		}
		return publicScenic;
	}

}
