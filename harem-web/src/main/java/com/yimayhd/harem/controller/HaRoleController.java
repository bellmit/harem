package com.yimayhd.harem.controller;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVo;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.model.HaRoleDO;
import com.yimayhd.harem.service.HaRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 角色表（菜单）
 * @author czf
 */
@Controller
@RequestMapping("haRole")
public class HaRoleController extends BaseController{

    @Autowired
    private HaRoleService haRoleService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseVo getList(HaRoleDO haRoleDO, PageVo<HaRoleDO> vo) throws Exception {
        vo.setEntity(haRoleDO);
        vo.setList(haRoleService.getList(vo));
        vo.setTotalSum(haRoleService.getCount(haRoleDO));
        return new ResponseVo(vo);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVo get(@PathVariable("id") long id) throws Exception {
        return new ResponseVo(haRoleService.getById(id));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseVo save(@RequestBody HaRoleDO haRoleDO) throws Exception {
        haRoleService.add(haRoleDO);
        return new ResponseVo();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseVo update(@PathVariable("id")long id, @RequestBody HaRoleDO haRoleDO) throws Exception {
        haRoleDO.setId(id);
        haRoleService.modify(haRoleDO);
        return new ResponseVo();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseVo delete(@PathVariable("id") long id) throws Exception {
        haRoleService.delete(id);
        return new ResponseVo();
    }
}
