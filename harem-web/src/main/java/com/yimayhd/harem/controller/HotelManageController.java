package com.yimayhd.harem.controller;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.model.HotelVO;
import com.yimayhd.harem.model.Region;
import com.yimayhd.harem.model.query.HotelListQuery;
import com.yimayhd.harem.service.CommodityService;
import com.yimayhd.harem.service.FacilityIconService;
import com.yimayhd.harem.service.HotelService;
import com.yimayhd.harem.service.RegionService;
import com.yimayhd.ic.client.model.domain.FacilityIconDO;
import com.yimayhd.ic.client.model.domain.HotelDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    private final static int ROOMFACILITY_TYPE = 1;
    private final static int ROOMSERVICELIST_TYPE = 2;
    private final static int HOTELFACILITYLIST_TYPE = 3;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private FacilityIconService facilityIconService;
    @Autowired
    private CommodityService commodityService;
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
     * 选择酒店列表
     * @param model
     * @param hotelListQuery
     * @param multiSelect 是否多选（1：单选；2：多选）
     * @return 酒店（资源）列表
     * @throws Exception
     */
    @RequestMapping(value = "/selectList", method = RequestMethod.GET)
    public
    String selectList(Model model,HotelListQuery hotelListQuery,int multiSelect) throws Exception {
        List<HotelDO> hotelDOList = hotelService.getList(hotelListQuery);
        PageVO pageVo = new PageVO(1,10,300);
        model.addAttribute("pageVo", pageVo);
        model.addAttribute("hotelListQuery", hotelListQuery);
        model.addAttribute("hotelDOList", hotelDOList);
        model.addAttribute("multiSelect",multiSelect);
        return "/system/hotel/selectList";
    }

    /**
     * 新增酒店（资源）
     * @return 酒店（资源）详情
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
     * 编辑酒店（资源）
     * @return 酒店（资源）详情
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
        model.addAttribute("pictureList",hotelVO.getPictureList());
        model.addAttribute("roomFacilityList",roomFacilityList);
        model.addAttribute("roomServiceList",roomServiceList);
        model.addAttribute("hotelFacilityList",hotelFacilityList);

        return "/system/hotel/edit";
    }

    /**
     * 编辑酒店（资源）
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
     * 新增酒店（资源）
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public
    String add(HotelVO hotelVO) throws Exception {
        hotelService.add(hotelVO);
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
        hotelService.setHotelStatus(id,hotelStatus);
        return new ResponseVo();
    }
    /**
     * 酒店状态变更(批量)
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/setHotelStatusList", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo setHotelStatusList(@RequestParam("hotelIdList[]")ArrayList<Long> hotelIdList,int hotelStatus) throws Exception {
        hotelService.setHotelStatusList(hotelIdList, hotelStatus);
        return new ResponseVo();
    }

    //TODO
    /**
     * 添加图片
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/picture/add/{hotelId}", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseVo addHotelPicture(@PathVariable("hotelId") long id,ArrayList<String> pictureList) throws Exception {
        return new ResponseVo();
    }
    /**
     * 删除图片
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/picture/delete/{hotelId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo delHotelPicture(@PathVariable("hotelId") long id) throws Exception {
        return new ResponseVo();
    }
    /**
     * 置顶图片
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/picture/top/{hotelId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo topHotelPicture(@PathVariable("hotelId") long id) throws Exception {
        return new ResponseVo();
    }


}
