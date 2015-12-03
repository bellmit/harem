package com.yimayhd.harem.model;

import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;
import com.yimayhd.user.client.domain.UserDO;
import org.apache.commons.beanutils.BeanUtils;

/**
 * Created by Administrator on 2015/12/2.
 */
public class BizOrderVO extends BizOrderDO {
    private UserDO userDO;

    public UserDO getUserDO() {
        return userDO;
    }

    public void setUserDO(UserDO userDO) {
        this.userDO = userDO;
    }

    public static BizOrderVO getBizOrderVO(BizOrderDO bizOrderDO) throws Exception{
        BizOrderVO bizOrderVO = new BizOrderVO();
        BeanUtils.copyProperties(bizOrderVO,bizOrderDO);
        return bizOrderVO;
    }
    public static BizOrderDO getBizOrderDO(BizOrderVO bizOrderVO){
        return bizOrderVO;
    }
}
