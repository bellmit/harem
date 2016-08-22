package com.yimayhd.commission.biz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commission.repo.UserRelationRepo;
import com.yimayhd.marketing.client.model.query.UserRelationQuery;
import com.yimayhd.marketing.client.model.result.SpmPageResult;
import com.yimayhd.marketing.client.model.result.userrelation.HierarchyResult;
import com.yimayhd.palace.base.PageVO;

/**
 * User: fanyb
 * Date: 2016/2/16
 */
public class UserRelationBiz {
	
	private static final Logger logger = LoggerFactory.getLogger(UserRelationBiz.class);
	
	@Autowired
	private UserRelationRepo userRelationRepo;
	
	public PageVO<HierarchyResult> queryList(UserRelationQuery query){
		logger.info("UserRelationBiz.queryList begin,param:" + JSON.toJSONString(query));
		PageVO<HierarchyResult> pageVo = null;
		try{
			int totalCount = 0;
			int pageNo = query.getPageNo();
			int pageSize = query.getPageSize();
			
			SpmPageResult<HierarchyResult> remoteResult = userRelationRepo.queryList(query);
			if(remoteResult == null || !remoteResult.isSuccess()){
				logger.error("UserRelationBiz.queryList get unexpected result,param:{},result:{}",
						JSON.toJSONString(query), JSON.toJSONString(remoteResult));
				pageVo = new PageVO<HierarchyResult>(pageNo, pageSize, totalCount);
				return pageVo;
			}
			if(CollectionUtils.isEmpty(remoteResult.getList())){
				pageVo = new PageVO<HierarchyResult>(pageNo, pageSize, totalCount);
			}else{
				pageVo = new PageVO<HierarchyResult>(pageNo, pageSize, remoteResult.getTotalCount(), remoteResult.getList());
			}
		}catch(Exception e){
			logger.error("UserRelationBiz.queryList exceptions occur,param:{},ex:{}",
					JSON.toJSONString(query), e);
		}
		return pageVo;
	}
	
}
