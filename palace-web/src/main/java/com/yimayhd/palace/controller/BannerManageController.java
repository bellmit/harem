package com.yimayhd.palace.controller;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.BaseQuery;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.constant.Constants;
import com.yimayhd.palace.constant.ResponseStatus;
import com.yimayhd.palace.model.vo.booth.BoothVO;
import com.yimayhd.palace.model.vo.booth.ShowcaseVO;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.palace.service.BoothService;
import com.yimayhd.palace.service.ShowcaseService;
import com.yimayhd.palace.service.ThemeService;
import com.yimayhd.resourcecenter.model.enums.CacheType;
import com.yimayhd.resourcecenter.model.enums.RegionType;
import com.yimayhd.resourcecenter.model.enums.ShowcaseStauts;
import com.yimayhd.resourcecenter.model.query.OperationQuery;
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
import com.yimayhd.resourcecenter.model.enums.OperationParamCombineType;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.EnumMap;


/**
 * Created by czf on 2016/4/12.
 */
@Controller
@RequestMapping("/banner")
public class BannerManageController extends BaseController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Autowired  ShowcaseService showcaseService;
    @Autowired  BoothService boothService;
    @Autowired  ThemeService themeService;

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
    public String showcaseList(Model model,ShowcaseQuery showcaseQuery, @PathVariable(value = "boothCode") String boothCode,int pageNumber) throws Exception {
        showcaseQuery.setPageNo( 0 == pageNumber ? Constants.DEFAULT_PAGE_NUMBER : pageNumber );
        PageVO<ShowCaseResult> page = showcaseService.getShowcaseResult(showcaseQuery);
        model.addAttribute("pageVo",page);
        model.addAttribute("boothCode",boothCode);
        model.addAttribute("pageNumber",showcaseQuery.getPageNo());
        model.addAttribute("showcaseQuery",showcaseQuery);
        return "/system/banner/showcase/list";
    }

    @RequestMapping(value = "/showcase/toAdd", method = RequestMethod.GET)
    public String showcaseToAdd(Model model) throws Exception {
        return "/system/banner/showcase/edit";
    }

    /**
     * 新增showcase提交
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showcase/add", method = RequestMethod.POST)
    public String showcaseAdd(Model model,ShowcaseVO showcaseVO) throws Exception {
        return operation( showcaseVO);
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
        if(id == 0L){
            throw new Exception("参数【id】错误");
        }
        ShowcaseVO showcase = showcaseService.getById(id);
        model.addAttribute("showcase",showcase);
        return "/system/banner/showcase/edit";
    }

    /**
     * 编辑showcase提交
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showcase/edit/{showcaseId}", method = RequestMethod.POST)
    public String showcaseEdit(Model model,ShowcaseVO showcaseVO) throws Exception {
        return operation( showcaseVO);
    }

    /**
     * showcase上下架
     * @param showcaseId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showcase/publish", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo showcasePublish(long showcaseId,int status) throws Exception {
        if(showcaseId == 0 || null == ShowcaseStauts.getByStatus(status)){
            return new ResponseVo(ResponseStatus.INVALID_DATA);
        }
        ShowcaseVO sv = new ShowcaseVO();
        sv.setId(showcaseId);
        sv.setStatus(status);
        boolean flag = showcaseService.publish(showcaseId,ShowcaseStauts.getByStatus(status));
        if( flag ){
            return new ResponseVo(ResponseStatus.ERROR);
        }
        return new ResponseVo(ResponseStatus.SUCCESS);
    }

    //目的地
    @RequestMapping(value = "/destination/list")
    @ResponseBody
    public ResponseVo destinationList() throws Exception {
        return new ResponseVo(showcaseService.getListdestination(RegionType.JIUXIU_REGION));
    }
    //获取主题，内容
    @RequestMapping(value = "/theme/list")
    @ResponseBody
    public ResponseVo themeList(String code,int type) throws Exception {
        //TODO:根据code,和type去查询相应的数据
        return new ResponseVo(themeService.getListTheme(1));
    }

    public String operation(ShowcaseVO showcaseVO) throws Exception{
        ShowcaseVO showcase = showcaseService.saveOrUpdate(showcaseVO);
        if(null == showcase){
            return "error";
        }
        return "success";
    }


    //获取选择页面的列表
    @RequestMapping(value = "/operation/list")
    @ResponseBody
    public ResponseVo operationList() throws Exception {
        OperationParamCombineType[] allLight = OperationParamCombineType.values();
        //TODO:序列化枚举类有问题
        return new ResponseVo(allLight);
    }

}
