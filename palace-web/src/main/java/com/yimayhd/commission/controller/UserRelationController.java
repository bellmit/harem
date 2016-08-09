package com.yimayhd.commission.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commission.biz.LevelBiz;
import com.yimayhd.commission.biz.UserRelationBiz;
import com.yimayhd.marketing.client.model.domain.LevelDO;
import com.yimayhd.marketing.client.model.enums.DomainType;
import com.yimayhd.marketing.client.model.query.LevelQuery;
import com.yimayhd.marketing.client.model.query.UserRelationQuery;
import com.yimayhd.marketing.client.model.result.SpmResult;
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
	
	@Autowired
	private LevelBiz levelBiz;
	
	@RequestMapping(value="/queryList")
	public String hierarchyListPage(Model model,UserRelationQuery query){
		
		try{
			if(query == null){
				query = new UserRelationQuery();
			}
			int pageNumber = 0;
			long domainId = DomainType.DOMAIN_MYTHIC_FLOW.getDomainId(); 
			try{
				pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			}catch(Exception e){
				pageNumber = 1;
			}
			query.setPageNo(pageNumber);
			if(query.getPageSize() <= 0){
				query.setPageSize(BaseQuery.DEFAULT_SIZE);
			}
			String level = request.getParameter("level");
			long levelId = 0;
			try{
				levelId = Long.parseLong(level);
				query.setLevelId(levelId);
			}catch(Exception e){
				query.setLevelId(0);
			}
			query.setDomainId(domainId);
			
			PageVO<HierarchyResult> pageVo = userRelationBiz.queryList(query);
			model.addAttribute("pageVo", pageVo);
			model.addAttribute("userRelationQuery", query);
			
			LevelQuery levelQuery = new LevelQuery();
			levelQuery.setDomainId(domainId);
			SpmResult<List<LevelDO>> levelResult = levelBiz.getAll(levelQuery);
			if(levelResult != null && levelResult.isSuccess() && !CollectionUtils.isEmpty(levelResult.getT()) ){
				model.addAttribute("levelList", levelResult.getT());
			}
			
			return "/system/commission/hierarchyList";
		}catch(Exception e){
			logger.error("UserRelationController.hierarchyListPage exceptions occur,param:{},ex:{}",
					JSON.toJSONString(query), e);
			return "/error";
		}
	}
}
