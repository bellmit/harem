package com.yimayhd.harem.model.travel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemFeature;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.domain.share_json.LinePropertyType;
import com.yimayhd.ic.client.model.enums.ItemFeatureKey;
import com.yimayhd.ic.client.model.param.item.ItemSkuPVPair;

/**
 * 价格信息
 * 
 * @author yebin
 *
 */
public class PriceInfo {
	private List<PackageInfo> tcs;// 套餐
	private int limit;// 提前几天
	private long itemId;

	public PriceInfo() {
	}

	public PriceInfo(ItemDO itemDO, List<ItemSkuDO> itemSkuList) {
		this.itemId = itemDO.getId();
		if (itemDO != null && StringUtils.isNotBlank(itemDO.getFeature())) {
			ItemFeature feature = new ItemFeature(itemDO.getFeature());
			this.limit = feature.getStartBookTimeLimit() / (3600 * 24);
		}
		this.tcs = new ArrayList<PackageInfo>();
		if (CollectionUtils.isNotEmpty(itemSkuList)) {
			for (ItemSkuDO sku : itemSkuList) {
				if (StringUtils.isNotBlank(sku.getProperty())) {
					List<ItemSkuPVPair> pairs = sku.getItemSkuPVPairList();
					ItemSkuPVPair tcPair = null;
					ItemSkuPVPair dayPair = null;
					ItemSkuPVPair personPair = null;
					long dTime = 0;
					long mTime = 0;
					for (ItemSkuPVPair itemSkuPVPair : pairs) {
						if (itemSkuPVPair.getPId() == LinePropertyType.TRAVEL_PACKAGE.getType()) {
							tcPair = itemSkuPVPair;
						}
						if (itemSkuPVPair.getPId() == LinePropertyType.DEPART_DATE.getType()) {
							dayPair = itemSkuPVPair;
							dTime = Long.parseLong(itemSkuPVPair.getVTxt());
							Calendar c = Calendar.getInstance();
							c.setTimeInMillis(dTime);
							c.set(Calendar.DAY_OF_MONTH, 1);
							mTime = c.getTimeInMillis();
						}
						if (itemSkuPVPair.getPId() == LinePropertyType.PERSON.getType()) {
							personPair = itemSkuPVPair;
						}
					}
					// 组装套餐
					PackageInfo packageInfo = null;
					for (PackageInfo piItem : this.tcs) {
						if (tcPair.getVId() > 0 && tcPair.getVId() == piItem.getId()) {
							packageInfo = piItem;
							break;
						}
					}
					if (packageInfo == null) {
						packageInfo = new PackageInfo(tcPair);
						this.tcs.add(packageInfo);
					}
					// 组装月
					List<PackageMonth> pms = packageInfo.getMonths();
					if (pms == null) {
						pms = new ArrayList<PackageMonth>();
						packageInfo.setMonths(pms);
					}
					PackageMonth packageMonth = null;
					for (PackageMonth pmItem : pms) {
						if (pmItem.getDate().getTime() == mTime) {
							packageMonth = pmItem;
							break;
						}
					}
					if (packageMonth == null) {
						packageMonth = new PackageMonth(mTime);
						pms.add(packageMonth);
					}
					// 组装天
					List<PackageDay> pds = packageMonth.getDays();
					if (pds == null) {
						pds = new ArrayList<PackageDay>();
						packageMonth.setDays(pds);
					}
					PackageDay packageDay = null;
					for (PackageDay pdItem : pds) {
						if (pdItem.getTime() == dTime) {
							packageDay = pdItem;
							break;
						}
					}
					if (packageDay == null) {
						packageDay = new PackageDay(dayPair, dTime);
						pds.add(packageDay);
					}
					// 块
					List<PackageBlock> pbs = packageDay.getBlocks();
					if (pbs == null) {
						pbs = new ArrayList<PackageBlock>();
						packageDay.setBlocks(pbs);
					}
					// TODO discount
					pbs.add(new PackageBlock(personPair, sku.getPrice(), sku.getStockNum(), 0));
				}
			}
		}
	}

	public List<PackageInfo> getTcs() {
		return tcs;
	}

	public void setTcs(List<PackageInfo> tcs) {
		this.tcs = tcs;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getStartTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (CollectionUtils.isNotEmpty(tcs)) {
			List<PackageMonth> months = tcs.get(0).getMonths();
			if (CollectionUtils.isNotEmpty(months)) {
				List<PackageDay> days = months.get(0).getDays();
				if (CollectionUtils.isNotEmpty(days)) {
					return sdf.format(new Date(days.get(0).getTime()));
				}
			}
		}
		return "";
	}

	public String getEndTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (CollectionUtils.isNotEmpty(tcs)) {
			List<PackageMonth> months = tcs.get(0).getMonths();
			if (CollectionUtils.isNotEmpty(months)) {
				List<PackageDay> days = months.get(months.size() - 1).getDays();
				if (CollectionUtils.isNotEmpty(days)) {
					return sdf.format(new Date(days.get(days.size() - 1).getTime()));
				}
			}
		}
		return "";
	}

	public ItemDO toItemDO() {
		ItemDO itemDO = new ItemDO();
		itemDO.setId(this.itemId);
		ItemFeature itemFeature = new ItemFeature(null);
		itemFeature.put(ItemFeatureKey.START_BOOK_TIME_LIMIT, this.limit);
		itemDO.setItemFeature(itemFeature);
		return itemDO;
	}

	public List<ItemSkuDO> toItemSkuDOList() {
		List<ItemSkuDO> itemSkuDOs = new ArrayList<ItemSkuDO>();
		if (CollectionUtils.isNotEmpty(this.tcs)) {
			for (PackageInfo packageInfo : this.tcs) {
				ItemSkuPVPair itemSkuPVPair1 = packageInfo.toItemSkuPVPair();
				if (CollectionUtils.isNotEmpty(packageInfo.getMonths())) {
					for (PackageMonth packageMonth : packageInfo.getMonths()) {
						if (CollectionUtils.isNotEmpty(packageMonth.getDays())) {
							for (PackageDay packageDay : packageMonth.getDays()) {
								ItemSkuPVPair itemSkuPVPair2 = packageDay.toItemSkuPVPair();
								if (CollectionUtils.isNotEmpty(packageMonth.getDays())) {
									for (PackageBlock packageBlock : packageDay.getBlocks()) {
										ItemSkuPVPair itemSkuPVPair3 = packageBlock.toItemSkuPVPair();
										List<ItemSkuPVPair> itemSkuPVPairs = new ArrayList<ItemSkuPVPair>();
										itemSkuPVPairs.add(itemSkuPVPair1);
										itemSkuPVPairs.add(itemSkuPVPair2);
										itemSkuPVPairs.add(itemSkuPVPair3);
										ItemSkuDO itemSkuDO = new ItemSkuDO();
										itemSkuDO.setItemSkuPVPairList(itemSkuPVPairs);
										itemSkuDO.setPrice(packageBlock.getPrice());
										itemSkuDO.setStockNum(packageBlock.getStock());
										itemSkuDOs.add(itemSkuDO);
									}
								}
							}
						}
					}
				}
			}
		}
		return itemSkuDOs;
	}
}
