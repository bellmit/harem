package com.yimayhd.palace.biz;

import com.yimayhd.palace.config.ResourceConfig;
import com.yimayhd.palace.constant.AttachmentConstant;
import com.yimayhd.palace.model.attachment.AttachmentVO;
import com.yimayhd.palace.result.AttachmentUploadResult;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2016/8/22.
 */
public class AttachmentBiz {
    public AttachmentUploadResult uploadAttachment(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        String fileName = getFileName();
        try {
            File tempFile = File.createTempFile(fileName, null);
            file.transferTo(tempFile);
            AttachmentUpload attachmentUpload = new AttachmentUpload();
            String bucketName = ResourceConfig.getInstance().getValueByKey(AttachmentConstant.CHINANETCENTER_CONFIG_BUCKETNAME);
            AttachmentUploadResult attachmentUploadResult = attachmentUpload.upload(bucketName, fileName, tempFile.getPath());
            if(attachmentUploadResult!=null){
                attachmentUploadResult.setBucketName(bucketName);
            }
            return attachmentUploadResult;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getFileName() {
        String fileName = RandomStringUtils.randomAlphabetic(32);
        return fileName;
    }

    public String getExtension(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        String contentType = file.getContentType();
        //获得文件后缀名称
        String extension = contentType.substring(contentType.indexOf("/") + 1);
        return extension;
    }

    public String getTime(long duration) {
        if (duration <= 0) {
            return null;
        }
        return duration / 60 + "分" + duration % 60 + "秒";
    }
}
