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

	private Long pv;

	private Date gmtCreated;

	private Date gmtModified;
	
	private List<ArticleItemVO> articleItemList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDomainId() {
		return domainId;
	}

	public void setDomainId(Long domainId) {
		this.domainId = domainId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public String getFrontcover() {
		return frontcover;
	}

	public void setFrontcover(String frontcover) {
		this.frontcover = frontcover == null ? null : frontcover.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getPv() {
		return pv;
	}

	public void setPv(Long pv) {
		this.pv = pv;
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

	public List<ArticleItemVO> getArticleItems() {
		return articleItemList;
	}

	public void setArticleItems(List<ArticleItemVO> articleItems) {
		this.articleItemList = articleItems;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
}
