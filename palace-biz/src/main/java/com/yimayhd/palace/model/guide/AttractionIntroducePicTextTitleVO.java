package com.yimayhd.palace.model.guide;

import java.util.List;

/**
 * Created by haozhu on 16/9/2.
 */
public class AttractionIntroducePicTextTitleVO {

    /**
     * 景点id
     */
    private long attractionId;

    /**
     *  图文信息
     */
    private String json;

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

    public com.yimayhd.palace.model.guide.AttractionIntroducePicTextTitleVO setAttractionId(long attractionId) {
        this.attractionId = attractionId;
        return this;
    }

    public String getJson() {
        return json;
    }

    public com.yimayhd.palace.model.guide.AttractionIntroducePicTextTitleVO setJson(String  json) {
        this.json = json;
        return this;
    }

    public String getUuidPicText() {
        return uuidPicText;
    }

    public com.yimayhd.palace.model.guide.AttractionIntroducePicTextTitleVO setUuidPicText(String uuidPicText) {
        this.uuidPicText = uuidPicText;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public com.yimayhd.palace.model.guide.AttractionIntroducePicTextTitleVO setTitle(String title) {
        this.title = title;
        return this;
    }
}
