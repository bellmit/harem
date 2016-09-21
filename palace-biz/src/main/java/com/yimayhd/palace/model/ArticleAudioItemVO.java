package com.yimayhd.palace.model;

import java.io.Serializable;

/**
 * 达人故事资源项
 *
 * @author xiemingna
 */
public class ArticleAudioItemVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 音频文件名
     */
    private String audioName;
    /**
     * 音频封面
     */
    private String audioPic;
    /**
     * 音频链接
     */
    private String audioUrl;
    /**
     * 音频时长
     */
    private long audioTime;

    public String getAudioName() {
        return audioName;
    }

    public void setAudioName(String audioName) {
        this.audioName = audioName;
    }

    public String getAudioPic() {
        return audioPic;
    }

    public void setAudioPic(String audioPic) {
        this.audioPic = audioPic;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public long getAudioTime() {
        return audioTime;
    }

    public void setAudioTime(long audioTime) {
        this.audioTime = audioTime;
    }
}
