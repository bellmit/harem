package com.yimayhd.harem.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.harem.base.BaseController;
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

    
  
    
    
}
