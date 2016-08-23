package com.yimayhd.palace.result;

import java.io.Serializable;

/**
 * Created by xushubing on 2016/8/23.
 */
public class AttachmentUploadResult implements Serializable {
    private static final long serialVersionUID = -6536248627635723874L;
    private String key;
    private String fname;
    private String fsize;
    private String url;
    private String hash;
    private String bucketName;
    private long duration;


    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFsize() {
        return fsize;
    }

    public void setFsize(String fsize) {
        this.fsize = fsize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
