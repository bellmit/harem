package com.yimayhd.harem.model;

import com.yimayhd.snscenter.client.domain.SnsSubjectDO;
import com.yimayhd.user.client.domain.UserDO;
import org.springframework.beans.BeanUtils;

/**
 * Created by czf on 2015/12/31.
 */
public class SnsSubjectVO extends SnsSubjectDO {
    private UserDO userDO;

    public static SnsSubjectVO getSnsSubjectVO(SnsSubjectDO snsSubjectDO){
        SnsSubjectVO snsSubjectVO = new SnsSubjectVO();
        BeanUtils.copyProperties(snsSubjectDO,snsSubjectVO);
        return snsSubjectVO;
    }

    public UserDO getUserDO() {
        return userDO;
    }

    public void setUserDO(UserDO userDO) {
        this.userDO = userDO;
    }
}
