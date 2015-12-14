package com.yimayhd.harem.model.travel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.yimayhd.ic.client.model.domain.item.ItemFeature;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.domain.share_json.LinePropertyType;
import com.yimayhd.ic.client.model.param.item.ItemSkuPVPair;
import com.yimayhd.ic.client.model.result.item.LineResult;

/**
 * 价格信息
 * 
 * @author yebin
 *
 */
public class PriceInfo {
	private List<PackageInfo> tcs;// 套餐
	private int limit;// 提前几天

	public PriceInfo() {
	}

	public PriceInfo(LineResult lineResult) {
		List<ItemSkuDO> itemSkuList = lineResult.getItemSkuDOList();
		if (StringUtils.isNotBlank(lineResult.getItemDO().getFeature())) {
			ItemFeature feature = JSON.parseObject(lineResult.getItemDO().getFeature(), ItemFeature.class);
			this.limit = feature.getStartBookTimeLimit();
		}
		this.tcs = new ArrayList<PackageInfo>();
		if (CollectionUtils.isNotEmpty(itemSkuList)) {
			for (ItemSkuDO sku : itemSkuList) {
				if (StringUtils.isNotBlank(sku.getProperty())) {
					List<ItemSkuPVPair> pairs = JSON.parseArray(sku.getProperty(), ItemSkuPVPair.class);
					long pType = 0;
					String pName = "";
					long dTime = 0;
					long mTime = 0;
					long dType = 0;
					String dName = "";
					for (ItemSkuPVPair itemSkuPVPair : pairs) {
						if (itemSkuPVPair.getPId() == LinePropertyType.TRAVEL_PACKAGE.getType()) {
							pType = itemSkuPVPair.getVId();
							pName = itemSkuPVPair.getVTxt();
						}
						if (itemSkuPVPair.getPId() == LinePropertyType.DEPART_DATE.getType()) {
							dTime = Long.parseLong(itemSkuPVPair.getVTxt());
							Calendar c = Calendar.getInstance();
							c.setTimeInMillis(dTime);
							c.set(Calendar.DAY_OF_MONTH, 1);
							mTime = c.getTimeInMillis();
						}
						if (itemSkuPVPair.getPId() == LinePropertyType.PERSON.getType()) {
							dType = itemSkuPVPair.getVId();
							dName = itemSkuPVPair.getVTxt();
						}
					}
					// 组装套餐
					PackageInfo packageInfo = null;
					for (PackageInfo piItem : this.tcs) {
						if (piItem.getType() == pType) {
							packageInfo = piItem;
							break;
						}
					}
					if (packageInfo == null) {
						packageInfo = new PackageInfo();
						packageInfo.setType(pType);
						packageInfo.setName(pName);
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
						packageMonth = new PackageMonth();
						packageMonth.setDate(new Date(mTime));
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
						if (pdItem.getDate().getTime() == dTime) {
							packageDay = pdItem;
							break;
						}
					}
					if (packageDay == null) {
						packageDay = new PackageDay();
						packageDay.setDate(new Date(dTime));
						pds.add(packageDay);
					}
					// 块
					List<PackageBlock> pbs = packageDay.getPackageBlocks();
					if (pbs == null) {
						pbs = new ArrayList<PackageBlock>();
						packageDay.setPackageBlocks(pbs);
					}
					PackageBlock pb = new PackageBlock();
					pb.setType(dType);
					pb.setName(dName);
					pb.setPrice(sku.getPrice());
					pb.setStock(sku.getStockNum());
					pbs.add(pb);
				}
			}
			// 计算参数;
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

}
