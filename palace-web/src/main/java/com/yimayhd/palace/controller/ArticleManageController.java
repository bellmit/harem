package com.yimayhd.palace.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.constant.ResponseStatus;
import com.yimayhd.palace.model.ArticleItemVO;
import com.yimayhd.palace.model.ArticleVO;
import com.yimayhd.palace.model.query.ArticleListQuery;
import com.yimayhd.palace.service.ArticleService;
import com.yimayhd.resourcecenter.model.enums.ArticleStauts;
import com.yimayhd.resourcecenter.model.enums.ArticleSubType;
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
	 * 新增H5文章跳转
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(Model model) throws Exception {
		model.addAttribute("articleTypeList", ArticleType.values());
		return "/system/article/articleEdit";
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
			List<ArticleItemVO> articleItemList = new ArrayList<ArticleItemVO>();
			for (int i = 0; i < 3; i++) {
				ArticleItemVO articleItemVO = new ArticleItemVO();
				articleItemVO.setContent("测试" + i);
				articleItemVO.setGmtCreated(new Date());
				articleItemVO.setType(ArticleType.EXPERTSTORY.getValue());
				articleItemVO.setSubType(ArticleSubType.CITY_ACTIVITY.getType());
				articleItemVO.setTitle("测试" + i);
				articleItemVO.setSort(Long.valueOf(i));
				articleItemList.add(articleItemVO);
			}
			articleVO.setArticleItems(articleItemList);
			RcResult<Boolean> result = articleService.add(articleVO);
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

	/**
	 * 根据文章ID获取文章详情
	 *
	 * @return 文章详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/toEdit/{id}", method = RequestMethod.GET)
	public String toEdit(Model model, @PathVariable(value = "id") long id) throws Exception {
		ArticleVO articleVO = articleService.getArticleById(id);
		model.addAttribute("articleTypeList", ArticleType.values());
		model.addAttribute("articleStautsList", ArticleStauts.values());
		model.addAttribute("article", articleVO);
		return "/system/article/articleEdit";
	}

	/**
	 * 根据文章ID修改文章
	 *
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo edit(@PathVariable(value = "id") long id, ArticleVO articleVO) throws Exception {
		ResponseVo responseVo = new ResponseVo();
		articleVO.setId(id);
		RcResult<Boolean> result = articleService.modify(articleVO);
		if (result.isSuccess()) {
			responseVo.setMessage("添加成功！");
			responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
		} else {
			responseVo.setMessage(result.getResultMsg());
			responseVo.setStatus(ResponseStatus.ERROR.VALUE);
		}
		return responseVo;
	}

	/**
	 * 直播违规
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/violation/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo publish(@PathVariable("id") long id) throws Exception {
		ResponseVo responseVo = new ResponseVo();
		RcResult<Boolean> result = articleService.violation(id);
		if (result.isSuccess()) {
			responseVo.setMessage("添加成功！");
			responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
		} else {
			responseVo.setMessage(result.getResultMsg());
			responseVo.setStatus(ResponseStatus.ERROR.VALUE);
		}

		return responseVo;
	}

	/**
	 * 直播恢复
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/regain/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo close(@PathVariable("id") long id) throws Exception {
		ResponseVo responseVo = new ResponseVo();
		RcResult<Boolean> result = articleService.regain(id);
		if (result.isSuccess()) {
			responseVo.setMessage("添加成功！");
			responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
		} else {
			responseVo.setMessage(result.getResultMsg());
			responseVo.setStatus(ResponseStatus.ERROR.VALUE);
		}

		return responseVo;
	}

	/**
	 * 直播违规(批量)
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchViolation", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo batchPublish(@RequestParam("articleIdList[]") ArrayList<Long> articleIdList) throws Exception {
		ResponseVo responseVo = new ResponseVo();
		RcResult<Boolean> result = articleService.batchViolation(articleIdList);
		if (result.isSuccess()) {
			responseVo.setMessage("添加成功！");
			responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
		} else {
			responseVo.setMessage(result.getResultMsg());
			responseVo.setStatus(ResponseStatus.ERROR.VALUE);
		}

		return responseVo;
	}
}
