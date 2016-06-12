package com.yimayhd.palace.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.dto.TagInfoByOutIdDTO;
import com.yimayhd.commentcenter.client.dto.TagInfoPageDTO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.result.BasePageResult;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.service.ComTagCenterService;
import com.yimayhd.ic.client.model.domain.item.ItemDTO;
import com.yimayhd.ic.client.model.domain.item.ItemInfo;
import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.service.item.ItemBizQueryService;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.convert.ShowCaseItem;
import com.yimayhd.palace.model.vo.booth.ShowcaseVO;
import com.yimayhd.palace.service.ShowcaseService;
import com.yimayhd.palace.util.DateFormat;
import com.yimayhd.palace.util.NumUtil;
import com.yimayhd.resourcecenter.domain.BoothDO;
import com.yimayhd.resourcecenter.domain.OperationDO;
import com.yimayhd.resourcecenter.domain.RegionDO;
import com.yimayhd.resourcecenter.domain.ShowcaseDO;
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
import com.yimayhd.user.client.cache.CityDataCacheClient;
import com.yimayhd.user.client.dto.CityDTO;
import com.yimayhd.user.client.dto.MerchantUserDTO;
import com.yimayhd.user.client.enums.MerchantOption;
import com.yimayhd.user.client.query.MerchantPageQuery;
import com.yimayhd.user.client.service.MerchantService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

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

    @Autowired CityDataCacheClient cityDataCacheClient;

    @Autowired MerchantService merchantService;


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

        if(null == entity){
            throw  new Exception("参数不能为空");
        }

        if(null == entity.getId() ){
            entity.setStatus(ShowcaseStauts.OFFLINE.getStatus());//默认下架
            return add(entity);
        }

        ShowcaseDO dbShowcase =  getById(entity.getId());
        if(null == dbShowcase ){
            throw  new Exception("查询无数据");
        }
        dbShowcase =  showcaseVoToShowcaseDo(entity,dbShowcase);
        RcResult<Boolean> rcResult = showcaseClientServer.update(dbShowcase);
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
        if(null == result || !result.isSuccess() || null == result.getList()){
            LOGGER.error("getTagListByTagType|comTagCenterService.getTagListByTagType result is " + JSON.toJSONString(result) +",parameter is "+JSON.toJSONString(itemQryDTO));
            return null;
        }
        List<ShowCaseItem> list = new ArrayList<ShowCaseItem>();
        List<Long> idList = new ArrayList<Long>();

        //
        if(CollectionUtils.isNotEmpty(result.getList())){
            for (ItemInfo io:result.getList()) {
                if(null != io.getItemDTO()){
                    idList.add(io.getItemDTO().getId());
                }
            }
        }

        //批量获取该商品的目的地标签
        TagInfoByOutIdDTO tag = new TagInfoByOutIdDTO();
        tag.setDomain(1200);
        tag.setIdList(idList);
        tag.setOutType(TagType.DESTPLACE.name());

        BaseResult<Map<Long, List<ComTagDO>>> tagResult = comTagCenterService.getComTag(tag);
        if(null == tagResult || !tagResult.isSuccess()){
            LOGGER.error("getItemByItemOptionDTO|comTagCenterService.getComTag result is " + JSON.toJSONString(tagResult) +",parameter is "+JSON.toJSONString(tag));
            return null;
        }

        //标签获取完了
        List<String> cityCodeList = null;
        List<String> itemCityList = null;
        for (ItemInfo io:result.getList() ) {
            itemCityList = new ArrayList<String>();
            cityCodeList = new ArrayList<String>();
            ItemDTO ido = io.getItemDTO();
            if(tagResult.getValue().containsKey(ido.getId())){
                List<ComTagDO> listComTagDO = tagResult.getValue().get(ido.getId());
                if(CollectionUtils.isNotEmpty(listComTagDO)){
                    for (ComTagDO ctd:listComTagDO) {
                        cityCodeList.add(ctd.getName());
                    }
                    Map<String, CityDTO> cityResult = cityDataCacheClient.getCities(cityCodeList);
                    if(null != cityCodeList && cityCodeList.size()>0){
                        for (CityDTO cd : cityResult.values()) {
                            itemCityList.add(cd.getName());
                        }
                    }
                }
            }
            ShowCaseItem sc = null;
            sc = new ShowCaseItem();
            sc.setId(io.getItemDTO().getId());
            sc.setName(io.getItemDTO().getTitle());//标题
            List<String> listPic = io.getItemDTO().getItemMainPics();
            if(CollectionUtils.isNotEmpty(listPic)){
                sc.setImgUrl(listPic.get(0)); //主图
            }
            sc.setDestination(itemCityList);
            if(null == itemQryDTO.getItemTypes() || itemQryDTO.getItemTypes().length<=0){
                sc.setShowType("");//显示类别
            }
            if(null !=itemQryDTO.getItemTypes()){
                int type = (itemQryDTO.getItemTypes())[0];
                sc.setShowType(ItemType.get(type).getText());//显示类别
            }
            sc.setSalerName(null==io.getIcMerchantInfoInfo()?"":io.getIcMerchantInfoInfo().getMerchantName());//卖家名称
            String displayPrice = NumUtil.moneyTransform(io.getItemDTO().getPrice());
            sc.setPrice(displayPrice);//单价
            sc.setShowStatus(ItemStatus.get(io.getItemDTO().getStatus()).getText()); //显示状态
            if(null !=io.getItemDTO().getGmtCreated()){
                sc.setPushDate(DateFormat.dateFormat(io.getItemDTO().getGmtCreated(),"yyyy-MM-dd"));//发布时间
            }else{
                sc.setPushDate(null);
            }
            list.add(sc);
        }
        PageVO<ShowCaseItem> page  = new PageVO<ShowCaseItem>(itemQryDTO.getPageNo(), itemQryDTO.getPageSize(), result.getTotalCount(), list);
        return page;
    }

    private ShowcaseDO showcaseVoToShowcaseDo(ShowcaseVO sw,ShowcaseDO sd){
        if(null == sd ){sd = new ShowcaseDO();}
        if(StringUtils.isNotEmpty(sw.getInfo())){
            sd.setInfo(sw.getInfo());
        }
        if(StringUtils.isNotEmpty(sw.getTitle())){
            sd.setTitle(sw.getTitle());
        }
        if(StringUtils.isNotEmpty(sw.getBusinessCode())){
            sd.setBusinessCode(sw.getBusinessCode());
        }
        if(StringUtils.isNotEmpty(sw.getSummary())){
            sd.setSummary(sw.getSummary());
        }
        if(StringUtils.isNotEmpty(sw.getBoothContent())){
            sd.setBoothContent(sw.getBoothContent());
        }
        if(StringUtils.isNotEmpty(sw.getOperationContent())){
            sd.setOperationContent(sw.getOperationContent());
        }
        if(StringUtils.isNotEmpty(sw.getImgUrl())){
            sd.setImgUrl(sw.getImgUrl());
        }
        if(StringUtils.isNotEmpty(sw.getFeature())){
            sd.setFeature(sw.getFeature());
        }
        if(StringUtils.isNotEmpty(sw.getContent())){
            sd.setContent(sw.getContent());
        }
        sd.setShowcaseFeature(sw.getShowcaseFeature());
        sd.setStatus(sw.getStatus());//状态是手动改的
        sd.setOperationId(sw.getOperationId());
        sd.setSerialNo(sw.getSerialNo());
        sd.setGmtModified(new Date());
        //sd.setId(sw.getId());
        //sd.setShowType(sw.getShowType());
        //sd.setBoothId(sw.getBoothId());
        //sd.setVersion(sw.getVersion());
        //sd.setAppVersionCode(sw.getAppVersionCode());
        //sd.setTimingOnDate(sw.getTimingOnDate());
        //sd.setTimingOffDate(sw.getTimingOffDate());
        //sd.setOnOffTime(sw.getOnOffTime());
        //sd.setGmtCreated();
        return sd;
    }

    public PageVO<ShowCaseItem> getMerchants(MerchantPageQuery merchantPageQuery, MerchantOption merchantOption){
        com.yimayhd.user.client.result.BasePageResult<MerchantUserDTO> result = merchantService.getMerchantUserList(merchantPageQuery);
        if(null == result || !result.isSuccess()){
            LOGGER.error("getMerchants|merchantService.getMerchantUserList result is " + JSON.toJSONString(result) + ",parameter is "+JSON.toJSONString(merchantPageQuery));
            return null;
        }
        List<ShowCaseItem> list = new ArrayList<ShowCaseItem>();
        if(CollectionUtils.isNotEmpty(result.getList())){
            String cityName = "";
            /*List<MerchantOption> optionList = null;*/
             /*optionList  = MerchantOption.getContainedMerchantOptions(merchantPageQuery.getOption());*/
            for (MerchantUserDTO mu :result.getList()){
                if(null == mu.getMerchantDO() || StringUtils.isEmpty(mu.getMerchantDO().getName())){
                   continue;
                }
                ShowCaseItem sc = new ShowCaseItem();
                if(null != merchantOption && MerchantOption.TALENT.name().equals(merchantOption.name())){
                    sc.setId(mu.getUserDO().getId());
                    sc.setName(mu.getUserDO().getName());
                    cityName = (StringUtils.isEmpty(mu.getUserDO().getCity())?"":mu.getUserDO().getCity());
                }else{
                    sc.setId(mu.getMerchantDO().getSellerId());
                    sc.setName(mu.getMerchantDO().getName());
                    cityName = (StringUtils.isEmpty(mu.getMerchantDO().getCityName())?"":mu.getMerchantDO().getCityName());
                }
                sc.setDestination(Arrays.asList(cityName));
                list.add(sc);
            }
        }
        PageVO<ShowCaseItem> page  = new PageVO<ShowCaseItem>(merchantPageQuery.getPageNo(), merchantPageQuery.getPageSize(), result.getTotalCount(), list);
        return page;
    }
}
