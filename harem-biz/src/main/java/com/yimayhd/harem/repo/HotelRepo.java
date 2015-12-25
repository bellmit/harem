package com.yimayhd.harem.repo;

import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.service.item.HotelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 */
public class HotelRepo {
    private static final Logger log = LoggerFactory.getLogger(HotelRepo.class);
    @Autowired
    private HotelService hotelService;

    public boolean updateHotel(HotelDO hotelDO) {
        try {
            ICResult<Boolean> ret = hotelService.updateHotel(hotelDO);
            if (ret != null && ret.isSuccess() && ret.getModule() != null && ret.getModule()) {
                return true;
            }
        } catch (Exception e) {
            log.error("hotelService.updateHotel(hotelDO) exception," + hotelDO,e);
        }
        return false;
    }
}
