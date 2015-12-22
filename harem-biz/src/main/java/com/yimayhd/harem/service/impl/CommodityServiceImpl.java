package com.yimayhd.harem.service.impl;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.base.BaseException;
import com.yimayhd.harem.exception.NoticeException;
import com.yimayhd.harem.model.CategoryVO;
import com.yimayhd.harem.model.ItemResultVO;
import com.yimayhd.harem.model.ItemVO;
import com.yimayhd.harem.service.CommodityService;
import com.yimayhd.harem.service.TfsService;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.param.item.CommonItemPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemOptionDTO;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.ic.client.model.result.item.ItemResult;
import com.yimayhd.ic.client.service.item.ItemPublishService;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/11/24.
 */
public class CommodityServiceImpl implements CommodityService {
    private static final Logger log = LoggerFactory.getLogger(CommodityServiceImpl.class);
    @Autowired
    private ItemQueryService itemQueryServiceRef;
    @Autowired
    private ItemPublishService itemPublishServiceRef;
    @Autowired
    private TfsService tfsService;
    @Override
    public List<ItemDO> getList() throws Exception {
        List<ItemDO> itemDOList = new ArrayList<ItemDO>();
        for (int i = 0; i < 10; i++) {
            ItemDO itemDOData = new ItemDO();
            itemDOData.setId(i);
            itemDOData.setRootCategoryId(1);
            itemDOData.setCategoryId(2);
            //酒店id
            //排序
            //时间区间
            if(i % 3 == 0){
                itemDOData.setTitle("故宫门票");
                itemDOData.setSubTitle("通票");
                itemDOData.setPrice(10000);
            }else{
                itemDOData.setTitle("高级双床房");
                itemDOData.setSubTitle("房间35m，双床，可住2人");
                itemDOData.setPrice(9900);
            }
            //会员限购
            itemDOData.setPicUrls("beautiful.png");
            itemDOData.setGmtCreated(new Date());
            itemDOData.setGmtModified(new Date());
            itemDOData.setStatus(1);
            itemDOData.setStockNum(i * 50);
            itemDOList.add(itemDOData);
        }
        return itemDOList;
    }

    @Override
    public ItemResultVO getCommodityById(long id) throws Exception {
        ItemOptionDTO itemOptionDTO = new ItemOptionDTO();
        //TODO 暂时全部设置成true
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
        if(StringUtils.isNotBlank(itemResult.getItem().getDescription())){
            itemResult.getItem().setDescription(tfsService.readHtml5(itemResult.getItem().getDescription()));

        }
        //TODO
        itemResultVO.setCategoryVO(CategoryVO.getCategoryVO(itemResult.getCategory()));
        itemResultVO.setItemVO(ItemVO.getItemVO(itemResult.getItem(), itemResultVO.getCategoryVO()));
        return itemResultVO;
    }

    @Override
    public ItemDO getCommHotelById(long id) throws Exception {
        ItemDO itemDOData = new ItemDO();
        itemDOData.setId(id);
        itemDOData.setRootCategoryId(1);
        itemDOData.setCategoryId(2);
        //酒店id
        //排序
        //时间区间
        itemDOData.setTitle("高级双床房");
        itemDOData.setSubTitle("房间35m，双床，可住2人");
        itemDOData.setPrice(9900);
        //会员限购
        itemDOData.setPicUrls("");
        itemDOData.setGmtCreated(new Date());
        itemDOData.setGmtModified(new Date());

        return itemDOData;
    }

    @Override
    public ItemDO addCommHotel(ItemDO itemDO) throws Exception {
        return null;
    }

    @Override
    public void modifyCommHotel(ItemDO itemDO) throws Exception {

    }

    @Override
    public void setCommStatusList(List<Long> idList, int status) throws Exception {

    }

    @Override
    public void setCommStatus(long id, int status) throws Exception {

    }

    @Override
    public void addCommonItem(ItemVO itemVO) throws Exception {
        //参数类型匹配
        CommonItemPublishDTO commonItemPublishDTO = new CommonItemPublishDTO();
        ItemDO itemDO = ItemVO.getItemDO(itemVO);
        //详细描述存tfs（富文本编辑）
        itemDO.setDescription(tfsService.publishHtml5(itemDO.getDescription()));
        commonItemPublishDTO.setItemDO(itemDO);
        commonItemPublishDTO.setItemSkuDOList(itemVO.getItemSkuDOList());

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
        //参数类型匹配
        CommonItemPublishDTO commonItemPublishDTO = new CommonItemPublishDTO();
        commonItemPublishDTO = ItemVO.getCommonItemPublishDTO(itemVO);
        ItemDO itemDO = ItemVO.getItemDO(itemVO);
        //详细描述存tfs（富文本编辑）
        commonItemPublishDTO.getItemDO().setDescription(tfsService.publishHtml5(itemDO.getDescription()));
        commonItemPublishDTO.setItemSkuDOList(itemVO.getItemSkuDOList());

        ItemPubResult itemPubResult = itemPublishServiceRef.updatePublishCommonItem(commonItemPublishDTO);
        if(null == itemPubResult){
            log.error("ItemPublishService.publishCommonItem result is null and parame: " + JSON.toJSONString(commonItemPublishDTO) + "and itemVO:" + JSON.toJSONString(itemVO));
            throw new BaseException("返回结果错误,修改失败");
        } else if(!itemPubResult.isSuccess()){
            log.error("ItemPublishService.publishCommonItem error:" + JSON.toJSONString(itemPubResult) + "and parame: " + JSON.toJSONString(commonItemPublishDTO) + "and itemVO:" + JSON.toJSONString(itemVO));
            throw new BaseException(itemPubResult.getResultMsg());
        }
    }
}
