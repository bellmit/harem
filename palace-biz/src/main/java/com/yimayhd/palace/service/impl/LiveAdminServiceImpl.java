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
     * @param pageQuery
     * @return
     */
    @Override
    public PageVO<LiveRecordVO> getPageLiveRecord(LiveAdminPageQuery pageQuery){
        LiveRecordPageResult liveRecordPageResult =  liveAdminRepo.getPageLiveRecord(pageQuery);
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
        if(!CollectionUtils.isEmpty(userIds) ){
            List<UserDO> userDOs = userRepo.getUsers(userIds);
            if(userDOs.size() == liveRecordVOList.size()) {
                for (int i = 0; i < userDOs.size(); i++) {
                    UserDO userDO = userDOs.get(i);
                    LiveRecordVO liveRecordVO = liveRecordVOList.get(i);
                    if (userDO.getId() == liveRecordVOList.get(i).getUserId()) {
                        liveRecordVO.setUserDO(userDO);
                    }
                }
            }
        }
        liveRecordPageResult.setPageSize(pageQuery.getPageSize());
        return new PageVO<LiveRecordVO>(liveRecordPageResult.getPageNo(), liveRecordPageResult.getPageSize(), liveRecordPageResult.getTotalCount(), liveRecordVOList);
    }

    /**
     * 更新直播排序
     * @param updateLiveOrderDTO
     * @return
     */
    public BizResult<String> updateLiveOrderById(UpdateLiveOrderDTO updateLiveOrderDTO){
        BizResult<String> bizResult = new BizResult<String>();
        UpdateLiveOrderResult updateLiveOrderResult = liveAdminRepo.updateLiveOrderById(updateLiveOrderDTO);
        bizResult.buildSuccessResult(updateLiveOrderDTO);
        return bizResult;
    }

    /**
     * 修改直播记录状态
     * @param updateLiveRecordStatusDTO
     * @return
     */
    public BizResult<String> updateLiveRecordStatus(UpdateLiveRecordStatusDTO updateLiveRecordStatusDTO){
        BizResult<String> bizResult = new BizResult<String>();
        UpdateLiveRecordStatusResult updateLiveRecordStatusResult = liveAdminRepo.updateLiveRecordStatus(updateLiveRecordStatusDTO);
        bizResult.buildSuccessResult(updateLiveRecordStatusResult);
        return bizResult;
    }

    /**
     * 关闭直播
     * @param liveRecordId
     * @return
     */
    public CloseLiveResult closeLive(long liveRecordId){
        return liveAdminRepo.closeLive(liveRecordId);
    }

    /**
     * 创建直播间
     * @param liveRoomDO
     * @return
     */
    public CreateLiveRoomResult createLiveRoom(LiveRoomDO liveRoomDO){
        return liveAdminRepo.createLiveRoom(liveRoomDO);
    }

    /**
     * 查询直播间列表
     * @param liveRoomPageQuery
     * @return
     */
    public PageVO<LiveRoomVO> getPageLiveRoom(LiveRoomPageQuery liveRoomPageQuery){
        LiveRoomPageResult liveRoomPageResult =  liveAdminRepo.getPageLiveRoom(liveRoomPageQuery);;
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
        if(!CollectionUtils.isEmpty(userIds) ){
            List<UserDO> userDOs = userRepo.getUsers(userIds);
            if(userDOs.size() == liveRoomVOList.size()) {
                for (int i = 0; i < userDOs.size(); i++) {
                    UserDO userDO = userDOs.get(i);
                    LiveRoomVO liveRoomVO = liveRoomVOList.get(i);
                    if (userDO.getId() == liveRoomVOList.get(i).getUserId()) {
                        liveRoomVO.setUserDO(userDO);
                    }
                }
            }
        }
        liveRoomPageResult.setPageSize(liveRoomPageQuery.getPageSize());
        return new PageVO<LiveRoomVO>(liveRoomPageResult.getPageNo(), liveRoomPageResult.getPageSize(), liveRoomPageResult.getTotalCount(), liveRoomVOList);
    }

    /**
     * 关闭直播间
     * @param closeLiveRoomDTO
     * @return
     */
    public BizResult<String> closeLiveRoom(CloseLiveRoomDTO closeLiveRoomDTO){
        BizResult<String> bizResult = new BizResult<String>();
        CloseLiveRoomResult closeLiveRoomResult = liveAdminRepo.closeLiveRoom(closeLiveRoomDTO);
        bizResult.buildSuccessResult(closeLiveRoomResult);
        return bizResult;
    }

    /**
     * 恢复直播间
     * @param liveRoomId
     * @return
     */
    public BizResult<String> recoverLiveRoom(long liveRoomId){
        BizResult<String> bizResult = new BizResult<String>();
        RecoverLiveRoomResult recoverLiveRoomResult = liveAdminRepo.recoverLiveRoom(liveRoomId);
        bizResult.buildSuccessResult(recoverLiveRoomResult);
        return bizResult;
    }
}

