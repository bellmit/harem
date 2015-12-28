package com.yimayhd.harem.controller;

import java.util.List;

import com.yimayhd.harem.constant.ResponseStatus;
import com.yimayhd.harem.model.TravelOfficial;
import com.yimayhd.harem.model.query.LiveTopicQuery;
import com.yimayhd.harem.service.LiveTopicService;
import com.yimayhd.snscenter.client.domain.SnsTravelSpecialtyDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.model.Club;
import com.yimayhd.harem.model.Live;
import com.yimayhd.harem.model.query.ClubListQuery;
import com.yimayhd.harem.model.query.LiveListQuery;
import com.yimayhd.harem.model.vo.LiveVO;
import com.yimayhd.harem.service.ClubService;
import com.yimayhd.harem.service.LiveService;

/**
 * 直播（标签）话题管理
 * @author xzj
 */
@Controller
@RequestMapping("/B2C/liveTopicManage")
public class LiveTopicController {
	 @Autowired
	 private LiveTopicService liveTopicService;
	
	 /**
     * 直播管理列表
     * @return 直播管理列表
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model,LiveTopicQuery liveTopicQuery)  throws Exception {

//        PageVO<SnsTravelSpecialtyDO> pageVo = travelOfficialService.getList(travelOfficialListQuery);


//        model.addAttribute("pageVo", pageVo);
//        model.addAttribute("liveTopicQuery", liveTopicQuery);
//        model.addAttribute("travelOfficialList", pageVo.getItemList());

    	return "/system/liveTopic/list";
    }
    
    
    
   /**
     * 直播管理列表
     * @return 添加
     * @throws Exception
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public String toAdd(Model model,LiveListQuery liveListQuery)  throws Exception {
    	return "/system/liveTopic/add";
    }



    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo add(TravelOfficial travelOfficial) throws Exception {
//        TravelOfficial db = travelOfficialService.add(travelOfficial);
//        if(null == db ){
//            return new ResponseVo(ResponseStatus.ERROR);
//        }
//        return new ResponseVo(ResponseStatus.SUCCESS);
        return null;
    }
    


   
	
}
