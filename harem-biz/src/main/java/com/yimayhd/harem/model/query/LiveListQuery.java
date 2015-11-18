package com.yimayhd.harem.model.query;

import com.yimayhd.harem.base.BaseQuery;

/**
 * Created by Administrator on 2015/11/16.
 */
public class LiveListQuery extends BaseQuery {
    private Integer liveTag;//直播标签
    private Integer status;//状态
    private String phoneNum;//用户手机号
    private String name;//用户昵称
    private String content;//内容
	
	public Integer getLiveTag() {
		return liveTag;
	}
	public void setLiveTag(Integer liveTag) {
		this.liveTag = liveTag;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
    
    
  
}

