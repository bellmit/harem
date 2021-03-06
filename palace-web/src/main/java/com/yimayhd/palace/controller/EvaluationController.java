package com.yimayhd.palace.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.commentcenter.client.enums.BaseStatus;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.enums.BizCommentStatus;
import com.yimayhd.palace.model.ComCommentVO;
import com.yimayhd.palace.model.query.EvaluationListQuery;
import com.yimayhd.palace.service.EvaluationService;
import com.yimayhd.palace.util.CommentUtil;

/**
 * 评价管理
 * 
 * @author xzj
 */
@Controller
@RequestMapping("/B2C/evaluationManage")
public class EvaluationController extends BaseController {
	@Autowired
	private EvaluationService evaluationService;

	/**
	 * 评价管理列表
	 * 
	 * @return 评价管理列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, EvaluationListQuery evaluationListQuery) throws Exception {
		PageVO<ComCommentVO> pageVO = evaluationService.getList(evaluationListQuery);
		model.addAttribute("evaluationList", pageVO.getItemList());
		model.addAttribute("pageVo", pageVO);
		model.addAttribute("commentStatusList", BizCommentStatus.values());
		model.addAttribute("commentTypeList", CommentUtil.getCommentTypes());
		model.addAttribute("evaluationListQuery", evaluationListQuery);
		return "/system/evaluate/list";
	}

	/**
	 * 评价违规
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/violation/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo publish(@PathVariable("id") long id) throws Exception {
		try {
			evaluationService.violation(id);
			return new ResponseVo();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVo.error(e);
		}
	}

	/**
	 * 评价恢复
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/regain/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo close(@PathVariable("id") long id) throws Exception {
		evaluationService.regain(id);
		return new ResponseVo();
	}

	/**
	 * 评价违规(批量)
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchViolation", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo batchPublish(@RequestParam("evaluationIdList[]") ArrayList<Long> evaluationIdList) throws Exception {
		try {
			evaluationService.batchViolation(evaluationIdList);
			return new ResponseVo();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVo.error(e);
		}
	}

}
