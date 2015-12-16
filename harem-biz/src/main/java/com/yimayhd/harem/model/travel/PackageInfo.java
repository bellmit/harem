package com.yimayhd.harem.model.travel;

import java.util.List;

import com.yimayhd.ic.client.model.param.item.ItemSkuPVPair;

/**
 * 套餐
 * 
 * @author yebin
 *
 */
public class PackageInfo {
	private long id;
	private String name;
	private long PId;
	private int PType;
	private String PTxt;
	private List<PackageMonth> months;

	public PackageInfo() {
	}

	public PackageInfo(ItemSkuPVPair itemSkuPVPair) {
		this.id = itemSkuPVPair.getVId();
		this.name = itemSkuPVPair.getVTxt();
		this.PId = itemSkuPVPair.getPId();
		this.PType = itemSkuPVPair.getPType();
		this.PTxt = itemSkuPVPair.getPTxt();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PackageMonth> getMonths() {
		return months;
	}

	public void setMonths(List<PackageMonth> months) {
		this.months = months;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPId() {
		return PId;
	}

	public void setPId(long pId) {
		PId = pId;
	}

	public int getPType() {
		return PType;
	}

	public void setPType(int pType) {
		PType = pType;
	}

	public String getPTxt() {
		return PTxt;
	}

	public void setPTxt(String pTxt) {
		PTxt = pTxt;
	}

	/**
	 * 生成ItemSkuPVPair
	 * 
	 * @return
	 */
	public ItemSkuPVPair toItemSkuPVPair() {
		ItemSkuPVPair SKU = new ItemSkuPVPair();
		SKU.setPId(this.PId);
		SKU.setPType(this.PType);
		SKU.setPTxt(this.PTxt);
		SKU.setVId(this.id);
		SKU.setVTxt(this.name);
		return SKU;
	}
}
