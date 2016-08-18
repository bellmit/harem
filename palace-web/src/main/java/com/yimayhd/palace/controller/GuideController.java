package com.yimayhd.palace.controller;

import com.yimayhd.palace.model.guide.GuideListQuery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 导览
 * Created by xushubing on 2016/8/18.
 */
@Controller
@RequestMapping("/jiuniu/guideManage")
public class GuideController {
    /**
     * 导览列表  分页
     * @param model
     * @param guideListQuery
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public String list(Model model, GuideListQuery guideListQuery) throws Exception {
        return null;
    }
}
