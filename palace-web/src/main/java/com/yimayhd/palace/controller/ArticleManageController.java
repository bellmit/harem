package com.yimayhd.palace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.constant.ResponseStatus;
import com.yimayhd.palace.model.ArticleVO;
import com.yimayhd.palace.model.query.ArticleListQuery;
import com.yimayhd.palace.service.ArticleService;
import com.yimayhd.resourcecenter.entity.Article;
import com.yimayhd.resourcecenter.model.enums.ArticleStauts;
import com.yimayhd.resourcecenter.model.enums.ArticleType;
import com.yimayhd.resourcecenter.model.result.RcResult;

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
	 */
	@RequestMapping(value = "/articleList", method = RequestMethod.GET)
	public String list(Model model, ArticleListQuery articleListQuery) {
		PageVO<ArticleVO> pageVO;
		try {
			pageVO = articleService.getList(articleListQuery);
			model.addAttribute("pageVo", pageVO);
			model.addAttribute("query", articleListQuery);
			model.addAttribute("articleList", pageVO.getItemList());
			model.addAttribute("articleTypeList", ArticleType.values());
			model.addAttribute("articleStautsList", ArticleStauts.values());
			return "/system/article/articleList";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/system/article/articleList";
	}

	/**
	 * 新增文章
	 * 
	 * @return 文章详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo add(ArticleVO articleVO) throws Exception {
		try {
			ResponseVo responseVo = new ResponseVo();

			RcResult result = articleService.add(articleVO);
			if (result.isSuccess()) {
				responseVo.setMessage("添加成功！");
				responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
			} else {
				responseVo.setMessage(result.getResultMsg());
				responseVo.setStatus(ResponseStatus.ERROR.VALUE);
			}
			return responseVo;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}

}
