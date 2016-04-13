package com.yimayhd.palace.controller;

import com.sun.tools.internal.ws.processor.model.Model;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.ResponseVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by czf on 2016/4/12.
 */
@Controller
@RequestMapping("/banner")
public class BannerManageController extends BaseController {

    /**
     * booth列表
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/booth/list", method = RequestMethod.GET)
    public String boothList(Model model) throws Exception {


        return "/system/banner/booth/list";
    }

    /**
     * 根据boothCode获取showcase列表
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showcase/list/{boothCode}", method = RequestMethod.GET)
    public String showcaseList(Model model) throws Exception {
        //TODO
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
