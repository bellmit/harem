package com.yimayhd.palace.service;

import java.util.List;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.TopicInfoVO;
import com.yimayhd.palace.model.TopicVO;
import com.yimayhd.palace.model.query.TopicListQuery;

public interface TopicService {
	
	PageVO<TopicVO> getTopicPageList(TopicListQuery topicListQuery) throws Exception;
	
    TopicVO getTopicDetailInfo(long id);
    
    TopicVO addTopic(TopicInfoVO topicInfoVO);
    
    TopicVO updateTopic(TopicInfoVO topicInfoVO);
    
    boolean updateTopicStatus(long id, String status);
        
    boolean setTopic(List<Long> idList, String status);

}
