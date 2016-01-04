package com.yimayhd.harem.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.constant.B2CConstant;
import com.yimayhd.harem.model.CategoryVO;
import com.yimayhd.harem.model.ItemResultVO;
import com.yimayhd.harem.model.ItemVO;
import com.yimayhd.harem.service.CategoryService;
import com.yimayhd.harem.service.CommActivityService;
import com.yimayhd.harem.service.CommScenicService;
import com.yimayhd.harem.service.CommodityService;
import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.model.enums.ReduceType;
import com.yimayhd.ic.client.model.param.item.CommonItemPublishDTO;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.user.session.manager.SessionUtils;

/**
 * 活动商品
 * 
 * @author xuzj
 */
@Controller
@RequestMapping("/B2C/comm/activityManage")
public class CommActivityManageController extends BaseController {
	
	@Autowired
	private CommActivityService commActivityService;
	@Autowired
    private CategoryService categoryService;
	@Autowired
    private CommodityService commodityService;
	
	/**
	 * 新增活动商品
	 * 
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(Model model,int categoryId) throws Exception {
		
		CategoryVO categoryVO = categoryService.getCategoryVOById(categoryId);
		model.addAttribute("category", categoryVO);
		model.addAttribute("itemType",ItemType.ACTIVITY.getValue());
		return "/system/comm/activity/edit";
	}

	
	 /**
     * 新增活动商品	
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public
    String add(ItemVO itemVO) throws Exception {
    	
		itemVO.setSellerId(Long.parseLong(SessionUtils.getUserId()));
		ItemPubResult result = commActivityService.add(itemVO);
        
        return "/success";
    }

    /**
     * 编辑活动（商品）
     * @return 活动（商品）详情
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public
    String toEdit(Model model,@PathVariable(value = "id") long id) throws Exception {
    
    	ItemResultVO itemResultVO = commodityService.getCommodityById(id);
    	List<ReduceType> reduceTypeList= Arrays.asList(ReduceType.values());
    	model.addAttribute("reduceTypeList", reduceTypeList);
    	model.addAttribute("itemResult", itemResultVO);
    	model.addAttribute("commodity", itemResultVO.getItemVO());
    	model.addAttribute("category", itemResultVO.getCategoryVO());
    	model.addAttribute("itemType",ItemType.ACTIVITY.getValue());
       
        return "/system/comm/activity/edit";
    }

    /**
     * 编辑活动（商品）
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public
    String edit(ItemVO itemVO,@PathVariable("id") long id) throws Exception {
        itemVO.setId(id);
        commActivityService.update(itemVO);
        return "/success";
    }
	
	
}
