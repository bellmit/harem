package com.yimayhd.palace.convert;

import com.yimayhd.live.client.domain.record.LiveRecordDO;
import com.yimayhd.live.client.domain.record.LiveRoomDO;
import com.yimayhd.palace.model.LiveAdmin.LiveRecordVO;
import com.yimayhd.palace.model.LiveAdmin.LiveRoomVO;

/**
 * Created by haozhu on 16/9/22.
 */
public class LiveAdminConverter {
    // 直播列表
    public static LiveRecordVO liveRecordDO2LiveRecordVO(LiveRecordDO liveRecordDO) {
        if (liveRecordDO == null) {
            return null;
        }
        LiveRecordVO liveRecordVO = new LiveRecordVO();
        liveRecordVO.setId(liveRecordDO.getId());
        liveRecordVO.setUserId(liveRecordDO.getUserId());
        liveRecordVO.setLiveRoom(liveRecordDO.getLiveRoom());
        liveRecordVO.setLiveCategory(liveRecordDO.getLiveCategory());
        liveRecordVO.setLiveTitle(liveRecordDO.getLiveTitle());
        liveRecordVO.setLiveDes(liveRecordDO.getLiveDes());
        liveRecordVO.setLiveCover(liveRecordDO.getLiveCover());
        liveRecordVO.setLiveStatus(liveRecordDO.getLiveStatus());
        liveRecordVO.setLocationCityCode(liveRecordDO.getLocationCityCode());
        liveRecordVO.setLocationCityName(liveRecordDO.getLocationCityName());
        liveRecordVO.setFeature(liveRecordDO.getFeature());
        liveRecordVO.setStartDate(liveRecordDO.getStartDate());
        liveRecordVO.setEndDate(liveRecordDO.getEndDate());
        liveRecordVO.setOnlineCount(liveRecordDO.getOnlineCount());
        liveRecordVO.setViewCount(liveRecordDO.getViewCount());
        liveRecordVO.setPeakCount(liveRecordDO.getPeakCount());
        liveRecordVO.setReplaySecond(liveRecordDO.getReplaySecond());
        liveRecordVO.setLiveOrder(liveRecordDO.getLiveOrder());
        liveRecordVO.setStatus(liveRecordDO.getStatus());
        liveRecordVO.setGmtCreated(liveRecordDO.getGmtCreated());
        liveRecordVO.setGmtModified(liveRecordDO.getGmtModified());
        return liveRecordVO;
    }

    // 房间管理
    public static LiveRoomVO liveRoomDO2LiveRoomVO(LiveRoomDO liveRoomDO) {
        if (liveRoomDO == null) {
            return null;
        }
        LiveRoomVO liveRoomVO = new LiveRoomVO();
        liveRoomVO.setId(liveRoomDO.getId());
        liveRoomVO.setUserId(liveRoomDO.getUserId());
        liveRoomVO.setLivingRecord(liveRoomDO.getLivingRecord());
        liveRoomVO.setLiveCategory(liveRoomDO.getLiveCategory());
        liveRoomVO.setLiveTitle(liveRoomDO.getLiveTitle());
        liveRoomVO.setRoomNotice(liveRoomDO.getRoomNotice());
        liveRoomVO.setRoomOrder(liveRoomDO.getRoomOrder());
        liveRoomVO.setFeature(liveRoomDO.getFeature());
        liveRoomVO.setStatus(liveRoomDO.getStatus());
        liveRoomVO.setGmtCreated(liveRoomDO.getGmtCreated());
        liveRoomVO.setGmtModified(liveRoomDO.getGmtModified());
        return liveRoomVO;
    }
}
