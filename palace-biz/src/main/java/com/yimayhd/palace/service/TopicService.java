package com.yimayhd.palace.service;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.SugTopicVO;
import com.yimayhd.palace.model.TopicInfoVO;
import com.yimayhd.palace.model.TopicVO;
import com.yimayhd.palace.model.query.SugTopicListQuery;
import com.yimayhd.palace.model.query.TopicListQuery;
import com.yimayhd.palace.result.BizResult;

public interface TopicService {
	
	/**
	 * 话题列表
	 * @param topicListQuery
	 * @return
	 * @throws Exception
	 */
	PageVO<TopicVO> getTopicPageList(TopicListQuery topicListQuery) throws Exception;
	
	/**
	 * 话题详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
    TopicVO getTopicDetailInfo(long id) throws Exception;
    
    /**
     * 添加话题
     * @param topicVO
     * @return
     * @throws Exception
     */
    TopicVO addTopic(TopicVO topicVO) throws Exception;
    
    /**
     * 编辑话题
     * @param topicInfoVO
     * @return
     * @throws Exception
     */
    boolean updateTopic(TopicInfoVO topicInfoVO) throws Exception;
    
    /**
     * 话题上下架
     * @param id
     * @param type
     * @return
     * @throws Exception
     */
    boolean updateTopicStatus(long id, int type) throws Exception;
    
    /**
     * 推荐话题列表
     * @param pageQuery
     * @return
     * @throws Exception
     */
    public PageVO<SugTopicVO> getSugTopicPageList(SugTopicListQuery sugTopicListQuery) throws Exception;
    
    /**
     * 获取推荐的话题
     * @param id
     * @return
     * @throws Exception
     */
    public boolean getSugTopicById(long id) throws Exception;
    
    /**
     * 添加推荐话题
     * @param id
     * @return
     * @throws Exception
     */
    boolean addSugTopic(Long id) throws Exception;
    
    /**
     * 取消推荐话题
     * @param id
     * @return
     * @throws Exception
     */
    boolean removeSugTopic(Long id) throws Exception;
    /**
     * 
    * created by zhangxiaoyang
    * @date 2016年8月26日
    * @Title: modifyTopicWeight 
    * @Description: 设置话题权重
    * @param @return
    * @return boolean    返回类型 
    * @throws
     */
    BizResult<Boolean> modifyTopicWeight(long id,int weightValue);

}
