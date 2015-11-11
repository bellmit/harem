package com.yimayhd.harem.controller;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVo;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.model.Trend;
import com.yimayhd.harem.model.query.TrendListQuery;
import com.yimayhd.harem.model.vo.TrendVO;
import com.yimayhd.harem.service.TrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 动态管理
 * @author czf
 */
@Controller
@RequestMapping("/B2C/trendManage")
public class TrendManageController extends BaseController {
    @Autowired
    private TrendService trendService;

    /**
     * 动态列表
     * @return 动态列表
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public
    String list(Model model,TrendListQuery trendListQuery,PageVo pageVo) throws Exception {
        TrendVO trendVO = new TrendVO();
        trendVO.setTrendListQuery(trendListQuery);
        List<Trend> trendList = trendService.getList(trendVO.getTrend());
        pageVo.setTotalSum((long) 14800);
        //pageVo.setCurrentPage(60);
        model.addAttribute("pageVo", pageVo);
        model.addAttribute("trendListQuery", trendListQuery);
        model.addAttribute("trendList", trendList);
        return "/system/trend/list";
    }

    /**
     * 根据动态ID获取动态详情
     * @return 动态详情
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public
    String getById(Model model,@PathVariable(value = "id") long id) throws Exception {
        Trend trend = trendService.getById(id);
        model.addAttribute("trend", trend);
        return "/system/trend/detail";
    }


    /**
     * 动态状态变更
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/setIsStatus/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo setJoinStatus(@PathVariable(value = "id")long id,int trendStatus) throws Exception {
        Trend trend = new Trend();
        trend.setId(id);
        trend.setTrendStatus(trendStatus);
        trendService.modify(trend);
        return new ResponseVo();
    }




}
