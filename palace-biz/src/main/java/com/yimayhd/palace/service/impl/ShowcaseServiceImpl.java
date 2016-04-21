package com.yimayhd.palace.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.container.page.Page;
import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.dto.TagInfoPageDTO;
import com.yimayhd.commentcenter.client.dto.TagRelationDomainDTO;
import com.yimayhd.commentcenter.client.result.BasePageResult;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.service.ComTagCenterService;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemInfo;
import com.yimayhd.ic.client.model.param.item.ItemOptionDTO;
import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.model.result.item.ItemPageResult;
import com.yimayhd.ic.client.model.result.item.SingleItemQueryResult;
import com.yimayhd.ic.client.service.item.ItemBizQueryService;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.ic.client.util.PicUrlsUtil;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.convert.ShowCaseItem;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.model.ItemVO;
import com.yimayhd.palace.model.query.CommodityListQuery;
import com.yimayhd.palace.model.vo.VoucherTemplateVO;
import com.yimayhd.palace.model.vo.booth.ShowcaseVO;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.palace.result.BizResultSupport;
import com.yimayhd.palace.service.CommodityService;
import com.yimayhd.palace.service.RegionService;
import com.yimayhd.palace.service.ShowcaseService;
import com.yimayhd.palace.util.DateFormat;
import com.yimayhd.resourcecenter.domain.BoothDO;
import com.yimayhd.resourcecenter.domain.OperationDO;
import com.yimayhd.resourcecenter.domain.RegionDO;
import com.yimayhd.resourcecenter.domain.ShowcaseDO;
import com.yimayhd.resourcecenter.entity.Booth;
import com.yimayhd.resourcecenter.model.enums.RegionType;
import com.yimayhd.resourcecenter.model.enums.ShowcaseStauts;
import com.yimayhd.resourcecenter.model.param.ShowCaseDTO;
import com.yimayhd.resourcecenter.model.query.OperationQuery;
import com.yimayhd.resourcecenter.model.query.RegionQuery;
import com.yimayhd.resourcecenter.model.query.ShowcaseQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.model.result.ShowCaseResult;
import com.yimayhd.resourcecenter.service.BoothClientServer;
import com.yimayhd.resourcecenter.service.OperationClientServer;
import com.yimayhd.resourcecenter.service.RegionClientService;
import com.yimayhd.resourcecenter.service.ShowcaseClientServer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/13.
 */
public class ShowcaseServiceImpl implements ShowcaseService {

    private final  Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired  ShowcaseClientServer showcaseClientServer;

    @Autowired OperationClientServer operationClientServer;

    @Autowired RegionClientService regionClientService;

    @Autowired BoothClientServer boothClientServer;

    @Autowired ComTagCenterService comTagCenterService;

    @Autowired ItemBizQueryService itemBizQueryService;

    @Autowired CommodityService commodityService;

    public List<ShowcaseVO> getList(long boothId) throws Exception {
        return null;
    }

    @Override
    public PageVO<ShowCaseResult> getShowcaseResult(ShowcaseQuery showcaseQuery) {
        if(null == showcaseQuery){
            LOGGER.error("getShowcaseResult|showcaseClientServer.getShowcaseResult parameter is null");
            return null;
        }
        showcaseQuery.setNeedCount(true);
        RCPageResult<ShowCaseResult> result = showcaseClientServer.getShowcaseResult(showcaseQuery);
        if(null == result || !result.isSuccess()){
            LOGGER.error("getShowcaseResult|showcaseClientServer.getShowcaseResult result is " + JSON.toJSONString(result) +",parameter is "+JSON.toJSONString(showcaseQuery));
            return null;
        }
        List<ShowCaseResult> list  = new ArrayList<ShowCaseResult>();
        if(CollectionUtils.isNotEmpty(result.getList())){
            list = result.getList();
        }
        PageVO page  = new PageVO<ShowCaseResult>(showcaseQuery.getPageNo(), showcaseQuery.getPageSize(), result.getTotalCount(), list);
        return page;
    }

    @Override
    public ShowcaseVO getById(long id) throws Exception {
        ShowCaseDTO sc = new ShowCaseDTO();
        sc.setShowcaseId(id);
        RcResult<ShowCaseResult> result = showcaseClientServer.getShowcaseResult(sc);
        if(null == result || !result.isSuccess() || null == result.getT().getShowcaseDO()){
            LOGGER.error("getById|showcaseClientServer.getShowcaseResult result is " + JSON.toJSONString(result) +",parameter is "+id);
            return null;
        }
        //TODO:父类不能转子类，这里用BeanUtils.copyProperties，可改进
        ShowcaseDO sdo = result.getT().getShowcaseDO();
        ShowcaseVO svo = new ShowcaseVO();
        BeanUtils.copyProperties(sdo, svo);
        return svo;
    }

    @Override
    public ShowcaseVO add(ShowcaseVO entity) throws Exception {
        RcResult<Boolean> result = showcaseClientServer.insert(entity);
        if(null == result || !result.isSuccess()){
            LOGGER.error("add|showcaseClientServer.update result is " + JSON.toJSONString(result) +",parameter is "+JSON.toJSONString(entity));
            return null;
        }
        return entity;
    }

    @Override
    public ShowcaseVO saveOrUpdate(ShowcaseVO entity) throws Exception {
        BizResult<ShowcaseVO> result = new BizResult<ShowcaseVO>() ;
        if(null == entity){
            throw  new Exception("参数不能为空");
        }else if(null == entity.getId()){
            entity.setStatus(ShowcaseStauts.OFFLINE.getStatus());//默认下架
            return add(entity);
        }
        ShowcaseVO sv = getById(entity.getId());
        RcResult<Boolean> rcResult = showcaseClientServer.update(entity);
        if(null == rcResult || !rcResult.isSuccess()){
            LOGGER.error("saveOrUpdate|showcaseClientServer.update result is " + JSON.toJSONString(rcResult) +",parameter is "+JSON.toJSONString(entity));
           return null;
        }
        return entity;
    }

    @Override
    public boolean publish(long id, ShowcaseStauts status) throws Exception {
        ShowcaseVO sv = getById(id);
        if(null == sv){
            return false;
        }
        sv.setStatus(status.getStatus());
        if(null == saveOrUpdate(sv)){
            return false;
        }
        return true;
    }

    public PageVO<OperationDO> getPageOperationDO(OperationQuery operationQuery) {
        operationQuery.setNeedCount(true);
        RCPageResult<OperationDO> result = operationClientServer.getOperationResult(operationQuery);
        if(null == result || !result.isSuccess()){
            LOGGER.error("getListOperationDO|showcaseClientServer.getOperationResult result is "
                    + JSON.toJSONString(result) +",parameter is "+JSON.toJSONString(operationQuery));
            return null;
        }
        List<OperationDO> list = new ArrayList<OperationDO>();
        if(CollectionUtils.isNotEmpty(result.getList())){
            list=result.getList();
        }
        PageVO page  = new PageVO<OperationDO>(operationQuery.getPageNo(), operationQuery.getPageSize(),
                result.getTotalCount(), list);
        return page;
    }




    @Override
    public List<RegionDO> getListdestination(RegionType regionType) {
        return regionClientService.getAllRegionListByType(regionType.getType());
    }

    public PageVO<RegionDO> getRegionDOListByType(RegionQuery regionQuery) {
        RCPageResult<RegionDO> result = regionClientService.getRegionDOListByType(regionQuery);
        if(null == result || !result.isSuccess()){
            LOGGER.error("getListOperationDO|showcaseClientServer.getOperationResult result is "
                    + JSON.toJSONString(result) +",parameter is "+JSON.toJSONString(regionQuery));
            return null;
        }
        List<RegionDO> list = new ArrayList<RegionDO>();
        if(CollectionUtils.isNotEmpty(result.getList())){
            list=result.getList();
        }
        PageVO<RegionDO> page  = new PageVO<RegionDO>(regionQuery.getPageNo(), regionQuery.getPageSize(),
                result.getTotalCount(), list);
        return page;
    }

    public List<OperationDO> getListtheme(OperationQuery operationQuery) {
        return null;
    }

    public BoothDO getBoothInfoByBoothCode(String code) throws Exception{
        if(StringUtils.isEmpty(code)){
            throw new Exception("参数【code】不能为空");
        }
        BoothDO result = boothClientServer.getBoothDoByCode(code);
        return result;
    }
    @Override
    public List<OperationDO> getAllOperactions(){
    	RcResult<List<OperationDO>> result = operationClientServer.getAllOperactions() ;
    	if( result == null || !result.isSuccess() ){
    		LOGGER.error("getAllOperactions failed!  result={}",JSON.toJSONString(result));;
    		return null;
    	}
    	return result.getT() ;
    }

    public PageVO<ComTagDO> getTagListByTagType(TagInfoPageDTO tagInfoPageDTO)throws Exception{
        BasePageResult<ComTagDO> result = comTagCenterService.pageTagList(tagInfoPageDTO);
        if(null == result || !result.isSuccess()){
            LOGGER.error("getTagListByTagType|comTagCenterService.getTagListByTagType result is " + JSON.toJSONString(result) +",parameter is "+JSON.toJSONString(tagInfoPageDTO));
            return null;
        }
        List<ComTagDO> list = new ArrayList<ComTagDO>();
        if(CollectionUtils.isNotEmpty(result.getList())){
            list=result.getList();
        }
        PageVO<ComTagDO> page  = new PageVO<ComTagDO>(tagInfoPageDTO.getPageNo(), tagInfoPageDTO.getPageSize(), result.getTotalCount(), list);
        return page;
    }

    public PageVO<ShowCaseItem> getItemByItemOptionDTO(ItemQryDTO itemQryDTO) throws Exception {
        ICPageResult<ItemInfo> result = itemBizQueryService.getItem(itemQryDTO);
        if(null == result || !result.isSuccess()){
            LOGGER.error("getTagListByTagType|comTagCenterService.getTagListByTagType result is " + JSON.toJSONString(result) +",parameter is "+JSON.toJSONString(itemQryDTO));
            return null;
        }
        List<ShowCaseItem> list = new ArrayList<ShowCaseItem>();
        ShowCaseItem sc = null;
        if(CollectionUtils.isNotEmpty(result.getList())){
            for (ItemInfo io:result.getList()) {
                sc = new ShowCaseItem();
                sc.setId(io.getItemDTO().getId());
                sc.setName(io.getItemDTO().getTitle());//标题
                //TODO:参照源码改
                sc.setImgUrl("---");//主图
                sc.setShowType("自由行");//显示类别
                sc.setSalerName(io.getIcMerchantInfoInfo().getMerchantName());//卖家名称
                sc.setDestination("北京");//目的地
                sc.setPrice("111");//单价
                sc.setShowStatus("上架"); //显示状态
                if(null !=io.getItemDTO().getStartDate()){
                    sc.setPushDate(DateFormat.dateFormat(io.getItemDTO().getStartDate(),"yyyy-MM-dd"));//发布时间
                }else{
                    sc.setPushDate(null);
                }
                list.add(sc);
            }
        }
        PageVO<ShowCaseItem> page  = new PageVO<ShowCaseItem>(itemQryDTO.getPageNo(), itemQryDTO.getPageSize(), result.getTotalCount(), list);
        return page;
    }
}
