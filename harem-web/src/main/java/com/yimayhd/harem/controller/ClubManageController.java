package com.yimayhd.harem.controller;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVo;
import com.yimayhd.harem.model.Club;
import com.yimayhd.harem.model.Order;
import com.yimayhd.harem.model.query.ClubListQuery;
import com.yimayhd.harem.model.vo.ClubVO;
import com.yimayhd.harem.service.ClubService;
import com.yimayhd.harem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

//import com.yimayhd.service.MessageCodeService;

/**
 * 俱乐部管理
 * @author czf
 */
@Controller
@RequestMapping("/clubManage")
public class ClubManageController extends BaseController {
    @Autowired
    private ClubService clubService;

    /**
     * 俱乐部列表
     * @return 俱乐部列表
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public
    String list(Model model,ClubListQuery clubListQuery,PageVo pageVo) throws Exception {
        ClubVO clubVO = new ClubVO();
        clubVO.setClubListQuery(clubListQuery);
        List<Club> clubList = clubService.getList(clubVO.getClub());

        pageVo.setTotalSum((long) 14800);
        //pageVo.setCurrentPage(60);


        model.addAttribute("pageVo", pageVo);
        model.addAttribute("clubListQuery", clubListQuery);
        model.addAttribute("clubList", clubList);


        return "/system/club/list";
    }

    /**
     * 根据俱乐部ID获取俱乐部详情
     * @return 俱乐部详情
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public
    String getById(Model model,@PathVariable(value = "id") long id) throws Exception {
        Club club = clubService.getById(id);
        model.addAttribute("club",club);
        return "/system/club/detail";
    }

    /**
     * 根据俱乐部ID获取俱乐部详情
     * @return 俱乐部详情
     * @throws Exception
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public
    String toAdd() throws Exception {
        return "/system/club/add";
    }

}
