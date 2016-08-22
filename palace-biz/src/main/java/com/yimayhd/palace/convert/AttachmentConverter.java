package com.yimayhd.palace.convert;

import com.yimayhd.palace.config.ResourceConfig;
import com.yimayhd.palace.constant.AttachmentConstant;
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
        mediaDTO.setDuration(attachmentVO.getDuration());
        mediaDTO.setBucketName(ResourceConfig.getInstance().getValueByKey(AttachmentConstant.CHINANETCENTER_CONFIG_BUCKETNAME));
        mediaDTO.setFileType(attachmentVO.getFileType());
        mediaDTO.setScope(attachmentVO.getScope());
        mediaDTO.setInputFileTitle(attachmentVO.getInputFileTitle());
        mediaDTO.setRemark(attachmentVO.getRemark());
        return mediaDTO;
    }

    public static AttachmentVO mediaDO2AttachmentVO(MediaDO mediaDO) {
        if (mediaDO == null) {
            return null;
        }
        AttachmentVO attachmentVO = new AttachmentVO();
        attachmentVO.setDuration(mediaDO.getDuration());
        //attachmentVO.setBucketName(ResourceConfig.getInstance().getValueByKey(AttachmentConstant.CHINANETCENTER_CONFIG_BUCKETNAME));
        attachmentVO.setFileType(mediaDO.getFileType());
        attachmentVO.setScope(mediaDO.getScope());
        attachmentVO.setInputFileTitle(mediaDO.getInputFileTitle());
        attachmentVO.setRemark(mediaDO.getRemark());
        return attachmentVO;
    }

}
