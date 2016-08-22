package com.yimayhd.palace.service;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.attachment.AttachmentListQuery;
import com.yimayhd.palace.model.attachment.AttachmentVO;
import com.yimayhd.resourcecenter.model.query.MediaPageQuery;
import org.springframework.web.multipart.MultipartFile;

/**
 * 附件中心 音频管理 导览
 * Created by xushubing on 2016/8/22.
 */
public interface AttachmentManageService {
    /**
     * 分页查询附件
     *
     * @param mediaPageQuery
     * @return
     */
    public PageVO<AttachmentVO> getAttachmentList(MediaPageQuery mediaPageQuery);

    /**
     * 添加附件
     *
     * @param attachmentVO
     * @return
     */
    public AttachmentVO addAttachment(AttachmentVO attachmentVO, MultipartFile file);

    /**
     * 修改附件
     *
     * @param attachmentVO
     * @return
     */
    public boolean updateAttachment(AttachmentVO attachmentVO);

    /**
     * 上架
     *
     * @param attachmentId
     * @return
     */
    public boolean upStatus(final long attachmentId);

    /**
     * 下架
     *
     * @param attachmentId
     * @return
     */
    public boolean downStatus(final long attachmentId);
}
