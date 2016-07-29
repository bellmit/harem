package com.yimayhd.palace.model;

import java.io.Serializable;
import java.util.List;

public class ArticleConsultServiceItemVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String serviceHeadPic;
	private String serviceName;
	private List<String> serviceCity;
	private long serviceOriginalPrice;
	private long serviceCurrentPrice;
	private int consultTime;

	public String getServiceHeadPic() {
		return serviceHeadPic;
	}

	public void setServiceHeadPic(String serviceHeadPic) {
		this.serviceHeadPic = serviceHeadPic;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public List<String> getServiceCity() {
		return serviceCity;
	}

	public void setServiceCity(List<String> serviceCity) {
		this.serviceCity = serviceCity;
	}

	public long getServiceOriginalPrice() {
		return serviceOriginalPrice;
	}

	public void setServiceOriginalPrice(long serviceOriginalPrice) {
		this.serviceOriginalPrice = serviceOriginalPrice;
	}

	public long getServiceCurrentPrice() {
		return serviceCurrentPrice;
	}

	public void setServiceCurrentPrice(long serviceCurrentPrice) {
		this.serviceCurrentPrice = serviceCurrentPrice;
	}

	public int getConsultTime() {
		return consultTime;
	}

	public void setConsultTime(int consultTime) {
		this.consultTime = consultTime;
	}

}
