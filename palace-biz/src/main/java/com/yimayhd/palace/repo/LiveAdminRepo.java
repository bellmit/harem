package com.yimayhd.palace.repo;

import com.alibaba.fastjson.JSON;
import com.yimayhd.live.client.domain.record.CloseLiveRoomDTO;
import com.yimayhd.live.client.domain.record.LiveRoomDO;
import com.yimayhd.live.client.domain.record.UpdateLiveOrderDTO;
import com.yimayhd.live.client.domain.record.UpdateLiveRecordStatusDTO;
import com.yimayhd.live.client.query.LiveAdminPageQuery;
import com.yimayhd.live.client.query.LiveRoomPageQuery;
import com.yimayhd.live.client.result.record.*;
import com.yimayhd.live.client.service.LiveAdminService;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.error.PalaceReturnCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by zhuhao on 2016/9/21.
 */
public class LiveAdminRepo {

    protected Logger log = LoggerFactory.getLogger(LiveAdminRepo.class);

    @Autowired
    private LiveAdminService liveAdminServiceRef;

    /**
     * 获取直播列表
     * @param pageQuery
     * @return
     */
    public LiveRecordPageResult getPageLiveRecord(LiveAdminPageQuery pageQuery){
        try {
            LiveRecordPageResult result = liveAdminServiceRef.getPageLiveRecord(pageQuery);
            if (result != null) {
                if (result.isSuccess()) {
                    log.info("getPageLiveRecord pageQuery={}, result={}", JSON.toJSONString(pageQuery), JSON.toJSONString(result));
                    return result;
                } else {
                    log.error("getPageLiveRecord pageQuery={}, result={}", JSON.toJSONString(pageQuery), JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("getPageLiveRecord pageQuery={}, exception={}", JSON.toJSONString(pageQuery), e);
            throw new BaseException(e, e.getMessage());
        }
    }

    /**
     * 更新直播排序
     * @param updateLiveOrderDTO
     * @return
     */
    public UpdateLiveOrderResult updateLiveOrderById(UpdateLiveOrderDTO updateLiveOrderDTO)
    {
        try {
            UpdateLiveOrderResult result = liveAdminServiceRef.updateLiveOrderById(updateLiveOrderDTO);
            if (result != null) {
                if (result.isSuccess()) {
                    log.info("updateLiveOrderById updateLiveOrderDTO={}, result={}", JSON.toJSONString(updateLiveOrderDTO), JSON.toJSONString(result));
                    return result;
                } else {
                    log.error("updateLiveOrderById updateLiveOrderDTO={}, result={}", JSON.toJSONString(updateLiveOrderDTO), JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("updateLiveOrderById updateLiveOrderDTO={}, exception={}", JSON.toJSONString(updateLiveOrderDTO), e);
            throw new BaseException(e, e.getMessage());
        }
    }

    /**
     * 修改直播记录状态
     * @param updateLiveRecordStatusDTO
     * @return
     */
    public UpdateLiveRecordStatusResult updateLiveRecordStatus(UpdateLiveRecordStatusDTO updateLiveRecordStatusDTO)
    {
        try {
            UpdateLiveRecordStatusResult result = liveAdminServiceRef.updateLiveRecordStatus(updateLiveRecordStatusDTO);
            if (result != null) {
                if (result.isSuccess()) {
                    log.info("updateLiveRecordStatus updateLiveRecordStatusDTO={}, result={}", JSON.toJSONString(updateLiveRecordStatusDTO), JSON.toJSONString(result));
                    return result;
                } else {
                    log.error("updateLiveRecordStatus updateLiveRecordStatusDTO={}, result={}", JSON.toJSONString(updateLiveRecordStatusDTO), JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("updateLiveRecordStatus updateLiveRecordStatusDTO={}, exception={}", JSON.toJSONString(updateLiveRecordStatusDTO), e);
            throw new BaseException(e, e.getMessage());
        }
    }

    /**
     * 关闭直播
     * @param liveRecordId
     * @return
     */
    public CloseLiveResult closeLive(long liveRecordId)
    {
        try {
            CloseLiveResult result = liveAdminServiceRef.closeLive(liveRecordId);
            if (result != null) {
                if (result.isSuccess()) {
                    log.info("closeLive liveRecordId={}, result={}", JSON.toJSONString(liveRecordId), JSON.toJSONString(result));
                    return result;
                } else {
                    log.error("closeLive liveRecordId={}, result={}", JSON.toJSONString(liveRecordId), JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("getPageLiveRecord liveRecordId={}, exception={}", JSON.toJSONString(liveRecordId), e);
            throw new BaseException(e, e.getMessage());
        }
    }

    /**
     * 创建直播间
     * @param liveRoomDO
     * @return
     */
    public CreateLiveRoomResult createLiveRoom(LiveRoomDO liveRoomDO)
    {
        try {
            CreateLiveRoomResult result = liveAdminServiceRef.createLiveRoom(liveRoomDO);
            if (result != null) {
                if (result.isSuccess()) {
                    log.info("createLiveRoom liveRoomDO={}, result={}", JSON.toJSONString(liveRoomDO), JSON.toJSONString(result));
                    return result;
                } else {
                    log.error("createLiveRoom liveRoomDO={}, result={}", JSON.toJSONString(liveRoomDO), JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("createLiveRoom liveRoomDO={}, exception={}", JSON.toJSONString(liveRoomDO), e);
            throw new BaseException(e, e.getMessage());
        }
    }

    /**
     * 查询直播间列表
     * @param liveRoomPageQuery
     * @return
     */
    public LiveRoomPageResult getPageLiveRoom(LiveRoomPageQuery liveRoomPageQuery)
    {
        try {
            LiveRoomPageResult result = liveAdminServiceRef.getPageLiveRoom(liveRoomPageQuery);
            if (result != null) {
                if (result.isSuccess()) {
                    log.info("getPageLiveRoom liveRoomPageQuery={}, result={}", JSON.toJSONString(liveRoomPageQuery), JSON.toJSONString(result));
                    return result;
                } else {
                    log.error("getPageLiveRoom liveRoomPageQuery={}, result={}", JSON.toJSONString(liveRoomPageQuery), JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("getPageLiveRecord pageQuery={}, exception={}", JSON.toJSONString(liveRoomPageQuery), e);
            throw new BaseException(e, e.getMessage());
        }
    }

    /**
     * 关闭直播间
     * @param closeLiveRoomDTO
     * @return
     */
    public CloseLiveRoomResult closeLiveRoom(CloseLiveRoomDTO closeLiveRoomDTO)
    {
        try {
            CloseLiveRoomResult result = liveAdminServiceRef.closeLiveRoom(closeLiveRoomDTO);
            if (result != null) {
                if (result.isSuccess()) {
                    log.info("getPageLiveRecord pageQuery={}, result={}", JSON.toJSONString(closeLiveRoomDTO), JSON.toJSONString(result));
                    return result;
                } else {
                    log.error("getPageLiveRecord pageQuery={}, result={}", JSON.toJSONString(closeLiveRoomDTO), JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("getPageLiveRecord pageQuery={}, exception={}", JSON.toJSONString(closeLiveRoomDTO), e);
            throw new BaseException(e, e.getMessage());
        }
    }

    /**
     * 恢复直播间
     * @param liveRoomId
     * @return
     */
    public RecoverLiveRoomResult recoverLiveRoom(long liveRoomId)
    {
        try {
            RecoverLiveRoomResult result = liveAdminServiceRef.recoverLiveRoom(liveRoomId);
            if (result != null) {
                if (result.isSuccess()) {
                    log.info("recoverLiveRoom liveRoomId={}, result={}", JSON.toJSONString(liveRoomId), JSON.toJSONString(result));
                    return result;
                } else {
                    log.error("recoverLiveRoom liveRoomId={}, result={}", JSON.toJSONString(liveRoomId), JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("recoverLiveRoom liveRoomId={}, exception={}", JSON.toJSONString(liveRoomId), e);
            throw new BaseException(e, e.getMessage());
        }
    }
}

