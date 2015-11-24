package com.yimayhd.harem.controller;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.model.HotelVO;
import com.yimayhd.harem.service.ScenicService;
import com.yimayhd.ic.client.model.domain.ScenicDO;

/**
 * 发布景区（商品）
 * @author xzj
 */
@Controller
@RequestMapping("/B2C/comm/scenicManage")
public class CommScenicManageController extends BaseController {
	 @Autowired
	 private ScenicService scenicService;
	 

    /**
     * 新增景区
     * @return 景区
     * @throws Exception
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public
    String toAdd() throws Exception {
        return "/system/comm/scenic/edit";
    }
    
    
    /**
     * 编辑景区（商品）
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public
    String edit(ScenicDO scenicDO) throws Exception {
     
        return "/success";
    }

    
    /**
     * 选择景区
     * @return 景区
     * @throws Exception
     */
    @RequestMapping("/scenicList")
	public String supplierList(){
    	return "/system/comm/scenic/scenicSpotList";
    }
    
    /**
     * 景区列表
     * @return 景区
     * @throws Exception
     */
    @RequestMapping("/scenicListTable")
	public String supplierListTable(ModelMap model){
    	List<ScenicDO> list = new ArrayList<ScenicDO>();
    	for (int i = 0; i < 5; i++) {
    		ScenicDO scenic = new ScenicDO();
    		scenic.setName("景区"+i);
    		scenic.setStatus(1);
    		scenic.setId(i);
    		list.add(scenic);
		}
    	model.addAttribute("scenicList", list);
    	return "/system/comm/scenic/scenicSpotListTable";
    }
    
    
    
}
