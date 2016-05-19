package com.yimayhd.gf.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.CategoryDO;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.result.CategoryResult;
import com.yimayhd.gf.biz.GFCategoryBiz;
import com.yimayhd.gf.model.query.GFCategoryVo;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.constant.ResponseStatus;
import com.yimayhd.palace.model.ItemVO;
import com.yimayhd.palace.model.query.CommodityListQuery;

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
	public String gfCategoryList(Model model,GFCategoryVo gfCategoryVo){
		try {
			PageVO<CategoryResult> pageVo = gfCategoryBiz.pageQueryCategory(gfCategoryVo);
			model.addAttribute("themeList", pageVo.getItemList());
			model.addAttribute("pageVo", pageVo);
			return "/system/gfCategory/list";
		} catch (Exception e) {
			return "/error";
		}
	}
	
	/**
	 * @author : zhangchao
	 * @date : 2015年12月30日 下午3:51:13
	 * @Description: 品类查询
	 */
	@RequestMapping(value = "/listSearch", method = RequestMethod.POST)
	public String listSearch(Model model,GFCategoryVo gfCategoryVo){
		try {
			PageVO<CategoryResult> pageVo = gfCategoryBiz.pageQueryCategory(gfCategoryVo);
			model.addAttribute("GFCategoryVo", gfCategoryVo);
			model.addAttribute("pageVo", pageVo);
			model.addAttribute("themeList", pageVo.getItemList());
			return "/system/gfCategory/list";
		} catch (Exception e) {
			LOGGER.error("gfTagList:gfTagVoQuery={}",JSON.toJSONString(gfCategoryVo), e);
			return "/error";
		}
	}
	/*@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String gfCategoryList(Model model,GFCategoryVo gfCategoryVo){
		try {
			BasePageResult<CategoryDO> baseResult = gfCategoryBiz.pageQueryCategory();
			
			List<CategoryDO> categoryList = baseResult.getList();
			
			ArrayList<Map<Object,Object>> arrayList = new ArrayList<Map<Object,Object>>();
			
			for (int i = 0; i < categoryList.size(); i++) {
				Map<Object, Object> map = new HashMap<Object,Object>();
				CategoryDO categoryDO = categoryList.get(i);
				map.put("id", categoryDO.getId());
				map.put("pId", categoryDO.getParentId());
				map.put("name", categoryDO.getName());
				if(categoryDO.getParentId()==0){
					map.put("open", true);
				}
				arrayList.add(map);
			}
			String jsonObject = JSONObject.toJSONString(arrayList);
			LOGGER.info("jsonObject:"+jsonObject);
//			System.out.println("jsonObject:"+jsonObject);
			model.addAttribute("themeList", jsonObject);
			return "/system/gfCategory/list";
		} catch (Exception e) {
			return "/error";
		}
	}*/
	
	/**
	 * @author : zhangchao
	 * @date : 2016年4月28日 下午3:01:03
	 * @Description: 添加商品品类跳转
	 */
	@RequestMapping(value = "/toAddCategory", method = RequestMethod.GET)
	public String toAddCategory(ModelMap modelMap,GFCategoryVo gfCategoryVo){
		//查询所有列表
		BaseResult<List<CategoryDO>> baseResult = gfCategoryBiz.getPrimaryCategoryList();
		
		List<CategoryDO> categoryList = baseResult.getValue();
		modelMap.put("categoryList", categoryList);
		
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
	public String toEditCategory(Model model, @PathVariable(value = "id") long id,ModelMap modelMap){
		try {
			
			//查询所有列表
			BaseResult<List<CategoryDO>> baseResult = gfCategoryBiz.getPrimaryCategoryList();
			List<CategoryDO> categoryList = baseResult.getValue();
			modelMap.addAttribute("categoryList", categoryList);
			
			BaseResult<CategoryDO> baseResult2 = gfCategoryBiz.getCategoryById(id);
			model.addAttribute("theme", baseResult2.getValue());
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
	
	/*===================================  华丽的分割线  ===============================*/
	
	/**
	 * @author : zhangchao
	 * @date : 2016年5月10日 下午2:48:29
	 * @Description: 添加绑定的商品跳转
	 */
	@RequestMapping(value = "/toAddProduct/{id}/{parentId}", method = RequestMethod.GET)
	public String toAddProduct(Model model, @PathVariable(value = "id") long id,@PathVariable(value = "parentId") long parentId,CommodityListQuery commodityListQuery){
		try {
			//获取绑定商品的品类ID
			commodityListQuery.setCategory_id(id);
			//查询所有商品
			PageVO<ItemVO> pageVO = gfCategoryBiz.getItemList(commodityListQuery);
			List<ItemType> itemTypeList = Arrays.asList(ItemType.values());
			model.addAttribute("pageVo", pageVO);
			model.addAttribute("itemTypeList", itemTypeList);
			model.addAttribute("themeItemList", pageVO.getItemList());
			model.addAttribute("commodityListQuery", commodityListQuery);
			model.addAttribute("id", id);
			model.addAttribute("parentId", parentId);
			return "/system/gfCategory/pro_add_list";
		} catch (Exception e) {
			LOGGER.error("toEditCategory:id={}",id, e);
			return "/error";
		}
	}
	
	/**
	 * @author : zhangchao
	 * @date : 2016年5月10日 下午3:55:55
	 * @Description: 商品查询
	 */
	@RequestMapping(value = "/listProSearch/{id}", method = RequestMethod.POST)
	public String listProSearch(Model model, @PathVariable(value = "id") long id,CommodityListQuery commodityListQuery){
		try {
			//查询所有商品
			PageVO<ItemVO> pageVO = gfCategoryBiz.getItemList(commodityListQuery);
			model.addAttribute("themeItemList", pageVO.getItemList());
			model.addAttribute("commodityListQuery", commodityListQuery);
			model.addAttribute("id", id);
			model.addAttribute("pageVo", pageVO);
			return "/system/gfCategory/pro_add_list";
		} catch (Exception e) {
			LOGGER.error("toEditCategory:id={}",id, e);
			return "/error";
		}
	}
	
	/**
	 * @author : zhangchao
	 * @date : 2016年5月12日 下午4:27:00
	 * @Description: 商品绑定操作  包括批量
	 * 
	 */
	@RequestMapping(value = "/batchBoundProduct/{id}/{parentId}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo batchBoundProduct(@PathVariable(value = "id") long id,@PathVariable(value = "parentId") int parentId,@RequestParam("themeIdList[]") ArrayList<Long> themeIdList,GFCategoryVo gfCategoryVo) throws Exception {
		try {
			gfCategoryVo.setId(id);
			gfCategoryVo.setParentId(parentId);
			gfCategoryVo.setItemIdList(themeIdList);
			BaseResult<Boolean> baseResult = gfCategoryBiz.batchBoundProduct(gfCategoryVo);
			if(baseResult.isSuccess()){
				return new ResponseVo();
			}else{
				return new ResponseVo(ResponseStatus.ERROR);
			}
		} catch (Exception e) {
			LOGGER.error("themeIdList={}",JSON.toJSONString(themeIdList), e);
			return new ResponseVo(ResponseStatus.ERROR);
		}
	}
	
	
	/**
	 * @author : zhangchao
	 * @date : 2016年5月16日 下午3:07:36
	 * @Description: 批量移除商品管理
	 */
	@RequestMapping(value = "/batchDelProduct/{id}/{parentId}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo batchDelProduct(@PathVariable(value = "id") long id,@PathVariable(value = "parentId") int parentId,@RequestParam("themeIdList[]") ArrayList<Long> themeIdList,GFCategoryVo gfCategoryVo) throws Exception {
		try {
			gfCategoryVo.setId(id);
			gfCategoryVo.setParentId(parentId);
			gfCategoryVo.setItemIdList(themeIdList);
			BaseResult<Boolean> baseResult = gfCategoryBiz.batchDelProduct(gfCategoryVo);
			if(baseResult.isSuccess()){
				return new ResponseVo();
			}else{
				return new ResponseVo(ResponseStatus.ERROR);
			}
		} catch (Exception e) {
			LOGGER.error("themeIdList={}",JSON.toJSONString(themeIdList), e);
			return new ResponseVo(ResponseStatus.ERROR);
		}
	}
	
	
	/**
	 * @author : zhangchao
	 * @date : 2016年5月16日 下午2:48:57
	 * @Description: 查看绑定商品
	 */
	@RequestMapping(value = "/findBoundProduct/{id}/{parentId}", method = RequestMethod.GET)
	public String findBoundProduct(Model model, @PathVariable(value = "id") long id,@PathVariable(value = "parentId") long parentId,CommodityListQuery commodityListQuery){
		try {
			//查询所有商品
			commodityListQuery.setId(id);
			PageVO<ItemVO> pageVO = gfCategoryBiz.getCategoryRelationList(commodityListQuery);
			model.addAttribute("themeItemList", pageVO.getItemList());
			model.addAttribute("commodityListQuery", commodityListQuery);
			model.addAttribute("id", id);
			model.addAttribute("parentId", parentId);
			model.addAttribute("pageVo", pageVO);
			return "/system/gfCategory/pro_del_list";
		} catch (Exception e) {
			LOGGER.error("toEditCategory:id={}",id, e);
			return "/error";
		}
	}
	
	
	
}
