package com.yimayhd.palace.controller;

import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.service.item.CategoryService;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.model.ItemListQuery;
import com.yimayhd.palace.model.enums.ItemOperate;
import com.yimayhd.palace.model.item.ItemInfoVO;
import com.yimayhd.palace.service.ItemService;
import com.yimayhd.user.session.manager.SessionManager;

/**
 * 商品管理
 * 
 * @author yebin
 *
 */
@Controller
@RequestMapping("/jiuxiu/item")
public class ItemController extends BaseController {
	@Autowired
	private ItemService itemService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private SessionManager sessionManager;

	/**
	 * 商品列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String list(Model model, ItemListQuery itemListQuery) throws Exception {
		long sellerId = sessionManager.getUserId();
		if (sellerId <= 0) {
			log.warn("未登录");
			throw new BaseException("请登陆后重试");
		}
		PageVO<ItemInfoVO> pageVO = itemService.getItemList(itemListQuery);
		
		model.addAttribute("pageVo", pageVO);
		model.addAttribute("itemList", pageVO.getItemList());
		model.addAttribute("itemListQuery", itemListQuery);
		model.addAttribute("itemTypeList", ItemType.values());
		model.addAttribute("itemStatusList", ItemStatus.values());
		return "/system/comm/itemList";
	}
	
	@RequestMapping(value = "/{id}/operate")
	@ResponseBody 
	public ResponseVo opeate(@PathVariable("id") long id, @RequestParam("operate") String operate) throws Exception {
		long sellerId = sessionManager.getUserId();
		if (sellerId <= 0) {
			log.warn("未登录");
			throw new BaseException("请登陆后重试");
		}
		if (ItemOperate.SHELVE.name().equalsIgnoreCase(operate)) {
			itemService.shelve(sellerId, id);
		} else if (ItemOperate.UNSHELVE.name().equalsIgnoreCase(operate)) {
			itemService.unshelve(sellerId, id);
		} else if (ItemOperate.DELETE.name().equalsIgnoreCase(operate)) {
			itemService.delete(sellerId, id);
		}
		return new ResponseVo();
	}
	
	@RequestMapping(value = "/batchOperate")
	@ResponseBody
	public ResponseVo batchPerate(@RequestParam("itemIds[]") Long[] itemIds, @RequestParam("operate") String operate) throws Exception {
		long sellerId = sessionManager.getUserId();
		if (sellerId <= 0) {
			log.warn("未登录");
			throw new BaseException("请登陆后重试");
		}
		if (ArrayUtils.isEmpty(itemIds)) {
			log.warn("itemIds is null");
			throw new BaseException("itemIds is null");
		}
		if (ItemOperate.SHELVE.name().equalsIgnoreCase(operate)) {
			itemService.batchShelve(sellerId, Arrays.asList(itemIds));
		} else if (ItemOperate.UNSHELVE.name().equalsIgnoreCase(operate)) {
			itemService.batchUnshelve(sellerId, Arrays.asList(itemIds));
		} else if (ItemOperate.DELETE.name().equalsIgnoreCase(operate)) {
			itemService.batchDelete(sellerId, Arrays.asList(itemIds));
		}
		return new ResponseVo();
	}
}
