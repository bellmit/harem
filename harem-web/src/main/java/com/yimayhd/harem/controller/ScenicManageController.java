package com.yimayhd.harem.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.service.ScenicService;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;

/**
 * 景区管理（资源）
 * @author xzj
 */
@Controller
@RequestMapping("/B2C/scenicSpotManage")
public class ScenicManageController extends BaseController {
    @Autowired
    private ScenicService scenicSpotService;
    /**
     * 景区（资源）列表
     * @return 景区（资源）列表
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public
    String list(Model model,ScenicPageQuery scenicPageQuery) throws Exception {
    /*	ScenicSpotVO scenicSpotVO = new ScenicSpotVO();
    	scenicSpotVO.setScenicSpotListQuery(scenicSpotListQuery);
        List<ScenicDO> scenicDOList = scenicSpotService.getList(scenicSpotVO);
        PageVO pageVo = new PageVO(1,10,300);
        model.addAttribute("pageVo", pageVo);
        model.addAttribute("scenicSpotListQuery", scenicSpotListQuery);
        model.addAttribute("scenicDOList", scenicDOList);*/
    	
    	ICPageResult<ScenicDO> list = scenicSpotService.getList(scenicPageQuery);
    	PageVO pageVo = new PageVO<>(list.getPageNo(), list.getPageSize(), list.getTotalCount(), list.getList());
    	model.addAttribute("pageVo", pageVo);
    	model.addAttribute("scenicPageQuery", scenicPageQuery);
        return "/system/scenicSpot/list";
    }

    /**
     * 新增景区（资源）
     * @return 景区（资源）详情
     * @throws Exception
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public
    String toAdd() throws Exception {
        return "/system/scenicSpot/edit";
    }
    /**
     * 编辑景区（资源）
     * @return 景区（资源）详情
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public
    String toEdit(Model model,@PathVariable(value = "id") long id) throws Exception {
        ScenicDO scenicDO = scenicSpotService.getById(id);
        model.addAttribute("scenicDO",scenicDO);
        return "/system/scenicSpot/edit";
    }

    /**
     * 编辑景区（资源）
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public
    String edit(ScenicDO scenicDO) throws Exception {
        if(scenicDO.getId() == 0) {
        	scenicSpotService.modify(scenicDO);
        }else{
        	scenicSpotService.modify(scenicDO);
        }
        return "/success";
    }

    /**
     * 景区（资源）状态变更
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/setScenicStatus/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo setScenicStatus(@PathVariable("id") long id,int scenicStatus) throws Exception {
        ScenicDO scenicDO = new ScenicDO();
        scenicDO.setId(id);
        scenicDO.setStatus(scenicStatus);
        scenicSpotService.modify(scenicDO);
        System.out.println(0);
        return new ResponseVo();
    }
    /**
     * 动态状态变更(批量)
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/setScenicStatusList", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo setHotelStatusList(@RequestParam("scenicIdList[]")ArrayList<Integer> scenicIdList,int scenicStatus) throws Exception {
    	scenicSpotService.setScenicStatusList(scenicIdList,scenicStatus);
        return new ResponseVo();
    }


}
