package com.yimayhd.palace.model;

import java.io.Serializable;
import java.util.Date;

import com.yimayhd.palace.model.vo.AudioVO;

/**
 * H5文章
 *
 * @author xiemingna
 *
 */
public class ArticleItemVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;

	private long articleId;

	private int type;

	private int subType;

	private String title;

	private String content;

	private int status;

	private long sort;

	private Date gmtCreated;

	private Date gmtModified;
	/**
	 * 商品项
	 */
	private ArticleProductItemVO articleProductItemVO;
	/**
	 * 咨询服务项
	 */
	private ArticleConsultServiceItemVO articleConsultServiceItemVO;
	/**
	 * 达人项
	 */
	private ArticleExpertManItemVO articleExpertManItemVO;

	/**
	 * 酒店资源项
	 */
	private ArticleHotelResourceItemVO articleHotelResourceItemVO;
	/**
	 * 景区资源项
	 */
	private ArticleScenicResourceItemVO articleScenicResourceItemVO;

	/**
	 * 音频资源
	 */
	private ArticleAudioItemVO articleAudioItemVO;
	private AudioVO audioResource;
	
	public AudioVO getAudioResource() {
		return audioResource;
	}

	public void setAudioResource(AudioVO audioResource) {
		this.audioResource = audioResource;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getArticleId() {
		return articleId;
	}

	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSubType() {
		return subType;
	}

	public void setSubType(int subType) {
		this.subType = subType;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getSort() {
		return sort;
	}

	public void setSort(long sort) {
		this.sort = sort;
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

	public ArticleProductItemVO getArticleProductItemVO() {
		return articleProductItemVO;
	}

	public void setArticleProductItemVO(ArticleProductItemVO articleProductItemVO) {
		this.articleProductItemVO = articleProductItemVO;
	}

	public ArticleConsultServiceItemVO getArticleConsultServiceItemVO() {
		return articleConsultServiceItemVO;
	}

	public void setArticleConsultServiceItemVO(ArticleConsultServiceItemVO articleConsultServiceItemVO) {
		this.articleConsultServiceItemVO = articleConsultServiceItemVO;
	}

	public ArticleExpertManItemVO getArticleExpertManItemVO() {
		return articleExpertManItemVO;
	}

	public void setArticleExpertManItemVO(ArticleExpertManItemVO articleExpertManItemVO) {
		this.articleExpertManItemVO = articleExpertManItemVO;
	}

	public ArticleHotelResourceItemVO getArticleHotelResourceItemVO() {
		return articleHotelResourceItemVO;
	}

	public void setArticleHotelResourceItemVO(ArticleHotelResourceItemVO articleHotelResourceItemVO) {
		this.articleHotelResourceItemVO = articleHotelResourceItemVO;
	}

	public ArticleScenicResourceItemVO getArticleScenicResourceItemVO() {
		return articleScenicResourceItemVO;
	}

	public void setArticleScenicResourceItemVO(ArticleScenicResourceItemVO articleScenicResourceItemVO) {
		this.articleScenicResourceItemVO = articleScenicResourceItemVO;
	}

    public ArticleAudioItemVO getArticleAudioItemVO() {
        return articleAudioItemVO;
    }

    public void setArticleAudioItemVO(ArticleAudioItemVO articleAudioItemVO) {
        this.articleAudioItemVO = articleAudioItemVO;
    }
}
