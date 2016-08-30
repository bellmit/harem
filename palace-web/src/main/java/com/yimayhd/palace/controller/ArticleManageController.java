package com.yimayhd.palace.controller;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.yimayhd.resourcecenter.model.enums.ArticleItemType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.checker.ArticleChecker;
import com.yimayhd.palace.checker.result.CheckResult;
import com.yimayhd.palace.constant.ResponseStatus;
import com.yimayhd.palace.convert.ArticleConverter;
import com.yimayhd.palace.model.ArticleItemVO;
import com.yimayhd.palace.model.ArticleVO;
import com.yimayhd.palace.model.query.ArticleListQuery;
import com.yimayhd.palace.model.vo.AudioVO;
import com.yimayhd.palace.result.BizPageResult;
import com.yimayhd.palace.service.ArticleService;
import com.yimayhd.resourcecenter.model.enums.ArticleStatus;
import com.yimayhd.resourcecenter.model.enums.ArticleType;
import com.yimayhd.resourcecenter.model.query.MediaPageQuery;
import com.yimayhd.resourcecenter.model.result.ResourceResult;

/**
 * 文章管理
 *
 * @author xiemingna
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
            model.addAttribute("articleStautsList", ArticleStatus.values());
            return "/system/article/articleList";
        } catch (Exception e) {
            log.error("/system/article/articleList error", e);
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
    @RequestMapping(value = "/add")
    public String add(ArticleVO articleVO) throws Exception {
        CheckResult checkResult = ArticleChecker.checkArticleVO(articleVO);
        ArticleVO vo = null;
        if (checkResult.isSuccess()) {
            vo = ArticleConverter.convertToArticleVO(articleVO);
        } else {
            throw new BaseException(checkResult.getMsg());
        }
        ResourceResult<Boolean> result = articleService.add(vo);
        if (result.isSuccess()) {
            return "/success";
        } else {
            throw new BaseException(result.getMsg());
        }
    }

    /**
     * 根据商品id获取相关信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getArticleItemDetailById/{id}/{type}")
    @ResponseBody
    public ResponseVo getArticleItemDetailById(@PathVariable("id") long id, @PathVariable("type") int type)
            throws Exception {
        try {
            ResponseVo responseVo = new ResponseVo();
            if (id <= 0 || type <= 0 || ArticleItemType.getByType(type) == null) {
                return new ResponseVo(ResponseStatus.INVALID_DATA);
            }
            ArticleItemVO articleItemVO = articleService.getArticleItemDetailById(id, type);
            if (articleItemVO == null) {
                return new ResponseVo(ResponseStatus.NOT_FOUND);
            }
            responseVo.setData(articleItemVO);
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
        model.addAttribute("articleStautsList", ArticleStatus.values());
        model.addAttribute("article", articleVO);
        return "/system/article/articleEdit";
    }

    /**
     * 根据文章ID修改文章
     *
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String edit(ArticleVO articleVO) throws Exception {
        CheckResult checkResult = ArticleChecker.checkArticleVO(articleVO);
        ArticleVO vo = null;
        if (checkResult.isSuccess()) {
            vo = ArticleConverter.convertToArticleVO(articleVO);
        } else {
            throw new BaseException(checkResult.getMsg());
        }
        ResourceResult<Boolean> result = articleService.modify(vo);
        if (result.isSuccess()) {
            return "/success";
        } else {
            throw new BaseException(result.getMsg());
        }
    }

    /**
     * 文章下架
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/violation/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo publish(@PathVariable("id") long id) throws Exception {
        ResponseVo responseVo = new ResponseVo();
        ResourceResult<Boolean> result = articleService.violation(id);
        if (result.isSuccess()) {
            responseVo.setMessage("文章下架成功！");
            responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
        } else {
            responseVo.setMessage(result.getMsg());
            responseVo.setStatus(ResponseStatus.ERROR.VALUE);
        }

        return responseVo;
    }

    /**
     * 文章上架
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/regain/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo close(@PathVariable("id") long id) throws Exception {
        ResponseVo responseVo = new ResponseVo();
        ResourceResult<Boolean> result = articleService.regain(id);
        if (result.isSuccess()) {
            responseVo.setMessage("文章上架成功！");
            responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
        } else {
            responseVo.setStatus(ResponseStatus.ERROR.VALUE);
            responseVo.setMessage(result.getMsg());
        }

        return responseVo;
    }

    /**
     * 文章下架(批量)
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/batchViolation", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo batchViolation(@RequestParam("articleIdList[]") ArrayList<Long> articleIdList) throws Exception {
        ResponseVo responseVo = new ResponseVo();
        ResourceResult<Boolean> result = articleService.batchViolation(articleIdList, ArticleStatus.OFFLINE);
        if (result.isSuccess()) {
            responseVo.setMessage("添加成功！");
            responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
        } else {
            responseVo.setMessage(result.getMsg());
            responseVo.setStatus(ResponseStatus.ERROR.VALUE);
        }

        return responseVo;
    }
    
//    @RequestMapping(value="queryAudioListPage",method=RequestMethod.POST)
//    @ResponseBody
//    public ResponseVo queryAudioListPage(MediaPageQuery mediaPageQuery) {
//    	ResponseVo responseVo = new ResponseVo();
//    	if (mediaPageQuery == null || mediaPageQuery.getPageNo() <=0 || mediaPageQuery.getPageSize() <= 0) {
//			return new ResponseVo(ResponseStatus.INVALID_DATA);
//		}
//    	try {
//			BizPageResult<AudioVO> audioArticleListPage = articleService.getAudioArticleListPage(mediaPageQuery);
//			if (audioArticleListPage == null || (audioArticleListPage != null && CollectionUtils.isEmpty(audioArticleListPage.getList()))) {
//				return new ResponseVo(ResponseStatus.NOT_FOUND);
//				
//			}
//			responseVo.setData(audioArticleListPage);
//			return responseVo;
//		} catch (Exception e) {
//			log.error("param:MediaPageQuery={},error:{}",JSON.toJSONString(mediaPageQuery),e);
//			return new ResponseVo(ResponseStatus.ERROR);
//		}
//    }
}
