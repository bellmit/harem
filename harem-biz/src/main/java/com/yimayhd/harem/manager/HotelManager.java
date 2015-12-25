package com.yimayhd.harem.manager;

import com.yimayhd.harem.model.HotelVO;
import com.yimayhd.harem.repo.HotelRepo;
import com.yimayhd.ic.client.model.domain.HotelDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 */
public class HotelManager {

    private static final Logger log = LoggerFactory.getLogger(HotelManager.class);
    @Autowired
    private HotelRepo hotelRepo;


    public boolean modify(HotelVO hotelVO) {
        try {
            HotelDO hotelDO = HotelVO.getHotelDO(hotelVO);
            if (hotelDO == null) {
                return false;
            }
            return hotelRepo.updateHotel(hotelDO);
        } catch (Exception e) {
            log.error("HotelVO.getHotelDO(hotelVO); exception," + hotelVO,e);
        }
        return false;
    }
}
