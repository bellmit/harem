package com.yimayhd.palace.service.impl;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.convert.AttachmentConverter;
import com.yimayhd.palace.model.attachment.AttachmentVO;
import com.yimayhd.palace.repo.MediaClientRepo;
import com.yimayhd.palace.service.AttachmentManageService;
import com.yimayhd.resourcecenter.domain.MediaDO;
import com.yimayhd.resourcecenter.model.enums.MediaFileStatus;
import com.yimayhd.resourcecenter.model.query.MediaPageQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 附件中心 音频管理 导览
 * Created by xushubing on 2016/8/22.
 */
public class AttachmentManageServiceImpl implements AttachmentManageService {
    protected Logger log = LoggerFactory.getLogger(AttachmentManageServiceImpl.class);
    @Resource
    private MediaClientRepo mediaClientRepo;

    /**
     * 分页查询附件
     *
     * @param mediaPageQuery
     * @return
     */
    @Override
    public PageVO<AttachmentVO> getAttachmentList(MediaPageQuery mediaPageQuery) {
        RCPageResult<MediaDO> result = mediaClientRepo.getMediaPageList(mediaPageQuery);
        if (result == null) {
            return null;
        }
        List<AttachmentVO> attachmentVOList = new ArrayList<AttachmentVO>();
        List<MediaDO> mediaDOList = result.getList();
        for (MediaDO mediaDO : mediaDOList) {
            attachmentVOList.add(AttachmentConverter.mediaDO2AttachmentVO(mediaDO));
        }
        return new PageVO<AttachmentVO>(result.getPageNo(), result.getPageSize(), result.getTotalCount(), attachmentVOList);
    }

    /**
     * 添加附件
     *
     * @param attachmentVO
     * @return
     */
    @Override
    public AttachmentVO addAttachment(AttachmentVO attachmentVO, MultipartFile file) {
        Long id = mediaClientRepo.addMedia(AttachmentConverter.attachmentVO2MediaDTO(attachmentVO));
        if (id != null && id > 0) {
            attachmentVO.setId(id);
            //上传
            File file1 = new File("");
            return attachmentVO;
        }
        return null;
    }

    /**
     * 修改附件
     *
     * @param attachmentVO
     * @return
     */
    @Override
    public boolean updateAttachment(AttachmentVO attachmentVO) {
        return mediaClientRepo.updateMedia(AttachmentConverter.attachmentVO2MediaDTO(attachmentVO));
    }

    /**
     * 上架
     *
     * @param attachmentId
     * @return
     */
    @Override
    public boolean upStatus(long attachmentId) {
        return mediaClientRepo.updateMediaStatus(MediaFileStatus.ON.getValue(), attachmentId);
    }

    /**
     * 下架
     *
     * @param attachmentId
     * @return
     */
    @Override
    public boolean downStatus(long attachmentId) {
        return mediaClientRepo.updateMediaStatus(MediaFileStatus.OFF.getValue(), attachmentId);
    }
}
