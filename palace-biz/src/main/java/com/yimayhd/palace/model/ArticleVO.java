package com.yimayhd.palace.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * H5文章
 * 
 * @author xiemingna
 *
 */
public class ArticleVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long domainId;

	private String title;

	private String subTitle;

	private String frontcover;

	private Integer status;

	private Integer type;

	private Integer pv;

	private Date gmtCreated;

	private Date gmtModified;

	private String articleItems;

	private List<ArticleItemVO> articleItemList;

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getDomainId() {
		return domainId;
	}

	public void setDomainId(Long domainId) {
		this.domainId = domainId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getFrontcover() {
		return frontcover;
	}

	public void setFrontcover(String frontcover) {
		this.frontcover = frontcover;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public List<ArticleItemVO> getArticleItemList() {
		return articleItemList;
	}

	public void setArticleItemList(List<ArticleItemVO> articleItemList) {
		this.articleItemList = articleItemList;
	}

	public String getArticleItems() {
		return articleItems;
	}

	public void setArticleItems(String articleItems) {
		this.articleItems = articleItems;
	}

	public int getPv() {
		return pv;
	}

	public void setPv(Integer pv) {
		this.pv = pv;
	}

}
