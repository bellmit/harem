package com.yimayhd.palace.service.impl;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.dto.TagRelationInfoDTO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.constant.B2CConstant;
import com.yimayhd.palace.exception.NoticeException;
import com.yimayhd.palace.model.CommScenicVO;
import com.yimayhd.palace.service.CommScenicService;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemFeature;
import com.yimayhd.ic.client.model.enums.ItemFeatureKey;
import com.yimayhd.ic.client.model.enums.ItemPicUrlsKey;
import com.yimayhd.ic.client.model.enums.ReduceType;
import com.yimayhd.ic.client.model.enums.ResourceType;
import com.yimayhd.ic.client.model.param.item.CommonItemPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemOptionDTO;
import com.yimayhd.ic.client.model.param.item.ScenicAddNewDTO;
import com.yimayhd.ic.client.model.param.item.ScenicPublishDTO;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.ic.client.model.result.item.ItemResult;
import com.yimayhd.ic.client.service.item.ItemPublishService;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.ic.client.service.item.ResourcePublishService;

/**
 * Created by Administrator on 2015/11/18.
 */
public class CommScenicServiceImpl implements CommScenicService {
	private static final Logger log = LoggerFactory.getLogger(CommScenicServiceImpl.class);
	@Autowired
	private ItemPublishService itemPublishService;
	@Resource
	protected ComCenterService comCenterServiceRef;
	@Autowired
	private ItemQueryService itemQueryServiceRef;
	@Autowired
	private ResourcePublishService resourcePublishServiceRef;
	@Override
	public ItemPubResult save(CommScenicVO commScenic) throws Exception {
		ScenicPublishDTO dto = new ScenicPublishDTO();
		ItemDO itemDO = commScenic.getItemDO();

		if(itemDO.getId()!=0){
			  ItemOptionDTO itemOptionDTO = new ItemOptionDTO();
		        //全部设置成true
		        itemOptionDTO.setCreditFade(true);
		        itemOptionDTO.setNeedCategory(true);
		        itemOptionDTO.setNeedSku(true);
		      
			ItemResult itemResult = itemQueryServiceRef.getItem(itemDO.getId(), itemOptionDTO);
			if(null == itemResult){
	            log.error("CommScenicServiceImpl.update--itemQueryService.getItem return value is null and parame: " + JSON.toJSONString(itemOptionDTO) + " and id is : " + itemDO.getId());
	            throw new BaseException("查询商品，返回结果错误");
	        }else if(!itemResult.isSuccess()){
	            log.error("CommScenicServiceImpl.update--itemQueryService.getItem return value error ! returnValue : "+ JSON.toJSONString(itemResult) + " and parame:" + JSON.toJSONString(itemOptionDTO) + " and id is : " + itemDO.getId());
	            throw new NoticeException(itemResult.getResultMsg());
	        }
			 ItemDO itemDB = itemResult.getItem();
			   if(null != itemDB) {
				   ScenicPublishDTO scenicPublishDTO = new ScenicPublishDTO();
				   scenicPublishDTO.setItemDO(itemDB);
				 
				   if(null!=commScenic.getEndTime()){
					   ItemFeature itemFeature = null;
					   if(null != itemDB.getItemFeature()){
						   itemFeature=itemDB.getItemFeature();
						   itemFeature.put(ItemFeatureKey.END_BOOK_TIME_LIMIT, commScenic.getEndTime()* 24 * 3600 );
						   itemFeature.put(ItemFeatureKey.START_BOOK_TIME_LIMIT,(commScenic.getStartDayTime()*24+(24-commScenic.getStartHourTime())) * 3600);
					   }else{
						   itemFeature=new ItemFeature(null);
						   itemFeature.put(ItemFeatureKey.END_BOOK_TIME_LIMIT, commScenic.getEndTime()* 24 * 3600 );
						   itemFeature.put(ItemFeatureKey.START_BOOK_TIME_LIMIT,(commScenic.getStartDayTime()*24+(24-commScenic.getStartHourTime())) * 3600);
					   }
				   }
				  
				   itemDB.setOutId(itemDO.getOutId());
				   //商品图片
		            if(StringUtils.isNotBlank(commScenic.getSmallListPic())){
		            	itemDB.addPicUrls(ItemPicUrlsKey.SMALL_LIST_PIC, commScenic.getSmallListPic());
		            }
		            if(StringUtils.isNotBlank(commScenic.getBigListPic())){
		            	itemDB.addPicUrls(ItemPicUrlsKey.BIG_LIST_PIC, commScenic.getBigListPic());
		            }
		            if(StringUtils.isNotBlank(commScenic.getCoverPics())){
		            	itemDB.addPicUrls(ItemPicUrlsKey.COVER_PICS, commScenic.getCoverPics());
		            }
		            //商品名称
					itemDB.setTitle(itemDO.getTitle());
					//包含项目
					itemDB.setSubTitle(itemDO.getSubTitle());
					//入园限制
					itemDB.setOneWord(itemDO.getOneWord());
					//价格
					itemDB.setPrice((long) (commScenic.getPriceF() * 100));
					itemDB.setOutId(itemDO.getOutId());
					/*ItemUpdDTO itemDto = new ItemUpdDTO();
					itemDto.setItem(itemDB);
					
					
					ItemUpdResult updItem = itemPublishService.updItem(itemDto);*/
					CommonItemPublishDTO commonItemPublishDTO = new CommonItemPublishDTO();
					commonItemPublishDTO.setItemDO(itemDB);
					ItemPubResult itemPubResult = itemPublishService.updatePublishCommonItem(commonItemPublishDTO);
					//更新景区信息  
					ICResult<ScenicDO> scenic = itemQueryServiceRef.getScenic(commScenic.getItemDO().getOutId());
					if(null == scenic){
			            log.error("CommScenicServiceImpl.update--itemQueryServiceRef.getScenic return value is null and parame: " + JSON.toJSONString(scenic) + " and id is : " + commScenic.getItemDO().getOutId());
			            throw new BaseException("查询景区，返回结果错误");
			        }else if(!scenic.isSuccess()){
			            log.error("CommScenicServiceImpl.update--itemQueryServiceRef.getScenic return value error ! returnValue : "+ JSON.toJSONString(scenic) + " and parame: id is : " +  commScenic.getItemDO().getOutId());
			            throw new NoticeException(itemResult.getResultMsg());
			        }else{
			        	
			        	ScenicAddNewDTO addScenicNew = new ScenicAddNewDTO();
			        	scenic.getModule().setOrderNum(commScenic.getScenicDO().getOrderNum());
			        	addScenicNew.setScenic(scenic.getModule());
			        	ICResult<ScenicDO> updateScenicNew = resourcePublishServiceRef.updateScenicNew(addScenicNew);
			        	if(null == updateScenicNew){
							log.error("ScenicServiceImpl.save-ResourcePublishService.updateScenicNew result is null and parame: " + JSON.toJSONString(addScenicNew));
							throw new BaseException("修改景区返回结果为空,修改失败");
						} else if(!updateScenicNew.isSuccess()){
							log.error("ScenicServiceImpl.save-ResourcePublishService.updateScenicNew error:" + JSON.toJSONString(updateScenicNew) + "and parame: " + JSON.toJSONString(addScenicNew));
							throw new BaseException(updateScenicNew.getResultMsg());
						}
			        
			        }
					if (itemPubResult != null && itemPubResult.isSuccess()) {
						TagRelationInfoDTO tagRelationInfoDTO = new TagRelationInfoDTO();
						tagRelationInfoDTO.setTagType(TagType.VIEWTAG.getType());
						tagRelationInfoDTO.setOutId(commScenic.getItemDO().getOutId());
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
						log.error("保存景区失败：" + itemPubResult.getResultMsg());
						log.error(MessageFormat.format("保存景区失败：line={0}", JSON.toJSONString(itemPubResult)));
						throw new BaseException("保存景区失败");
					}
					
			   }
		}else{
			ScenicDO scenicDO= new ScenicDO();
			scenicDO.setOrderNum(commScenic.getScenicDO().getOrderNum());
			scenicDO.setId(commScenic.getItemDO().getOutId());

			itemDO.setDomain(B2CConstant.B2C_DOMAIN);
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
	        itemFeature.put(ItemFeatureKey.START_BOOK_TIME_LIMIT,(commScenic.getStartDayTime()*24+(24-commScenic.getStartHourTime())) * 3600);
	        itemDO.setItemFeature(itemFeature);
			itemDO.setSellerId(B2CConstant.YIMAY_OFFICIAL_ID);
			itemDO.setOutType(ResourceType.SCENIC.getType());
			itemDO.setCredit(0);
			itemDO.setPoint(0);
			itemDO.setOriginalCredit(0);
			itemDO.setOriginalPoint(0);
			itemDO.setOriginalPrice(0);
			itemDO.setStockNum(9999);//默认库存
			itemDO.setDetailUrl("");
			dto.setItemDO(itemDO);
			dto.setScenicDO(scenicDO);
			ItemPubResult publicScenic = itemPublishService.publishScenic(dto);
			if (publicScenic != null && publicScenic.isSuccess()) {
				TagRelationInfoDTO tagRelationInfoDTO = new TagRelationInfoDTO();
				tagRelationInfoDTO.setTagType(TagType.VIEWTAG.getType());
				tagRelationInfoDTO.setOutId(scenicDO.getId());
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
		return null;
		
		
	}
	
	
	
	

}
