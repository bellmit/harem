package com.yimayhd.palace.model.vo;

import java.io.Serializable;
import java.util.Date;
/**
 * 
* @ClassName: RadioVO
* @Description: 音频
* @author zhangxiaoyang
* @date 2016年8月24日 下午6:31:18
*
 */
public class AudioVO implements Serializable {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = 4741142714305309417L;

	/**
     * ,所属表字段为rc_media.id
     */
    private long id;

    /**
     * 域,所属表字段为rc_media.user_id
     */
    private long userId;

    

    /**
     * 文件名称,所属表字段为rc_media.input_file_name
     */
    private String inputFileName;

    /**
     * 域,所属表字段为rc_media.domain
     */
    private long domainId;

    /**
     * 时长,单位秒,所属表字段为rc_media.duration
     */
    private long duration;

    /**
     * 30:mp3 40:mp4,所属表字段为rc_media.file_type
     */
    private int fileType;
    private String fileTypeName;
    
    public String getFileTypeName() {
		return fileTypeName;
	}

	public void setFileTypeName(String fileTypeName) {
		this.fileTypeName = fileTypeName;
	}

	/**
     * 用途,10:全部;20:导览;30:H5,所属表字段为rc_media.scope
     */
    private int scope;
    private String scopeName;
    
    public String getScopeName() {
		return scopeName;
	}

	public void setScopeName(String scopeName) {
		this.scopeName = scopeName;
	}

	/**
     * 状态,10:上架;20:下架,所属表字段为rc_media.status
     */
    private int status;

    /**
     * 备注,所属表字段为rc_media.remark
     */
    private String remark;


    /**
     * 网宿地址,所属表字段为rc_media.remote_url
     */
    private String remoteUrl;

    /**
     * 网宿空间名称,所属表字段为rc_media.bucket_name
     */
    private String bucketName;

    /**
     * 网宿文件key,所属表字段为rc_media.file_key
     */
    private String fileKey;

    /**
     * 文件哈希值,所属表字段为rc_media.file_hash
     */
    private String fileHash;


    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getInputFileName() {
		return inputFileName;
	}

	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	public long getDomainId() {
		return domainId;
	}

	public void setDomainId(long domainId) {
		this.domainId = domainId;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public int getFileType() {
		return fileType;
	}

	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

	public int getScope() {
		return scope;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemoteUrl() {
		return remoteUrl;
	}

	public void setRemoteUrl(String remoteUrl) {
		this.remoteUrl = remoteUrl;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getFileKey() {
		return fileKey;
	}

	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}

	public String getFileHash() {
		return fileHash;
	}

	public void setFileHash(String fileHash) {
		this.fileHash = fileHash;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	/**
     * ,所属表字段为rc_media.gmt_created
     */
    private Date gmtCreated;

    /**
     * ,所属表字段为rc_media.gmt_modify
     */
    private Date gmtModify;
}
