package com.yimayhd.harem.util;

import com.yimayhd.ic.client.model.domain.FacilityIconDO;

import java.util.*;

/**
 * Created by Administrator on 2015/11/20.
 */
public class BitUtil {
    /*
	* index: 表示facilityBits对应map中key从（index*64）到（index*64 + 63）的范围，
	* 如index为0时，表示facilityBits代表了map中前64个facility
	*/
    public static List<String> convertFacility(long facilityBits, Map<Integer, FacilityIconDO> map, int index) {
        if(map == null)
            return null;
        List<String> facilities = new ArrayList<String>();
        long mask = 1;
        for(int i = 0;i < 64;i++) {
            if(map.get(i + index * 64) == null)
                break;
            if((facilityBits & mask) == 1) {
                facilities.add(map.get(i + index * 64).getName());
            }
            facilityBits = facilityBits >>> 1;
        }
        return facilities;
    }
    /*
	* list转bit
	* 如index为0时，表示facilityBits代表了map中前64个facility
	*/
    public static long convertLong(List<String> facilityList,int index) {
        if(facilityList == null || facilityList.size() == 0){
            return 0;
        }
        //排序
        Collections.sort(facilityList);
        long facilityBits = 0;
        for (String facility:facilityList){
            facilityBits = facilityBits | 1 << Integer.parseInt(facility);
        }

        return facilityBits;
    }

}
