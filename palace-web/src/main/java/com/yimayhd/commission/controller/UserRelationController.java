package com.yimayhd.commission.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commission.biz.UserRelationBiz;
import com.yimayhd.marketing.client.model.enums.DomainType;
import com.yimayhd.marketing.client.model.query.UserRelationQuery;
import com.yimayhd.marketing.client.model.result.userrelation.HierarchyResult;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.BaseQuery;
import com.yimayhd.palace.base.PageVO;

/**
 * User: fanyb
 * Date: 2016/2/16
 * 用户上下级关系
 */
@Controller
@RequestMapping(value = "/userRelation")
public class UserRelationController  extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserRelationController.class);
	
	@Autowired
	private UserRelationBiz userRelationBiz;
	
	@RequestMapping(value="/queryList")
	public String hierarchyListPage(Model model,UserRelationQuery query){
		
		try{
			if(query == null){
				query = new UserRelationQuery();
			}
			int pageNumber = 0;
			try{
				pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			}catch(Exception e){
				pageNumber = 1;
			}
			query.setPageNo(pageNumber);
			if(query.getPageSize() <= 0){
				query.setPageSize(BaseQuery.DEFAULT_SIZE);
			}
			query.setDomainId(DomainType.DOMAIN_MYTHIC_FLOW.getDomainId());
			
			PageVO<HierarchyResult> pageVo = userRelationBiz.queryList(query);
			model.addAttribute("pageVo", pageVo);
			model.addAttribute("userRelationQuery", query);
			return "/system/commission/hierarchyList";
		}catch(Exception e){
			logger.error("UserRelationController.hierarchyListPage exceptions occur,param:{},ex:{}",
					JSON.toJSONString(query), e);
			return "/error";
		}
	}
}
