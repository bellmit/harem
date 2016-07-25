package com.yimayhd.palace.model;

import java.io.Serializable;

public class ArticleConsultServiceItemVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String serviceHeadPic;
	private String serviceName;
	private String serviceCity;
	private long serviceOriginalPrice;
	private long serviceCurrentPrice;

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

	public String getServiceCity() {
		return serviceCity;
	}

	public void setServiceCity(String serviceCity) {
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

}
