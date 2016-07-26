package com.yimayhd.palace.controller;

import com.yimayhd.commentcenter.client.domain.ComRateDO;
import com.yimayhd.commentcenter.client.dto.RatePageListDTO;
import com.yimayhd.commentcenter.client.result.BasePageResult;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.result.ComRateResult;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.ComRateVO;
import com.yimayhd.palace.service.ComRateService;
import com.yimayhd.palace.model.query.ComRateListQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by p on 7/18/16.
 */
@Controller
@RequestMapping("/GF/comRateManage")
public class GFcomRateController {
    @Autowired
    private ComRateService comRateService;
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String comRateList(Model model, ComRateListQuery comRateListQuery) throws Exception {

        PageVO<ComRateVO> comRatePage = comRateService.getRatePageList(comRateListQuery);

        model.addAttribute("comRateListQuery", comRateListQuery);
        model.addAttribute("comRateList", comRatePage.getItemList());
        model.addAttribute("pageVo", comRatePage);
        return "/system/gfcomrate/list";
    }

    @RequestMapping(value = "/batchdelete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<Boolean> deleteComRate(@RequestParam("ids[]") ArrayList<Long> ids) {
        BaseResult<Boolean> result = comRateService.deletComRate(ids);
        return result;
    }

    @RequestMapping(value = "/batchreplay", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<Boolean> batchreplay(@RequestParam("ids[]") ArrayList<Long> ids, String content) {
        BaseResult<Boolean> result = comRateService.replayComRate(ids, content);
        return result;
    }
}