package com.yimayhd.gf.service.impl;

import com.alibaba.fastjson.JSON;
import com.yimayhd.gf.service.CommodityService;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemFeature;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.enums.ItemFeatureKey;
import com.yimayhd.ic.client.model.enums.ItemOptions;
import com.yimayhd.ic.client.model.enums.ItemPicUrlsKey;
import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.ic.client.model.param.item.*;
import com.yimayhd.ic.client.model.result.item.*;
import com.yimayhd.ic.client.service.item.ItemPublishService;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.constant.B2CConstant;
import com.yimayhd.palace.exception.NoticeException;
import com.yimayhd.palace.model.CategoryVO;
import com.yimayhd.palace.model.ItemResultVO;
import com.yimayhd.palace.model.ItemSkuVO;
import com.yimayhd.palace.model.ItemVO;
import com.yimayhd.palace.model.query.CommodityListQuery;
import com.yimayhd.palace.repo.ItemRepo;
import com.yimayhd.palace.service.TfsService;
import com.yimayhd.palace.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by czf on 2015/11/24.
 */
public class CommodityServiceImpl implements CommodityService {
    private static final Logger log = LoggerFactory.getLogger(CommodityServiceImpl.class);
    @Autowired
    private ItemQueryService itemQueryServiceRef;
    @Autowired
    private ItemPublishService itemPublishServiceRef;
    @Autowired
    private TfsService tfsService;
    @Autowired
    private ItemRepo itemRepo ;
    @Override
    public PageVO<ItemVO> getList(CommodityListQuery commodityListQuery) throws Exception {
        ItemQryDTO itemQryDTO = new ItemQryDTO();
        List<Integer> domainList = new ArrayList<Integer>();
        domainList.add(B2CConstant.GF_DOMAIN);
        itemQryDTO.setDomains(domainList);
        itemQryDTO.setPageNo(commodityListQuery.getPageNumber());
        itemQryDTO.setPageSize(commodityListQuery.getPageSize());

        if(!StringUtils.isBlank(commodityListQuery.getCommName())) {
            itemQryDTO.setName(commodityListQuery.getCommName());
        }
        if(null != commodityListQuery.getId()) {
            itemQryDTO.setId(commodityListQuery.getId());
        }
        if(!StringUtils.isBlank(commodityListQuery.getBeginDate())) {
            Date beginTime = DateUtil.formatMinTimeForDate(commodityListQuery.getBeginDate());
            itemQryDTO.setBeginDate(beginTime);
        }
        if(!StringUtils.isBlank(commodityListQuery.getEndDate())) {
            Date endTime = DateUtil.formatMaxTimeForDate(commodityListQuery.getEndDate());
            itemQryDTO.setEndDate(endTime);
        }
        List<Integer> status = new ArrayList<Integer>();
        if(0 != commodityListQuery.getCommStatus()){
            status.add(commodityListQuery.getCommStatus());
        }else{
            status.add(ItemStatus.create.getValue());
            status.add(ItemStatus.valid.getValue());
            status.add(ItemStatus.invalid.getValue());
        }
        itemQryDTO.setStatus(status);
        //
        if (commodityListQuery.getItemType() != 0) {
            itemQryDTO.setItemType(commodityListQuery.getItemType());
        }
        //TODO
        //分类 暂时没想好怎么做

        //暂时用不着
        //itemQryDTO.setSellerId();

        ItemPageResult itemPageResult = itemQueryServiceRef.getItem(itemQryDTO);
        if(null == itemPageResult){
            log.error("CommodityServiceImpl.getList-ItemQueryService.getItem result is null and parame: " + JSON.toJSONString(itemQryDTO));
            throw new BaseException("返回结果错误,新增失败 ");
        } else if(!itemPageResult.isSuccess()){
            log.error("CommodityServiceImpl.getList-ItemQueryService.getItem error:" + JSON.toJSONString(itemPageResult) + "and parame: " + JSON.toJSONString(itemQryDTO));
            throw new BaseException(itemPageResult.getResultMsg());
        }
        List<ItemDO> itemDOList = itemPageResult.getItemDOList();
        List<ItemVO> itemVOList = new ArrayList<ItemVO>();
        for(ItemDO itemDO:itemDOList){
        	long itemId = itemDO.getId();
        	ItemVO itemVO = ItemVO.getItemVO(itemDO,new CategoryVO());
        	boolean hasSku = ItemOptions.HAS_SKU.has(itemDO.getOptions());
        	int stock = 0 ;
        	if( hasSku ){
        		stock = itemRepo.getItemSkuSumStock(itemId);
        	}else{
        		stock = itemDO.getStockNum() ;
        	}
        	itemVO.setStockNum(stock);
        	itemVOList.add(itemVO);
        }

        PageVO<ItemVO> pageVO = new PageVO<ItemVO>(commodityListQuery.getPageNumber(),commodityListQuery.getPageSize(),itemPageResult.getRecordCount(),itemVOList);
        return pageVO;
    }
    
    
    @Override
    public ItemResultVO getCommodityById(long id) throws Exception {
        ItemOptionDTO itemOptionDTO = new ItemOptionDTO();
        //全部设置成true
        itemOptionDTO.setCreditFade(true);
        itemOptionDTO.setNeedCategory(true);
        itemOptionDTO.setNeedSku(true);
        ItemResult itemResult = itemQueryServiceRef.getItem(id,itemOptionDTO);
        if(null == itemResult){
            log.error("itemQueryService.getItem return value is null and parame: " + JSON.toJSONString(itemOptionDTO) + " and id is : " + id);
            throw new BaseException("返回结果错误");
        }else if(!itemResult.isSuccess()){
            log.error("itemQueryService.getItem return value error ! returnValue : "+ JSON.toJSONString(itemResult) + " and parame:" + JSON.toJSONString(itemOptionDTO) + " and id is : " + id);
            throw new NoticeException(itemResult.getResultMsg());
        }
        ItemResultVO itemResultVO = new ItemResultVO();
        //详细描述从tfs读出来（富文本编辑）
        if(StringUtils.isNotBlank(itemResult.getItem().getDetailUrl())){
            itemResult.getItem().setDetailUrl(tfsService.readHtml5(itemResult.getItem().getDetailUrl()));

        }


        itemResultVO.setCategoryVO(CategoryVO.getCategoryVO(itemResult.getCategory()));
        itemResultVO.setItemVO(ItemVO.getItemVO(itemResult.getItem(), itemResultVO.getCategoryVO()));
        //商品的排序字段
        itemResultVO.getItemVO().setSort(itemResult.getSortNum());
        //pc版详情H5
        if(StringUtils.isNotBlank(itemResult.getItem().getPicUrls(ItemPicUrlsKey.PC_DETAIL_H5))){
            itemResultVO.getItemVO().setPcDetail(tfsService.readHtml5(itemResult.getItem().getPicUrls(ItemPicUrlsKey.PC_DETAIL_H5)));
        }
        return itemResultVO;
    }

    public void publish(long sellerId,long id)throws Exception{
        ItemPublishDTO itemPublishDTO = new ItemPublishDTO();
        itemPublishDTO.setSellerId(sellerId);
        itemPublishDTO.setItemId(id);
        ItemPubResult itemPubResult = itemPublishServiceRef.publish(itemPublishDTO);
        if(null == itemPubResult){
            log.error("ItemPublishService.publish result is null and parame: " + JSON.toJSONString(itemPublishDTO) + "and sellerId:" + sellerId + "and id:" + id);
            throw new BaseException("返回结果错误,商品上架失败 ");
        } else if(!itemPubResult.isSuccess()){
            log.error("ItemPublishService.publish error:" + JSON.toJSONString(itemPubResult) + "and parame: " + JSON.toJSONString(itemPublishDTO) + "and sellerId:" + sellerId + "and id:" + id);
            throw new BaseException(itemPubResult.getResultMsg());
        }
    }

    public void close(long sellerId,long id)throws Exception{
        ItemPublishDTO itemPublishDTO = new ItemPublishDTO();
        itemPublishDTO.setSellerId(sellerId);
        itemPublishDTO.setItemId(id);
        ItemCloseResult itemCloseResult = itemPublishServiceRef.close(itemPublishDTO);
        if(null == itemCloseResult){
            log.error("ItemPublishService.itemCloseResult result is null and parame: " + JSON.toJSONString(itemPublishDTO) + "and sellerId:" + sellerId + "and id:" + id);
            throw new BaseException("返回结果错误,商品下架失败 ");
        } else if(!itemCloseResult.isSuccess()){
            log.error("ItemPublishService.itemCloseResult error:" + JSON.toJSONString(itemCloseResult) + "and parame: " + JSON.toJSONString(itemPublishDTO) + "and sellerId:" + sellerId + "and id:" + id);
            throw new BaseException(itemCloseResult.getResultMsg());
        }
    }

    public void batchPublish(long sellerId,List<Long> idList){
        ItemBatchPublishDTO itemBatchPublishDTO = new ItemBatchPublishDTO();
        itemBatchPublishDTO.setSellerId(sellerId);
        itemBatchPublishDTO.setItemIdList(idList);
        ItemPubResult itemPubResult = itemPublishServiceRef.batchPublish(itemBatchPublishDTO);
        if(null == itemPubResult){
            log.error("ItemPublishService.batchPublish result is null and parame: " + JSON.toJSONString(itemBatchPublishDTO) + "and sellerId:" + sellerId + "and idList:" + JSON.toJSONString(idList));
            throw new BaseException("返回结果错误,商品上架失败 ");
        } else if(!itemPubResult.isSuccess()){
            log.error("ItemPublishService.batchPublish error:" + JSON.toJSONString(itemPubResult) + "and parame: " + JSON.toJSONString(itemBatchPublishDTO) + "and sellerId:" + sellerId + "and idList:" + JSON.toJSONString(idList));
            throw new BaseException(itemPubResult.getResultMsg());
        }
    }

    public void batchClose(long sellerId,List<Long> idList){
        ItemBatchPublishDTO itemBatchPublishDTO = new ItemBatchPublishDTO();
        itemBatchPublishDTO.setSellerId(sellerId);
        itemBatchPublishDTO.setItemIdList(idList);
        ItemCloseResult itemCloseResult = itemPublishServiceRef.batchClose(itemBatchPublishDTO);
        if(null == itemCloseResult){
            log.error("ItemPublishService.batchClose result is null and parame: " + JSON.toJSONString(itemBatchPublishDTO) + "and sellerId:" + sellerId + "and idList:" + JSON.toJSONString(idList));
            throw new BaseException("返回结果错误,商品下架失败 ");
        } else if(!itemCloseResult.isSuccess()){
            log.error("ItemPublishService.batchClose error:" + JSON.toJSONString(itemCloseResult) + "and parame: " + JSON.toJSONString(itemBatchPublishDTO) + "and sellerId:" + sellerId + "and idList:" + JSON.toJSONString(idList));
            throw new BaseException(itemCloseResult.getResultMsg());
        }
    }


    @Override
    public void addCommonItem(ItemVO itemVO) throws Exception {
        //参数类型匹配
        CommonItemPublishDTO commonItemPublishDTO = new CommonItemPublishDTO();
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemVO, itemDO);
        //元转分
        itemDO.setPrice((long) (itemVO.getPriceY() * 100));

        //新增的时候设置skuDOList
        if(CollectionUtils.isNotEmpty(itemVO.getItemSkuVOListByStr())){
            List<ItemSkuDO> itemSkuDOList = new ArrayList<ItemSkuDO>();
            for (ItemSkuVO itemSkuVO : itemVO.getItemSkuVOListByStr()){
                if(itemSkuVO.isChecked()) {
                    itemSkuDOList.add(ItemSkuVO.getItemSkuDO(itemVO, itemSkuVO));
                }
            }
            itemDO.setItemSkuDOList(itemSkuDOList);
        }

        ItemFeature itemFeature = itemDO.getItemFeature();;
        if (itemDO.getItemFeature() == null) {
            itemFeature = new ItemFeature(null);
            itemDO.setItemFeature(itemFeature);
        }

        //picUrls转换
        if(StringUtils.isNotBlank(itemVO.getSmallListPic())){
            itemDO.addPicUrls(ItemPicUrlsKey.SMALL_LIST_PIC, itemVO.getSmallListPic());
        }
        if(StringUtils.isNotBlank(itemVO.getBigListPic())){
            itemDO.addPicUrls(ItemPicUrlsKey.BIG_LIST_PIC, itemVO.getBigListPic());
        }
        //pc详细描述tfs（富文本编辑）
        if(StringUtils.isNotBlank(itemVO.getPcDetail())){
            String pcDetailTfs = tfsService.publishHtml5(itemVO.getPcDetail());
            itemDO.addPicUrls(ItemPicUrlsKey.PC_DETAIL_H5, pcDetailTfs);

        }
        //详细描述存tfs（富文本编辑）
        if(StringUtils.isNotBlank(itemDO.getDetailUrl())){
            String detailUrlTfs = tfsService.publishHtml5(itemDO.getDetailUrl());
            itemDO.setDetailUrl(detailUrlTfs);
            itemDO.addPicUrls(ItemPicUrlsKey.DETAIL_H5, detailUrlTfs);
        }
        itemDO.setPicUrlsString(itemDO.getPicUrlsString());
        //减库存方式
        itemFeature.put(ItemFeatureKey.REDUCE_TYPE, itemVO.getReduceType());
        itemDO.setDomain(B2CConstant.GF_DOMAIN);

        commonItemPublishDTO.setItemDO(itemDO);
        commonItemPublishDTO.setItemSkuDOList(itemDO.getItemSkuDOList());

        ItemPubResult itemPubResult = itemPublishServiceRef.publishCommonItem(commonItemPublishDTO);
        if(null == itemPubResult){
            log.error("ItemPublishService.publishCommonItem result is null and parame: " + JSON.toJSONString(commonItemPublishDTO) + "and itemVO:" + JSON.toJSONString(itemVO));
            throw new BaseException("返回结果错误,新增失败 ");
        } else if(!itemPubResult.isSuccess()){
            log.error("ItemPublishService.publishCommonItem error:" + JSON.toJSONString(itemPubResult) + "and parame: " + JSON.toJSONString(commonItemPublishDTO) + "and itemVO:" + JSON.toJSONString(itemVO));
            throw new BaseException(itemPubResult.getResultMsg());
        }
    }

    @Override
    public void modifyCommonItem(ItemVO itemVO) throws Exception {

        //修改的时候要先取出来，在更新
        ItemOptionDTO itemOptionDTO = new ItemOptionDTO();
        ItemResult itemResult = itemQueryServiceRef.getItem(itemVO.getId(), itemOptionDTO);
        if(null == itemResult){
            log.error("itemQueryService.getItem return value is null and parame: " + JSON.toJSONString(itemOptionDTO) + " and id is : " + itemVO.getId());
            throw new BaseException("查询商品，返回结果错误");
        }else if(!itemResult.isSuccess()){
            log.error("itemQueryService.getItem return value error ! returnValue : "+ JSON.toJSONString(itemResult) + " and parame:" + JSON.toJSONString(itemOptionDTO) + " and id is : " + itemVO.getId());
            throw new NoticeException(itemResult.getResultMsg());
        }
        ItemDO itemDB = itemResult.getItem();
        if(null != itemDB) {
            //参数类型匹配
            CommonItemPublishDTO commonItemPublishDTO = new CommonItemPublishDTO();
            //设置itemDB
             commonItemPublishDTO.setItemDO(itemDB);
            //设置sku
            ItemVO.setItemSkuDOListCommonItemPublishDTO(commonItemPublishDTO, itemVO);
            //商品名称
            itemDB.setTitle(itemVO.getTitle());
            //商品编码
            itemDB.setCode(itemVO.getCode());
            //价格
            itemDB.setPrice(itemVO.getPriceLong());
            //商品库存
            itemDB.setStockNum(itemVO.getStockNum());
            //商品图片
            if(StringUtils.isNotBlank(itemVO.getBigListPic())){
                itemDB.addPicUrls(ItemPicUrlsKey.BIG_LIST_PIC,itemVO.getBigListPic());
            }
            if(StringUtils.isNotBlank(itemVO.getSmallListPic())){
                itemDB.addPicUrls(ItemPicUrlsKey.SMALL_LIST_PIC, itemVO.getSmallListPic());
            }
            //自定义属性
            itemDB.setItemPropertyList(itemVO.getItemPropertyList());

            //pc详细描述tfs（富文本编辑）
            if(StringUtils.isNotBlank(itemVO.getPcDetail())){
                String pcDetailTfs = tfsService.publishHtml5(itemVO.getPcDetail());
                itemDB.addPicUrls(ItemPicUrlsKey.PC_DETAIL_H5, pcDetailTfs);
            }
            //详细描述存tfs（富文本编辑）
            if(StringUtils.isNotBlank(itemVO.getDetailUrl())){
                String detailUrlTfs = tfsService.publishHtml5(itemVO.getDetailUrl());
                itemDB.setDetailUrl(detailUrlTfs);
                itemDB.addPicUrls(ItemPicUrlsKey.DETAIL_H5, detailUrlTfs);
            }
            //减库存方式
            itemDB.getItemFeature().put(ItemFeatureKey.REDUCE_TYPE, itemVO.getReduceType());

//            System.err.println(JSON.toJSONString(commonItemPublishDTO));
            ItemPubResult itemPubResult = itemPublishServiceRef.updatePublishCommonItem(commonItemPublishDTO);
//            System.err.println(JSON.toJSONString(itemPubResult));
            if(null == itemPubResult){
                log.error("ItemPublishService.publishCommonItem result is null and parame: " + JSON.toJSONString(commonItemPublishDTO) + "and itemVO:" + JSON.toJSONString(itemVO));
                throw new BaseException("返回结果错误,修改失败");
            } else if(!itemPubResult.isSuccess()){
                log.error("ItemPublishService.publishCommonItem error:" + JSON.toJSONString(itemPubResult) + "and parame: " + JSON.toJSONString(commonItemPublishDTO) + "and itemVO:" + JSON.toJSONString(itemVO));
                throw new BaseException(itemPubResult.getResultMsg());
            }

        }
    }

    @Override
    public List<ItemSkuVO> getSkuListByItemId(Long itemId) throws Exception {
        ItemOptionDTO options = new ItemOptionDTO();
        options.setNeedSku(true);
        ItemResult itemResult = itemQueryServiceRef.getItem(itemId, options);
        if(null == itemResult){
            log.error("itemQueryServiceRef.getItem result is null and itemId:" + itemId + " and options:" + JSON.toJSONString(options));
            throw new BaseException("返回结果错误,修改失败");
        } else if(!itemResult.isSuccess()){
            log.error("itemQueryServiceRef.getItem error:" + JSON.toJSONString(itemResult) + "and itemId:" + itemId + " and options:" + JSON.toJSONString(options));
            throw new BaseException(itemResult.getResultMsg());
        }
        List<ItemSkuDO> skuDOList = itemResult.getItemSkuDOList();
        if(CollectionUtils.isNotEmpty(skuDOList)){
            List<ItemSkuVO> itemSkuVOList = new ArrayList<ItemSkuVO>();
            for(ItemSkuDO itemSkuDO : skuDOList){
                ItemSkuVO itemSkuVO = ItemSkuVO.getItemSkuVO(itemSkuDO);
                itemSkuVOList.add(itemSkuVO);
            }
            return itemSkuVOList;
        }
        return null;
    }
}
