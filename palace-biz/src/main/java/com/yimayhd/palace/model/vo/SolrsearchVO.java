package com.yimayhd.palace.model.vo;

import java.io.Serializable;
import java.util.List;
/**
 * 
* @ClassName: SolrsearchVO
* @Description: TODO(这里用一句话描述这个类的作用)
* @author zhangxiaoyang
* @date 2016年9月7日
 */
public class SolrsearchVO implements Serializable {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = -8244363733188647062L;
	private List<Long> idList;
	public List<Long> getIdList() {
		return idList;
	}
	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}
	
}
