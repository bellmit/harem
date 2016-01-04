package com.yimayhd.harem.controller;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.constant.ResponseStatus;
import com.yimayhd.harem.model.CategoryVO;
import com.yimayhd.harem.service.CategoryService;
import com.yimayhd.harem.service.CommScenicService;
import com.yimayhd.ic.client.model.domain.item.ItemFeature;
import com.yimayhd.ic.client.model.enums.ItemFeatureKey;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.model.param.item.ScenicPublishDTO;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.tradecenter.client.model.enums.ReduceType;
import com.yimayhd.user.session.manager.SessionManager;

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
	private SessionManager sessionManager;

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
    	model.addAttribute("itemType",ItemType.SPOTS.getValue());
    	model.addAttribute("tagResult",tagResult.getValue());
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
    ResponseVo save(ScenicPublishDTO scenicPublishDTO,long endTime,long startDayTime , long startHourTime,Long[] check) throws Exception {
    	ResponseVo responseVo = new ResponseVo(); 	
//    	scenicPublishDTO.getItemDO().setSellerId(Long.parseLong(SessionUtils.getUserId()));
    	long sellerId = sessionManager.getUserId();
    	scenicPublishDTO.getItemDO().setSellerId(sellerId);
    	ItemFeature itemFeature = new ItemFeature(null);
	        //减库存方式
	        itemFeature.put(ItemFeatureKey.REDUCE_TYPE,ReduceType.NONE.getBizType());
	        //未付款超时时间
	        //itemFeature.put(ItemFeatureKey.NOT_PAY_TIMEOUT,3 * 24 * 3600 * 1000L);
	        //商品星级
	        itemFeature.put(ItemFeatureKey.GRADE,5);
	        //可预订时间，秒
	        itemFeature.put(ItemFeatureKey.END_BOOK_TIME_LIMIT,endTime* 24 * 3600 );
	        //需要提前多久预订，秒startDayTime*24 * 3600 * 1000L+startHourTime* 3600 * 1000L
	        itemFeature.put(ItemFeatureKey.START_BOOK_TIME_LIMIT,startDayTime*24+(24-startHourTime));
	        scenicPublishDTO.getItemDO().setItemFeature(itemFeature);
	        ItemPubResult result = commScenicService.save(scenicPublishDTO,check);
	        if(result.isSuccess()){
				responseVo.setMessage("添加成功！");
				responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
			}else{
				responseVo.setMessage(result.getResultMsg());
				responseVo.setStatus(ResponseStatus.ERROR.VALUE);
			}
	        
	    	return responseVo;
    }

    
  
    
    
}
