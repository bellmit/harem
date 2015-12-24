package com.yimayhd.harem.model.travel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemFeature;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.domain.share_json.LinePropertyType;
import com.yimayhd.ic.client.model.param.item.ItemSkuPVPair;

/**
 * 价格信息
 * 
 * @author yebin
 *
 */
public class PriceInfo {
	private static final int LIMIT_UNIT = 3600 * 24;
	private List<PackageInfo> tcs;// 套餐
	private int limit;// 提前几天
	private String importantInfosCode;
	private long itemId;

	public PriceInfo() {
	}

	public PriceInfo(ItemDO itemDO, List<ItemSkuDO> itemSkuList) {
		this.itemId = itemDO.getId();
		if (itemDO != null && StringUtils.isNotBlank(itemDO.getFeature())) {
			ItemFeature feature = new ItemFeature(itemDO.getFeature());
			this.limit = feature.getStartBookTimeLimit() / LIMIT_UNIT;
			this.importantInfosCode = feature.getAgreement();
		}
		Map<String, PackageInfo> piMap = new LinkedHashMap<String, PackageInfo>();
		Map<Long, PackageDay> pdMap = new LinkedHashMap<Long, PackageDay>();
		Map<Long, PackageBlock> pbMap = new LinkedHashMap<Long, PackageBlock>();
		Map<String, Map<Long, Map<Long, ItemSkuDO>>> treeMap = new LinkedHashMap<String, Map<Long, Map<Long, ItemSkuDO>>>();
		if (CollectionUtils.isNotEmpty(itemSkuList)) {
			for (ItemSkuDO sku : itemSkuList) {
				ItemSkuPVPair tcPair = null;
				ItemSkuPVPair dayPair = null;
				ItemSkuPVPair personPair = null;
				if (StringUtils.isNotBlank(sku.getProperty())) {
					List<ItemSkuPVPair> pairs = sku.getItemSkuPVPairList();
					for (ItemSkuPVPair itemSkuPVPair : pairs) {
						if (itemSkuPVPair.getPId() == LinePropertyType.TRAVEL_PACKAGE.getType()) {
							tcPair = itemSkuPVPair;
							if (piMap.containsKey(tcPair.getVTxt())) {
								piMap.put(tcPair.getVTxt(), new PackageInfo(tcPair));
							}
						}
						if (itemSkuPVPair.getPId() == LinePropertyType.DEPART_DATE.getType()) {
							dayPair = itemSkuPVPair;
							if (!pdMap.containsKey(dayPair.getVTxt())) {
								long time = Long.parseLong(dayPair.getVTxt());
								pdMap.put(time, new PackageDay(dayPair, time));
							}
						}
						if (itemSkuPVPair.getPId() == LinePropertyType.PERSON.getType()) {
							personPair = itemSkuPVPair;
							if (!pbMap.containsKey(personPair.getVId())) {
								pbMap.put(personPair.getVId(), new PackageBlock(sku.getId(), personPair, sku.getPrice(),
										sku.getStockNum(), 0));
							}
						}
					}
				}
				// TODO 完成数据组装
			}
		}
		// this.tcs = new ArrayList<PackageInfo>(pMap.values());
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

	public int getLimitBySecond() {
		return limit * LIMIT_UNIT;
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

	public List<ItemSkuDO> toItemSkuDOList(long categoryId, long sellerId) {
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
										itemSkuDO.setId(packageBlock.getId());
										itemSkuDO.setTitle(itemSkuPVPair1.getVTxt() + "," + itemSkuPVPair3.getVTxt());
										itemSkuDO.setCategoryId(categoryId);
										itemSkuDO.setSellerId(sellerId);
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

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public String getImportantInfosCode() {
		return importantInfosCode;
	}

	public void setImportantInfosCode(String importantInfosCode) {
		this.importantInfosCode = importantInfosCode;
	}
}
