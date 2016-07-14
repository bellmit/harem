package com.yimayhd.palace.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.dto.TagInfoPageDTO;
import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.ic.client.model.query.HotelPageQuery;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.BaseQuery;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.constant.Constants;
import com.yimayhd.palace.constant.ResponseStatus;
import com.yimayhd.palace.convert.ShowCaseItem;
import com.yimayhd.palace.model.vo.booth.BoothVO;
import com.yimayhd.palace.model.vo.booth.ShowcaseVO;
import com.yimayhd.palace.service.BoothService;
import com.yimayhd.palace.service.ShowcaseService;
import com.yimayhd.palace.service.ThemeService;
import com.yimayhd.resourcecenter.domain.AppVersionDO;
import com.yimayhd.resourcecenter.model.enums.*;
import com.yimayhd.resourcecenter.model.query.AppVersionQuery;
import com.yimayhd.resourcecenter.model.query.RegionQuery;
import com.yimayhd.resourcecenter.model.query.ShowcaseQuery;
import com.yimayhd.resourcecenter.model.resource.vo.OperactionVO;
import com.yimayhd.resourcecenter.model.result.ShowCaseResult;
import com.yimayhd.snscenter.client.dto.SubjectInfoDTO;
import com.yimayhd.snscenter.client.dto.topic.TopicQueryDTO;
import com.yimayhd.snscenter.client.dto.topic.TopicQueryListDTO;
import com.yimayhd.snscenter.client.result.topic.TopicResult;
import com.yimayhd.user.client.enums.MerchantOption;
import com.yimayhd.user.client.enums.MerchantStatus;
import com.yimayhd.user.client.query.MerchantPageQuery;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by czf on 2016/4/12.
 */
@Controller
@RequestMapping("/banner")
public class BannerManageController extends BaseController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Autowired  ShowcaseService showcaseService;
    @Autowired  BoothService boothService;
    @Autowired  ThemeService themeService;

    /**
     * booth列表
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/booth/list", method = RequestMethod.GET)
    public String boothList(Model model,BaseQuery baseQuery) throws Exception {
        if(StringUtils.isNotEmpty(baseQuery.getBoothCode())){
            baseQuery.setBoothCode(baseQuery.getBoothCode().trim());
        }
        if(StringUtils.isNotEmpty(baseQuery.getBoothName())){
            baseQuery.setBoothName(baseQuery.getBoothName().trim());
        }
        PageVO<BoothVO> pageVO = boothService.getList(baseQuery);
        model.addAttribute("pageVo",pageVO);
        model.addAttribute("query",baseQuery);
        return "/system/banner/booth/list";
    }

    /**
     * 新增booth
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/booth/add", method = RequestMethod.GET)
    public String boothToAdd(Model model) throws Exception {
        model.addAttribute("cacheType", Arrays.asList(CacheType.values()));
        AppVersionQuery appvQuery = new AppVersionQuery();
        appvQuery.setStatus(AppVersionStauts.ONLINE.getStatus());
        model.addAttribute("listAppVersion", boothService.queryAppVersionList(appvQuery));
        return "/system/banner/booth/edit";
    }

    /**
     * 新增booth提交
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/booth/add", method = RequestMethod.POST)
    public String boothAdd(Model model,BoothVO boothVO) throws Exception {
        boothService.add(boothVO);
        return "success";
    }

    /**
     * 根据boothCode获取showcase列表
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showcase/list/{boothCode}", method = RequestMethod.GET)
    public String showcaseList(Model model,ShowcaseQuery showcaseQuery, @PathVariable(value = "boothCode") String boothCode,int pageNumber) throws Exception {
        showcaseQuery.setPageNo( 0 == pageNumber ? Constants.DEFAULT_PAGE_NUMBER : pageNumber );
        PageVO<ShowCaseResult> page = showcaseService.getShowcaseResult(showcaseQuery);
        model.addAttribute("pageVo",page);
        model.addAttribute("boothCode",boothCode);
        model.addAttribute("boothId",showcaseQuery.getBoothId());
        model.addAttribute("appVersionCode",showcaseQuery.getAppVersionCode());
        model.addAttribute("pageNumber",showcaseQuery.getPageNo());
        model.addAttribute("showcaseQuery",showcaseQuery);
        return "/system/banner/showcase/list";
    }

    @RequestMapping(value = "/showcase/toAdd", method = RequestMethod.GET)
    public String showcaseToNewAdd(Model model,ShowcaseQuery showcaseQuery) throws Exception {
        model.addAttribute("boothId",showcaseQuery.getBoothId());
        model.addAttribute("boothCode",showcaseQuery.getBoothCode());
        model.addAttribute("appVersionCode",showcaseQuery.getAppVersionCode());
        model.addAttribute("operationDetailId",0);
        List<OperactionVO> operationDOs = showcaseService.getAllOperations();
        model.addAttribute("operationDOs",operationDOs);
        model.addAttribute("isEdit",false);
        return "/system/banner/showcase/edit_new";
        
    }


    /**
     * 新增showcase提交
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showcase/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo showcaseAdd(Model model,ShowcaseVO showcaseVO) throws Exception {
        ShowcaseVO showcase = showcaseService.saveOrUpdate(showcaseVO);
        if(null != showcase){
            return new ResponseVo(ResponseStatus.SUCCESS);
        }
            return new ResponseVo(ResponseStatus.ERROR);
    }

    /**
     * 编辑showcase
     * @param model
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showcase/edit/{showcaseId}", method = RequestMethod.GET)
    public String showcaseToEdit(Model model,@PathVariable("showcaseId") long id,String boothCode) throws Exception {
        if(id == 0L){throw new Exception("参数【id】错误");}
        ShowcaseVO showcase = showcaseService.getById(id);
        model.addAttribute("boothId",null==showcase?0:showcase.getBoothId());
        model.addAttribute("showcase",showcase);
        model.addAttribute("boothCode",boothCode);
        model.addAttribute("appVersionCode",showcase.getAppVersionCode());
        model.addAttribute("operationDetailId",showcase.getOperationDetailId());
        List<OperactionVO> operationDOs = showcaseService.getAllOperations();
        model.addAttribute("operationDOs",operationDOs);
        model.addAttribute("isEdit",true);
        return "/system/banner/showcase/edit_new";
    }

    /**
     * 编辑showcase提交
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showcase/edit/{showcaseId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo showcaseEdit(Model model,ShowcaseVO showcaseVO) throws Exception {
        ShowcaseVO showcase = showcaseService.saveOrUpdate(showcaseVO);
        if(null != showcase){
            return new ResponseVo(ResponseStatus.SUCCESS.VALUE,showcaseVO.getBoothCode());
        }
        return new ResponseVo(ResponseStatus.ERROR.VALUE,showcaseVO.getBoothCode());
    }

    /**
     * showcase上下架
     * @param showcaseId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showcase/publish", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo showcasePublish(long showcaseId,int status) throws Exception {
        if(showcaseId == 0 || null == ShowcaseStauts.getByStatus(status)){
            return new ResponseVo(ResponseStatus.INVALID_DATA);
        }
        ShowcaseVO sv = new ShowcaseVO();
        sv.setId(showcaseId);
        sv.setStatus(status);
        boolean flag = showcaseService.publish(showcaseId,ShowcaseStauts.getByStatus(status));
        if( flag ){
            return new ResponseVo(ResponseStatus.SUCCESS);
        }
        return new ResponseVo(ResponseStatus.ERROR);
    }

    //选择内容页面
    @RequestMapping(value = "/showcase/chooseContent",method = RequestMethod.GET)
    public String getChooseContent(Model model,String code,int type) throws Exception {
        model.addAttribute("code",code);
        model.addAttribute("type",type); //根据type去判断跳转
        if(Constant.SHOWCASE_SHOE_TYPE_CHOOSE_DESTINATION == type){//选目的地
            return "/system/banner/showcase/chooseDestination";
        }else if(Constant.SHOWCASE_SHOE_TYPE_THEME == type){//选目主题
            return "/system/banner/showcase/chooseTheme";
        }else if(Constant.SHOWCASE_ITEM_LIST == type
                || Constant.SHOWCASE_HOTEL_LIST == type
                || Constant.SHOWCASE_SCENIC_LIST == type
                || Constant.SHOWCASE_VIEW_TOPIC_LIST == type ){//选列表
            return "/system/banner/showcase/chooseItemList";
        }else if(Constant.SHOWCASE_ITEM_DETAIL == type
                || Constant.SHOWCASE_VIEW_TOPIC_DETAIL == type
                || Constant.SHOWCASE_MASTER_CIRCLE_DETAIL == type ){//选详情
            return "/system/banner/showcase/chooseItemDetail";
        }else if(Constant.SHOWCASE_SHOE_TYPE_MASTER == type || Constant.SHOWCASE_SHOE_TYPE_FOOD_DETAIL == type){//选达人或美食
            return "/system/banner/showcase/chooseDaRenMeiShiDetail";
        }
        return "error";
    }

    //选择内容页面
    @RequestMapping(value = "/showcase/chooseContents")
    public @ResponseBody ResponseVo ChooseContent(HttpServletRequest request) throws Exception {
        String code = request.getParameter("code");
        int type = StringUtils.isEmpty(request.getParameter("type")) ? Constant.SHOWCASE_SHOE_TYPE_KEYWORD:Integer.parseInt(request.getParameter("type"));
        int pageNumber = StringUtils.isEmpty(request.getParameter("pageNumber")) ? Constant.DEFAULT_PAGE_NO:Integer.parseInt(request.getParameter("pageNumber"));
        int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? Constant.DEFAULT_PAGE_SIZE_TEN:Integer.parseInt(request.getParameter("pageSize"));
        String keyWord = request.getParameter("tags");
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", code);
        result.put("type", type);
        result.put("PageNumber", pageNumber);
        result.put("pageSize", pageSize);
        switch (type){
            case Constant.SHOWCASE_SHOE_TYPE_CHOOSE_DESTINATION : //选目的地
                getRegion(pageNumber,pageSize,result);
                break;
            case Constant.SHOWCASE_SHOE_TYPE_THEME : //选主题
                getTagList(pageNumber,pageSize,result,code);
                break;
            case Constant.SHOWCASE_HOTEL_LIST : //选酒店列表
                getHotelList(pageNumber,pageSize,result,keyWord);
                break;
            case Constant.SHOWCASE_SCENIC_LIST ://选景区列表
                getScenicList(pageNumber,pageSize,result,keyWord);
                break;
            case Constant.SHOWCASE_ITEM_DETAIL ://选商品详情
                getItem(pageNumber,pageSize,result,code,keyWord);
                break;
            case Constant.SHOWCASE_SHOE_TYPE_MASTER ://达人的，美食的,店铺首页
                getMerchants(pageNumber,pageSize,result,code,keyWord);
                break;
            case Constant.SHOWCASE_SHOE_TYPE_FOOD_DETAIL ://达人的，美食的,店铺首页
                getMerchants(pageNumber,pageSize,result,code,keyWord);
                break;
            case Constant.SHOWCASE_VIEW_TOPIC_LIST ://话题列表
                getTopicList(pageNumber,pageSize,result,keyWord);
                break;
            case Constant.SHOWCASE_MASTER_CIRCLE_DETAIL ://选达人圈详情
                result = getUgcPageList(pageNumber,pageSize,result,keyWord);
                break;
            /*case Constant.SHOWCASE_VIEW_TOPIC_DETAIL ://选话题详情
                getScenicList(pageNumber,pageSize,result,keyWord);
                break;*/
        }
        return new ResponseVo(result);
    }

    public Map<String, Object> getUgcPageList(int pageNumber,int pageSize,Map<String, Object> result,String keyWord){
       SubjectInfoDTO subjectInfoDTO = new SubjectInfoDTO();
        subjectInfoDTO.setNeedCount(true);
        subjectInfoDTO.setPageNo(pageNumber);
        subjectInfoDTO.setPageSize(pageSize);
        if(NumberUtils.isNumber(keyWord)){
            subjectInfoDTO.setId(Long.parseLong(keyWord));
        }else if(StringUtils.isNotEmpty(keyWord)){
            subjectInfoDTO.setTextContent(keyWord);
        }
        PageVO<ShowCaseItem> page = showcaseService.getUgcPageList(subjectInfoDTO);
        result.put("pageVo", page);
        return result;
    }

    public Map<String, Object> getTopicList(int pageNumber,int pageSize,Map<String, Object> result,String keyWord){
        TopicQueryListDTO topicQueryListDTO = new TopicQueryListDTO();
        topicQueryListDTO.setNeedCount(true);
        topicQueryListDTO.setPageNo(pageNumber);
        topicQueryListDTO.setPageSize(pageSize);
        if(NumberUtils.isNumber(keyWord)){
            topicQueryListDTO.setId(Long.parseLong(keyWord));
        }else if(StringUtils.isNotEmpty(keyWord)){
            topicQueryListDTO.setTitle(keyWord);
        }
        PageVO<ShowCaseItem> page = showcaseService.getTopicPageList(topicQueryListDTO);
        result.put("pageVo", page);
        return result;
    }


    public Map<String, Object> getMerchants(int pageNumber,int pageSize,Map<String, Object> result,String code,String keyWord){
        MerchantPageQuery merchantQuery = new MerchantPageQuery();
        MerchantOption option = MerchantOption.valueOfName(code);
        merchantQuery.setDomainId(1200);
        merchantQuery.setPageNo(pageNumber);
        merchantQuery.setNeedCount(true);
        merchantQuery.setStatus(MerchantStatus.ONLINE.getCode());
        if(null != option){
            merchantQuery.setOption(option.getOption());
        }
        if(NumberUtils.isNumber(keyWord)){
            merchantQuery.setCityCode(Integer.parseInt(keyWord));
        }else if(StringUtils.isNotEmpty(keyWord)){
            merchantQuery.setName(keyWord);
        }
        PageVO<ShowCaseItem> page = showcaseService.getMerchants(merchantQuery,option);
        result.put("pageVo", page);
        return result;
    }


    public Map<String, Object> getItem(int pageNumber,int pageSize,Map<String, Object> result,String code,String keyWord){
        ItemQryDTO query = new ItemQryDTO();
        query.setDomains(Arrays.asList(1200,1100));

        query.setPageNo(pageNumber);
        query.setPageSize(pageSize);
        query.setStatus(Arrays.asList(ItemStatus.valid.getValue()));
        if(NumberUtils.isNumber(keyWord)){
            query.setId(Long.parseLong(keyWord));
        }else if(StringUtils.isNotEmpty(keyWord)){
            query.setName(keyWord);
        }
        if(null != ItemType.getByName(code)){ //商品详情这里不确定
            query.setItemType(null == ItemType.getByName(code) ? 0 : ItemType.getByName(code).getValue());
        }
        PageVO<ShowCaseItem> page = showcaseService.getItemByItemOptionDTO(query);
        result.put("pageVo", page);
        return result;
    }

    public Map<String, Object> getScenicList(int pageNumber,int pageSize,Map<String, Object> result,String keyWord){
        ScenicPageQuery sp = new ScenicPageQuery();
        sp.setNeedCount(true);
        sp.setPageNo(pageNumber);
        sp.setPageSize(pageSize);
        sp.setDomain(1200);
        sp.setStatus(ItemStatus.valid.getValue());
        if(NumberUtils.isNumber(keyWord)){
            sp.setIds(Arrays.asList(Long.parseLong(keyWord)));
        }else if(StringUtils.isNotEmpty(keyWord)){
            sp.setTags(keyWord);
        }
        PageVO<ShowCaseItem> page = showcaseService.getScenicList(sp);
        result.put("pageVo", page);
        return result;
    }

    public Map<String, Object> getHotelList(int pageNumber,int pageSize,Map<String, Object> result,String keyWord){
        HotelPageQuery sp = new HotelPageQuery();
        sp.setNeedCount(true);
        sp.setPageNo(pageNumber);
        sp.setPageSize(pageSize);
        sp.setDomain(1200);
        sp.setStatus(ItemStatus.valid.getValue());
        if(NumberUtils.isNumber(keyWord)){
            sp.setIds(Arrays.asList(Long.parseLong(keyWord)));
        }else if(StringUtils.isNotEmpty(keyWord)){
            sp.setTags(keyWord);
        }
        PageVO<ShowCaseItem> page = showcaseService.getHotelList(sp);
        result.put("pageVo", page);
        return result;
    }

    public Map<String, Object> getTagList(int pageNumber,int pageSize,Map<String, Object> result,String code){
        TagInfoPageDTO tag = new TagInfoPageDTO();
        tag.setDomain(1200);
        tag.setOutType(code);
        tag.setNeedCount(true);
        tag.setPageNo(pageNumber);
        tag.setPageSize(pageSize);
        PageVO<ComTagDO> page = showcaseService.getTagListByTagType(tag);
        result.put("pageVo", page);
        return result;
    }

    public Map<String, Object> getRegion(int pageNumber,int pageSize,Map<String, Object> result){
        RegionQuery rq = new RegionQuery();
        rq.setNeedCount(true);
        rq.setPageNo(pageNumber);
        rq.setStatus(RegionStatus.VALID.getStatus());
        rq.setPageSize(0  ==  pageSize ? Constant.DEFAULT_PAGE_SIZE_TEN:pageSize);
        rq.setType(RegionType.JIUXIU_REGION.getType());
        PageVO page = showcaseService.getRegionDOListByType(rq);
        result.put("pageVo", page);
        return result;
    }

    @RequestMapping(value = "/operation/list", method = RequestMethod.GET)
    public String operationList(Model model,BaseQuery baseQuery) throws Exception {
        PageVO<BoothVO> pageVO = boothService.getList(baseQuery);
        model.addAttribute("pageVo",pageVO);
        model.addAttribute("query",baseQuery);
        return "/system/banner/booth/list";
    }

    @RequestMapping(value = "/operation/add", method = RequestMethod.GET)
    public String operationtoAdd(Model model) throws Exception {
        return "/system/banner/operation/edit";
    }

    @RequestMapping(value = "/operation/add", method = RequestMethod.POST)
    public String operationAdd(Model model) throws Exception {

        return "/system/banner/booth/list";
    }


    @RequestMapping(value = "/appversion/list", method = RequestMethod.GET)
    public String list(Model model,AppVersionQuery query) throws Exception {
        String codeVal = request.getParameter("codeVal");
        if(StringUtils.isNotEmpty(codeVal) ){
            codeVal = codeVal.replaceAll("\\s*", "");
            if(NumberUtils.isNumber(codeVal)){query.setCode(Integer.parseInt(codeVal));}
        }
        if(StringUtils.isNotEmpty(query.getName())){query.setName(query.getName().replaceAll("\\s*", ""));}
        PageVO<AppVersionDO> page = boothService.getAppVersionList(query);
        model.addAttribute("query",query);
        model.addAttribute("pageVo",page);
        return "/system/banner/appversion/list";
    }


    @RequestMapping(value = "/appversion/add", method = RequestMethod.GET)
    public String appVersionToAdd(Model model) throws Exception {
        return "/system/banner/appversion/edit";
    }

    @RequestMapping(value = "/appversion/add", method = RequestMethod.POST)
    public String appVersionAdd(Model model,String appId,AppVersionDO appVersionDO) throws Exception {
        if(StringUtils.isNotEmpty(appId) && NumberUtils.isNumber(appId) ){
            appVersionDO.setId(Long.parseLong(appId));
        }
        AppVersionDO ado = boothService.saveOrUpdateAppVersionDO(appVersionDO);
        if(null == ado){
            return "error";
        }
        return "success";
    }
    

}
