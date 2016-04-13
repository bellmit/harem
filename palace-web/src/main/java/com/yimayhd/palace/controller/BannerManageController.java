package com.yimayhd.palace.controller;

import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.BaseQuery;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.constant.Constants;
import com.yimayhd.palace.model.vo.booth.BoothVO;
import com.yimayhd.palace.service.BoothService;
import com.yimayhd.palace.service.ShowcaseService;
import com.yimayhd.resourcecenter.model.enums.CacheType;
import com.yimayhd.resourcecenter.model.query.ShowcaseQuery;
import com.yimayhd.resourcecenter.model.result.ShowCaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;


/**
 * Created by czf on 2016/4/12.
 */
@Controller
@RequestMapping("/banner")
public class BannerManageController extends BaseController {

    @Autowired  ShowcaseService showcaseService;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Autowired
    private BoothService boothService;

    /**
     * booth列表
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/booth/list", method = RequestMethod.GET)
    public String boothList(Model model,BaseQuery baseQuery) throws Exception {

        PageVO<BoothVO> pageVO = boothService.getList(baseQuery);
        model.addAttribute("pageVo",pageVO);
        return "/system/banner/booth/list";
    }

    /**
     * 新增booth
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/booth/add", method = RequestMethod.GET)
    public String boothToAdd(Model model) throws Exception {
        model.addAttribute("cacheType", Arrays.asList(CacheType.values()));
        return "/system/banner/booth/edit";
    }

    /**
     * 新增booth提交
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/booth/add", method = RequestMethod.POST)
    public String boothAdd(Model model,BoothVO boothVO) throws Exception {
        boothService.add(boothVO);
        return "success";
    }

    /**
     * 根据boothCode获取showcase列表
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showcase/list/{boothCode}", method = RequestMethod.GET)
    public String showcaseList(Model model,ShowcaseQuery showcaseQuery,int pageNumber) throws Exception {
        showcaseQuery.setPageNo( 0 == pageNumber ? Constants.DEFAULT_PAGE_NUMBER : pageNumber );
        PageVO<ShowCaseResult> page = showcaseService.getShowcaseResult(showcaseQuery);
        model.addAttribute("page",page);
        model.addAttribute("showcaseQuery",showcaseQuery);
        return "/system/banner/showcase/list";
    }

    /**
     * 新增showcase
     * @param model
     * @param boothId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showcase/add/{boothId}", method = RequestMethod.GET)
    public String showcaseToAdd(Model model,@PathVariable("boothId") long boothId) throws Exception {
        //TODO
        return "/system/banner/showcase/edit";
    }

    /**
     * 新增showcase提交
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showcase/add", method = RequestMethod.POST)
    public String showcaseAdd(Model model) throws Exception {
        //TODO
        return "success";
    }

    /**
     * 编辑showcase
     * @param model
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showcase/edit/{showcaseId}", method = RequestMethod.GET)
    public String showcaseToEdit(Model model,@PathVariable("showcaseId") long id) throws Exception {
        //TODO
        return "/system/banner/showcase/edit";
    }

    /**
     * 编辑showcase提交
     * @param model
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showcase/edit/{showcaseId}", method = RequestMethod.POST)
    public String showcaseEdit(Model model,@PathVariable("showcaseId") long id) throws Exception {
        //TODO
        return "success";
    }

    /**
     * showcase上架
     * @param model
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showcase/publish/{showcaseId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo showcasePublish(Model model,@PathVariable("showcaseId") long id) throws Exception {
        //TODO
        return new ResponseVo();
    }

    /**
     * showcase下架
     * @param model
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showcase/close/{showcaseId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo showcaseClose(Model model,@PathVariable("showcaseId") long id) throws Exception {
        //TODO
        return new ResponseVo();
    }



}
