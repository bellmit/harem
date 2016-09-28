package com.yimayhd.palace.convert;

import com.yimayhd.live.client.domain.record.LiveRecordDO;
import com.yimayhd.live.client.domain.record.LiveRoomDO;
import com.yimayhd.live.client.enums.LiveOrder;
import com.yimayhd.live.client.query.LiveAdminPageQuery;
import com.yimayhd.live.client.query.LiveRoomPageQuery;
import com.yimayhd.palace.model.LiveAdmin.LiveRecordVO;
import com.yimayhd.palace.model.LiveAdmin.LiveRoomVO;
import com.yimayhd.palace.model.query.LiveAdminQuery;
import com.yimayhd.palace.model.query.LiveRoomQuery;
import com.yimayhd.live.client.enums.LiveOrder;

import static com.yimayhd.live.client.enums.LiveOrder.START_TIME_DESC;


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

    // 直播列表查询
    public static LiveAdminPageQuery liveAdminQuery2LiveAdminPageQuery(LiveAdminQuery liveAdminQuery) {
        if (liveAdminQuery == null) {
            return null;
        }
        LiveAdminPageQuery liveAdminPageQuery = new LiveAdminPageQuery();
        liveAdminPageQuery.setLiveRoomId(liveAdminQuery.getLiveRoomId());
        liveAdminPageQuery.setLiveCategory(liveAdminQuery.getLiveCategory());
        liveAdminPageQuery.setLiveStatus(liveAdminQuery.getLiveStatus());
        liveAdminPageQuery.setLocationCityCode(liveAdminQuery.getLocationCityCode());
        liveAdminPageQuery.setLocationCityName(liveAdminQuery.getLocationCityName());
        liveAdminPageQuery.setStartDate(liveAdminQuery.getStartDate());
        liveAdminPageQuery.setEndDate(liveAdminQuery.getEndDate());
        liveAdminPageQuery.setViewCount(liveAdminQuery.getViewCount());
        liveAdminPageQuery.setOnlineCount(liveAdminQuery.getOnlineCount());
        liveAdminPageQuery.setStartSecondTime(liveAdminQuery.getStartSecondTime());
        liveAdminPageQuery.setReplaySecondTime(liveAdminQuery.getReplaySecondTime());
        liveAdminPageQuery.setPageNo(liveAdminQuery.getPageNo());
        liveAdminPageQuery.setPageSize(liveAdminQuery.getOldPageSize());
        liveAdminPageQuery.setLiveOrder(START_TIME_DESC);
        return liveAdminPageQuery;
    }

    // 直播房间查询
    public static LiveRoomPageQuery liveRoomQuery2LiveRoomPageQuery(LiveRoomQuery liveRoomQuery) {
        if (liveRoomQuery == null) {
            return null;
        }
        LiveRoomPageQuery liveRoomPageQuery = new LiveRoomPageQuery();
//        liveRoomPageQuery.setUserIds(liveRoomQuery.getUserIds());
//        liveRoomPageQuery.setLiveRoomIds(liveRoomQuery.getLiveRoomIds());
        liveRoomPageQuery.setStatus(liveRoomQuery.getStatus());
        return liveRoomPageQuery;
    }
}
