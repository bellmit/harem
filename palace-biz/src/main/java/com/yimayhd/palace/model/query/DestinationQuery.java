package com.yimayhd.palace.model.query;

import com.yimayhd.palace.base.BaseQuery;

/**
 * Created by Administrator on 2015/11/18.
 */
public class DestinationQuery extends BaseQuery {
	/**
	 * 
	 */
	private static final long serialVersionUID = 232066482869418032L;
	
	//参考DestinationType
	private int level;
	
	//参考DestinationOutType
	private int outType;
	
	//目的地编码
	private int code;
	
	//父级目的地编码
	private int parentCode;
	
	public Integer getOutType() {
		return outType;
	}

	public void setOutType(Integer outType) {
		this.outType = outType;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getParentCode() {
		return parentCode;
	}

	public void setParentCode(Integer parentCode) {
		this.parentCode = parentCode;
	}
}
