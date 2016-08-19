package com.yimayhd.palace.controller;

import com.yimayhd.lgcenter.client.domain.ExpressVO;
import com.yimayhd.lgcenter.client.dto.TaskInfoRequestDTO;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.BaseQuery;
import com.yimayhd.palace.service.LogisticsService;
import com.yimayhd.snscenter.client.dto.ClubDOInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @Description：物流信息
 * @author create by yushengwei on 2016/3/23
 */
@Controller
@RequestMapping("/B2C/logisticsManage/")
public class LogisticsController extends BaseController {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired LogisticsService logisticsService;


    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String list(Model model,TaskInfoRequestDTO taskInfoRequestDTO) {
        //logistics
        taskInfoRequestDTO.setNumber("V030344422");
        taskInfoRequestDTO.setCompany("yauntong");
        ExpressVO expressVO = logisticsService.getLogisticsInfo(taskInfoRequestDTO);
        model.addAttribute("logistics",expressVO);
        return "/system/logistics/logisticsInfo";
    }
}
