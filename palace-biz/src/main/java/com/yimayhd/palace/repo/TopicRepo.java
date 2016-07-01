package com.yimayhd.palace.repo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.util.RepoUtils;
import com.yimayhd.snscenter.client.domain.SnsTopicDO;
import com.yimayhd.snscenter.client.dto.topic.TopicInfoAddDTO;
import com.yimayhd.snscenter.client.dto.topic.TopicInfoUpdateDTO;
import com.yimayhd.snscenter.client.dto.topic.TopicQueryDTO;
import com.yimayhd.snscenter.client.dto.topic.TopicQueryListDTO;
import com.yimayhd.snscenter.client.dto.topic.TopicSetDTO;
import com.yimayhd.snscenter.client.dto.topic.TopicUpdateStatusDTO;
import com.yimayhd.snscenter.client.result.BasePageResult;
import com.yimayhd.snscenter.client.result.BaseResult;
import com.yimayhd.snscenter.client.result.topic.TopicResult;
import com.yimayhd.snscenter.client.service.SnsTopicCenterService;

/**
 * 话题管理Repo
 * 
 * @author hongfei.guo
 *
 */
public class TopicRepo{

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected SnsTopicCenterService snsTopicCenterServiceRef;
	
	public PageVO<TopicResult> getTopicPageList(TopicQueryListDTO query) {
		RepoUtils.requestLog(log, "snsTopicCenterServiceRef.getTopicPageList", query);
		BasePageResult<TopicResult> pageResult = snsTopicCenterServiceRef.getTopicPageList(query);
		RepoUtils.resultLog(log, "snsTopicCenterServiceRef.getTopicPageList", pageResult);
		int totalCount = pageResult.getTotalCount();
		List<TopicResult> topicResultList = pageResult.getList();
		if (topicResultList == null) {
			topicResultList = new ArrayList<TopicResult>();
		}
		return new PageVO<TopicResult>(query.getPageNo(), query.getPageSize(), totalCount, topicResultList);

	}

	public BaseResult<SnsTopicDO> getTopicDetailInfo(TopicQueryDTO topicQueryDTO) {
		RepoUtils.requestLog(log, "snsTopicCenterServiceRef.getTopicDetailInfo", topicQueryDTO);
		BaseResult<SnsTopicDO> result = snsTopicCenterServiceRef.getTopicDetailInfo(topicQueryDTO);
		RepoUtils.resultLog(log, "snsTopicCenterServiceRef.getTopicDetailInfo", result);
		return result;
	}

	public BaseResult<SnsTopicDO> addTopic(TopicInfoAddDTO topicInfoAddDTO) {
		RepoUtils.requestLog(log, "snsTopicCenterServiceRef.addTopic", topicInfoAddDTO);
		BaseResult<SnsTopicDO> result = snsTopicCenterServiceRef.addTopic(topicInfoAddDTO);
		RepoUtils.resultLog(log, "snsTopicCenterServiceRef.addTopic", result);
		return result;
	}

	public BaseResult<SnsTopicDO> updateTopic(TopicInfoUpdateDTO topicInfoUpdateDTO) {
		RepoUtils.requestLog(log, "snsTopicCenterServiceRef.updateTopic", topicInfoUpdateDTO);
		BaseResult<SnsTopicDO> result = null;//snsTopicCenterServiceRef.updateTopic(topicInfoUpdateDTO);
//		RepoUtils.resultLog(log, "snsTopicCenterServiceRef.updateTopic", result);
		return result;
	}
	
	/**
     * 设置取消 推荐话题
     * @param topicSetDTO
     * @return
     */
	public BaseResult<Boolean> setTopic(TopicSetDTO topicSetDTO) {
		RepoUtils.requestLog(log, "snsTopicCenterServiceRef.setTopic", topicSetDTO);
		BaseResult<Boolean> result = snsTopicCenterServiceRef.setTopic(topicSetDTO);
		RepoUtils.resultLog(log, "snsTopicCenterServiceRef.setTopic", result);
		return result;
	}
	
	/**
     * 上下架 推荐话题
     * @param topicUpdateStatusDTO
     * @return
     */
    public BaseResult<Boolean> updateTopicStatus(TopicUpdateStatusDTO topicUpdateStatusDTO){
    	RepoUtils.requestLog(log, "snsTopicCenterServiceRef.updateTopicStatus", topicUpdateStatusDTO);
		BaseResult<Boolean> result = null;//snsTopicCenterServiceRef.updateTopicStatus(topicUpdateStatusDTO);
		RepoUtils.resultLog(log, "snsTopicCenterServiceRef.updateTopicStatus", result);
		return result;
    }
}
