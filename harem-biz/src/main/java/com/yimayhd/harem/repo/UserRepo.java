package com.yimayhd.harem.repo;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.tradecenter.client.model.param.order.OrderQueryDTO;
import com.yimayhd.tradecenter.client.model.result.order.BatchQueryResult;
import com.yimayhd.tradecenter.client.model.result.order.SingleQueryResult;
import com.yimayhd.tradecenter.client.service.trade.TcQueryService;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.domain.UserDOPageQuery;
import com.yimayhd.user.client.result.BasePageResult;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: zhaozhaonan
 * Date: 2015/12/21
 * 用户server
 */
public class UserRepo {

    public static final Logger logger = LoggerFactory.getLogger(UserRepo.class);

    @Autowired
    private UserService userServiceRef;

    public UserDO findUserByCondition(UserDOPageQuery userDOPageQuery){
        BasePageResult<UserDO> basePageResult = userServiceRef.findPageResultByCondition(userDOPageQuery);
        if (basePageResult.isSuccess()){
            if (CollectionUtils.isNotEmpty(basePageResult.getList())){
                return basePageResult.getList().get(0);
            }
        }
        return null;
    }


}
