package com.yimayhd.palace.convert;

import com.yimayhd.palace.model.attachment.AttachmentVO;
import com.yimayhd.resourcecenter.domain.MediaDO;
import com.yimayhd.resourcecenter.dto.MediaDTO;

/**
 * Created by Administrator on 2016/8/22.
 */
public class AttachmentConverter {
    public static MediaDTO attachmentVO2MediaDTO(AttachmentVO attachmentVO) {
        if (attachmentVO == null) {
            return null;
        }
        MediaDTO mediaDTO = new MediaDTO();
        mediaDTO.setDuration(attachmentVO.getMinute()*60+attachmentVO.getSecond());
        mediaDTO.setScope(attachmentVO.getScope());
        mediaDTO.setInputFileTitle(attachmentVO.getInputFileTitle());
        mediaDTO.setRemark(attachmentVO.getRemark());
        mediaDTO.setId(attachmentVO.getId());
        return mediaDTO;
    }

    public static AttachmentVO mediaDO2AttachmentVO(MediaDO mediaDO) {
        if (mediaDO == null) {
            return null;
        }
        AttachmentVO attachmentVO = new AttachmentVO();
        attachmentVO.setId(mediaDO.getId());
        attachmentVO.setDuration(mediaDO.getDuration());
        //attachmentVO.setBucketName(ResourceConfig.getInstance().getValueByKey(AttachmentConstant.CHINANETCENTER_CONFIG_BUCKETNAME));
        attachmentVO.setFileType(mediaDO.getFileType());
        attachmentVO.setScope(mediaDO.getScope());
        attachmentVO.setInputFileTitle(mediaDO.getInputFileTitle());
        attachmentVO.setRemark(mediaDO.getRemark());
        attachmentVO.setStatus(mediaDO.getStatus());
        attachmentVO.setGmtCreated(mediaDO.getGmtCreated());
        return attachmentVO;
    }

    public static AttachmentVO mediaDTO2AttachmentVO(MediaDTO mediaDTO) {
        if (mediaDTO == null) {
            return null;
        }
        AttachmentVO attachmentVO = new AttachmentVO();
        attachmentVO.setId(mediaDTO.getId());
        attachmentVO.setDuration(mediaDTO.getDuration());
        //attachmentVO.setBucketName(ResourceConfig.getInstance().getValueByKey(AttachmentConstant.CHINANETCENTER_CONFIG_BUCKETNAME));
        attachmentVO.setFileType(mediaDTO.getFileType());
        attachmentVO.setScope(mediaDTO.getScope());
        attachmentVO.setInputFileTitle(mediaDTO.getInputFileTitle());
        attachmentVO.setRemark(mediaDTO.getRemark());
        attachmentVO.setRemoteUrl(mediaDTO.getRemoteUrl());
        return attachmentVO;
    }
}
