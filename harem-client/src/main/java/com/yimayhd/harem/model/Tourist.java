package com.yimayhd.harem.model;

/**
 * 游客
 * 
 * @author yebin
 *
 */
public class Tourist {
	private Integer touristType;// 游客类型
	private String name;// 名称
	private String mobile;// 手机
	private Integer docType;// 证件类型
	private String docNo;// 证件号

	public int getTouristType() {
		return touristType;
	}

	public void setTouristType(int touristType) {
		this.touristType = touristType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getDocType() {
		return docType;
	}

	public void setDocType(int docType) {
		this.docType = docType;
	}

	public String getDocNo() {
		return docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}
}
