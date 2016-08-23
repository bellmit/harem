package com.yimayhd.palace.repo;

import com.alibaba.fastjson.JSON;
import com.yimayhd.resourcecenter.domain.MediaDO;
import com.yimayhd.resourcecenter.dto.MediaDTO;
import com.yimayhd.resourcecenter.model.enums.MediaFileStatus;
import com.yimayhd.resourcecenter.model.query.MediaPageQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.service.MediaClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xushubing on 2016/8/22.
 */
public class MediaClientRepo {
    protected Logger log = LoggerFactory.getLogger(MediaClientRepo.class);

    @Autowired
    private MediaClientService mediaClientServiceRef;

    public RCPageResult<MediaDO> getMediaPageList(MediaPageQuery mediaPageQuery) {

        try {
            RCPageResult<MediaDO> result = mediaClientServiceRef.query(mediaPageQuery);
            if (result.isSuccess() && !result.getList().isEmpty()) {
                log.info("getMediaPageList mediaPageQuery={}, result={}", JSON.toJSONString(mediaPageQuery), JSON.toJSONString(result));
                return result;
            } else {
                log.error("getMediaPageList mediaPageQuery={}, result={}", JSON.toJSONString(mediaPageQuery), JSON.toJSONString(result));
                return null;
            }
        } catch (Exception e) {
            log.error("getMediaPageList mediaPageQuery={}, exception={}", JSON.toJSONString(mediaPageQuery), e);
            return null;
        }
    }

    public Long addMedia(MediaDTO mediaDTO) {
        try {
            RcResult<Long> result = mediaClientServiceRef.addMedia(mediaDTO);
            if (result.isSuccess() && result.getT() != null) {
                log.info("addMedia mediaDTO={}, result={}", JSON.toJSONString(mediaDTO), JSON.toJSONString(result));
                return result.getT();
            } else {
                log.error("addMedia mediaDTO={}, result={}", JSON.toJSONString(mediaDTO), JSON.toJSONString(result));
                return null;
            }
        } catch (Exception e) {
            log.error("addMedia mediaDTO={}, exception={}", JSON.toJSONString(mediaDTO), e);
            return null;
        }
    }

    public Boolean updateMedia(MediaDTO updateDTO) {
        try {
            RcResult<Boolean> result = mediaClientServiceRef.updateMedia(updateDTO);
            if (result.isSuccess() && result.getT()) {
                log.info("updateMedia updateDTO={}, result={}", JSON.toJSONString(updateDTO), JSON.toJSONString(result));
                return result.getT();
            } else {
                log.error("updateMedia updateDTO={}, result={}", JSON.toJSONString(updateDTO), JSON.toJSONString(result));
                return false;
            }
        } catch (Exception e) {
            log.error("updateMedia updateDTO={}, exception={}", JSON.toJSONString(updateDTO), e);
            return false;
        }
    }

    public Boolean updateMediaStatus(long mediaId, MediaFileStatus mediaFileStatus) {
        try {
            RcResult<Boolean> result = mediaClientServiceRef.updateMediaStatus(mediaId, mediaFileStatus);
            if (result.isSuccess() && result.getT()) {
                log.info("updateMediaStatus status={}, mediaId={}, result={}", mediaFileStatus, mediaId, JSON.toJSONString(result));
                return result.getT();
            } else {
                log.error("updateMediaStatus status={},mediaId={}, result={}", mediaFileStatus, mediaId, JSON.toJSONString(result));
                return false;
            }
        } catch (Exception e) {
            log.error("updateMediaStatus status={},guideId={}, exception={}", mediaFileStatus, mediaId, e);
            return false;
        }
    }

    /**
     * 根据id 返回media
     *
     * @param id
     * @return
     */
    public MediaDTO getMediaById(final long id) {
        try {
            RcResult<MediaDTO> result = mediaClientServiceRef.getMediaById(id);
            if (result.isSuccess() && result.getT() != null) {
                log.info("getMediaById id={}, , result={}", id, JSON.toJSONString(result));
                return result.getT();
            } else {
                log.error("getMediaById id={}, result={}", id, JSON.toJSONString(result));
                return null;
            }
        } catch (Exception e) {
            log.error("getMediaById id={}, exception={}", id, e);
            return null;
        }
    }

}
