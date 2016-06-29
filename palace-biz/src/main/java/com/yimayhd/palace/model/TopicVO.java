package com.yimayhd.palace.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.yimayhd.snscenter.client.domain.SnsTopicDO;
import com.yimayhd.snscenter.client.result.topic.TopicResult;

/**
 * Created by hongfei.guo on 2016/06/27.
 */
public class TopicVO extends SnsTopicDO {

	private static final long serialVersionUID = 1L;
	
	//阅读数
    private long readNum;
    //讨论数
    private long talkNum;

    public long getReadNum() {
        return readNum;
    }

    public void setReadNum(long readNum) {
        this.readNum = readNum;
    }

    public long getTalkNum() {
        return talkNum;
    }

    public void setTalkNum(long talkNum) {
        this.talkNum = talkNum;
    }
	
	public static SnsTopicDO getTopicDO(TopicVO topicVO){
		if(topicVO == null){
    		return null;
    	}
		
    	SnsTopicDO topicDO = new SnsTopicDO();
        BeanUtils.copyProperties(topicVO, topicDO);
        return topicDO;
    }
    
    public static TopicVO getTopicVO(SnsTopicDO topicDO){
    	if(topicDO == null){
    		return null;
    	}
    	
        TopicVO topicVO = new TopicVO();
        BeanUtils.copyProperties(topicDO, topicVO);
        return topicVO;
    }
    
    public static List<TopicVO> getTopicVOList(List<TopicResult> topicResultList){
    	
    	List<TopicVO> topicVOList = new ArrayList<TopicVO>();
    	if(topicResultList == null){
    		return topicVOList;
    	}
    	
    	TopicVO topicVO = null;
    	for(int i = 0; i < topicResultList.size(); i++){
    		topicVO = new TopicVO();
    		BeanUtils.copyProperties(topicResultList.get(i), topicVO);
    		topicVOList.add(topicVO);
    	}
        
        return topicVOList;
    }

}
