package com.yimayhd.palace.model;

import org.springframework.beans.BeanUtils;

import com.yimayhd.snscenter.client.dto.topic.TopicInfoAddDTO;
import com.yimayhd.snscenter.client.dto.topic.TopicInfoUpdateDTO;

/**
 * Created by hongfei.guo on 2016/06/27.
 */
public class TopicInfoVO{
	
	private long id;

	private String title;

    private String pics;

    private String content;

	private String picVal;

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
    
    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public String getPicVal() {
		return picVal;
	}

	public void setPicVal(String picVal) {
		this.picVal = picVal;
	}
    
    public static TopicInfoUpdateDTO getTopicInfoUpdateDTO(TopicInfoVO topicInfoVO){
		
    	TopicInfoUpdateDTO topicInfoUpdateDTO = new TopicInfoUpdateDTO();
        if(topicInfoVO == null){
        	return topicInfoUpdateDTO;
        }
        BeanUtils.copyProperties(topicInfoVO, topicInfoUpdateDTO);
        topicInfoUpdateDTO.setPics(topicInfoVO.getPicVal());
        return topicInfoUpdateDTO;
    }
    
    
}
