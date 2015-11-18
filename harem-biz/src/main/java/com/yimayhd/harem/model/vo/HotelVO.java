package com.yimayhd.harem.model.vo;

import com.yimayhd.harem.model.query.HotelListQuery;
import com.yimayhd.ic.client.model.domain.HotelDO;

/**
 * Created by Administrator on 2015/11/18.
 */
public class HotelVO {
    private HotelDO hotelDO;
    private HotelListQuery hotelListQuery;

    public HotelDO getHotelDO() {
        return hotelDO;
    }

    public void setHotelDO(HotelDO hotelDO) {
        this.hotelDO = hotelDO;
    }

    public HotelListQuery getHotelListQuery() {
        return hotelListQuery;
    }

    public void setHotelListQuery(HotelListQuery hotelListQuery) {
        this.hotelListQuery = hotelListQuery;
    }
}
