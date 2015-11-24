package com.yimayhd.harem.controller;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.model.HotelVO;
import com.yimayhd.harem.model.Region;
import com.yimayhd.harem.model.query.HotelListQuery;
import com.yimayhd.harem.service.FacilityIconService;
import com.yimayhd.harem.service.HotelService;
import com.yimayhd.harem.service.RegionService;
import com.yimayhd.ic.client.model.domain.FacilityIconDO;
import com.yimayhd.ic.client.model.domain.HotelDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 酒店管理（商品）
 * @author czf
 */
@Controller
@RequestMapping("/B2C/hotelManage")
public class CommHotelManageController extends BaseController {
    private final static int ROOMFACILITY_TYPE = 1;
    private final static int ROOMSERVICELIST_TYPE = 2;
    private final static int HOTELFACILITYLIST_TYPE = 3;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private FacilityIconService facilityIconService;


    /**
     * 新增酒店（商品）
     * @return 酒店（商品）详情
     * @throws Exception
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public
    String toAdd(Model model) throws Exception {
        List<Region> provinceList= regionService.getProvince();
        List<FacilityIconDO> roomFacilityList = facilityIconService.getListByType(ROOMFACILITY_TYPE);
        List<FacilityIconDO> roomServiceList = facilityIconService.getListByType(ROOMSERVICELIST_TYPE);
        List<FacilityIconDO> hotelFacilityList = facilityIconService.getListByType(HOTELFACILITYLIST_TYPE);
        model.addAttribute("provinceList", provinceList);
        model.addAttribute("roomFacilityList",roomFacilityList);
        model.addAttribute("roomServiceList",roomServiceList);
        model.addAttribute("hotelFacilityList",hotelFacilityList);
        return "/system/hotel/edit";
    }
    /**
     * 编辑酒店（商品）
     * @return 酒店（商品）详情
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public
    String toEdit(Model model,@PathVariable(value = "id") long id) throws Exception {
        HotelVO hotelVO = hotelService.getById(id);
        List<Region> provinceList= regionService.getProvince();
        List<Region> cityList= regionService.getRegionByParentId(hotelVO.getLocationProvinceId());
        List<FacilityIconDO> roomFacilityList = facilityIconService.getListByType(ROOMFACILITY_TYPE);
        List<FacilityIconDO> roomServiceList = facilityIconService.getListByType(ROOMSERVICELIST_TYPE);
        List<FacilityIconDO> hotelFacilityList = facilityIconService.getListByType(HOTELFACILITYLIST_TYPE);
        model.addAttribute("provinceList", provinceList);
        model.addAttribute("cityList", cityList);
        model.addAttribute("hotel",hotelVO);
        model.addAttribute("roomFacilityList",roomFacilityList);
        model.addAttribute("roomServiceList",roomServiceList);
        model.addAttribute("hotelFacilityList",hotelFacilityList);

        return "/system/hotel/edit";
    }

    /**
     * 编辑酒店（商品）
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public
    String edit(HotelVO hotelVO,@PathVariable("id") long id) throws Exception {
        hotelVO.setId(id);
        hotelService.modify(hotelVO);
        return "/success";
    }

    /**
     * 新增酒店（商品）
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public
    String add(HotelVO hotelVO) throws Exception {
        hotelService.add(hotelVO);
        return "/success";
    }
}
