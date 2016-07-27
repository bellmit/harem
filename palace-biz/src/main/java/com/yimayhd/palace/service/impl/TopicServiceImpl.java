package com.yimayhd.palace.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.model.SugTopicVO;
import com.yimayhd.palace.model.TopicInfoVO;
import com.yimayhd.palace.model.TopicVO;
import com.yimayhd.palace.model.query.SugTopicListQuery;
import com.yimayhd.palace.model.query.TopicListQuery;
import com.yimayhd.palace.repo.TopicRepo;
import com.yimayhd.palace.service.TopicService;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.snscenter.client.domain.SnsSugTopicDO;
import com.yimayhd.snscenter.client.domain.SnsTopicDO;
import com.yimayhd.snscenter.client.dto.topic.SugTopicQueryListDTO;
import com.yimayhd.snscenter.client.dto.topic.TopicInfoUpdateDTO;
import com.yimayhd.snscenter.client.dto.topic.TopicQueryDTO;
import com.yimayhd.snscenter.client.dto.topic.TopicQueryListDTO;
import com.yimayhd.snscenter.client.enums.TopicStatus;
import com.yimayhd.snscenter.client.result.BasePageResult;
import com.yimayhd.snscenter.client.result.BaseResult;
import com.yimayhd.snscenter.client.result.topic.TopicResult;

/**
 * Created by hongfei.guo on 2015/11/18.
 */
public class TopicServiceImpl implements TopicService {
	private static final Logger log = LoggerFactory.getLogger(TopicServiceImpl.class);
	
	@Autowired
    private TopicRepo topicRepo ;
	
	@Override
	public PageVO<TopicVO> getTopicPageList(TopicListQuery topicListQuery) throws Exception {
		
		TopicQueryListDTO pageQuery = new TopicQueryListDTO();
		if(topicListQuery.getPageNumber() != null){
			int pageNumber =topicListQuery.getPageNumber();
			int pageSize = topicListQuery.getPageSize();
			pageQuery.setPageNo(pageNumber);
			pageQuery.setPageSize(pageSize);
		}
		
		//话题名称
		if (StringUtils.isNotBlank(topicListQuery.getTitle())) {
			pageQuery.setTitle(topicListQuery.getTitle().trim());			
		}
		//景区状态
		if (StringUtils.isNotBlank(topicListQuery.getStatus())) {			
			pageQuery.setStatus(Integer.parseInt(topicListQuery.getStatus()));
		}else{
			pageQuery.setStatus(-1);
		}
		//是否有描述
		if (topicListQuery.getHasContent() != null) {			
			pageQuery.setHasContent(topicListQuery.getHasContent());
		}
		//开始时间
		if (StringUtils.isNotBlank(topicListQuery.getStartTime())) {
			Date startTime = DateUtil.parseDate(topicListQuery.getStartTime());
			pageQuery.setStartTime(startTime);
		}
		//结束时间
		if (StringUtils.isNotBlank(topicListQuery.getEndTime())) {
			Date endTime = DateUtil.parseDate(topicListQuery.getEndTime());
			pageQuery.setEndTime(DateUtil.add23Hours(endTime));
		}
		
		pageQuery.setNeedCount(true);
		
		PageVO<TopicResult> pageResult = topicRepo.getTopicPageList(pageQuery);
		
		List<TopicVO> itemList = new ArrayList<TopicVO>();
		int totalCount = 0;
		if (pageResult != null) {
			totalCount = pageResult.getTotalCount();
			List<TopicResult> resultItemList = pageResult.getItemList();
			if (CollectionUtils.isNotEmpty(resultItemList)) {
				itemList = TopicVO.getTopicVOList(resultItemList);	
			}
		} else {
			log.error("topicRepo.getTopicPageList return value is null !returnValue :" + JSON.toJSONString(pageResult));
		}
		return new PageVO<TopicVO>(pageQuery.getPageNo(), pageQuery.getPageSize(), totalCount, itemList);
	}
	
	public TopicVO getTopicDetailInfo(long id) throws Exception {
		
		TopicQueryDTO query = new TopicQueryDTO();
		query.setId(id);
		
		BaseResult<SnsTopicDO> topicResult = topicRepo.getTopicDetailInfo(query);
		if(topicResult == null){
			log.error("TopicServiceImpl.getTopicDetailInfo-topicRepo.getTopicDetailInfo result is null and parame: " + id);
			throw new BaseException("返回结果为空，获取景区资源失败");
		}else if(!topicResult.isSuccess()){
			log.error("TopicServiceImpl.getTopicDetailInfo-topicRepo.getTopicDetailInfo error:" + JSON.toJSONString(topicResult) + "and parame: " + id);
			throw new BaseException("返回结果错误，获取景区资源失败，" + topicResult.getResultMsg());
		}	

		TopicVO topicVO = TopicVO.getTopicVO(topicResult.getValue());
		return topicVO;
	}

	@Override
	public TopicVO addTopic(TopicVO topicVO) throws Exception {
		
		//判断景区是否存在
		this.judgeExist(topicVO);
		
		SnsTopicDO topicDO = TopicVO.getTopicDO(topicVO);
		topicDO.setStatus(TopicStatus.UNAVAILABLE.getType());
		topicDO.setDomain(Constant.DOMAIN_JIUXIU);
		
		BaseResult<SnsTopicDO> addResult = topicRepo.addTopic(topicDO);
		
		if(null == addResult){
			log.error("TopicServiceImpl.addTopic-topicRepo.addTopic result is null and parame: " + JSON.toJSONString(topicDO));
			throw new BaseException("修改返回结果为空,修改失败");
		} else if(!addResult.isSuccess()){
			log.error("TopicServiceImpl.addTopic-topicRepo.addTopic error:" + JSON.toJSONString(addResult) + "and parame: " + JSON.toJSONString(topicDO));
			throw new BaseException(addResult.getResultMsg());
		}
		
		return TopicVO.getTopicVO(addResult.getValue());
	}
	
	@Override
	public boolean updateTopic(TopicInfoVO topicInfoVO) throws Exception {
		
		TopicInfoUpdateDTO topicInfoUpdateDTO = TopicInfoVO.getTopicInfoUpdateDTO(topicInfoVO);
		BaseResult<Boolean> updateResult = topicRepo.updateTopic(topicInfoUpdateDTO);
		
		if(null == updateResult){
			log.error("TopicServiceImpl.updateTopic-topicRepo.updateTopic result is null and parame: " + JSON.toJSONString(topicInfoUpdateDTO));
			throw new BaseException("修改返回结果为空,修改失败");
		} else if(!updateResult.isSuccess()){
			log.error("TopicServiceImpl.updateTopic-topicRepo.updateTopic error:" + JSON.toJSONString(updateResult) + "and parame: " + JSON.toJSONString(topicInfoUpdateDTO));
			throw new BaseException(updateResult.getResultMsg());
		}
		return updateResult.getValue();
	}
	
	@Override
	public boolean updateTopicStatus(long id, int type) throws Exception {
		
		BaseResult<Boolean> result = topicRepo.updateTopicStatus(id, type);
		if(null == result){
			log.error("TopicServiceImpl.updateTopicStatus-topicRepo.updateTopicStatus result is null and parame: " + id +" "+ type);
			throw new BaseException("修改返回结果为空,修改失败");
		} else if(!result.isSuccess()){
			log.error("TopicServiceImpl.updateTopicStatus-topicRepo.updateTopicStatus error:" + JSON.toJSONString(result) + "and parame: " + id +" "+ type);
			throw new BaseException(result.getResultMsg());
		}
		return result.getValue() != null ? result.getValue() : false;
	}
	
	@Override
	public boolean addSugTopic(Long id) throws Exception {
		
		if(id == null){
			return false;
		}
		
		boolean isExist = this.getSugTopicById(id);
		if(isExist){
			throw new BaseException(Constant.TOPIC_SUG_REPEAT);
		}
		
		List<Long> idList = new ArrayList<Long>();
		idList.add(id);
		
		BaseResult<Boolean> result = topicRepo.addSugTopic(idList);
		if(null == result){
			log.error("TopicServiceImpl.addSugTopic-topicRepo.addSugTopic result is null and parame: " + JSON.toJSONString(idList));
			throw new BaseException("修改返回结果为空,修改失败");
		} else if(!result.isSuccess()){
			log.error("TopicServiceImpl.addSugTopic-topicRepo.addSugTopic error:" + JSON.toJSONString(result) + "and parame: " + JSON.toJSONString(idList));
			throw new BaseException(result.getResultMsg());
		}
		return result.getValue() != null ? result.getValue() : false;
	}
	
	@Override
	public boolean removeSugTopic(Long id) throws Exception {
		
		if(id == null){
			return false;
		}
		
		List<Long> idList = new ArrayList<Long>();
		idList.add(id);
		
		BaseResult<Boolean> result = topicRepo.removeSugTopic(idList);
		if(null == result){
			log.error("TopicServiceImpl.removeSugTopic-topicRepo.removeSugTopic result is null and parame: " + JSON.toJSONString(idList));
			throw new BaseException("修改返回结果为空,修改失败");
		} else if(!result.isSuccess()){
			log.error("TopicServiceImpl.removeSugTopic-topicRepo.removeSugTopic error:" + JSON.toJSONString(result) + "and parame: " + JSON.toJSONString(idList));
			throw new BaseException(result.getResultMsg());
		}
		return result.getValue() != null ? result.getValue() : false;
	}

	@Override
	public PageVO<SugTopicVO> getSugTopicPageList(SugTopicListQuery sugTopicListQuery) throws Exception {
		
		SugTopicQueryListDTO pageQuery = new SugTopicQueryListDTO();
		if(sugTopicListQuery.getPageNumber() != null){
			int pageNumber =sugTopicListQuery.getPageNumber();
			int pageSize = sugTopicListQuery.getPageSize();
			pageQuery.setPageNo(pageNumber);
			pageQuery.setPageSize(pageSize);
		}
		pageQuery.setNeedCount(true);
		
		BasePageResult<SnsSugTopicDO> pageResult = topicRepo.getSugTopicPageList(pageQuery);
		
		List<SugTopicVO> itemList = new ArrayList<SugTopicVO>();
		int totalCount = 0;
		if (pageResult != null) {
			totalCount = pageResult.getTotalCount();
			List<SnsSugTopicDO> resultItemList = pageResult.getList();
			if (CollectionUtils.isNotEmpty(resultItemList)) {
				itemList = SugTopicVO.getSugTopicVOList(resultItemList);	
			}
		} else {
			log.error("topicRepo.getSugTopicPageList return value is null !returnValue :"
					+ JSON.toJSONString(pageResult));
		}
		return new PageVO<SugTopicVO>(pageQuery.getPageNo(), pageQuery.getPageSize(), totalCount, itemList);
	}
	
	@Override
	public boolean getSugTopicById(long id) throws Exception {
		
		BaseResult<SnsSugTopicDO> sugTopicResult = topicRepo.getSugTopicById(id);
		if(sugTopicResult == null){
			log.error("TopicServiceImpl.getSugTopicById-topicRepo.getSugTopicById result is null and parame: " + id);
			throw new BaseException("返回结果为空，获取景区资源失败");
		}else if(!sugTopicResult.isSuccess()){
			log.error("TopicServiceImpl.getSugTopicById-topicRepo.getSugTopicById error:" + JSON.toJSONString(sugTopicResult) + "and parame: " + id);
			throw new BaseException("返回结果错误，获取景区资源失败，" + sugTopicResult.getResultMsg());
		}	

		SnsSugTopicDO sugTopicDO = sugTopicResult.getValue();
		if(sugTopicDO != null && sugTopicDO.getId() != 0){
			return true;
		}
		return false;
	}
	
	public void judgeExist(TopicVO topicInfoVO) throws Exception{
		
		if(topicInfoVO == null){
			return;
		}
		
		TopicListQuery query = new TopicListQuery();
		query.setTitle(topicInfoVO.getTitle());
		PageVO<TopicVO> pageVo = this.getTopicPageList(query);
		if(pageVo == null){
			return;
		}
		
		List<TopicVO> list = pageVo.getItemList();
		if(list == null || list.size() == 0){
			return;
		}
		
		String title = topicInfoVO.getTitle().trim();
		
		TopicVO topicVO = null;
		for(int i = 0; i < list.size(); i++){
			topicVO = list.get(i);
			if(!title.equals(topicVO.getTitle().trim())){
				continue;
			}
			
			if(topicInfoVO.getId() != topicVO.getId()){
				throw new BaseException(Constant.TOPIC_REPEAT);
			}
		}
	}

	
}
