package com.yimayhd.palace.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.live.client.domain.record.*;
import com.yimayhd.live.client.query.LiveAdminPageQuery;
import com.yimayhd.live.client.query.LiveRecordQuery;
import com.yimayhd.live.client.query.LiveRoomPageQuery;
import com.yimayhd.live.client.result.record.*;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.biz.LiveAdminBiz;
import com.yimayhd.palace.convert.LiveAdminConverter;
import com.yimayhd.palace.model.LiveAdmin.LiveRecordVO;
import com.yimayhd.palace.model.LiveAdmin.LiveRoomVO;
import com.yimayhd.palace.model.query.LiveAdminQuery;
import com.yimayhd.palace.model.query.LiveRoomQuery;
import com.yimayhd.palace.repo.LiveAdminRepo;
import com.yimayhd.palace.repo.TagRepo;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.palace.service.LiveAdminService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by haozhu on 16/9/21.
 */
public class LiveAdminServiceImpl implements LiveAdminService {

    @Resource
    private LiveAdminRepo liveAdminRepo;

    @Resource
    private TagRepo tagRepo;

    @Resource
    private LiveAdminBiz liveAdminBiz;

    /**
     * 获取直播列表
     *
     * @param pageQuery
     * @return
     */
    @Override
    public PageVO<LiveRecordVO> getPageLiveRecord(LiveAdminQuery pageQuery) {
        // 查询直播列表
        LiveAdminPageQuery liveRoomPageQuery = LiveAdminConverter.liveAdminQuery2LiveAdminPageQuery(pageQuery);
        if(StringUtils.isNotEmpty(pageQuery.getNickName()) || pageQuery.getUserId() != null){
            List<Long>userQueryIds = liveAdminBiz.filterSearchNickNameToUserId(pageQuery);
            if (userQueryIds.size() > 0) {
                liveRoomPageQuery.setUserIds(userQueryIds);
            }
            else {
                return new PageVO<LiveRecordVO>();
            }
        }
        LiveRecordPageResult liveRecordPageResult = liveAdminRepo.getPageLiveRecord(liveRoomPageQuery);
        if (liveRecordPageResult == null) {
            return new PageVO<LiveRecordVO>();
        }
        List<LiveRecordVO> liveRecordVOList = new ArrayList<LiveRecordVO>();
        List<LiveRecordDO> liveRecordDOList = liveRecordPageResult.getList();
        for (LiveRecordDO liveRecordDO : liveRecordDOList) {
            liveRecordVOList.add(LiveAdminConverter.liveRecordDO2LiveRecordVO(liveRecordDO));
        }
        return new PageVO<LiveRecordVO>(liveRecordPageResult.getPageNo(), liveRecordPageResult.getPageSize(), liveRecordPageResult.getTotalCount(), liveRecordVOList);
    }

    /**
     * 查询单个直播
     * @param recordQuery
     * @return
     */
    public LiveRecordVO getLiveRecord(LiveRecordQuery recordQuery){
        QueryLiveRecordResult queryLiveRecordResult = liveAdminRepo.getLiveRecord(recordQuery);
        return LiveAdminConverter.liveRecordDO2LiveRecordVO(queryLiveRecordResult.getValue());
    }

    /**
     * 获取直播分类
     *
     * @param tagType
     * @return
     */
    public List<ComTagDO> getTagListByTagType(TagType tagType) {
        List<ComTagDO> comTagDOList = tagRepo.getTagListByTagType(tagType);
        return comTagDOList;
    }

    /**
     * 更新直播排序
     *
     * @param updateLiveOrderDTO
     * @return
     */
    public BizResult<String> updateLiveOrderById(UpdateLiveOrderDTO updateLiveOrderDTO) {
        BizResult<String> bizResult = new BizResult<String>();
        UpdateLiveOrderResult updateLiveOrderResult = liveAdminRepo.updateLiveOrderById(updateLiveOrderDTO);
        bizResult.buildSuccessResult(updateLiveOrderDTO);
        return bizResult;
    }

    /**
     * 修改直播记录状态
     *
     * @param updateLiveRecordStatusDTO
     * @return
     */
    public BizResult<String> updateLiveRecordStatus(UpdateLiveRecordStatusDTO updateLiveRecordStatusDTO) {
        BizResult<String> bizResult = new BizResult<String>();
        UpdateLiveRecordStatusResult updateLiveRecordStatusResult = liveAdminRepo.updateLiveRecordStatus(updateLiveRecordStatusDTO);
        bizResult.buildSuccessResult(updateLiveRecordStatusResult);
        return bizResult;
    }

    /**
     * 关闭直播
     *
     * @param liveRecordId
     * @return
     */
//    public CloseLiveResult closeLive(long liveRecordId) {
//        return liveAdminRepo.closeLive(liveRecordId);
//    }

    /**
     * 创建直播间
     *
     * @param liveRoomDO
     * @return
     */
    public CreateLiveRoomResult createLiveRoom(LiveRoomDO liveRoomDO) {
        return liveAdminRepo.createLiveRoom(liveRoomDO);
    }

    /**
     * 查询直播间列表
     *
     * @param liveRoomQuery
     * @return
     */
    public PageVO<LiveRoomVO> getPageLiveRoom(LiveRoomQuery liveRoomQuery) {
        List<Long> liveQueryRoomIds = new ArrayList<Long>();
        LiveRoomPageQuery liveRoomPageQuery = LiveAdminConverter.liveRoomQuery2LiveRoomPageQuery(liveRoomQuery);
        if(StringUtils.isNotEmpty(liveRoomQuery.getNickName()) || liveRoomQuery.getUserId() != null) {
            List<Long> userQueryIds = liveAdminBiz.filterSearchNickNameToUserId(liveRoomQuery);
            if (userQueryIds.size() > 0) {
                liveRoomPageQuery.setUserIds(userQueryIds);
            }
            else {
                return new PageVO<LiveRoomVO>();
            }
        }
        if (liveRoomQuery.getLiveRoomId() != null) {
            liveQueryRoomIds.add(liveRoomQuery.getLiveRoomId());
            liveRoomPageQuery.setLiveRoomIds(liveQueryRoomIds);
        }
        LiveRoomPageResult liveRoomPageResult = liveAdminRepo.getPageLiveRoom(liveRoomPageQuery);
        if (liveRoomPageResult == null) {
            return new PageVO<LiveRoomVO>();
        }
        List<LiveRoomVO> liveRoomVOList = new ArrayList<LiveRoomVO>();
        List<LiveRoomDO> liveRoomDOList = liveRoomPageResult.getList();
        for (LiveRoomDO liveRoomDO : liveRoomDOList) {
            liveRoomVOList.add(LiveAdminConverter.liveRoomDO2LiveRoomVO(liveRoomDO));
        }
        return new PageVO<LiveRoomVO>(liveRoomPageResult.getPageNo(), liveRoomPageResult.getPageSize(), liveRoomPageResult.getTotalCount(), liveRoomVOList);
    }

    /**
     * 关闭直播间
     *
     * @param closeLiveRoomDTO
     * @return
     */
    public BizResult<String> closeLiveRoom(CloseLiveRoomDTO closeLiveRoomDTO) {
        BizResult<String> bizResult = new BizResult<String>();
        CloseLiveRoomResult closeLiveRoomResult = liveAdminRepo.closeLiveRoom(closeLiveRoomDTO);
        bizResult.buildSuccessResult(closeLiveRoomResult);
        return bizResult;
    }

    /**
     * 恢复直播间
     *
     * @param liveRoomId
     * @return
     */
    public BizResult<String> recoverLiveRoom(long liveRoomId) {
        BizResult<String> bizResult = new BizResult<String>();
        RecoverLiveRoomResult recoverLiveRoomResult = liveAdminRepo.recoverLiveRoom(liveRoomId);
        bizResult.buildSuccessResult(recoverLiveRoomResult);
        return bizResult;
    }
}

