package com.yimayhd.harem.model;

import java.io.Serializable;
import java.util.List;


/** 
* @ClassName: TripBo 
* @Description: (出发地，目的地 bo对象) 
* @author create by yushengwei @ 2015年12月10日 下午8:01:53 
*/
public class TripBo extends Destination implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	//---以下关联id，以数组方式存放-----------------------------------------------
	public int[] biMai;/** 必买推荐 */
	public int[] biQu;/** 必去景点*/
	public int[] jiuDian;/** 精选酒店*/
	public int[] zhiBo;/** 精选直播*/
	public int[] xianLu;/** 羡慕*/
	
	
	
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
