package com.yimayhd.palace.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yimayhd.live.client.domain.record.*;
import com.yimayhd.live.client.query.LiveAdminPageQuery;
import com.yimayhd.live.client.query.LiveRoomPageQuery;
import com.yimayhd.live.client.result.record.*;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.convert.LiveAdminConverter;
import com.yimayhd.palace.model.LiveAdmin.LiveRecordVO;
import com.yimayhd.palace.model.LiveAdmin.LiveRoomVO;
import com.yimayhd.palace.model.query.LiveAdminQuery;
import com.yimayhd.palace.model.query.LiveRoomQuery;
import com.yimayhd.palace.repo.LiveAdminRepo;
import com.yimayhd.palace.repo.user.UserRepo;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.palace.service.LiveAdminService;
import com.yimayhd.user.client.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by haozhu on 16/9/21.
 */
public class LiveAdminServiceImpl implements LiveAdminService {

    @Resource
    private LiveAdminRepo liveAdminRepo;

    @Autowired
    private UserRepo userRepo;

    /**
     * 获取直播列表
     *
     * @param pageQuery
     * @return
     */
    @Override
    public PageVO<LiveRecordVO> getPageLiveRecord(LiveAdminQuery pageQuery) {
        // 昵称和id同时输入 只有统一才会返回
        List<Long> userQueryIds = new ArrayList<Long>();
        if (pageQuery.getNickName() != null && !pageQuery.getNickName().equals("")) {
            List<UserDO> userDOs = userRepo.getUserByNickname(pageQuery.getNickName());
            if (userDOs.size() == 0)
                return new PageVO<LiveRecordVO>();
            if (pageQuery.getUserId() > 0) {
                for (UserDO userDO : userDOs) {
                    if (userDO.getId() == pageQuery.getUserId()) {
                        userQueryIds.add(pageQuery.getUserId());
                        break;
                    }
                }
                if (userQueryIds.size() == 0)
                    return new PageVO<LiveRecordVO>();
            }else {
                for (UserDO userDO : userDOs) {
                    if (userDO.getId() == pageQuery.getUserId()) {
                        userQueryIds.add(userDO.getId());
                    }
                }
            }
        }else {
            if(pageQuery.getUserId() >0)
                userQueryIds.add(pageQuery.getUserId());
        }
        LiveAdminPageQuery liveRoomPageQuery = LiveAdminConverter.liveAdminQuery2LiveAdminPageQuery(pageQuery);
        if (userQueryIds.size()>0)
            liveRoomPageQuery.setUserIds(userQueryIds);
        LiveRecordPageResult liveRecordPageResult = liveAdminRepo.getPageLiveRecord(liveRoomPageQuery);
        if (liveRecordPageResult == null) {
            return new PageVO<LiveRecordVO>();
        }
        List<Long> userIds = new ArrayList<Long>();
        List<LiveRecordVO> liveRecordVOList = new ArrayList<LiveRecordVO>();
        List<LiveRecordDO> liveRecordDOList = liveRecordPageResult.getList();
        for (LiveRecordDO liveRecordDO : liveRecordDOList) {
            liveRecordVOList.add(LiveAdminConverter.liveRecordDO2LiveRecordVO(liveRecordDO));
            userIds.add(liveRecordDO.getUserId());
        }
        // // TODO: 16/9/22 需要优化
        if (!CollectionUtils.isEmpty(userIds)) {
            List<UserDO> userDOs = userRepo.getUsers(userIds);
            if (userDOs.size() == liveRecordVOList.size()) {
                for (int i = 0; i < userDOs.size(); i++) {
                    UserDO userDO = userDOs.get(i);
                    LiveRecordVO liveRecordVO = liveRecordVOList.get(i);
                    if (userDO.getId() == liveRecordVOList.get(i).getUserId()) {
                        liveRecordVO.setUserDO(userDO);
                    }
                }
            }
        }
        /// // TODO: 16/9/23 标签分类

        liveRecordPageResult.setPageSize(pageQuery.getPageSize());
        return new PageVO<LiveRecordVO>(liveRecordPageResult.getPageNo(), liveRecordPageResult.getPageSize(), liveRecordPageResult.getTotalCount(), liveRecordVOList);
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
    public CloseLiveResult closeLive(long liveRecordId) {
        return liveAdminRepo.closeLive(liveRecordId);
    }

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
        // 昵称和id同时输入 只有统一才会返回
        List<Long> userQueryIds = new ArrayList<Long>();
        if (!liveRoomQuery.getNickName().equals("")) {
            List<UserDO> userDOs = userRepo.getUserByNickname(liveRoomQuery.getNickName());
            if (userDOs.size() == 0)
                return new PageVO<LiveRoomVO>();
            if (liveRoomQuery.getUserId() > 0) {
                for (UserDO userDO : userDOs) {
                    if (userDO.getId() == liveRoomQuery.getUserId()) {
                        userQueryIds.add(liveRoomQuery.getUserId());
                        break;
                    }
                }
                if (userQueryIds.size() == 0)
                    return new PageVO<LiveRoomVO>();
            }else {
                for (UserDO userDO : userDOs) {
                    if (userDO.getId() == liveRoomQuery.getUserId()) {
                        userQueryIds.add(userDO.getId());
                    }
                }
            }
        }else {
            if(liveRoomQuery.getUserId() >0)
                userQueryIds.add(liveRoomQuery.getUserId());
        }
        List<Long> liveQueryRoomIds = new ArrayList<Long>();
        LiveRoomPageQuery liveRoomPageQuery = LiveAdminConverter.liveRoomQuery2LiveRoomPageQuery(liveRoomQuery);
        if (liveRoomQuery.getLiveRoomId() >0) {
            liveQueryRoomIds.add(liveRoomQuery.getLiveRoomId());
            liveRoomPageQuery.setLiveRoomIds(liveQueryRoomIds);
        }
        LiveRoomPageResult liveRoomPageResult = liveAdminRepo.getPageLiveRoom(liveRoomPageQuery);
        if (liveRoomPageResult == null) {
            return new PageVO<LiveRoomVO>();
        }
        List<Long> userIds = new ArrayList<Long>();
        List<LiveRoomVO> liveRoomVOList = new ArrayList<LiveRoomVO>();
        List<LiveRoomDO> liveRoomDOList = liveRoomPageResult.getList();
        for (LiveRoomDO liveRoomDO : liveRoomDOList) {
            liveRoomVOList.add(LiveAdminConverter.liveRoomDO2LiveRoomVO(liveRoomDO));
            userIds.add(liveRoomDO.getUserId());
        }
        // // TODO: 16/9/22 需要优化
        if (!CollectionUtils.isEmpty(userIds)) {
            List<UserDO> userDOs = userRepo.getUsers(userIds);
            if (userDOs.size() == liveRoomVOList.size()) {
                for (int i = 0; i < userDOs.size(); i++) {
                    UserDO userDO = userDOs.get(i);
                    LiveRoomVO liveRoomVO = liveRoomVOList.get(i);
                    if (userDO.getId() == liveRoomVOList.get(i).getUserId()) {
                        liveRoomVO.setUserDO(userDO);
                    }
                }
            }
        }
        /// // TODO: 16/9/23 标签分类


        liveRoomPageResult.setPageSize(liveRoomQuery.getPageSize());
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

