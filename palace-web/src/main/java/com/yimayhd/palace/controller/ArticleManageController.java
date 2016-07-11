package com.yimayhd.palace.controller;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.model.ArticleVO;
import com.yimayhd.palace.model.SnsSubjectVO;
import com.yimayhd.palace.model.SubjectInfoAddVO;
import com.yimayhd.palace.model.query.ArticleListQuery;
import com.yimayhd.palace.model.query.LiveListQuery;
import com.yimayhd.palace.repo.TagRepo;
import com.yimayhd.palace.service.ArticleService;
import com.yimayhd.palace.service.LiveService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章管理
 * 
 * @author xiemingna
 *
 */
@Controller
@RequestMapping("/jiuxiu/articleManage")
public class ArticleManageController extends BaseController {
	@Autowired
	private ArticleService articleService;

	/**
	 * 文章列表
	 *
	 * @return 文章列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/articleList", method = RequestMethod.GET)
	public String list(Model model, ArticleListQuery articleListQuery) throws Exception {
		PageVO<ArticleVO> pageVO = articleService.getList(articleListQuery);
		model.addAttribute("pageVo", pageVO);
		model.addAttribute("articleListQuery", articleListQuery);
		model.addAttribute("articleList", pageVO.getItemList());
		return "/system/article/articleList";
	}

}
