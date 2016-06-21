package com.yimayhd.gf.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.gf.biz.GFCorrelationBiz;
import com.yimayhd.gf.model.CorrelationResultVO;
import com.yimayhd.gf.model.CorrelationVO;
import com.yimayhd.gf.model.query.GFCategoryVo;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.constant.ResponseStatus;
import com.yimayhd.palace.model.ItemVO;
import com.yimayhd.palace.model.query.CommodityListQuery;
import com.yimayhd.resourcecenter.model.result.RcResult;

@Controller
@RequestMapping("/GF/correlationManager")
public class GFCorrelationManagerConttroller {

	private static final Logger LOGGER = LoggerFactory.getLogger(GFCorrelationManagerConttroller.class);
	
	@Autowired
	private GFCorrelationBiz correlationBiz;
	
	@RequestMapping(value = "/findItem/{id}", method = RequestMethod.GET)
	public String findProduct(Model model, @PathVariable(value = "id") long id){
		try {
			//查询所有商品
			List<ItemVO> itemVOList = correlationBiz.getRecommendList(id);
			model.addAttribute("masterItem", itemVOList.get(0));
			if(itemVOList.size()>1){
				model.addAttribute("themeItemList", itemVOList.subList(1, itemVOList.size()));
			}
			return "/system/gfCorrelation/showList";
		} catch (Exception e) {
			LOGGER.error("findProduct:id=",id, e);
			return "/error";
		}
	}
	
	@RequestMapping("/toAddItem/{itemId}")
	public String toAddItem(Model model, @PathVariable(value = "itemId") long itemId,CommodityListQuery commodityListQuery){
		try {
			CorrelationResultVO correlationResultVO = correlationBiz.getItemList(commodityListQuery,itemId);
			model.addAttribute("pageVo", correlationResultVO.getPageVO());
			model.addAttribute("themeItemList", correlationResultVO.getPageVO().getItemList());
			model.addAttribute("commodityListQuery", commodityListQuery);
			model.addAttribute("id", itemId);
			model.addAttribute("masterItem", correlationResultVO.getMasterItemVO());
			model.addAttribute("recommendItem", correlationResultVO.getRecommendDTO());
			
			return "/system/gfCorrelation/addList";
		} catch (Exception e) {
			LOGGER.error("toAddItem id=",itemId, e);
			return "/error";
		}
	}
	
	@RequestMapping(value = "/batchBindProduct/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo batchBindProduct(@PathVariable(value = "id") long id,@RequestParam("themeIdList[]") ArrayList<Long> themeIdList) throws Exception {
		try {
			
			CorrelationVO correlationVO = new CorrelationVO();
			correlationVO.setMasterId(id);
			correlationVO.setRecommendIds(themeIdList);
			RcResult<Boolean> batchInsertItemResult = correlationBiz.batchInsertItem(correlationVO );
			if(batchInsertItemResult.isSuccess()){
				return new ResponseVo();
			}else{
				return new ResponseVo(ResponseStatus.ERROR);
			}
		} catch (Exception e) {
			LOGGER.error("themeIdList={}",JSON.toJSONString(themeIdList), e);
			return new ResponseVo(ResponseStatus.ERROR);
		}
	}
	
	@RequestMapping("/batchDelItem")
	@ResponseBody
	public ResponseVo batchDelItem(@RequestParam("themeIdList[]") ArrayList<Long> themeIdList) throws Exception {
		try {
			
			RcResult<Boolean> batchDelRecommendResult = correlationBiz.batchDelRecommend(themeIdList);
			if(batchDelRecommendResult.isSuccess()){
				return new ResponseVo();
			}else{
				return new ResponseVo(ResponseStatus.ERROR);
			}
		} catch (Exception e) {
			LOGGER.error("themeIdList={}",JSON.toJSONString(themeIdList), e);
			return new ResponseVo(ResponseStatus.ERROR);
		}
	}
}
