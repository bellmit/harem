package com.yimayhd.palace.model.item;


/**
 * 商品信息
 * 
 * @author hognfei.guo
 *
 */
public class ItemInfoVO {
	
	private ItemVO itemVO;
	private IcMerchantVO icMerchantVO;
	
	public ItemVO getItemVO() {
		return itemVO;
	}
	public void setItemVO(ItemVO itemVO) {
		this.itemVO = itemVO;
	}
	public IcMerchantVO getIcMerchantVO() {
		return icMerchantVO;
	}
	public void setIcMerchantVO(IcMerchantVO icMerchantVO) {
		this.icMerchantVO = icMerchantVO;
	}
}
