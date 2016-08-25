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
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.domain.item.ItemDTO;
import com.yimayhd.ic.client.model.domain.item.ItemInfo;
import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.model.enums.OperationType;
import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.ic.client.model.query.HotelPageQuery;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.service.item.ItemBizQueryService;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.convert.ShowCaseItem;
import com.yimayhd.palace.model.attachment.AttachmentVO;
import com.yimayhd.palace.model.guide.GuideScenicListQuery;
import com.yimayhd.palace.model.guide.GuideScenicVO;
import com.yimayhd.palace.model.vo.booth.ShowcaseVO;
import com.yimayhd.palace.service.AttachmentManageService;
import com.yimayhd.palace.service.GuideManageService;
import com.yimayhd.palace.service.ShowcaseService;
import com.yimayhd.palace.util.DateFormat;
import com.yimayhd.palace.util.NumUtil;
import com.yimayhd.resourcecenter.domain.*;
import com.yimayhd.resourcecenter.dto.ArticleDTO;
import com.yimayhd.resourcecenter.model.enums.OperationStatusType;
import com.yimayhd.resourcecenter.model.enums.RegionType;
import com.yimayhd.resourcecenter.model.enums.ShowcaseStauts;
import com.yimayhd.resourcecenter.model.param.ShowCaseDTO;
import com.yimayhd.resourcecenter.model.param.SimpleBoothDTO;
import com.yimayhd.resourcecenter.model.query.*;
import com.yimayhd.resourcecenter.model.resource.vo.OperactionVO;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.model.result.ResourcePageResult;
import com.yimayhd.resourcecenter.model.result.ShowCaseResult;
import com.yimayhd.resourcecenter.service.BoothClientServer;
import com.yimayhd.resourcecenter.service.OperationClientServer;
import com.yimayhd.resourcecenter.service.RegionClientService;
import com.yimayhd.resourcecenter.service.ShowcaseClientServer;
import com.yimayhd.resourcecenter.service.backend.ArticleBackEndService;
import com.yimayhd.resourcecenter.util.FeatureUtil;
import com.yimayhd.snscenter.client.domain.SnsSubjectDO;
import com.yimayhd.snscenter.client.domain.SnsTopicDO;
import com.yimayhd.snscenter.client.dto.SubjectInfoDTO;
import com.yimayhd.snscenter.client.dto.topic.TopicQueryDTO;
import com.yimayhd.snscenter.client.dto.topic.TopicQueryListDTO;
import com.yimayhd.snscenter.client.result.topic.TopicResult;
import com.yimayhd.snscenter.client.result.ugc.UgcResult;
import com.yimayhd.snscenter.client.service.SnsTopicCenterService;
import com.yimayhd.user.client.cache.CityDataCacheClient;
import com.yimayhd.user.client.dto.CityDTO;
import com.yimayhd.user.client.dto.MerchantUserDTO;
import com.yimayhd.user.client.enums.MerchantOption;
import com.yimayhd.user.client.query.MerchantPageQuery;
import com.yimayhd.user.client.service.MerchantService;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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

    @Autowired ItemQueryService itemQueryService;

    @Autowired SnsTopicCenterService snsTopicCenterServiceRef;

    @Autowired ArticleBackEndService articleBackEndService;

    @Autowired GuideManageService guideManageService ;



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
        ShowcaseDO sdo = result.getT().getShowcaseDO();
        ShowcaseVO svo = new ShowcaseVO();
        BeanUtils.copyProperties(sdo, svo);
        svo.setOperationContentZHs(svo.getFeature());
        svo.setOperationDetailIds(svo.getFeature());
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
            LOGGER.error("getAllOperactions failed!  result={}",JSON.toJSONString(result));
            return null;
        }
        return result.getT() ;
    }

    public PageVO<ComTagDO> getTagListByTagType(TagInfoPageDTO tagInfoPageDTO){
        BasePageResult<ComTagDO> result = comTagCenterService.pageTagList(tagInfoPageDTO);
        if(null == result || !result.isSuccess()){
            LOGGER.error("getTagListByTagType|comTagCenterService.getTagListByTagType result is "
                    + JSON.toJSONString(result) +",parameter is "+JSON.toJSONString(tagInfoPageDTO));
            return null;
        }
        List<ComTagDO> list = new ArrayList<ComTagDO>();
        if(CollectionUtils.isNotEmpty(result.getList())){
            list=result.getList();
        }
        PageVO<ComTagDO> page  = new PageVO<ComTagDO>(tagInfoPageDTO.getPageNo(), tagInfoPageDTO.getPageSize(),
                result.getTotalCount(), list);
        return page;
    }

    public PageVO<ShowCaseItem> getItemByItemOptionDTO(ItemQryDTO itemQryDTO){
        PageVO<ShowCaseItem> page = new PageVO<ShowCaseItem>();
        ICPageResult<ItemInfo> result = itemBizQueryService.getItem(itemQryDTO);
        if(null == result || !result.isSuccess() || null == result.getList()){
            LOGGER.error("getTagListByTagType|comTagCenterService.getTagListByTagType result is "
                    + JSON.toJSONString(result) +",parameter is "+JSON.toJSONString(itemQryDTO));
            return page;
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
            return page;
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
        page  = new PageVO<ShowCaseItem>(itemQryDTO.getPageNo(), itemQryDTO.getPageSize(), result.getTotalCount(), list);
        return page;
    }

    private ShowcaseDO showcaseVoToShowcaseDo(ShowcaseVO sw,ShowcaseDO sd){
        if(null == sd ){sd = new ShowcaseDO();}
        sd.setInfo(sw.getInfo());
        sd.setTitle(sw.getTitle());
        sd.setSummary(sw.getSummary());
        sd.setBoothContent(sw.getBoothContent());

        if(StringUtils.isEmpty(sw.getOperationContent())){
            sd.setOperationContent("");
        }else{
            String oc = sw.getOperationContent().replaceAll(" ","");
            if(oc.contains(Constant.TOPIC_PREFIX_SUFFIX)){//去掉话题的##
                oc = oc.replaceAll(Constant.TOPIC_PREFIX_SUFFIX,"");
            }
            if(oc.contains(Constant.BOOTH_PREFIX_POSTFIX)){
                List<SimpleBoothDTO> list = new ArrayList<SimpleBoothDTO>();
                String[] OcArr = oc.split("\\|");
                if(null != OcArr && OcArr.length>0){
                    for (String str:OcArr) {
                        String[] strOc = str.split(Constant.BOOTH_PREFIX_SUFFIX);
                        if(null !=strOc && strOc.length == 2){
                            String code = strOc[0];
                            String version = strOc[1];
                            if(StringUtils.isNotEmpty(code) && StringUtils.isNotEmpty(version) && NumberUtils.isNumber(version)){
                                SimpleBoothDTO sb = new SimpleBoothDTO(code,Integer.parseInt(version));
                                list.add(sb);
                            }
                        }
                    }
                    oc = JSON.toJSONString(list);
                }
            }
            sd.setOperationContent(oc);
        }

        sd.setContent(sw.getContent());
        sd.setShowcaseFeature(sw.getShowcaseFeature());
        sd.setStatus(sw.getStatus());//状态是手动改的
        sd.setOperationId(sw.getOperationId());
        sd.setSerialNo(sw.getSerialNo());
        sd.setGmtModified(new Date());
        Map<String,String> map = new HashMap<String,String>();

        if(StringUtils.isEmpty(sw.getOperationContentZH())){
            map.put("operationContentZH","");
        }else{
            String ocZH = sw.getOperationContentZH().trim();
            if(ocZH.contains(Constant.TOPIC_PREFIX_SUFFIX)){
                ocZH = ocZH.replaceAll(Constant.TOPIC_PREFIX_SUFFIX,"");
            }
            map.put("operationContentZH",ocZH);
        }

        map.put("operationDetailId",String.valueOf(sw.getOperationDetailId()));
        //sw.setFeature(FeatureUtil.toString(map));
        sd.setFeature(FeatureUtil.toString(map));
        if(StringUtils.isNotEmpty(sw.getBusinessCode())){
            sd.setBusinessCode(sw.getBusinessCode());
        }
        if(StringUtils.isNotEmpty(sw.getImgUrl())){
            sd.setImgUrl(sw.getImgUrl());
        }
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

    public PageVO<ShowCaseItem> getHotelList(HotelPageQuery hotelPageQuery){
        ICPageResult<HotelDO> result = itemQueryService.pageQueryHotel(hotelPageQuery);
        if(null == result || !result.isSuccess()){
            LOGGER.error("getHotelList|itemQueryService.pageQueryHotel result is " + JSON.toJSONString(result) + ",parameter is "+JSON.toJSONString(hotelPageQuery));
            return new PageVO<ShowCaseItem>();
        }
        List<ShowCaseItem> list = new ArrayList<ShowCaseItem>();
        ShowCaseItem sc = null;
        List<HotelDO> listHotelDO = result.getList();
        if(CollectionUtils.isNotEmpty(result.getList())){
            for (HotelDO ho :listHotelDO) {
                sc = new ShowCaseItem();
                sc.setId(ho.getId());
                sc.setName(ho.getName());
                list.add(sc);
            }
        }
        PageVO<ShowCaseItem> page  = new PageVO<ShowCaseItem>(hotelPageQuery.getPageNo(), hotelPageQuery.getPageSize(), result.getTotalCount(), list);
        return page;
    }

    public PageVO<ShowCaseItem> getScenicList(ScenicPageQuery scenicPageQuery){
        ICPageResult<ScenicDO> result = itemQueryService.pageQueryScenic(scenicPageQuery);
        if(null == result || !result.isSuccess()){
            LOGGER.error("getScenicList|itemQueryService.pageQueryScenic result is " + JSON.toJSONString(result) + ",parameter is "+JSON.toJSONString(scenicPageQuery));
            return new PageVO<ShowCaseItem>();
        }
        List<ShowCaseItem> list = new ArrayList<ShowCaseItem>();
        ShowCaseItem sc = null;
        List<ScenicDO> listScenicDO = result.getList();
        if(CollectionUtils.isNotEmpty(result.getList())){
            for (ScenicDO ho :listScenicDO) {
                sc = new ShowCaseItem();
                sc.setId(ho.getId());
                sc.setName(ho.getName());
                list.add(sc);
            }
        }
        PageVO<ShowCaseItem> page  = new PageVO<ShowCaseItem>(scenicPageQuery.getPageNo(), scenicPageQuery.getPageSize(), result.getTotalCount(), list);
        return page;
    }

    //new---------
    public List<OperactionVO> getAllOperations(){
        try {
            RcResult<List<OperactionVO>> result = operationClientServer.getAllOperations(OperationStatusType.ON_SHELF.getValue()) ;
            if( result == null || !result.isSuccess() ){
                LOGGER.error("getAllOperations failed!  result={}",JSON.toJSONString(result));;
                return null;
            }
            List<OperactionVO> list = result.getT();
            //按名称排序一下
            Collections.sort(list, new Comparator<OperactionVO>() {
                public int compare(OperactionVO arg0, OperactionVO arg1) {
                    return arg0.getOperactio().getName().compareTo(arg1.getOperactio().getName());
                }
            });
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public PageVO<ShowCaseItem> getUgcPageList(SubjectInfoDTO subjectInfoDTO){
        com.yimayhd.snscenter.client.result.BasePageResult<SnsSubjectDO> result = snsTopicCenterServiceRef.getUgcPageList(subjectInfoDTO);
        if( result == null || !result.isSuccess() ){
            LOGGER.error("snsTopicCenterService.getUgcPageList failed! param="+JSON.toJSONString(subjectInfoDTO)+"|||result="+JSON.toJSONString(result));;
            return new PageVO<ShowCaseItem>();
        }
        List<ShowCaseItem> list = ugcResultToShowCaseItem(result.getList());
        if(CollectionUtils.isEmpty(list)){
            return new PageVO<ShowCaseItem>();
        }
        PageVO<ShowCaseItem> page  = new PageVO<ShowCaseItem>(subjectInfoDTO.getPageNo(), subjectInfoDTO.getPageSize(), result.getTotalCount(),list);
        return page;
    }

    public List<ShowCaseItem> ugcResultToShowCaseItem(List<SnsSubjectDO> listUgc){
        List<ShowCaseItem> list = new ArrayList<ShowCaseItem>();
        if(CollectionUtils.isNotEmpty(listUgc)){
            for (SnsSubjectDO sns:listUgc ) {
                ShowCaseItem sc = new ShowCaseItem();
                sc.setId(sns.getId());
                sc.setName(sns.getTextContent());//标题
                sc.setImgUrl(sns.getPicContent());
                list.add(sc);
            }
        }
        return list ;
    }


    public PageVO<ShowCaseItem>  getTopicPageList(TopicQueryListDTO topicQueryListDTO){
        com.yimayhd.snscenter.client.result.BasePageResult<TopicResult> result = snsTopicCenterServiceRef.getTopicPageList(topicQueryListDTO);
        if( result == null || !result.isSuccess() ){
            LOGGER.error("snsTopicCenterService.getUgcPageList failed! param="+JSON.toJSONString(topicQueryListDTO)
                    +"|||result="+JSON.toJSONString(result));;
            return new PageVO<ShowCaseItem>();
        }
        List<ShowCaseItem> list = topicResultToShowCaseItem(result.getList());
        PageVO<ShowCaseItem> page  = new PageVO<ShowCaseItem>(topicQueryListDTO.getPageNo(), topicQueryListDTO.getPageSize(), result.getTotalCount(),list);
        return page;
    }

    public List<ShowCaseItem> topicResultToShowCaseItem(List<TopicResult> listTop){
        List<ShowCaseItem> list = new ArrayList<ShowCaseItem>();
        if(CollectionUtils.isNotEmpty(listTop)){
            for (TopicResult top:listTop ) {
                ShowCaseItem sc = new ShowCaseItem();
                sc.setId(top.getId());
                sc.setName(top.getTitle());//标题
                sc.setImgUrl(top.getPics());
                list.add(sc);
            }
        }
        return list;
    }

    public SnsTopicDO getTopicDetailInfo(TopicQueryDTO topicQueryDTO){
        com.yimayhd.snscenter.client.result.BaseResult<SnsTopicDO> result = snsTopicCenterServiceRef.getTopicDetailInfo(topicQueryDTO);
        if( result == null || !result.isSuccess() ){
            LOGGER.error("snsTopicCenterService.getUgcPageList failed! param="+JSON.toJSONString(topicQueryDTO)
                    +"|||result="+JSON.toJSONString(result));;
            return null;
        }
        return result.getValue();
    }

    public SnsSubjectDO getSubjectInfo(SubjectInfoDTO subjectInfoDTO){
        com.yimayhd.snscenter.client.result.BaseResult<SnsSubjectDO> result = snsTopicCenterServiceRef.getSubjectInfo(subjectInfoDTO);
        if( result == null || !result.isSuccess() ){
            LOGGER.error("snsTopicCenterService.getUgcPageList failed! param="+JSON.toJSONString(subjectInfoDTO)
                    +"|||result="+JSON.toJSONString(result));;
            return null;
        }
        return result.getValue();
    }

    public PageVO<ShowCaseItem> getBoothPageList(BoothQuery boothQuery){
        RCPageResult<BoothDO> result = boothClientServer.getBoothDOByQuery(boothQuery);
        if(null == result || !result.isSuccess()){
            return null;
        }
        List<BoothDO> list = result.getList();
        List<ShowCaseItem> listBooth = boothToShowCaseItem(list);
        PageVO<ShowCaseItem> page  = new PageVO<ShowCaseItem>(boothQuery.getPageNo(), boothQuery.getPageSize(),result.getTotalCount(),listBooth);
        return page;
    }

    public List<ShowCaseItem> boothToShowCaseItem(List<BoothDO> list){
        List<ShowCaseItem> listSC = new ArrayList<ShowCaseItem>();
        if(CollectionUtils.isNotEmpty(list)){
            for (BoothDO oo:list ) {
                ShowCaseItem sc = new ShowCaseItem();
                sc.setId(oo.getId());
                sc.setName(oo.getName());//标题
                sc.setCode(oo.getCode());
                sc.setAppVersion(oo.getAppVersion());
                listSC.add(sc);
            }
        }
        return listSC;
    }

    public PageVO<ShowCaseItem> getArticlePageListByQuery(ArticleQueryDTO articleQueryDTO ){
        ResourcePageResult<ArticleDTO> result =  articleBackEndService.getArticlePageListByQuery(articleQueryDTO);
        if(null == result || !result.isSuccess()){
            return null;
        }
        List<ShowCaseItem> list = ArticleToShowCaseItem(result.getList());
        PageVO<ShowCaseItem> page  = new PageVO<ShowCaseItem>(articleQueryDTO.getPageNo(), articleQueryDTO.getPageSize(),result.getTotalCount(), list);
        return page;
    }

    public List<ShowCaseItem> ArticleToShowCaseItem(List<ArticleDTO> list){
        List<ShowCaseItem> listSC = new ArrayList<ShowCaseItem>();
        if(CollectionUtils.isEmpty(list)){
            return listSC;
        }
        for (ArticleDTO adt:list ) {
            if(null == adt.getArticleDO()){
                continue;
            }
            ArticleDO oo = adt.getArticleDO();
            ShowCaseItem sc = new ShowCaseItem();
            sc.setId(oo.getId());
            sc.setName(oo.getTitle());//标题
            sc.setImgUrl(StringUtils.isEmpty(oo.getFrontcover())? "" : oo.getFrontcover());
            sc.setURL(StringUtils.isEmpty(adt.getArticleUrl())  ? "" : adt.getArticleUrl());
            listSC.add(sc);
        }
        return listSC;
    }

    public PageVO<ShowCaseItem> getArticleDOPageListByQuery(ArticleQueryDTO  articleQueryDTO  ){
        ResourcePageResult<ArticleDO> result =  articleBackEndService.getArticleDOPageListByQuery(articleQueryDTO);
        if(null == result || !result.isSuccess()){
            return null;
        }
        List<ShowCaseItem> list = ArticleDOShowCaseItem(result.getList());
        PageVO<ShowCaseItem> page  = new PageVO<ShowCaseItem>(articleQueryDTO.getPageNo(), articleQueryDTO.getPageSize(),result.getTotalCount(), list);
        return page;
    }

    public List<ShowCaseItem> ArticleDOShowCaseItem(List<ArticleDO> list){
        List<ShowCaseItem> listSC = new ArrayList<ShowCaseItem>();
        if(CollectionUtils.isEmpty(list)){
            return listSC;
        }
        for (ArticleDO oo:list ) {
            ShowCaseItem sc = new ShowCaseItem();
            sc.setId(oo.getId());
            sc.setName(oo.getTitle());//标题
            sc.setImgUrl(StringUtils.isEmpty(oo.getFrontcover())?"":oo.getFrontcover());
            listSC.add(sc);
        }
        return listSC;
    }


    public PageVO<ShowCaseItem> getGuideListByQuery(GuideScenicListQuery query){
        PageVO<GuideScenicVO> pageA =  guideManageService.getGuideList(query);
        if(null == pageA){
            return null;
        }
        List<ShowCaseItem> list = attachmentVOToShowCaseItem(pageA.getItemList());
        PageVO<ShowCaseItem> page  = new PageVO<ShowCaseItem>(query.getPageNumber(), query.getPageSize(),pageA.getTotalCount(), list);
        return page;
    }

    public List<ShowCaseItem> attachmentVOToShowCaseItem(List<GuideScenicVO> list){
        List<ShowCaseItem> listSC = new ArrayList<ShowCaseItem>();
        for (GuideScenicVO oo:list) {
            ShowCaseItem sc = new ShowCaseItem();
            sc.setId(oo.getGuideid());
            sc.setName(oo.getScenicName());//标题
            sc.setImgUrl(StringUtils.isEmpty(oo.getGuideImg())?"":oo.getGuideImg());
            listSC.add(sc);
        }
        return listSC;
    }
}
