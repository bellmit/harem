package com.yimayhd.palace.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
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
import com.yimayhd.palace.repo.CommentRepo;
import com.yimayhd.palace.repo.LiveAdminRepo;
import com.yimayhd.palace.repo.TagRepo;
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

    @Autowired
    private CommentRepo commentRepo;

    @Resource
    private TagRepo tagRepo;

    /**
     * 获取直播列表
     *
     * @param pageQuery
     * @return
     */
    @Override
    public PageVO<LiveRecordVO> getPageLiveRecord(LiveAdminQuery pageQuery) {
        // 昵称和id同时输入查询 只有一致才会返回
        List<Long> userQueryIds = new ArrayList<Long>();
        if (pageQuery.getNickName() != null && !pageQuery.getNickName().equals("")) {
            List<UserDO> userDOs = userRepo.getUserByNickname(pageQuery.getNickName());
            if (userDOs.size() == 0)
                return new PageVO<LiveRecordVO>();
            if (pageQuery.getUserId() != null && pageQuery.getUserId().longValue() >0) {
                for (UserDO userDO : userDOs) {
                    if (userDO.getId() == pageQuery.getUserId().longValue()) {
                        userQueryIds.add(pageQuery.getUserId().longValue());
                        break;
                    }
                }
                if (userQueryIds.size() == 0)
                    return new PageVO<LiveRecordVO>();
            } else {
                for (UserDO userDO : userDOs) {
                    userQueryIds.add(userDO.getId());
                }
            }
        } else {
            if (pageQuery.getUserId() != null && pageQuery.getUserId().longValue() >0)
                userQueryIds.add(pageQuery.getUserId());
        }
        // 查询直播列表
        LiveAdminPageQuery liveRoomPageQuery = LiveAdminConverter.liveAdminQuery2LiveAdminPageQuery(pageQuery);
        if (userQueryIds.size() > 0)
            liveRoomPageQuery.setUserIds(userQueryIds);
        LiveRecordPageResult liveRecordPageResult = liveAdminRepo.getPageLiveRecord(liveRoomPageQuery);
        if (liveRecordPageResult == null) {
            return new PageVO<LiveRecordVO>();
        }
        List<Long> userIds = new ArrayList<Long>();
        List<Long> categorys = new ArrayList<Long>();
        List<LiveRecordVO> liveRecordVOList = new ArrayList<LiveRecordVO>();
        List<LiveRecordDO> liveRecordDOList = liveRecordPageResult.getList();
        for (LiveRecordDO liveRecordDO : liveRecordDOList) {
            liveRecordVOList.add(LiveAdminConverter.liveRecordDO2LiveRecordVO(liveRecordDO));
            userIds.add(liveRecordDO.getUserId());
            categorys.add(liveRecordDO.getLiveCategory());
        }
        // 获取昵称
        if (!CollectionUtils.isEmpty(userIds)) {
            List<UserDO> userDOs = userRepo.getUsers(userIds);
            for (int i = 0; i < liveRecordDOList.size(); i++) {
                LiveRecordVO liveRecordVO = liveRecordVOList.get(i);
                for (int j = 0; j < userDOs.size(); j++) {
                    UserDO userDO = userDOs.get(j);
                    if (userDO.getId() == liveRecordVO.getUserId()) {
                        liveRecordVO.setUserDO(userDO);
                    }
                }
            }
        }

        // 直播分类
        if (categorys.size() > 0) {
            List<ComTagDO> comTagDOList = commentRepo.selectTagsIn(categorys);
            if (comTagDOList != null && comTagDOList.size() > 0) {
                for (int i = 0; i < liveRecordDOList.size(); i++) {
                    LiveRecordVO liveRecordVO = liveRecordVOList.get(i);
                    for (int j = 0; j < comTagDOList.size(); j++) {
                        ComTagDO comTagDO = comTagDOList.get(j);
                        if (comTagDO.getId() == liveRecordVO.getLiveCategory()) {
                            liveRecordVO.setLiveCategoryString(comTagDO.getName());
                        }
                    }
                }
            }
        }
        return new PageVO<LiveRecordVO>(liveRecordPageResult.getPageNo(), liveRecordPageResult.getPageSize(), liveRecordPageResult.getTotalCount(), liveRecordVOList);
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
        // // TODO: 16/9/30  增加修改时间
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
        // 昵称和id同时输入 只有统一才会返回
        List<Long> userQueryIds = new ArrayList<Long>();
        if (liveRoomQuery.getNickName() != null && !liveRoomQuery.getNickName().equals("")) {
            List<UserDO> userDOs = userRepo.getUserByNickname(liveRoomQuery.getNickName());
            if (userDOs.size() == 0)
                return new PageVO<LiveRoomVO>();
            if (liveRoomQuery.getUserId() != null && liveRoomQuery.getUserId().longValue() >0) {
                for (UserDO userDO : userDOs) {
                    if (userDO.getId() == liveRoomQuery.getUserId().longValue()) {
                        userQueryIds.add(liveRoomQuery.getUserId().longValue());
                        break;
                    }
                }
                if (userQueryIds.size() == 0)
                    return new PageVO<LiveRoomVO>();
            } else {
                for (UserDO userDO : userDOs) {
                    userQueryIds.add(userDO.getId());
                }
            }
        } else {
            if (liveRoomQuery.getUserId() != null && liveRoomQuery.getUserId().longValue() > 0)
                userQueryIds.add(liveRoomQuery.getUserId());
        }
        List<Long> liveQueryRoomIds = new ArrayList<Long>();
        LiveRoomPageQuery liveRoomPageQuery = LiveAdminConverter.liveRoomQuery2LiveRoomPageQuery(liveRoomQuery);
        if (userQueryIds.size() > 0)
            liveRoomPageQuery.setUserIds(userQueryIds);
        if (liveRoomQuery.getLiveRoomId() != null) {
            liveQueryRoomIds.add(liveRoomQuery.getLiveRoomId());
            liveRoomPageQuery.setLiveRoomIds(liveQueryRoomIds);
        }
        LiveRoomPageResult liveRoomPageResult = liveAdminRepo.getPageLiveRoom(liveRoomPageQuery);
        if (liveRoomPageResult == null) {
            return new PageVO<LiveRoomVO>();
        }
        List<Long> userIds = new ArrayList<Long>();
        List<Long> categorys = new ArrayList<Long>();
        List<LiveRoomVO> liveRoomVOList = new ArrayList<LiveRoomVO>();
        List<LiveRoomDO> liveRoomDOList = liveRoomPageResult.getList();
        for (LiveRoomDO liveRoomDO : liveRoomDOList) {
            liveRoomVOList.add(LiveAdminConverter.liveRoomDO2LiveRoomVO(liveRoomDO));
            userIds.add(liveRoomDO.getUserId());
            categorys.add(liveRoomDO.getLiveCategory());
        }

        // 获取昵称
        if (!CollectionUtils.isEmpty(userIds)) {
            List<UserDO> userDOs = userRepo.getUsers(userIds);
            for (int i = 0; i < liveRoomVOList.size(); i++) {
                LiveRoomVO liveRoomVO = liveRoomVOList.get(i);
                for (int j = 0; j < userDOs.size(); j++) {
                    UserDO userDO = userDOs.get(j);
                    if (userDO.getId() == liveRoomVO.getUserId()) {
                        liveRoomVO.setUserDO(userDO);
                    }
                }
            }
        }
        return new PageVO<LiveRoomVO>(liveRoomPageResult.getPageNo(), 10, liveRoomPageResult.getTotalCount(), liveRoomVOList);
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

