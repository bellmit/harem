package com.yimayhd.harem.model;

import com.yimayhd.harem.util.DateUtil;
import com.yimayhd.snscenter.client.domain.SnsSubjectDO;
import com.yimayhd.user.client.domain.UserDO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by czf on 2015/12/31.
 */
public class SnsSubjectVO extends SnsSubjectDO {
    private UserDO userDO;
    private long tagId;
    private List<String> picList;
    private String gmtCreatedStr;//时间字符串(yyyy-MM-dd HH:mm)

    public static SnsSubjectVO getSnsSubjectVO(SnsSubjectDO snsSubjectDO){
        SnsSubjectVO snsSubjectVO = new SnsSubjectVO();
        BeanUtils.copyProperties(snsSubjectDO, snsSubjectVO);
        //图片list
        if(StringUtils.isNotBlank(snsSubjectDO.getPicContent())) {
            snsSubjectVO.setPicList(Arrays.asList(snsSubjectDO.getPicContent().split("\\|")));
        }
        //时间
        snsSubjectVO.setGmtCreatedStr(DateUtil.dateToString(snsSubjectDO.getGmtCreated(), DateUtil.DAY_HORU_FORMAT));
        return snsSubjectVO;
    }

    public UserDO getUserDO() {
        return userDO;
    }

    public void setUserDO(UserDO userDO) {
        this.userDO = userDO;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }

    public String getGmtCreatedStr() {
        return gmtCreatedStr;
    }

    public void setGmtCreatedStr(String gmtCreatedStr) {
        this.gmtCreatedStr = gmtCreatedStr;
    }
}
