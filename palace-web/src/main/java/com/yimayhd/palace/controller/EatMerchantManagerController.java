package com.yimayhd.palace.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.model.EatMerchantVO;
import com.yimayhd.palace.model.query.EatMerchantListQuery;
import com.yimayhd.palace.service.EatMerchantService;

/**
 * 
 * ClassName: EatMerchantManagerController <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason: TODO ADD REASON(可选). <br/>  
 * date: 2016年3月24日 上午10:47:11 <br/>  
 * 美食商家相关管理
 * @author zhangjian  
 * @version
 */

@RestController
@RequestMapping("/B2C/eatMerchantManage")
public class EatMerchantManagerController extends BaseController{
	@Autowired
	private EatMerchantService eatMerchantService;
	
	@RequestMapping("/toAddEatMerchant")
	public String toAddMerchant(){
		return "/system/eatMerchant/addEatMerchant";
	}
	
	@RequestMapping("/addEatMerchant")
	public ResponseVo addEatMerchant(EatMerchantVO eatMerchantVO){
		//判断必填字段
		
		//增加merchant
		eatMerchantService.addEatMerchant(eatMerchantVO);
		//
		return new ResponseVo();
		
	}
	
	@RequestMapping("/updateEatMerchant")
	public ResponseVo updateEatMerchant(EatMerchantVO eatMerchantVO){
		//判断必填字段
		
		//
		eatMerchantService.updateEatMerchant(eatMerchantVO);
		
		return new ResponseVo();
	}
	
	@RequestMapping("/queryEatMerchantList")
	public PageVO<EatMerchantVO> queryEatMerchantList(EatMerchantListQuery eatMerchantQuery){
		//判断必填字段
		
		
		//
		PageVO<EatMerchantVO> pageVO = eatMerchantService.queryEatMerchantList(eatMerchantQuery);
		
		return pageVO;
		
	}
	
	@RequestMapping("/batchDeleteEatMerchant")
	public ResponseVo batchDeleteEatMerchant(Long [] idsList){
		//参数校验
		
		List<Long> eatMerchantIds = new ArrayList<Long>();
		eatMerchantIds = Arrays.asList(idsList);
		
		eatMerchantService.batchDeleteEatMerchant(eatMerchantIds);
		
		return new ResponseVo();
	}
	
	@RequestMapping("/batchOfflineEatMerchant")
	public ResponseVo batchOfflineEatMerchant(Long [] idsList){
		List<Long> eatMerchantIds = new ArrayList<Long>();
		eatMerchantIds = Arrays.asList(idsList);
		
		eatMerchantService.batchOfflineEatMerchant(eatMerchantIds);
		
		return new ResponseVo();
	}
	
	
}
