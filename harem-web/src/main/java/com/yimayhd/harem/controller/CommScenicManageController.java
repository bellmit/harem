package com.yimayhd.harem.controller;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.model.CategoryVO;
import com.yimayhd.harem.model.CommScenicVO;
import com.yimayhd.harem.model.ItemResultVO;
import com.yimayhd.harem.model.ScenicVO;
import com.yimayhd.harem.service.CategoryService;
import com.yimayhd.harem.service.CommScenicService;
import com.yimayhd.harem.service.CommodityService;
import com.yimayhd.harem.service.ScenicService;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.model.param.item.ScenicAddNewDTO;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;

/**
 * 发布景区（商品）
 * @author xzj
 */
@Controller
@RequestMapping("/B2C/comm/scenicManage")
public class CommScenicManageController extends BaseController {
	@Autowired
	private CommScenicService commScenicService;
	@Resource
	protected ComCenterService comCenterServiceRef;
	@Autowired
	private CategoryService categoryService;


	@Autowired
	private CommodityService commodityService;
	@Autowired
	private ScenicService scenicSpotService;
    /**
     * 新增景区
     * @return 景区
     * @throws Exception
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public
    String toAdd(Model model,long categoryId) throws Exception {
    	CategoryVO categoryVO = categoryService.getCategoryVOById(categoryId);
    	//主题
    	BaseResult<List<ComTagDO>> tagResult = comCenterServiceRef.selectTagListByTagType(TagType.VIEWTAG.name());
    	if(tagResult!=null){
    		model.addAttribute("tagResult",tagResult.getValue());
    	}
    	model.addAttribute("itemType",ItemType.SPOTS.getValue());
    	model.addAttribute("category", categoryVO);
    	return "/system/comm/scenic/edit";
    }
    
    
    /**
     * 编辑景区（商品）
     * @return
     * @throws Exception,
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public
    ResponseVo save(CommScenicVO scenicVO) throws Exception {
    	ResponseVo responseVo = new ResponseVo(); 	
//<<<<<<< HEAD
////    	scenicPublishDTO.getItemDO().setSellerId(Long.parseLong(SessionUtils.getUserId()));
//    	long sellerId = sessionManager.getUserId();
//    	scenicPublishDTO.getItemDO().setSellerId(sellerId);
//    	ItemFeature itemFeature = new ItemFeature(null);
//	        //减库存方式
//	        itemFeature.put(ItemFeatureKey.REDUCE_TYPE,ReduceType.NONE.getBizType());
//	        //未付款超时时间
//	        //itemFeature.put(ItemFeatureKey.NOT_PAY_TIMEOUT,3 * 24 * 3600 * 1000L);
//	        //商品星级
//	        itemFeature.put(ItemFeatureKey.GRADE,5);
//	        //可预订时间，秒
//	        itemFeature.put(ItemFeatureKey.END_BOOK_TIME_LIMIT,endTime* 24 * 3600 );
//	        //需要提前多久预订，秒startDayTime*24 * 3600 * 1000L+startHourTime* 3600 * 1000L
//	        itemFeature.put(ItemFeatureKey.START_BOOK_TIME_LIMIT,startDayTime*24+(24-startHourTime));
//	        scenicPublishDTO.getItemDO().setItemFeature(itemFeature);
//	        ItemPubResult result = commScenicService.save(scenicPublishDTO,check);
//	        if(result.isSuccess()){
//				responseVo.setMessage("添加成功！");
//				responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
//			}else{
//				responseVo.setMessage(result.getResultMsg());
//				responseVo.setStatus(ResponseStatus.ERROR.VALUE);
//			}
//	        
//	    	return responseVo;
//=======
   
    	ItemPubResult result = commScenicService.save(scenicVO);
	    return responseVo;
//>>>>>>> d470e5034a6f18ffe553138ad1c5fc8bb3f21c5d
    }

    /**
     * 编辑景区（商品）
     * @return 景区（商品）详情
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public
    String toEdit(Model model,@PathVariable(value = "id") long id) throws Exception {

        ItemResultVO itemResultVO = commodityService.getCommodityById(id);
        //主题
    	BaseResult<List<ComTagDO>> tagResult = comCenterServiceRef.selectTagListByTagType(TagType.VIEWTAG.name());
    	BaseResult<List<ComTagDO>> tagResultCheck =comCenterServiceRef.getTagInfoByOutIdAndType(itemResultVO.getItemVO().getOutId(),TagType.VIEWTAG.name() );
    	if(tagResult!=null){
    		model.addAttribute("tagResult",tagResult.getValue());
    	}
    	if(tagResult!=null){
    		model.addAttribute("tagResultCheck",tagResultCheck.getValue());
    	}
    	ScenicVO scenicDO = scenicSpotService.getById(itemResultVO.getItemVO().getOutId());
    	if(null != scenicDO){
			model.addAttribute("scenicName", scenicDO.getName());
			model.addAttribute("orderNum", scenicDO.getOrderNum());
		}
        model.addAttribute("itemResult", itemResultVO);
        model.addAttribute("commScenic", itemResultVO.getItemVO());
        model.addAttribute("category", itemResultVO.getCategoryVO());
        model.addAttribute("itemType",ItemType.SPOTS.getValue());

        return "/system/comm/scenic/edit";
    }
  
    
    
}
