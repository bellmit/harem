package com.yimayhd.palace.model.item;


/**
 * 店铺信息
 * 
 * @author hongfei.guo
 *
 */
public class IcMerchantVO {
	
	 //用户id
    private long userId;
    //用户昵称
    private String userNick;
    //用户个人头像
    private String userAvatar;

    //店铺id
    private long merchantId;
    //店铺名称
    private String merchantName;
    //店铺Logo
    private String merchantLogo;


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(long merchantId) {
        this.merchantId = merchantId;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantLogo() {
        return merchantLogo;
    }

    public void setMerchantLogo(String merchantLogo) {
        this.merchantLogo = merchantLogo;
    }
}
