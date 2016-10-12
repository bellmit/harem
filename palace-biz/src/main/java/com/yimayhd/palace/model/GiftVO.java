package com.yimayhd.palace.model;

/**
 * Created by p on 9/1/16.
 */
public class GiftVO {
    private String title;
    private String imgUrl;
    private long price;
    private String priceY;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getPriceY() {
        return priceY;
    }

    public void setPriceY(String priceY) {
        this.priceY = priceY;
    }
}
