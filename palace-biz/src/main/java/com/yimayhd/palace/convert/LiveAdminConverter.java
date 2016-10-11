package com.yimayhd.palace.convert;

import com.yimayhd.live.client.domain.record.LiveRecordDO;
import com.yimayhd.live.client.domain.record.LiveRoomDO;
import com.yimayhd.live.client.enums.LiveRoomStatus;
import com.yimayhd.live.client.query.LiveAdminPageQuery;
import com.yimayhd.live.client.query.LiveRoomPageQuery;
import com.yimayhd.palace.model.LiveAdmin.LiveRecordVO;
import com.yimayhd.palace.model.LiveAdmin.LiveRoomVO;
import com.yimayhd.palace.model.query.LiveAdminQuery;
import com.yimayhd.palace.model.query.LiveRoomQuery;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.yimayhd.live.client.enums.LiveOrder.*;


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
        // 标题和话题
        if (liveRecordDO.getLiveTitle() != null) {
            String topic = new String();
            //正则表达式，取#和#之间的字符串，不包括#和#
            Pattern p = Pattern.compile("\\#(.*?)\\#");
            Matcher m = p.matcher(liveRecordDO.getLiveTitle());
            while(m.find()) {
                topic += m.group(1);
                topic += "\n";
            }
            liveRecordVO.setLiveTopic(topic);
            String[] ary = liveRecordDO.getLiveTitle().split("\\#");
            liveRecordVO.setLiveTitle(ary[ary.length-1]);
        }
//      liveRecordVO.setLiveDes(liveRecordDO.getLiveDes());
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
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (liveRecordVO.getStartDate() != null && liveRecordVO.getEndDate() != null) {
            liveRecordVO.setStartDateString(sdf.format(liveRecordVO.getStartDate()));
            liveRecordVO.setEndDateString(sdf.format(liveRecordVO.getStartDate()));
        }
        if (liveRecordDO.getStatus() == 1)
            liveRecordVO.setStatusString("上架");
        else if (liveRecordDO.getStatus() == 2)
            liveRecordVO.setStatusString("删除");
        else
            liveRecordVO.setStatusString("下架");
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
        liveRoomVO.setStatusString(LiveRoomStatus.getDesc(liveRoomDO.getStatus()));
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
        if (liveAdminQuery.getLiveRoomId() != null)
            liveAdminPageQuery.setLiveRoomId(liveAdminQuery.getLiveRoomId().longValue());
        if (liveAdminQuery.getLiveCategory() != null)
            liveAdminPageQuery.setLiveCategory(liveAdminQuery.getLiveCategory().longValue());
        liveAdminPageQuery.setLiveStatus(liveAdminQuery.getLiveStatus());
        liveAdminPageQuery.setLocationCityCode(liveAdminQuery.getLocationCityCode());
        liveAdminPageQuery.setLocationCityName(liveAdminQuery.getLocationCityName());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            if (liveAdminQuery.getStartDateString() != null && liveAdminQuery.getEndDateString() != null) {
                liveAdminPageQuery.setStartDate(sdf.parse(liveAdminQuery.getStartDateString()));
                liveAdminPageQuery.setEndDate(sdf.parse(liveAdminQuery.getEndDateString()));
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        liveAdminPageQuery.setViewCount(liveAdminQuery.getViewCount());
        liveAdminPageQuery.setOnlineCount(liveAdminQuery.getOnlineCount());
        liveAdminPageQuery.setStartSecondTime(liveAdminQuery.getStartSecondTime());
        liveAdminPageQuery.setReplaySecondTime(liveAdminQuery.getReplaySecondTime());
        liveAdminPageQuery.setPageNo(liveAdminQuery.getPageNumber());
        liveAdminPageQuery.setPageSize(liveAdminQuery.getPageSize());
        liveAdminPageQuery.setLiveRecordStatus(liveAdminQuery.getLiveRecordStatus());
        if (liveAdminQuery.getLiveOrder() != null) {
            if (liveAdminQuery.getLiveOrder().intValue() == 1)
                liveAdminPageQuery.setLiveOrder(START_TIME_DESC);
            else if (liveAdminQuery.getLiveOrder().intValue() == 2)
                liveAdminPageQuery.setLiveOrder(VIEW_COUNT_DESC);
            else
                liveAdminPageQuery.setLiveOrder(LIVE_WEIGHT_DESC);
        } else
            liveAdminPageQuery.setLiveOrder(START_TIME_DESC);// 默认
        return liveAdminPageQuery;
    }

    // 直播房间查询
    public static LiveRoomPageQuery liveRoomQuery2LiveRoomPageQuery(LiveRoomQuery liveRoomQuery) {
        if (liveRoomQuery == null) {
            return null;
        }
        LiveRoomPageQuery liveRoomPageQuery = new LiveRoomPageQuery();
        if (liveRoomQuery.getStatus() != null)
            liveRoomPageQuery.setStatus(liveRoomQuery.getStatus().intValue());
        return liveRoomPageQuery;
    }
}
