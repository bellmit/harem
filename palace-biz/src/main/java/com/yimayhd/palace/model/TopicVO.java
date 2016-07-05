package com.yimayhd.palace.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.yimayhd.palace.constant.Constant;
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
    		topicVO.setTitle(getTopicTitle2(topicVO.getTitle()));
    		topicVOList.add(topicVO);
    	}
        
        return topicVOList;
    }
    
    public static String getTopicTitle(String title){
    	if(StringUtils.isBlank(title)){
    		return "";
    	}
    	
    	title = title.replaceAll("#", "");
    	StringBuilder sb = new StringBuilder();
    	sb.append(Constant.TOPIC_PREFIX_SUFFIX);
    	sb.append(title);
    	sb.append(Constant.TOPIC_PREFIX_SUFFIX);
    	return sb.toString();
    }
    
    public static String getTopicTitle2(String title){
    	if(StringUtils.isBlank(title)){
    		return "";
    	}
    	
    	title = title.replaceAll("#", "");
    	return title;
    }
    
    public static void main(String[] args){
    	System.out.println(TopicVO.getTopicTitle("alkdsfjlka''as\"df## asdf"));
    }
}
