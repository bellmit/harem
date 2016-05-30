package com.yimayhd.palace.controller;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.dto.TagInfoPageDTO;
import com.yimayhd.commentcenter.client.dto.TagRelationDomainDTO;
import com.yimayhd.ic.client.model.domain.item.ItemInfo;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.BaseQuery;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.constant.Constants;
import com.yimayhd.palace.constant.ResponseStatus;
import com.yimayhd.palace.controller.vo.OperationParamConstant;
import com.yimayhd.palace.controller.vo.OperationVO;
import com.yimayhd.palace.convert.ShowCaseItem;
import com.yimayhd.palace.model.ItemVO;
import com.yimayhd.palace.model.query.CommodityListQuery;
import com.yimayhd.palace.model.vo.booth.BoothVO;
import com.yimayhd.palace.model.vo.booth.ShowcaseVO;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.palace.service.BoothService;
import com.yimayhd.palace.service.ShowcaseService;
import com.yimayhd.palace.service.ThemeService;
import com.yimayhd.resourcecenter.domain.BoothDO;
import com.yimayhd.resourcecenter.domain.OperationDO;
import com.yimayhd.resourcecenter.domain.RegionDO;
import com.yimayhd.resourcecenter.domain.RegionIntroduceDO;
import com.yimayhd.resourcecenter.model.enums.CacheType;
import com.yimayhd.palace.constant.OperationType;
import com.yimayhd.resourcecenter.model.enums.RegionType;
import com.yimayhd.resourcecenter.model.enums.ShowcaseStauts;
import com.yimayhd.resourcecenter.model.query.RegionQuery;
import com.yimayhd.resourcecenter.model.query.ShowcaseQuery;
import com.yimayhd.resourcecenter.model.result.ShowCaseResult;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


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
//        if(StringUtils.isNotEmpty(baseQuery.getBoothName())){
//            baseQuery.setBoothName(new String(baseQuery.getBoothName().getBytes("ISO-8859-1"),"utf-8").trim());
//        }
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
        //boothService.add(boothVO);
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
        model.addAttribute("pageNumber",showcaseQuery.getPageNo());
        model.addAttribute("showcaseQuery",showcaseQuery);
        //把boothid返回去。假如该booth没有showcase，那么在查一遍
        if(CollectionUtils.isEmpty(page.getItemList())){
            BoothDO db = showcaseService.getBoothInfoByBoothCode(boothCode);
            if(null != db ){
                model.addAttribute("boothId",db.getId());
            }
        }
        return "/system/banner/showcase/list";
    }

    @RequestMapping(value = "/showcase/toAdd", method = RequestMethod.GET)
    public String showcaseToAdd(Model model,long boothId,String boothCode) throws Exception {
        model.addAttribute("boothId",boothId);
        model.addAttribute("boothCode",boothCode);
        return "/system/banner/showcase/edit";
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
        return "/system/banner/showcase/edit";
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
        model.addAttribute("type",type);
        //根据type去判断跳转
        if(Constant.SHOWCASE_SHOE_TYPE_CHOOSE_DESTINATION==type){
            return "/system/banner/showcase/chooseDestination";//选目的地
        }else if(Constant.SHOWCASE_SHOE_TYPE_THEME==type){
            return "/system/banner/showcase/chooseTheme";//选目主题
        }else if(Constant.SHOWCASE_SHOE_TYPE_ITEM_LIST==type){
            return "/system/banner/showcase/chooseItemList";//选商品列表
        }else if(Constant.SHOWCASE_SHOE_TYPE_ITEM_DETAIL==type){
            return "/system/banner/showcase/chooseItemDetail";//选商品详情
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
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(keyWord)){
            keyWord = (new String(keyWord.getBytes("ISO-8859-1"),"utf-8")).trim();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", code);
        result.put("type", type);
        result.put("PageNumber", pageNumber);
        result.put("pageSize", pageSize);
        if(Constant.SHOWCASE_SHOE_TYPE_CHOOSE_DESTINATION  ==  type){//选目的地
            RegionQuery rq = new RegionQuery();
            rq.setNeedCount(true);
            rq.setPageNo(pageNumber);
            rq.setPageSize(0  ==  pageSize ? Constant.DEFAULT_PAGE_SIZE_TEN:pageSize);
            rq.setType(RegionType.JIUXIU_REGION.getType());
            PageVO page = showcaseService.getRegionDOListByType(rq);
            result.put("pageVo", page);
            return new ResponseVo(result);
        }else if(Constant.SHOWCASE_SHOE_TYPE_THEME  ==  type){//选主题
            TagInfoPageDTO tag = new TagInfoPageDTO();
            tag.setDomain(1200);
            tag.setOutType(code);
            tag.setNeedCount(true);
            tag.setPageNo(pageNumber);
            tag.setPageSize(pageSize);
            PageVO<ComTagDO> page = showcaseService.getTagListByTagType(tag);
            result.put("pageVo", page);
        }else if(Constant.SHOWCASE_SHOE_TYPE_ITEM_LIST  ==  type){ //选商品列表
        }else if(Constant.SHOWCASE_SHOE_TYPE_ITEM_DETAIL  ==  type){//选商品详情
            ItemQryDTO query = new ItemQryDTO();
            query.setDomains(Arrays.asList(1200,1100));
            query.setPageNo(pageNumber);
            query.setPageSize(pageSize);
            //判断keyWord是纯数字则放id，否则放title
            if(NumberUtils.isNumber(keyWord)){
                query.setId(Long.parseLong(keyWord));
            }else{
                query.setName(keyWord);
            }
            if(null != ItemType.getByName(code)){ //商品详情这里不确定
                query.setItemType(null == ItemType.getByName(code) ? 0 : ItemType.getByName(code).getValue());
            }
            PageVO<ShowCaseItem> page = showcaseService.getItemByItemOptionDTO(query);
            result.put("pageVo", page);
        }
        return new ResponseVo(result);
    }

    //获取选择页面的列表
    @RequestMapping(value = "/operation/list")
    @ResponseBody
    public ResponseVo operationList() throws Exception {
        //TODO:rc_oper表加个type,把乱七八糟的类型过滤一下，不要在这里显示出来了
    	ResponseVo rs = null;
    	List<OperationDO> operationDOs = showcaseService.getAllOperactions();
        if( CollectionUtils.isEmpty(operationDOs) ){
            return new ResponseVo(ResponseStatus.ERROR);
        }
        List<OperationVO> vos = new ArrayList<OperationVO>() ;
        for( OperationDO operationDO : operationDOs ){
    		String code = operationDO.getCode() ;
    		OperationVO vo = new OperationVO();
    		vo.setOperationId(operationDO.getId());
    		vo.setOperationCode(code);
    		vo.setOperationName(operationDO.getName());
    		
    		for (OperationType type : OperationType.values()) {
    			if( code.equals(type.name())) {
    				if (OperationType.FREE_TOUR_LIST == type    ||
                        OperationType.PACKAGE_TOUR_LIST == type ||
                        OperationType.AROUND_FUN_LIST == type ||
                        OperationType.SCENIC_TAG_LIST == type    ) {// 选择目的地、选择路线标签
    					String[] types = { OperationParamConstant.CHOOSE_DESTINATION, OperationParamConstant.THEM_LINE };
    					vo.setParamTypes(types);
    				} else if (OperationType.CITY_ACTIVITY_LIST == type) {// 选择目的地、选择活动标签
    					String[] types = { OperationParamConstant.CHOOSE_DESTINATION, OperationParamConstant.THEM_ACTIVITY };
    					vo.setParamTypes(types);
    				} else if (// 选择商品 周边玩乐详情
    					OperationType.AROUND_FUN_DETAIL == type) {
    					String[] types = { OperationParamConstant.ITEM_DETAIL };
    					vo.setParamTypes(types);
    				}else if(OperationType.FREE_TOUR_DETAIL == type){//自由行
                        String[] types = { OperationParamConstant.ITEM_DETAIL_FREE_LINE };
                        vo.setParamTypes(types);
                    }else if(OperationType.PACKAGE_TOUR_DETAIL == type){////跟团游
                        String[] types = { OperationParamConstant.ITEM_DETAIL_TOUR_LINE };
                        vo.setParamTypes(types);
                    }else if(OperationType.CITY_ACTIVITY_DETAIL == type){//同城活动
                        String[] types = { OperationParamConstant.ITEM_DETAIL_CITY_ACTIVITY };
                        vo.setParamTypes(types);
                    }else if(OperationType.JIUXIU_BUY_DETAIL == type ){//实物商品详情
                        String[] types = { OperationParamConstant.ITEM_DETAIL_NORMAL };
                        vo.setParamTypes(types);
                    }else if(OperationType.HOTEL_DETAIL == type ){//酒店详情
                        String[] types = { OperationParamConstant.ITEM_DETAIL_HOTEL };
                        vo.setParamTypes(types);
                    }else if(OperationType.SCENIC_DETAIL == type ){//景区详情
                        String[] types = { OperationParamConstant.ITEM_DETAIL_SPOTS };
                        vo.setParamTypes(types);
                    }
    				break;
    			}
    		}
    		vos.add(vo);
    	}
        return new ResponseVo(vos);
    }
    
/**
1、从operation中查询所有跳转方式
2、定义需要特殊处理的OperationType（选目的地、主题）
3、从operaction表中查询出所有的跳转配置，然后和operactionType比较，如果是需要特殊处理，那么跳转的radio
4、选择目的地；主题（使用tagType中的主题）
5、


OperationType.FREE_TOUR_LIST ,  OperationType.PACKAGE_TOUR_LIST , OperationType.AROUND_FUN_LISL





 */

}
