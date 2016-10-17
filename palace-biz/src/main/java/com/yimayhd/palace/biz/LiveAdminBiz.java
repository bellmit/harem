package com.yimayhd.palace.biz;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.live.client.query.LiveRoomPageQuery;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.convert.LiveAdminConverter;
import com.yimayhd.palace.model.LiveAdmin.LiveRecordVO;
import com.yimayhd.palace.model.LiveAdmin.LiveRoomVO;
import com.yimayhd.palace.model.query.LiveAdminQuery;
import com.yimayhd.palace.model.query.LiveRoomQuery;
import com.yimayhd.palace.repo.CommentRepo;
import com.yimayhd.palace.repo.user.UserRepo;
import com.yimayhd.palace.service.LiveAdminService;
import com.yimayhd.user.client.domain.UserDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by haozhu on 16/9/21.
 */
public class LiveAdminBiz {

    private static final Logger log = LoggerFactory.getLogger("TrouistlistBiz");
    @Resource
    private LiveAdminService liveAdminService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CommentRepo commentRepo;

    /**
     * 获取直播回放详情列表
     *
     * @param pageQuery
     * @return
     */
    public PageVO<LiveRecordVO> getPageLiveRecord(LiveAdminQuery pageQuery) {
        // 查询回放列表
        PageVO<LiveRecordVO> page = liveAdminService.getPageLiveRecord(pageQuery);
        // 获取分页数据
        List<LiveRecordVO> liveRecordVOList = page.getItemList();
        // 获取昵称
        obtainNickName11(liveRecordVOList);
        // 获取分类
        obtainCategorys(liveRecordVOList);
        // 返回查询结果
        return page;
    }

    /**
     * 通过用户id获取用户信息
     *
     * @param pageQuery
     * @return
     */
    public List<Long> filterSearchNickNameToUserId(LiveAdminQuery pageQuery) {
        // 昵称和id同时输入查询 只有一致才会返回
        List<Long> userQueryIds = new ArrayList<Long>();
        if (pageQuery.getNickName() != null && !pageQuery.getNickName().equals("")) {
            List<UserDO> userDOs = userRepo.getUserByNickname(pageQuery.getNickName());
            if (pageQuery.getUserId() != null && pageQuery.getUserId().longValue() > 0) {
                for (UserDO userDO : userDOs) {
                    if (userDO.getId() == pageQuery.getUserId().longValue()) {
                        userQueryIds.add(pageQuery.getUserId().longValue());
                        break;
                    }
                }
            } else {
                for (UserDO userDO : userDOs) {
                    userQueryIds.add(userDO.getId());
                }
            }
        } else {
            if (pageQuery.getUserId() != null && pageQuery.getUserId().longValue() > 0) {
                userQueryIds.add(pageQuery.getUserId());
            }
        }
        return userQueryIds;
    }

    /**
     * 通过用户id获取用户信息
     *
     * @param liveRecordVOList
     * @return
     */
    public void obtainNickName11(List<LiveRecordVO> liveRecordVOList) {
        List<Long> userIds = new ArrayList<Long>();
        for (LiveRecordVO liveRecordVO : liveRecordVOList) {
            userIds.add(liveRecordVO.getUserId());
        }
        // 获取昵称
        if (!CollectionUtils.isEmpty(userIds)) {
            List<UserDO> userDOs = userRepo.getUsers(userIds);
            for (int i = 0; i < liveRecordVOList.size(); i++) {
                LiveRecordVO liveRecordVO = liveRecordVOList.get(i);
                for (int j = 0; j < userDOs.size(); j++) {
                    UserDO userDO = userDOs.get(j);
                    if (userDO.getId() == liveRecordVO.getUserId()) {
                        liveRecordVO.setUserDO(userDO);
                    }
                }
            }
        }
    }

    /**
     * 通过分类id获取分类信息
     *
     * @param liveRecordVOList
     * @return
     */
    public void obtainCategorys(List<LiveRecordVO> liveRecordVOList) {
        List<Long> categorys = new ArrayList<Long>();
        for (LiveRecordVO liveRecordVO : liveRecordVOList) {
            categorys.add(liveRecordVO.getLiveCategory());
        }
        // 直播分类
        if (categorys.size() > 0) {
            List<ComTagDO> comTagDOList = commentRepo.selectTagsIn(categorys);
            if (comTagDOList != null && comTagDOList.size() > 0) {
                for (int i = 0; i < liveRecordVOList.size(); i++) {
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
    }

    /**
     * 获取房间管理列表
     *
     * @param liveRoomQuery
     * @return
     */
    public PageVO<LiveRoomVO> getPageLiveRoom(LiveRoomQuery liveRoomQuery) {
        // 查询回放列表
        PageVO<LiveRoomVO> page = liveAdminService.getPageLiveRoom(liveRoomQuery);
        // 获取分页数据
        List<LiveRoomVO> liveRecordVOList = page.getItemList();
        // 获取昵称
        obtainNickName(liveRecordVOList);
        // 返回查询结果
        return page;
    }

    public List<Long> filterSearchNickNameToUserId(LiveRoomQuery liveRoomQuery) {
        // 昵称和id同时输入 只有统一才会返回
        List<Long> userQueryIds = new ArrayList<Long>();
        if (liveRoomQuery.getNickName() != null && !liveRoomQuery.getNickName().equals("")) {
            List<UserDO> userDOs = userRepo.getUserByNickname(liveRoomQuery.getNickName());
            if (liveRoomQuery.getUserId() != null && liveRoomQuery.getUserId().longValue() > 0) {
                for (UserDO userDO : userDOs) {
                    if (userDO.getId() == liveRoomQuery.getUserId().longValue()) {
                        userQueryIds.add(liveRoomQuery.getUserId().longValue());
                        break;
                    }
                }
            } else {
                for (UserDO userDO : userDOs) {
                    userQueryIds.add(userDO.getId());
                }
            }
        } else {
            if (liveRoomQuery.getUserId() != null && liveRoomQuery.getUserId().longValue() > 0) {
                userQueryIds.add(liveRoomQuery.getUserId());
            }
        }
        List<Long> liveQueryRoomIds = new ArrayList<Long>();
        LiveRoomPageQuery liveRoomPageQuery = LiveAdminConverter.liveRoomQuery2LiveRoomPageQuery(liveRoomQuery);
        if (userQueryIds.size() > 0) {
            liveRoomPageQuery.setUserIds(userQueryIds);
        }
        if (liveRoomQuery.getLiveRoomId() != null) {
            liveQueryRoomIds.add(liveRoomQuery.getLiveRoomId());
            liveRoomPageQuery.setLiveRoomIds(liveQueryRoomIds);
        }
        return userQueryIds;
    }

    /**
     * 通过用户id获取用户信息
     *
     * @param liveRoomVOList
     * @return
     */
    public void obtainNickName(List<LiveRoomVO> liveRoomVOList) {
        List<Long> userIds = new ArrayList<Long>();
        for (LiveRoomVO liveRoomVO : liveRoomVOList) {
            userIds.add(liveRoomVO.getUserId());
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
    }
}


