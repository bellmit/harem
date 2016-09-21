package com.yimayhd.palace.service;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.attachment.AttachmentListQuery;
import com.yimayhd.palace.model.attachment.AttachmentVO;
import com.yimayhd.resourcecenter.dto.MediaDTO;
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
     * @param attachmentListQuery
     * @return
     */
    public PageVO<AttachmentVO> getAttachmentList(AttachmentListQuery attachmentListQuery);

    /**
     * 添加附件
     *
     * @param attachmentVO
     * @return
     */
    public boolean addAttachment(AttachmentVO attachmentVO, MultipartFile file,final long userId);

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


    /**
     * 根据id 返回media
     *
     * @param id
     * @return
     */
    public MediaDTO getMediaById(final long id);


    /**
     * 根据filekey 查询
     *
     * @param fileKey
     * @return
     */
    public MediaDTO getMediaByFileKey(final String fileKey) ;

    /**
     * 根据文件名称查询
     * @param fileName
     * @return
     */
    public MediaDTO getMediaByFileName(final String fileName) ;

    /**
     * 根据filekey 查询
     *
     * @param fileKey
     * @return
     */
    public AttachmentVO getAttachmentVOByFileKey(final String fileKey) ;
}
