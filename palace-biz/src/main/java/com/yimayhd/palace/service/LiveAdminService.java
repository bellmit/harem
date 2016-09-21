package com.yimayhd.palace.service;

import com.yimayhd.live.client.domain.record.CloseLiveRoomDTO;
import com.yimayhd.live.client.domain.record.LiveRoomDO;
import com.yimayhd.live.client.domain.record.UpdateLiveOrderDTO;
import com.yimayhd.live.client.domain.record.UpdateLiveRecordStatusDTO;
import com.yimayhd.live.client.query.LiveAdminPageQuery;
import com.yimayhd.live.client.query.LiveRoomPageQuery;
import com.yimayhd.live.client.result.record.*;
import com.yimayhd.palace.result.BizResult;

/**
 * Created by haozhu on 16/9/21.
 */
public interface LiveAdminService {

    /**
     * 获取直播列表
     * @param pageQuery
     * @return
     */
    public LiveRecordPageResult getPageLiveRecord(LiveAdminPageQuery pageQuery);

    /**
     * 更新直播排序
     * @param updateLiveOrderDTO
     * @return
     */
    public BizResult<String>  updateLiveOrderById(UpdateLiveOrderDTO updateLiveOrderDTO);

    /**
     * 修改直播记录状态
     * @param updateLiveRecordStatusDTO
     * @return
     */
    public BizResult<String> updateLiveRecordStatus(UpdateLiveRecordStatusDTO updateLiveRecordStatusDTO);

    /**
     * 关闭直播
     * @param liveRecordId
     * @return
     */
    public CloseLiveResult closeLive(long liveRecordId);

    /**
     * 创建直播间
     * @param liveRoomDO
     * @return
     */
    public CreateLiveRoomResult createLiveRoom(LiveRoomDO liveRoomDO);

    /**
     * 查询直播间列表
     * @param liveRoomPageQuery
     * @return
     */
    public LiveRoomPageResult getPageLiveRoom(LiveRoomPageQuery liveRoomPageQuery);

    /**
     * 关闭直播间
     * @param closeLiveRoomDTO
     * @return
     */
    public BizResult<String> closeLiveRoom(CloseLiveRoomDTO closeLiveRoomDTO);

    /**
     * 恢复直播间
     * @param liveRoomId
     * @return
     */
    public  BizResult<String> recoverLiveRoom(long liveRoomId);
}


