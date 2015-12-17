package com.yimayhd.harem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.service.CategoryService;
import com.yimayhd.harem.service.CommActivityService;
import com.yimayhd.harem.service.CommScenicService;
import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.param.item.CommonItemPublishDTO;

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
	public String toAdd() throws Exception {
		List<CategoryDO> categoryDOList = categoryService.getCategoryDOList();
		return "/system/comm/activity/edit";
	}

	
	 /**
     * 新增活动商品
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public
    String add(CommonItemPublishDTO commonItemPublishDTO) throws Exception {
        
        return "/success";
    }

	
	
}
