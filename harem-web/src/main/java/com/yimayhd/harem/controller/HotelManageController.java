package com.yimayhd.harem.controller;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.model.HotelVO;
import com.yimayhd.harem.model.query.HotelListQuery;
import com.yimayhd.harem.service.HotelService;
import com.yimayhd.ic.client.model.domain.HotelDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 酒店管理（资源）
 * @author czf
 */
@Controller
@RequestMapping("/B2C/hotelManage")
public class HotelManageController extends BaseController {
    @Autowired
    private HotelService hotelService;
    /**
     * 酒店（资源）列表
     * @return 酒店（资源）列表
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public
    String list(Model model,HotelListQuery hotelListQuery) throws Exception {
        List<HotelDO> hotelDOList = hotelService.getList(hotelListQuery);
        PageVO pageVo = new PageVO(1,10,300);
        model.addAttribute("pageVo", pageVo);
        model.addAttribute("hotelListQuery", hotelListQuery);
        model.addAttribute("hotelDOList", hotelDOList);


        return "/system/hotel/list";
    }

    /**
     * 新增酒店（资源）
     * @return 酒店（资源）详情
     * @throws Exception
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public
    String toAdd() throws Exception {
        return "/system/hotel/edit";
    }
    /**
     * 编辑酒店（资源）
     * @return 酒店（资源）详情
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public
    String toEdit(Model model,@PathVariable(value = "id") long id) throws Exception {
        HotelDO hotelDO = hotelService.getById(id);
        model.addAttribute("hotelDO",hotelDO);
        return "/system/hotel/edit";
    }

    /**
     * 编辑酒店（资源）
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public
    String edit(HotelVO hotelVO) throws Exception {
        if(hotelVO.getId() == 0) {
            hotelService.modify(hotelVO);
        }else{
            hotelService.modify(hotelVO);
        }
        return "/success";
    }

    /**
     * 酒店（资源）状态变更
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/setHotelStatus/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo setHotelStatus(@PathVariable("id") long id,int hotelStatus) throws Exception {
        HotelVO hotelVO = new HotelVO();
        hotelVO.setId(id);
        hotelVO.setStatus(hotelStatus);
        hotelService.modify(hotelVO);
        return new ResponseVo();
    }
    /**
     * 动态状态变更(批量)
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/setHotelStatusList", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo setHotelStatusList(@RequestParam("hotelIdList[]")ArrayList<Integer> hotelIdList,int hotelStatus) throws Exception {
        hotelService.setHotelStatusList(hotelIdList,hotelStatus);
        System.out.println(1);
        return new ResponseVo();
    }


}
