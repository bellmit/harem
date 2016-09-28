package com.yimayhd.palace.service.impl;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.biz.AttachmentBiz;
import com.yimayhd.palace.checker.result.CheckResult;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.convert.AttachmentConverter;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.model.attachment.AttachmentListQuery;
import com.yimayhd.palace.model.attachment.AttachmentVO;
import com.yimayhd.palace.repo.MediaClientRepo;
import com.yimayhd.palace.result.AttachmentUploadResult;
import com.yimayhd.palace.service.AttachmentManageService;
import com.yimayhd.resourcecenter.domain.MediaDO;
import com.yimayhd.resourcecenter.dto.MediaDTO;
import com.yimayhd.resourcecenter.model.enums.MediaFileScope;
import com.yimayhd.resourcecenter.model.enums.MediaFileStatus;
import com.yimayhd.resourcecenter.model.enums.MediaFileType;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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
    @Resource
    private AttachmentBiz attachmentBiz;

    /**
     * 分页查询附件
     *
     * @param attachmentListQuery
     * @return
     */
    @Override
    public PageVO<AttachmentVO> getAttachmentList(AttachmentListQuery attachmentListQuery) {
        RCPageResult<MediaDO> result = mediaClientRepo.getMediaPageList(AttachmentConverter.attachmentListQuery2MediaPageQuery(attachmentListQuery));
        if (result == null) {
            return new PageVO<AttachmentVO>();
        }
        List<AttachmentVO> attachmentVOList = new ArrayList<AttachmentVO>();
        List<MediaDO> mediaDOList = result.getList();
        if (mediaDOList == null) {
            return new PageVO<AttachmentVO>();
        }
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
    public boolean addAttachment(AttachmentVO attachmentVO, MultipartFile file, final long userId) {
        AttachmentUploadResult attachmentUploadResult = attachmentBiz.uploadAttachment(file);
        if (attachmentUploadResult == null) {
            log.error("addAttachment uploadAttachment error");
            return false;
        }
        MediaDTO mediaDTO = new MediaDTO();
        mediaDTO.setScope(MediaFileScope.DEFAULT.getValue());
        MediaFileType mediaFileType = MediaFileType.getByDesc(attachmentUploadResult.getExtension());
        if (mediaFileType != null) {
            mediaDTO.setFileType(mediaFileType.getValue());
        }
        mediaDTO.setInputFileTitle(FilenameUtils.getBaseName(file.getOriginalFilename()));
        mediaDTO.setInputFileName(file.getOriginalFilename());
        mediaDTO.setDomainId(Constant.DOMAIN_JIUXIU);
        mediaDTO.setStatus(MediaFileStatus.OFF.getValue());
        mediaDTO.setUserId(userId);
        mediaDTO.setFsize(attachmentUploadResult.getFsize());

        //

        mediaDTO.setBucketName(attachmentUploadResult.getBucketName());
        mediaDTO.setFileHash(attachmentUploadResult.getHash());
        mediaDTO.setRemoteUrl(attachmentUploadResult.getUrl());
        mediaDTO.setFileKey(attachmentUploadResult.getKey());
        mediaDTO.setDuration(attachmentUploadResult.getDuration());
        Long id = mediaClientRepo.addMedia(mediaDTO);
        if (id != null && id > 0) {
            attachmentVO.setId(id);
            return true;
        }
        return false;
    }


    /**
     * 修改附件
     *
     * @param attachmentVO
     * @return
     */
    @Override
    public CheckResult updateAttachment(AttachmentVO attachmentVO) {
        CheckResult checkResult = new CheckResult();
        MediaDTO mediaDTO = mediaClientRepo.getMediaById(attachmentVO.getId());
        if (mediaDTO == null) {
            checkResult.setSuccess(false);
            checkResult.setPalaceReturnCode(PalaceReturnCode.ADD_ATTACHMENT_ERROR_UP_STATUS);
            return checkResult;
        }
        boolean result = mediaClientRepo.updateMedia(AttachmentConverter.attachmentVO2MediaDTO(attachmentVO));
        checkResult.setSuccess(result);
        return checkResult;
    }

    /**
     * 上架
     *
     * @param attachmentId
     * @return
     */
    @Override
    public CheckResult upStatus(long attachmentId) {
        CheckResult checkResult = new CheckResult();
        MediaDTO mediaDTO = mediaClientRepo.getMediaById(attachmentId);
        if (mediaDTO == null) {
            checkResult.setSuccess(false);
            checkResult.setPalaceReturnCode(PalaceReturnCode.ADD_ATTACHMENT_ERROR_UP_STATUS);
            return checkResult;
        }
        if (mediaDTO.getDuration() <= 0) {
            checkResult.setSuccess(false);
            checkResult.setPalaceReturnCode(PalaceReturnCode.ADD_ATTACHMENT_ERROR_UP_STATUS_DURATION);
            return checkResult;
        }
        boolean result = mediaClientRepo.updateMediaStatus(attachmentId, MediaFileStatus.ON);
        checkResult.setSuccess(result);
        return checkResult;
    }

    /**
     * 下架
     *
     * @param attachmentId
     * @return
     */
    @Override
    public CheckResult downStatus(long attachmentId) {
        CheckResult checkResult = new CheckResult();
        MediaDTO mediaDTO = mediaClientRepo.getMediaById(attachmentId);
        if (mediaDTO == null) {
            checkResult.setSuccess(false);
            checkResult.setPalaceReturnCode(PalaceReturnCode.ADD_ATTACHMENT_ERROR_UP_STATUS);
            return checkResult;
        }
        boolean result = mediaClientRepo.updateMediaStatus(attachmentId, MediaFileStatus.OFF);
        checkResult.setSuccess(result);
        return checkResult;
    }

    /**
     * 根据id 返回media
     *
     * @param id
     * @return
     */
    @Override
    public MediaDTO getMediaById(long id) {
        return mediaClientRepo.getMediaById(id);
    }

    /**
     * 根据filekey 查询
     *
     * @param fileKey
     * @return
     */
    @Override
    public MediaDTO getMediaByFileKey(String fileKey) {
        return mediaClientRepo.getMediaByFileKey(fileKey);
    }

    /**
     * 根据文件名称查询
     *
     * @param fileName
     * @return
     */
    @Override
    public MediaDTO getMediaByFileName(String fileName) {
        return mediaClientRepo.getMediaByFileName(fileName);
    }

    /**
     * 根据filekey 查询
     *
     * @param fileKey
     * @return
     */
    @Override
    public AttachmentVO getAttachmentVOByFileKey(String fileKey) {
        return AttachmentConverter.mediaDTO2AttachmentVO(getMediaByFileKey(fileKey));
    }
}
