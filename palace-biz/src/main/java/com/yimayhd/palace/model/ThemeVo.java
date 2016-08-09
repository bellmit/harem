package com.yimayhd.palace.model;

import java.io.Serializable;

import com.yimayhd.commentcenter.client.domain.ComTagDO;

/** 
* @ClassName: ThemeVo 
* @Description: 主题vo类 
* @author create by yushengwei @ 2015年12月1日 上午10:45:06 
*/
public class ThemeVo extends ComTagDO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int domain;

	public int getDomain() {
		return domain;
	}

	public void setDomain(int domain) {
		this.domain = domain;
	}
	
}
