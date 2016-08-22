package com.yimayhd.palace.service.impl;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.attachment.AttachmentListQuery;
import com.yimayhd.palace.model.attachment.AttachmentVO;
import com.yimayhd.palace.service.AttachmentManageService;

/**
 * 附件中心 音频管理 导览
 * Created by xushubing on 2016/8/22.
 */
public class AttachmentManageServiceImpl implements AttachmentManageService {
    /**
     * 分页查询附件
     *
     * @param attachmentListQuery
     * @return
     */
    @Override
    public PageVO<AttachmentVO> getAttachmentList(AttachmentListQuery attachmentListQuery) {
        return null;
    }

    /**
     * 添加附件
     *
     * @param attachmentVO
     * @return
     */
    @Override
    public AttachmentVO addAttachment(AttachmentVO attachmentVO) {
        return null;
    }

    /**
     * 修改附件
     *
     * @param attachmentVO
     * @return
     */
    @Override
    public AttachmentVO updateAttachment(AttachmentVO attachmentVO) {
        return null;
    }

    /**
     * 上架
     *
     * @param attachmentId
     * @return
     */
    @Override
    public boolean upStatus(long attachmentId) {
        return false;
    }

    /**
     * 下架
     *
     * @param attachmentId
     * @return
     */
    @Override
    public boolean downStatus(long attachmentId) {
        return false;
    }
}
