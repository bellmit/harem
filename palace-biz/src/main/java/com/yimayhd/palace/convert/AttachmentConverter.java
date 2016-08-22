package com.yimayhd.palace.convert;

import com.chinanetcenter.api.util.Config;
import com.yimayhd.palace.model.attachment.AttachmentVO;
import com.yimayhd.resourcecenter.dto.MediaDTO;

/**
 * Created by Administrator on 2016/8/22.
 */
public class AttachmentConverter {
    public static MediaDTO attachmentVO2MediaDTO(AttachmentVO attachmentVO) {
        if(attachmentVO==null){
            return null;
        }
        MediaDTO mediaDTO = new MediaDTO();
        mediaDTO.setDuration(attachmentVO.getDuration());
        mediaDTO.setBucketName(Config.)
    }
}
