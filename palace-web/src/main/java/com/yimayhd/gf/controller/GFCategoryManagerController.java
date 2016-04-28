package com.yimayhd.gf.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.CategoryDO;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.gf.biz.GFCategoryBiz;
import com.yimayhd.gf.model.query.GFCategoryVo;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.constant.ResponseStatus;

/**
 * @author : zhangchao
 * @date : 2016年4月28日 上午10:36:54
 * @Description: GF商品品类管理
 */
@Controller
@RequestMapping("/GF/CategoryManager")
public class GFCategoryManagerController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GFCategoryManagerController.class);
	
	@Autowired
	private GFCategoryBiz gfCategoryBiz;
	
	
	/**
	 * @author : zhangchao
	 * @date : 2016年4月28日 上午10:47:20
	 * @Description: 商品品类一级列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String gfCategoryList(Model model){
		try {
			BaseResult<List<CategoryDO>> baseResult = gfCategoryBiz.getCategoryList();
			model.addAttribute("themeList", baseResult.getValue());
			return "/system/gfCategory/list";
		} catch (Exception e) {
			return "/error";
		}
	}
	
	/**
	 * @author : zhangchao
	 * @date : 2016年4月28日 下午3:01:03
	 * @Description: 添加商品品类跳转
	 */
	@RequestMapping(value = "/toAddCategory", method = RequestMethod.GET)
	public String toAddCategory(){
		return "/system/gfCategory/edit";
	}
	
	/**
	 * @author : zhangchao
	 * @date : 2016年4月28日 下午3:02:15
	 * @Description: 添加商品品类提交form
	 */
	@RequestMapping(value = "/addCategory", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo addCategory(GFCategoryVo gfCategoryVo,ModelMap modelMap){
		try {
			BaseResult<CategoryDO> baseResult = gfCategoryBiz.saveCategoryDO(gfCategoryVo);
			
			if(baseResult.isSuccess()){
				return new ResponseVo();
			}else{
				return new ResponseVo(ResponseStatus.ERROR);
			}
		} catch (Exception e) {
			LOGGER.error("addCategory:gfCategoryVo={}",JSON.toJSONString(gfCategoryVo), e);
			return new ResponseVo(ResponseStatus.ERROR);
		}
	}
	
	/**
	 * @author : zhangchao
	 * @date : 2016年4月28日 下午3:04:07
	 * @Description: 修改商品品类跳转查询
	 */
	@RequestMapping(value = "/toEditCategory/{id}", method = RequestMethod.GET)
	public String toEditCategory(Model model, @PathVariable(value = "id") long id){
		try {
			BaseResult<CategoryDO> baseResult = gfCategoryBiz.getCategoryById(id);
			model.addAttribute("theme", baseResult.getValue());
			return "/system/gfCategory/edit";
		} catch (Exception e) {
			LOGGER.error("toEditCategory:id={}",id, e);
			return "/error";
		}
	}
	
	/**
	 * @author : zhangchao
	 * @date : 2016年4月28日 下午3:06:03
	 * @Description: 修改商品品类的提交
	 */
	@RequestMapping(value = "/editCategory/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo editCategory(ModelMap model,@PathVariable(value = "id") long id,GFCategoryVo gfCategoryVo){
		try {
			if(id<=0){
				return new ResponseVo(ResponseStatus.ERROR);
			}
			gfCategoryVo.setId(id);
			BaseResult<CategoryDO> baseResult = gfCategoryBiz.updateCategory(gfCategoryVo);
			if(baseResult.isSuccess()){
				return new ResponseVo();
			}else{
				return new ResponseVo(ResponseStatus.ERROR);
			}
		} catch (Exception e) {
			LOGGER.error("editCategory==>gfCategoryVo={}",JSON.toJSONString(gfCategoryVo), e);
			return new ResponseVo(ResponseStatus.ERROR);
		}
	}
	
	
	
}
