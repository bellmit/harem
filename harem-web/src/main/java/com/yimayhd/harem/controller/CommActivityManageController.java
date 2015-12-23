package com.yimayhd.harem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.constant.B2CConstant;
import com.yimayhd.harem.model.CategoryVO;
import com.yimayhd.harem.model.ItemVO;
import com.yimayhd.harem.service.CategoryService;
import com.yimayhd.harem.service.CommActivityService;
import com.yimayhd.harem.service.CommScenicService;
import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
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
	
	/**
	 * 新增活动商品
	 * 
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(Model model) throws Exception {
		//规格
		CategoryVO categoryVO = categoryService.getCategoryVOById(37);
		model.addAttribute("category", categoryVO);
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

	
	
}
