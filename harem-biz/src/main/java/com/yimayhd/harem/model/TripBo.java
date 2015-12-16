package com.yimayhd.harem.model;

import java.io.Serializable;
import java.util.List;

import com.yimayhd.harem.model.TripBo.TripDetail;


/** 
* @ClassName: TripBo 
* @Description: (出发地，目的地 bo对象) 
* @author create by yushengwei @ 2015年12月10日 下午8:01:53 
*/
public class TripBo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public int id; /** id */
	public String cityName; /**目的地名称 */
	public String cityCode; /** */
	public int cityLevel; /** 级别 省市区 */
	public int[] tag; /** 标签 */
	public String logoURL; /** 封面图 */
	public String coverURL; /** 目的地图 */
	public List<TripDetail> TripDetail; /** 概况 */
	public int type;/** 1出发地，2目的地*/
	//---以下关联id，以数组方式存放-----------------------------------------------
	public int[] biMai;/** 必买推荐 */
	public int[] biQu;/** 必去景点*/
	public int[] jiuDian;/** 精选酒店*/
	public int[] zhiBo;/** 精选直播*/
	public int[] xianLu;/** 线路*/
	
	
	
	public class TripDetail  implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		int id; /** id */
		String title; /** 标题*/
		String content; /** 内容 */
		int sort; /** 排序 */
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
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
		public int getSort() {
			return sort;
		}
		public void setSort(int sort) {
			this.sort = sort;
		}
		
	}



	

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getCityName() {
		return cityName;
	}



	public void setCityName(String cityName) {
		this.cityName = cityName;
	}



	public String getCityCode() {
		return cityCode;
	}



	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}



	public int getCityLevel() {
		return cityLevel;
	}



	public void setCityLevel(int cityLevel) {
		this.cityLevel = cityLevel;
	}



	public String getLogoURL() {
		return logoURL;
	}



	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
	}



	public String getCoverURL() {
		return coverURL;
	}



	public void setCoverURL(String coverURL) {
		this.coverURL = coverURL;
	}



	public List<TripDetail> getTripDetail() {
		return TripDetail;
	}



	public void setTripDetail(List<TripDetail> tripDetail) {
		TripDetail = tripDetail;
	}



	public int[] getTag() {
		return tag;
	}



	public void setTag(int[] tag) {
		this.tag = tag;
	}
	
	public int getType() {
		return type;
	}



	public void setType(int type) {
		this.type = type;
	}

	public int[] getBiMai() {
		return biMai;
	}



	public void setBiMai(int[] biMai) {
		this.biMai = biMai;
	}



	public int[] getBiQu() {
		return biQu;
	}



	public void setBiQu(int[] biQu) {
		this.biQu = biQu;
	}



	public int[] getJiuDian() {
		return jiuDian;
	}



	public void setJiuDian(int[] jiuDian) {
		this.jiuDian = jiuDian;
	}



	public int[] getZhiBo() {
		return zhiBo;
	}



	public void setZhiBo(int[] zhiBo) {
		this.zhiBo = zhiBo;
	}



	public int[] getXianLu() {
		return xianLu;
	}



	public void setXianLu(int[] xianLu) {
		this.xianLu = xianLu;
	}

}
