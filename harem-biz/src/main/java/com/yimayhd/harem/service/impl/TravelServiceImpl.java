package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.dto.TagRelationInfoDTO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.harem.base.BaseException;
import com.yimayhd.harem.model.travel.BaseTravel;
import com.yimayhd.ic.client.model.param.item.LinePublishDTO;
import com.yimayhd.ic.client.model.result.item.LinePublishResult;
import com.yimayhd.ic.client.model.result.item.LineResult;
import com.yimayhd.ic.client.service.item.ItemPublishService;
import com.yimayhd.ic.client.service.item.ItemQueryService;

public abstract class TravelServiceImpl<T extends BaseTravel> {
	private static final Logger log = LoggerFactory.getLogger(CommodityServiceImpl.class);
	@Resource
	protected ItemQueryService itemQueryServiceRef;
	@Resource
	protected ItemPublishService itemPublishServiceRef;
	@Resource
	protected ComCenterService comCenterServiceRef;

	public T getById(long id, Class<T> clazz) throws Exception {
		// TODO YEBIN 通过ID获取跟团游对象
		if (id == 0) {
			return null;
		}
		LineResult lineResult = null;
		lineResult = itemQueryServiceRef.getLineResult(id);
		if (lineResult != null && lineResult.isSuccess()) {
			BaseResult<List<ComTagDO>> tagResult = comCenterServiceRef.getTagInfoByOutIdAndType(id,
					TagType.LINETAG.name());
			List<ComTagDO> tags = null;
			if (tagResult != null && tagResult.isSuccess()) {
				tags = tagResult.getValue();
			} else {
				throw new BaseException("获取线路标签出错：tagResult={0}", tagResult);
			}
			if (tags == null) {
				tags = new ArrayList<ComTagDO>();
			}
			T travel = null;
			try {
				travel = createTravelInstance(lineResult, tags);
			} catch (Exception e) {
				log.error("解析线路信息失败：lineResult={}", JSON.toJSONString(lineResult));
				log.error("解析线路信息失败：tags={}", JSON.toJSONString(tags));
				log.error("解析线路信息失败", e);
				throw new BaseException("解析线路信息失败");
			}
			return travel;
			/*
			 * String json = "{'baseInfo':{" + "'extraInfos':[" +
			 * "{'content':'1','title':'a'}," + "{'content':'2','title':'b'}," +
			 * "{'content':'3','title':'c'}," + "{'content':'4','title':'d'}," +
			 * "{'content':'5','title':'e'}" + "{'content':'6','title':'f'}" +
			 * "],'from':{'id':'2','name':'南京'},'highlights':'123','id':0,'name':'跟团游1','productImage':'T1jyJTBCZT1RXrhCrK.jpg','publisher':{'id':'0','name':'Gost'},'publisherType':'2','recommond':'123','tags':[{'id':'2','name':'蜜月之旅'}],'themes':[{'id':'2','name':'亲子'}],'to':{'id':'4','name':'丽江'},'tripImage':'T1jtJTBCZT1RXrhCrK.jpg','type':0},'id':0,'priceInfo':{'limit':1,'tcs':[{'endDate':1448985600000,'from':'23123','months':[{'date':1446307200000,'days':[{'adult':{'discount':'1','price':'1','stock':'1'},'children02':{'discount':'1','price':'1','stock':'1'},'children212':{'discount':'1','price':'1','stock':'1'},'date':1448812800000,'srd':'1'}]},{'date':1448899200000,'days':[{'adult':{'discount':'1','price':'1','stock':'1'},'children02':{'discount':'1','price':'1','stock':'1'},'children212':{'discount':'1','price':'1','stock':'1'},'date':1448899200000,'srd':'1'},{'adult':{'discount':'1','price':'1','stock':'1'},'children02':{'discount':'1','price':'1','stock':'1'},'children212':{'discount':'1','price':'1','stock':'1'},'date':1448985600000,'srd':'1'}]}],'name':'123','startDate':1448812800000},{'endDate':1448985600000,'from':'上海','months':[{'date':1446307200000,'days':[{'adult':{'discount':'2','price':'13','stock':'2'},'children02':{'discount':'331','price':'121','stock':'2'},'children212':{'discount':'231','price':'32','stock':'2'},'date':1448812800000,'srd':'113'}]},{'date':1448899200000,'days':[{'adult':{'discount':'2','price':'13','stock':'2'},'children02':{'discount':'331','price':'121','stock':'2'},'children212':{'discount':'231','price':'32','stock':'2'},'date':1448899200000,'srd':'113'},{'adult':{'discount':'2','price':'13','stock':'2'},'children02':{'discount':'331','price':'121','stock':'2'},'children212':{'discount':'231','price':'32','stock':'2'},'date':1448985600000,'srd':'113'}]}],'name':'套餐2','startDate':1448812800000}]},"
			 * +
			 * "'tripInfo':[{'description':'123','hotels':[{'id':'5','name':'温德姆至尊豪廷大酒店5'},{'id':'4','name':'温德姆至尊豪廷大酒店4'}],'id':1,'restaurant1':[{'id':'1','name':'怡心园1'}],'restaurant2':[{'id':'2','name':'怡心园2'},{'id':'3','name':'怡心园3'}],'restaurant3':[{'id':'5','name':'怡心园5'},{'id':'6','name':'怡心园6'},{'id':'0','name':'123'}],'scenics':[{'id':'1','name':'景点1'},{'id':'3','name':'景点3'}],'traffic':{'from':'北京','to':'上海','way':'6'}},{'description':'123','hotels':[{'id':'7','name':'温德姆至尊豪廷大酒店7'},{'id':'5','name':'温德姆至尊豪廷大酒店5'}],'id':2,'restaurant1':[{'id':'3','name':'怡心园3'},{'id':'5','name':'怡心园5'}],'restaurant2':[{'id':'0','name':'啊'},{'id':'0','name':'dds'}],'restaurant3':[{'id':'0','name':'1'},{'id':'5','name':'怡心园5'}],'scenics':[{'id':'4','name':'景点4'},{'id':'3','name':'景点3'}],'traffic':{'from':'上海','to':'南京','way':'6'}}]}";
			 * GroupTravel result = JSONObject.parseObject(json,
			 * GroupTravel.class);
			 */
		} else {
			throw new BaseException("获取线路出错");
		}
	}

	protected abstract T createTravelInstance(LineResult lineResult, List<ComTagDO> comTagDOs) throws Exception;

	protected long publishLine(BaseTravel travel) throws BaseException {
		LinePublishDTO linePublishDTO = travel.toLinePublishDTO();
		LinePublishResult publishLine = itemPublishServiceRef.publishLine(linePublishDTO);
		if (publishLine != null && publishLine.isSuccess()) {
			TagRelationInfoDTO tagRelationInfoDTO = new TagRelationInfoDTO();
			tagRelationInfoDTO.setTagType(TagType.LINETAG);
			tagRelationInfoDTO.setOutId(publishLine.getLineId());
			tagRelationInfoDTO.setOrderTime(publishLine.getCreateTime());
			tagRelationInfoDTO.setList(travel.getTagIdList());
			BaseResult<Boolean> addTagRelationInfo = comCenterServiceRef.addTagRelationInfo(tagRelationInfoDTO);
			long lineId = 0;
			if (addTagRelationInfo != null && addTagRelationInfo.isSuccess()) {
				lineId = publishLine.getLineId();
			} else {
				log.error("保存线路标签失败：" + addTagRelationInfo.getResultMsg());
				log.error("保存线路标签失败：tagRelationInfo={}", JSON.toJSONString(tagRelationInfoDTO));
				log.error("保存线路标签失败：tagResult={}", JSON.toJSONString(addTagRelationInfo));
				throw new BaseException("保存线路标签失败");
			}
			return lineId;
		} else {
			log.error("保存线路失败：" + publishLine.getResultMsg());
			log.error("保存线路失败：line={}", JSON.toJSONString(linePublishDTO));
			log.error("保存线路失败：lineResult={}", JSON.toJSONString(publishLine));
			throw new BaseException("保存线路失败");
		}
	}
}
