package com.yimayhd.palace.checker.apply;

import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.model.query.apply.ApplyQuery;
import com.yimayhd.palace.model.vo.apply.ApproveVO;
import com.yimayhd.palace.result.BizResultSupport;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2016/5/24.
 */
public class ApplyQueryChecker {

    public static BizResultSupport checkSellerVO(ApplyQuery applyQuery){
        BizResultSupport result = new BizResultSupport() ;
        if( applyQuery == null || applyQuery.getSellerId() <=0 ){
            result.setPalaceReturnCode(PalaceReturnCode.PARAM_ERROR);
        }
        return result ;
    }
}
