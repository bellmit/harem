package com.yimayhd.gf.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.yimayhd.gf.biz.GfAgentBiz;
import com.yimayhd.gf.domain.GfAgentDO;
import com.yimayhd.gf.enums.GfAgentGender;
import com.yimayhd.gf.enums.GfAgentLevel;
import com.yimayhd.gf.enums.GfAgentStatus;
import com.yimayhd.gf.model.query.GfAgentPageQuery;
import com.yimayhd.gf.model.query.GfAgentVO;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.controller.poi.ViewExcel;
import com.yimayhd.palace.enums.ExportType;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.model.export.ExportAgent;
import com.yimayhd.palace.model.jiuxiu.helper.CSVUtils;
import com.yimayhd.palace.result.BizPageResult;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.palace.util.StringUtil;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by p on 10/21/16.
 */
@Controller
@RequestMapping("/GF/agent")
public class GFAgentManagerController {
    @Autowired
    private GfAgentBiz gfAgentBiz;
    @RequestMapping("/queryList")
    public String getAgents(Model model, GfAgentPageQuery gfAgentPageQuery) {
        PageVO<GfAgentVO> pageResult= gfAgentBiz.getList(gfAgentPageQuery);
        model.addAttribute("gfAgentPageQuery", gfAgentPageQuery);
        model.addAttribute("pageVo", pageResult);
        model.addAttribute("agentPageQueryList",pageResult.getItemList());
        return "/system/agent/list";
    }
    @RequestMapping("/add")
    @ResponseBody
    public BizResult<GfAgentVO> addAgent(GfAgentVO gfAgentVO){
        BizResult<GfAgentVO> bizResult = new BizResult<GfAgentVO>();
        BizResult<List<GfAgentVO>> checkResult = gfAgentBiz.checkAgent(gfAgentVO);
        List<GfAgentVO> repVO = checkResult.getValue();
        String errDes = "";
        String mobDes = "";
        String licDes = "";
        String idnDes = "";
        String wxnDes = "";
        if(repVO.size()>0){
            for(GfAgentVO agent:repVO){
                if(agent.getIdNum().equals(gfAgentVO.getIdNum())){
                    idnDes = "身份证号有重复!";
                }
                if(agent.getLicense().equals(gfAgentVO.getLicense())){
                    licDes = "授权号有重复!";
                }
                if(agent.getMobile().equals(gfAgentVO.getMobile())){
                    mobDes = "手机号有重复!";
                }
                if(agent.getWeixin().equals(gfAgentVO.getWeixin())){
                    wxnDes = "微信号有重复!";
                }
            }
            errDes = idnDes+licDes+mobDes+wxnDes;
            if(StringUtils.isNotBlank(errDes)){
                bizResult.setPalaceReturnCode(PalaceReturnCode.PARAM_ERROR);
                bizResult.setMsg(errDes);
                return bizResult;
            }
        }
        if(null!=gfAgentVO.getId() && gfAgentVO.getId()>0) {
            Boolean result = gfAgentBiz.updateAgent(gfAgentVO);
            if(result) {
                bizResult.setValue(gfAgentVO);
                return bizResult;
            }
        } else {
            GfAgentVO result = gfAgentBiz.saveAgent(gfAgentVO);
            bizResult.setValue(result);
            return bizResult;
        }
        bizResult.setPalaceReturnCode(PalaceReturnCode.EDIT_AGENT_ERROR);
        return bizResult;
    }
    @RequestMapping("/edit/{id}")
    public String editAgent(Model model, @PathVariable("id") long id){
        if(id>0){
            GfAgentVO gfAgentVO = gfAgentBiz.getAgent(id);
            model.addAttribute("gfAgentVO", gfAgentVO);
        } else {
            GfAgentVO gfAgentVO = new GfAgentVO();
            gfAgentVO.setId(0L);
            try {
                GfAgentVO firstAgent = gfAgentBiz.getAgent(1);
                gfAgentVO.setParentName(firstAgent.getAgentName());
                gfAgentVO.setParentMobile(firstAgent.getParentMobile());
                gfAgentVO.setParentId(firstAgent.getId());
            } catch (Exception e){

            }
            gfAgentVO.setGender(GfAgentGender.GENDER_WOMEN.getGender());
            model.addAttribute("gfAgentVO", gfAgentVO);
        }
        return "/system/agent/edit";
    }
    @RequestMapping("/get/{id}")
    @ResponseBody
    public BizResult<GfAgentVO> getAgent(Model model, @PathVariable("id") long id){
        BizResult<GfAgentVO> result = new BizResult<GfAgentVO>();
        GfAgentVO gfAgentVO = gfAgentBiz.getAgent(id);
        result.setValue(gfAgentVO);
        return result;
    }
    @RequestMapping("/search")
    @ResponseBody
    public BizResult<List<GfAgentVO>> searchAgent(String q){
        BizResult<List<GfAgentVO>> result = gfAgentBiz.searchAgent(q);
        return result;
    }
    @RequestMapping("/status/{id}")
    @ResponseBody
    public BizResult<Boolean> upAgentStatus(@PathVariable("id") long id, int status, int version) {
        BizResult<Boolean> result = new BizResult<Boolean>();
        GfAgentVO gfAgentVO = new GfAgentVO();
        gfAgentVO.setId(id);
        gfAgentVO.setStatus(status);
        gfAgentVO.setVersion(version);
        BizResult<Boolean> gfResult = gfAgentBiz.updateStatus(gfAgentVO);
        if(gfResult.isSuccess()) {
            return gfResult;
        }
        result.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
        return result;
    }
    @RequestMapping("/check")
    @ResponseBody
    public BizResult<List<GfAgentVO>> checkAgent(GfAgentVO gfAgentVO){
        BizResult<List<GfAgentVO>> result = gfAgentBiz.checkAgent(gfAgentVO);
        return result;
    }
    @RequestMapping("/export")
    public ModelAndView exportData(ModelMap model, HttpServletResponse response, GfAgentPageQuery gfAgentPageQuery){

        List<ExportAgent> agentList = exportAgents(gfAgentPageQuery);
        model.put("fileName", ExportType.EXPORT_AGENT.getDesc());
        model.put("type", ExportType.EXPORT_AGENT.getType());
        model.put("ListExportAgent", agentList);
        return new ModelAndView(new ViewExcel(), model);
    }
    private List<ExportAgent> exportAgents(GfAgentPageQuery gfAgentPageQuery){
        List<ExportAgent> agentList = new ArrayList<ExportAgent>();
        gfAgentPageQuery.setPageNumber(1);
        gfAgentPageQuery.setPageSize(Constant.EXPORTPAGESIZE);
        Boolean isNext = true;
        while (isNext){
            PageVO<GfAgentVO> pageResult = gfAgentBiz.getList(gfAgentPageQuery);
            gfAgentPageQuery.setPageNumber(gfAgentPageQuery.getPageNumber()+1);
            isNext = pageResult.isHasNextPage();
            List<GfAgentVO> agents= pageResult.getItemList();
            for(GfAgentVO agent:agents){
                ExportAgent exportAgent = new ExportAgent();
                exportAgent.setName(agent.getAgentName());
                exportAgent.setMobile(agent.getMobile());
                exportAgent.setParentName(agent.getParentName());
                exportAgent.setParentMobile(agent.getParentMobile());
                exportAgent.setWeixin(agent.getWeixin());
                exportAgent.setCreatedAt(agent.getCreateAt());
                exportAgent.setArea(agent.getProvinceName());
                exportAgent.setLicense(agent.getLicense());

                if(agent.getStatus()== GfAgentStatus.ABLE_STATUS.getStatus()){
                    exportAgent.setStatus(GfAgentStatus.ABLE_STATUS.getDes());
                } else if(agent.getStatus()== GfAgentStatus.DISABLE_STATUS.getStatus()){
                    exportAgent.setStatus(GfAgentStatus.DISABLE_STATUS.getDes());
                } else {
                    exportAgent.setStatus("");
                }
                String level = "";
                if(StringUtils.isNotBlank(agent.getAgentLevel())) {
                    level = GfAgentLevel.getByName(agent.getAgentLevel()).getDes();
                }
                exportAgent.setLevel(level);
                exportAgent.setCreatedAt(agent.getCreateAt());
                exportAgent.setStartAt(agent.getStartAt());
                exportAgent.setEndAt(agent.getEndAt());
                exportAgent.setIdNum(agent.getIdNum());
                agentList.add(exportAgent);
            }
        }
        return agentList;
    }
}
