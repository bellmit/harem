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
	
	public String picVal;

	public static SnsTopicDO getTopicDO(TopicVO topicVO){
		if(topicVO == null){
    		return null;
    	}
		
    	SnsTopicDO topicDO = new SnsTopicDO();
        BeanUtils.copyProperties(topicVO, topicDO);
        
        topicDO.setPics(topicVO.getPicVal());
        
        String title = topicDO.getTitle();
        if(title != null){
        	topicDO.setTitle(getTopicTitle(title));
        }
        
        return topicDO;
    }
    
    public static TopicVO getTopicVO(SnsTopicDO topicDO){
    	if(topicDO == null){
    		return null;
    	}
    	
        TopicVO topicVO = new TopicVO();
        BeanUtils.copyProperties(topicDO, topicVO);
        topicVO.setTitle(getTopicTitle2(topicVO.getTitle()));
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
    	
    	title = title.replaceAll(Constant.TOPIC_PREFIX_SUFFIX, "");
    	StringBuilder sb = new StringBuilder();
    	sb.append(Constant.TOPIC_PREFIX_SUFFIX);
    	sb.append(title.trim());
    	sb.append(Constant.TOPIC_PREFIX_SUFFIX);
    	return sb.toString();
    }
    
    public static String getTopicTitle2(String title){
    	if(StringUtils.isBlank(title)){
    		return "";
    	}
    	
    	title = title.replaceAll(Constant.TOPIC_PREFIX_SUFFIX, "");
    	return title;
    }
    
    public String getPicVal() {
		return picVal;
	}

	public void setPicVal(String picVal) {
		this.picVal = picVal;
	}
    
    public static void main(String[] args){
    	System.out.println(TopicVO.getTopicTitle("alkdsfjlka''as\"df## asdf"));
    }
}
