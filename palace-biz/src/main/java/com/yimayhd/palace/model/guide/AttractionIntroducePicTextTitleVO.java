package com.yimayhd.palace.model.guide;

import java.io.Serializable;


/**
 * Created by haozhu on 16/9/2.
 */
public class AttractionIntroducePicTextTitleVO implements Serializable {

    /**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = 8025780243738549629L;

	/**
     * 景点id
     */
    private long attractionId;

    /**
     *  图文信息
     */
    private String picTextString;

    /**
     *  重复提交的随机数
     */
    private String uuidPicText;

    /**
     *  景点详情标题
     */
    private String title;

	public long getAttractionId() {
		return attractionId;
	}

	public void setAttractionId(long attractionId) {
		this.attractionId = attractionId;
	}

	public String getPicTextString() {
		return picTextString;
	}

	public void setPicTextString(String picTextString) {
		this.picTextString = picTextString;
	}

	public String getUuidPicText() {
		return uuidPicText;
	}

	public void setUuidPicText(String uuidPicText) {
		this.uuidPicText = uuidPicText;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


   
}
