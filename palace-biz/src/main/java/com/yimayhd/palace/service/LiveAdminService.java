package com.yimayhd.palace.service;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.live.client.domain.record.CloseLiveRoomDTO;
import com.yimayhd.live.client.domain.record.LiveRoomDO;
import com.yimayhd.live.client.domain.record.UpdateLiveOrderDTO;
import com.yimayhd.live.client.domain.record.UpdateLiveRecordStatusDTO;
import com.yimayhd.live.client.query.LiveAdminPageQuery;
import com.yimayhd.live.client.query.LiveRecordQuery;
import com.yimayhd.live.client.query.LiveRoomPageQuery;
import com.yimayhd.live.client.result.record.CreateLiveRoomResult;
import com.yimayhd.live.client.result.record.QueryLiveRecordResult;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.LiveAdmin.LiveRecordVO;
import com.yimayhd.palace.model.LiveAdmin.LiveRoomVO;
import com.yimayhd.palace.model.query.LiveAdminQuery;
import com.yimayhd.palace.model.query.LiveRoomQuery;
import com.yimayhd.palace.result.BizResult;

import java.util.List;

/**
 * Created by haozhu on 16/9/21.
 */
public interface LiveAdminService {

    /**
     * 获取直播列表
     *
     * @param pageQuery
     * @return
     */
    public PageVO<LiveRecordVO> getPageLiveRecord(LiveAdminQuery pageQuery);

    /**
     * 查询单个直播
     * @param recordQuery
     * @return
     */
    public LiveRecordVO getLiveRecord(LiveRecordQuery recordQuery);

    /**
     * 获取直播分类
     *
     * @param tagType
     * @return
     */
    public List<ComTagDO> getTagListByTagType(TagType tagType);


    /**
     * 更新直播排序
     *
     * @param updateLiveOrderDTO
     * @return
     */
    public BizResult<String> updateLiveOrderById(UpdateLiveOrderDTO updateLiveOrderDTO);

    /**
     * 修改直播记录状态
     *
     * @param updateLiveRecordStatusDTO
     * @return
     */
    public BizResult<String> updateLiveRecordStatus(UpdateLiveRecordStatusDTO updateLiveRecordStatusDTO);

    /**
     * 关闭直播
     * @param liveRecordId
     * @return
     */
//    public CloseLiveResult closeLive(long liveRecordId);

    /**
     * 创建直播间
     *
     * @param liveRoomDO
     * @return
     */
    public CreateLiveRoomResult createLiveRoom(LiveRoomDO liveRoomDO);

    /**
     * 查询直播间列表
     *
     * @param liveRoomQuery
     * @return
     */
    public PageVO<LiveRoomVO> getPageLiveRoom(LiveRoomQuery liveRoomQuery);

    /**
     * 关闭直播间
     *
     * @param closeLiveRoomDTO
     * @return
     */
    public BizResult<String> closeLiveRoom(CloseLiveRoomDTO closeLiveRoomDTO);

    /**
     * 恢复直播间
     *
     * @param liveRoomId
     * @return
     */
    public BizResult<String> recoverLiveRoom(long liveRoomId);
}


