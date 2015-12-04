package com.yimayhd.harem.model.travel;

/**
 * 只包含ID和名称的数据对象
 * 
 * @author yebin
 *
 */
public class IdNamePair {
	private long id;
	private String name;

	public IdNamePair() {
	}

	public IdNamePair(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
