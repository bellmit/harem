package com.yimayhd.palace.repo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.util.RepoUtils;
import com.yimayhd.snscenter.client.domain.SnsSugTopicDO;
import com.yimayhd.snscenter.client.domain.SnsTopicDO;
import com.yimayhd.snscenter.client.dto.topic.SugTopicQueryListDTO;
import com.yimayhd.snscenter.client.dto.topic.TopicInfoUpdateDTO;
import com.yimayhd.snscenter.client.dto.topic.TopicQueryDTO;
import com.yimayhd.snscenter.client.dto.topic.TopicQueryListDTO;
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
	
	/**
	 * 话题列表
	 * @param query
	 * @return
	 */
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
	
	/**
	 * 话题详情
	 * @param topicQueryDTO
	 * @return
	 */
	public BaseResult<SnsTopicDO> getTopicDetailInfo(TopicQueryDTO topicQueryDTO) {
		RepoUtils.requestLog(log, "snsTopicCenterServiceRef.getTopicDetailInfo", topicQueryDTO);
		BaseResult<SnsTopicDO> result = snsTopicCenterServiceRef.getTopicDetailInfo(topicQueryDTO);
		RepoUtils.resultLog(log, "snsTopicCenterServiceRef.getTopicDetailInfo", result);
		return result;
	}
	
	/**
	 * 添加话题
	 * @param snsTopicDO
	 * @return
	 */
	public BaseResult<SnsTopicDO> addTopic(SnsTopicDO snsTopicDO) {
		RepoUtils.requestLog(log, "snsTopicCenterServiceRef.addTopic", snsTopicDO);
		BaseResult<SnsTopicDO> result = snsTopicCenterServiceRef.addTopic(snsTopicDO);
		RepoUtils.resultLog(log, "snsTopicCenterServiceRef.addTopic", result);
		return result;
	}
	
	/**
	 * 修改话题
	 * @param topicInfoUpdateDTO
	 * @return
	 */
	public BaseResult<Boolean> updateTopic(TopicInfoUpdateDTO topicInfoUpdateDTO) {
		RepoUtils.requestLog(log, "snsTopicCenterServiceRef.updateTopic", topicInfoUpdateDTO);
		BaseResult<Boolean> result = snsTopicCenterServiceRef.updateTopic(topicInfoUpdateDTO);
		RepoUtils.resultLog(log, "snsTopicCenterServiceRef.updateTopic", result);
		return result;
	}
	
	/**
     * 上下架 话题
     * @param topicUpdateStatusDTO
     * @return
     */
    public BaseResult<Boolean> updateTopicStatus(long id, int type){
    	RepoUtils.requestLog(log, "snsTopicCenterServiceRef.updateTopicStatus", id, type);
		BaseResult<Boolean> result = snsTopicCenterServiceRef.updateTopicStatus(id, type);
		RepoUtils.resultLog(log, "snsTopicCenterServiceRef.updateTopicStatus", result);
		return result;
    }
	
	/**
     * 推荐话题列表
     * @param pageQuery
     * @return
     */
    public BasePageResult<SnsSugTopicDO> getSugTopicPageList(SugTopicQueryListDTO pageQuery) {
		RepoUtils.requestLog(log, "snsTopicCenterServiceRef.getSugTopicPageList", pageQuery);
		BasePageResult<SnsSugTopicDO> result = snsTopicCenterServiceRef.getSugTopicPageList(pageQuery);
		RepoUtils.resultLog(log, "snsTopicCenterServiceRef.getSugTopicPageList", result);
		return result;
	}
	
	/**
     * 增加推荐话题
     * @param topicSetDTO
     * @return
     */
	public BaseResult<Boolean> addSugTopic(List<Long> idList) {
		RepoUtils.requestLog(log, "snsTopicCenterServiceRef.addSugTopic", idList);
		BaseResult<Boolean> result = snsTopicCenterServiceRef.addSugTopic(idList);
		RepoUtils.resultLog(log, "snsTopicCenterServiceRef.addSugTopic", result);
		return result;
	}
	
	/**
     * 取消推荐话题
     * @param topicSetDTO
     * @return
     */
	public BaseResult<Boolean> removeSugTopic(List<Long> idList) {
		RepoUtils.requestLog(log, "snsTopicCenterServiceRef.removeSugTopic", idList);
		BaseResult<Boolean> result = snsTopicCenterServiceRef.removeSugTopic(idList);
		RepoUtils.resultLog(log, "snsTopicCenterServiceRef.removeSugTopic", result);
		return result;
	}
	
    /**
     * 获取推荐的话题
     */
    public BaseResult<SnsSugTopicDO> getSugTopicById(long id){
    	RepoUtils.requestLog(log, "snsTopicCenterServiceRef.getSugTopicById", id);
    	BaseResult<SnsSugTopicDO> result = snsTopicCenterServiceRef.getSugTopicById(id);
		RepoUtils.resultLog(log, "snsTopicCenterServiceRef.getSugTopicById", result);
		return result;
    }
}
