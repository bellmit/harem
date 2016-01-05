package com.yimayhd.harem.model;

import com.alibaba.fastjson.JSON;
import com.taobao.tair.json.Json;
import com.yimayhd.harem.util.DateUtil;
import com.yimayhd.snscenter.client.dto.SubjectInfoAddDTO;
import org.springframework.beans.BeanUtils;

/**
 * Created by czf on 2016/1/5.
 */
public class SubjectInfoAddVO extends SubjectInfoAddDTO {
    private String picListStr;//图片列表的jsonStr
    private String gmtCreatedStr;//时间字符串（yyyy-MM-dd HH:mm）

    public static SubjectInfoAddDTO getSubjectInfoAddDTO(SubjectInfoAddVO subjectInfoAddVO) throws Exception{
        SubjectInfoAddDTO subjectInfoAddDTO = new SubjectInfoAddDTO();
        BeanUtils.copyProperties(subjectInfoAddVO,subjectInfoAddDTO);
        //图片集合
        subjectInfoAddDTO.setPicList(JSON.parseArray(subjectInfoAddVO.getPicListStr(),String.class));
        //时间
        subjectInfoAddDTO.setGmtCreated(DateUtil.convertStringToDateUseringFormats(subjectInfoAddVO.getGmtCreatedStr(), DateUtil.DAY_HORU_FORMAT));
        return subjectInfoAddDTO;
    }

    public String getPicListStr() {
        return picListStr;
    }

    public void setPicListStr(String picListStr) {
        this.picListStr = picListStr;
    }

    public String getGmtCreatedStr() {
        return gmtCreatedStr;
    }

    public void setGmtCreatedStr(String gmtCreatedStr) {
        this.gmtCreatedStr = gmtCreatedStr;
    }
}
