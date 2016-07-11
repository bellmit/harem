package com.yimayhd.palace.model.query;

import com.yimayhd.palace.base.BaseQuery;

/**
 * 文章管理
 * 
 * @author xiemingna
 *
 */
public class ArticleListQuery extends BaseQuery {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long type;// 文章分类
	private int liveStatus;// 状态
	private String tel;// 用户手机号
	private String nickName;// 用户昵称
	private String content;// 内容
	private String beginDate;
	private String endDate;

	public long getTag() {
		return type;
	}

	public void setTag(long tag) {
		this.type = tag;
	}

	public int getLiveStatus() {
		return liveStatus;
	}

	public void setLiveStatus(int liveStatus) {
		this.liveStatus = liveStatus;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
