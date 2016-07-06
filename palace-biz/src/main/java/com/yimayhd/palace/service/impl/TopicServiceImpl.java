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
import com.yimayhd.palace.model.SnsSugTopicVO;
import com.yimayhd.palace.model.TopicInfoVO;
import com.yimayhd.palace.model.TopicVO;
import com.yimayhd.palace.model.query.TopicListQuery;
import com.yimayhd.palace.repo.TopicRepo;
import com.yimayhd.palace.service.TopicService;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.snscenter.client.domain.SnsSugTopicDO;
import com.yimayhd.snscenter.client.domain.SnsTopicDO;
import com.yimayhd.snscenter.client.dto.topic.TopicInfoAddDTO;
import com.yimayhd.snscenter.client.dto.topic.TopicInfoUpdateDTO;
import com.yimayhd.snscenter.client.dto.topic.TopicQueryDTO;
import com.yimayhd.snscenter.client.dto.topic.TopicQueryListDTO;
import com.yimayhd.snscenter.client.dto.topic.TopicSetDTO;
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
			pageQuery.setTitle(topicListQuery.getTitle());			
		}
		//景区状态
		if (StringUtils.isNotBlank(topicListQuery.getStatus())) {			
			pageQuery.setStatus(Integer.parseInt(topicListQuery.getStatus()));
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
			log.error("topicRepo.getTopicPageList return value is null !returnValue :"
					+ JSON.toJSONString(pageResult));
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
	public TopicVO addTopic(TopicInfoVO topicInfoVO) throws Exception {
		
		//判断景区是否存在
		this.judgeExist(topicInfoVO);
		
		TopicInfoAddDTO topicInfoAddDTO = TopicInfoVO.getTopicInfoAddDTO(topicInfoVO);
		BaseResult<SnsTopicDO> addResult = topicRepo.addTopic(topicInfoAddDTO);
		
		if(null == addResult){
			log.error("TopicServiceImpl.addTopic-topicRepo.addTopic result is null and parame: " + JSON.toJSONString(topicInfoAddDTO));
			throw new BaseException("修改返回结果为空,修改失败");
		} else if(!addResult.isSuccess()){
			log.error("TopicServiceImpl.addTopic-topicRepo.addTopic error:" + JSON.toJSONString(addResult) + "and parame: " + JSON.toJSONString(topicInfoAddDTO));
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
		boolean ret = false;
		Boolean value = result.getValue();
		if(value != null){
			ret = value.booleanValue();
		}
		return ret;
	}
	
	@Override
	public boolean setTopic(List<Long> idList, int status) throws Exception {
		
		TopicSetDTO topicSetDTO = new TopicSetDTO();
		topicSetDTO.setIdList(idList);
		topicSetDTO.setStatus(status);
		BaseResult<Boolean> result = topicRepo.setTopic(topicSetDTO);
		if(null == result){
			log.error("TopicServiceImpl.setTopic-topicRepo.setTopic result is null and parame: " + JSON.toJSONString(topicSetDTO));
			throw new BaseException("修改返回结果为空,修改失败");
		} else if(!result.isSuccess()){
			log.error("TopicServiceImpl.setTopic-topicRepo.setTopic error:" + JSON.toJSONString(result) + "and parame: " + JSON.toJSONString(topicSetDTO));
			throw new BaseException(result.getResultMsg());
		}
		boolean ret = false;
		Boolean value = result.getValue();
		if(value != null){
			ret = value.booleanValue();
		}
		return ret;
	}

	@Override
	public List<SnsSugTopicVO> getSugTopicList() throws Exception {
		
		BaseResult<List<SnsSugTopicDO>> result = topicRepo.getSugTopicList();
		if(null == result){
			log.error("TopicServiceImpl.getSugTopicList-topicRepo.getSugTopicList result is null");
			throw new BaseException("修改返回结果为空,修改失败");
		} else if(!result.isSuccess()){
			log.error("TopicServiceImpl.getSugTopicList-topicRepo.getSugTopicList error:" + JSON.toJSONString(result));
			throw new BaseException(result.getResultMsg());
		}
		
		List<SnsSugTopicDO> list = result.getValue();
		if(list == null){
			list = new ArrayList<SnsSugTopicDO>();
		}
		return SnsSugTopicVO.getSugTopicVOList(list);
	}
	
	public void judgeExist(TopicInfoVO topicInfoVO) throws Exception{
		
		if(topicInfoVO == null){
			return;
		}
		
		TopicListQuery query = new TopicListQuery();
		query.setTitle(query.getTitle());
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
