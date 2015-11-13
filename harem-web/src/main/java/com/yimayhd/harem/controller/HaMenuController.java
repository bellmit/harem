package com.yimayhd.harem.controller;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVo;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.model.HaMenuDO;
import com.yimayhd.harem.service.HaMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 菜单表
 * @author czf
 */
@Controller
@RequestMapping("haMenu")
public class HaMenuController extends BaseController{

    @Autowired
    private HaMenuService haMenuService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseVo getList(HaMenuDO haMenuDO, PageVo<HaMenuDO> vo) throws Exception {
        vo.setEntity(haMenuDO);
        vo.setList(haMenuService.getList(vo));
        vo.setTotalSum(haMenuService.getCount(haMenuDO));
        return new ResponseVo(vo);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVo get(@PathVariable("id") long id) throws Exception {
        return new ResponseVo(haMenuService.getById(id));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseVo save(@RequestBody HaMenuDO haMenuDO) throws Exception {
        haMenuService.add(haMenuDO);
        return new ResponseVo();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseVo update(@PathVariable("id")long id, @RequestBody HaMenuDO haMenuDO) throws Exception {
        haMenuDO.setId(id);
        haMenuService.modify(haMenuDO);
        return new ResponseVo();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseVo delete(@PathVariable("id") long id) throws Exception {
        haMenuService.delete(id);
        return new ResponseVo();
    }
}