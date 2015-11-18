package com.yimayhd.harem.controller;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.model.query.HotelListQuery;
import com.yimayhd.harem.model.vo.HotelVO;
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
        HotelVO hotelVO = new HotelVO();
        hotelVO.setHotelListQuery(hotelListQuery);
        List<HotelDO> hotelDOList = hotelService.getList(hotelVO);
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
    String edit(HotelDO hotelDO) throws Exception {
        if(hotelDO.getId() == 0) {
            hotelService.modify(hotelDO);
        }else{
            hotelService.modify(hotelDO);
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
        HotelDO hotelDO = new HotelDO();
        hotelDO.setId(id);
        hotelDO.setStatus(hotelStatus);
        hotelService.modify(hotelDO);
        return new ResponseVo();
    }
    /**
     * 动态状态变更(批量)
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/setTrendStatusList", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo setHotelStatusList(@RequestParam("hotelStatusList[]")ArrayList<Integer> trendStatusList,int hotelStatus) throws Exception {
        hotelService.setHotelStatusList(trendStatusList,hotelStatus);
        return new ResponseVo();
    }


}
