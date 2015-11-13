package com.yimayhd.harem.controller;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.model.Region;
import com.yimayhd.harem.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2015/11/13.
 */
@Controller
@RequestMapping("/B2C/regionManage")
public class RegionManageController extends BaseController {
    @Autowired
    private RegionService regionService;

    /**
     * 地区列表
     * @return 地区列表
     * @throws Exception
     */
    @RequestMapping(value = "/regionList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVo list(Long id) throws Exception {
        List<Region> regionList;
        if(id == null){
            regionList = regionService.getProvince();
        }else{
            regionList = regionService.getRegionByParentId(id);
        }
        return new ResponseVo(regionList);
    }
}