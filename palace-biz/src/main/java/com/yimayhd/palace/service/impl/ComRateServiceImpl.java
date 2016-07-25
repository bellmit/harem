package com.yimayhd.palace.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yimayhd.commentcenter.client.domain.ComRateDO;
import com.yimayhd.commentcenter.client.dto.*;
import com.yimayhd.commentcenter.client.result.BasePageResult;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.result.ComRateResult;
import com.yimayhd.ic.client.model.domain.item.ItemDO;

import com.yimayhd.palace.constant.B2CConstant;
import com.yimayhd.palace.model.query.ComRateListQuery;
import com.yimayhd.palace.repo.ItemRepo;
import com.yimayhd.palace.repo.OrderRepo;
import com.yimayhd.palace.repo.user.UserRepo;
import com.yimayhd.palace.service.ComRateService;
import com.yimayhd.palace.base.PageVO;

import java.util.*;

import com.yimayhd.palace.model.ComRateVO;
import com.yimayhd.palace.repo.CommentRepo;
import com.yimayhd.palace.util.DateUtil;

import com.yimayhd.tradecenter.client.model.result.order.BatchBizQueryResult;
import com.yimayhd.tradecenter.client.model.result.order.create.TcDetailOrder;
import com.yimayhd.tradecenter.client.model.result.order.create.TcMainOrder;
import com.yimayhd.user.client.domain.UserDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;


public class ComRateServiceImpl implements ComRateService {
    private static final Logger log = LoggerFactory.getLogger(ComRateServiceImpl.class);

    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    private OrderRepo orderRepo;

    public PageVO<ComRateVO> getRatePageList(ComRateListQuery comRateListQuery) {
        PageVO<ComRateVO> pageVO = null;
        List<ComRateVO> comRateVOs = Lists.newArrayList();
        try {
            RatePageListDTO ratePageListDTO = new RatePageListDTO();
            ratePageListDTO.setDomainId(B2CConstant.GF_DOMAIN);
            if (!StringUtils.isBlank(comRateListQuery.getBeginDate())) {
                try {
                    ratePageListDTO.setStartTime(DateUtil.convertStringToDate(comRateListQuery.getBeginDate()).getTime());
                } catch (Exception e) {
                    log.error("error ", e);
                }
            }
            if (!StringUtils.isBlank(comRateListQuery.getEndDate())) {
                try {
                    ratePageListDTO.setEndTime(DateUtil.convertStringToDate(comRateListQuery.getEndDate()+"23:59:59").getTime());
                } catch (Exception e) {
                    log.error("error ", e);
                }
            }

            if (!StringUtils.isBlank(comRateListQuery.getBaseStatus())) {
                ratePageListDTO.setBaseStatus(comRateListQuery.getBaseStatus());
            }

            if (!StringUtils.isBlank(comRateListQuery.getRateStatus())) {
                ratePageListDTO.setRateStatus(comRateListQuery.getRateStatus());
            }

            if (!StringUtils.isBlank(comRateListQuery.getItemId())) {
                long itemId =  (long) StringUtils.parseInteger(comRateListQuery.getItemId());
                log.debug("comRateListQuery.getItemId() = {}, itemId = {}", comRateListQuery.getItemId(), itemId);
                if(itemId>0) {
                    ratePageListDTO.setItemId(itemId);
                } else {
                    throw new Exception("itemId is zero");
                }
            }

            ratePageListDTO.setPageSize(comRateListQuery.getPageSize());
            ratePageListDTO.setPageNo(comRateListQuery.getPageNumber());
            log.debug("ratePageListDTO {}", ratePageListDTO);
            BasePageResult<ComRateResult> comRatePageResult = commentRepo.getRatePageList(ratePageListDTO);
            log.debug("ratePageListDTO value {}", JSON.toJSONString(comRatePageResult));
            log.debug("ratePageList {}", JSON.toJSONString(comRatePageResult.getList()));
            if (comRatePageResult != null && comRatePageResult.isSuccess() && !CollectionUtils.isEmpty(comRatePageResult.getList())) {

                List<Long> userIds = new ArrayList<Long>();
                List<Long> itemIds = new ArrayList<Long>();
                List<Long> orderIds = new ArrayList<Long>();

                for (ComRateResult comRate : comRatePageResult.getList()) {
                    userIds.add(comRate.getUserId());
                    itemIds.add(comRate.getItemId());
                    orderIds.add(comRate.getOrderId());
                }

                List<UserDO> userDOs = userRepo.getUsers(userIds);
                log.debug("userDOs = {}", JSON.toJSONString(userDOs));
                Map<Long, UserDO> userDOMap = new HashMap<Long, UserDO>();
                if (userDOs != null) {
                    for (int i = 0; i < userDOs.size(); i++) {
                        UserDO userDO = userDOs.get(i);
                        if(StringUtils.isBlank(userDO.getNickname())){
                            String mobileNo = userRepo.findMobileByUserId(userDO.getId());
                            userDO.setMobileNo(mobileNo);
                        }
                        userDOMap.put(userDO.getId(), userDO);
                    }
                }

                List<ItemDO> itemDOs = itemRepo.getItemByIds(itemIds);
                Map<Long, ItemDO> itemDOMap = new HashMap<Long, ItemDO>();
                if (itemDOs != null) {
                    for (int i = 0; i < itemDOs.size(); i++) {
                        ItemDO itemDO = itemDOs.get(i);
                        itemDOMap.put(itemDO.getId(), itemDO);
                    }
                }

                BatchBizQueryResult bizQueryResult = orderRepo.queryOrderByOrderIds(orderIds);
                log.debug("BizQueryR {}", bizQueryResult);
                Map<Long, String> skuTitleMap = new HashMap<Long, String>();
                if(bizQueryResult!=null && bizQueryResult.getBizOrderDOList()!=null){
                    for(int i=0; i<bizQueryResult.getBizOrderDOList().size(); i++){
                        TcMainOrder tcMainOrder = bizQueryResult.getBizOrderDOList().get(i);
                        List<TcDetailOrder> detailOrders = tcMainOrder.getDetailOrders();
                        log.debug("tcdetail order {}", detailOrders);
                        if(detailOrders!=null) {
                            for (int j = 0; j < detailOrders.size(); j++) {
                                TcDetailOrder tcDetailOrder = detailOrders.get(j);
                                log.debug("tcDetailOrder={}",tcDetailOrder);
                                log.debug("tcDetailOrder.json.strong={}", JSON.toJSONString(tcDetailOrder));
                                skuTitleMap.put(tcDetailOrder.getBizOrder().getBizOrderId(), tcDetailOrder.getSkuTitle());
                            }
                        }
                    }
                }

                for (ComRateResult rate : comRatePageResult.getList()) {
                    ComRateVO comRateVO = new ComRateVO();
                    BeanUtils.copyProperties(rate, comRateVO);
                    comRateVO.setUserDO(userDOMap.get(rate.getUserId()));
                    comRateVO.setItemDO(itemDOMap.get(rate.getItemId()));
                    comRateVO.setItemSkuTitle(skuTitleMap.get(rate.getOrderId()));
                    List<String> urls = rate.getPicUrls();
                    comRateVO.setPicUrls(urls);
                    comRateVOs.add(comRateVO);
                }
                pageVO = new PageVO<ComRateVO>(comRateListQuery.getPageNumber(), comRateListQuery.getPageSize(),
                        comRatePageResult.getTotalCount(), comRateVOs);
            } else {
                pageVO = new PageVO<ComRateVO>(comRateListQuery.getPageNumber(), comRateListQuery.getPageSize(),
                        0, comRateVOs);
            }
        } catch (Exception e) {
            pageVO = new PageVO<ComRateVO>(comRateListQuery.getPageNumber(), comRateListQuery.getPageSize(),
                    0, comRateVOs);
            log.error("Exception error {}", e);
        }

        return pageVO;
    }
    public BaseResult<Boolean> deletComRate(List<Long> ids) {
        BaseResult<Boolean> result = new BaseResult<Boolean>();
        RateStatusDTO rateStatusDTO = new RateStatusDTO();
        rateStatusDTO.setIdList(ids);
        rateStatusDTO.setSellerId(B2CConstant.GF_OFFICIAL_ID);
        rateStatusDTO.setGmtModified(new Date());
        log.info("rateStatusDTO={}", JSON.toJSONString(rateStatusDTO));
        BaseResult<Boolean> comResult = commentRepo.deleteComRate(rateStatusDTO);
        log.info("comResult={}", JSON.toJSONString(comResult));
        result.setValue(comResult.getValue());
        return result;
    }
    public BaseResult<ComRateDO> replayComRate(Long id, String backContent) {
        BaseResult<ComRateDO> result = new BaseResult<ComRateDO>();
        try {
            List<String> picUrls = new ArrayList<String>();
            picUrls.add(0,"xxx.png");
            RateReployDTO rateReployDTO = new RateReployDTO();
            rateReployDTO.setId(id);
            rateReployDTO.setSellerId(B2CConstant.GF_OFFICIAL_ID);
            rateReployDTO.setGmtModified(new Date().getTime());
            rateReployDTO.setBackContent(backContent);
            rateReployDTO.setBackPicUrlList(picUrls);
            log.info("rateReployDTO={}", JSON.toJSONString(rateReployDTO));
            BaseResult<ComRateDO> comResult = commentRepo.addRateReploy(rateReployDTO);
            log.info("comResult={}", comResult);
            log.info("comResult value={}", JSON.toJSONString(comResult));
            log.info("comResult value={}", JSON.toJSONString(comResult.getValue()));
            result.setValue(comResult.getValue());
        } catch (Exception e) {
            log.error("Exception {}", e);
        }
        return result;
    }
    public BaseResult<Boolean> replayComRate(List<Long> ids, String backContent) {
        BaseResult<Boolean> result = new BaseResult<Boolean>();
        BatchRateReployDTO batchRateReployDTO = new BatchRateReployDTO();
        batchRateReployDTO.setIdList(ids);
        batchRateReployDTO.setBackContent(backContent);
        batchRateReployDTO.setSellerId(B2CConstant.GF_OFFICIAL_ID);
        batchRateReployDTO.setBackTime(new Date());
        BaseResult<Boolean> addResult = commentRepo.batchAddRateReploy(batchRateReployDTO);
        result.setValue(addResult.getValue());
        return result;
    }
}
