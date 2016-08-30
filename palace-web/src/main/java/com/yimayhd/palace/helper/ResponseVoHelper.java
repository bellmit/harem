package com.yimayhd.palace.helper;

import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.checker.result.CheckResult;
import com.yimayhd.palace.constant.ResponseStatus;
import com.yimayhd.palace.service.ServiceResult;
import com.yimayhd.tradecenter.client.validator.Check;

/**
 * Created by xushubing on 2016/8/29.
 */
public class ResponseVoHelper {
    public static ResponseVo returnResponseVo(boolean result) {
        ResponseVo responseVo = new ResponseVo();
        if (result) {
            responseVo.setMessage(ResponseStatus.SUCCESS.MESSAGE);
            responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
        } else {
            responseVo.setMessage(ResponseStatus.ERROR.MESSAGE);
            responseVo.setStatus(ResponseStatus.ERROR.VALUE);
        }
        return responseVo;
    }

    public static ResponseVo returnResponseVo(CheckResult result) {
        ResponseVo responseVo = new ResponseVo();
        if (result.isSuccess()) {
            responseVo.setMessage(ResponseStatus.SUCCESS.MESSAGE);
            responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
        } else {
            responseVo.setMessage(result.getMsg());
            responseVo.setStatus(ResponseStatus.ERROR.VALUE);
        }
        return responseVo;
    }
}
