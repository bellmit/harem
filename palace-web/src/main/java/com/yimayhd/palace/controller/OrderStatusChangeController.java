package com.yimayhd.palace.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.model.param.OrderStatusChangeParam;
import com.yimayhd.palace.model.vo.OrderStatusChangeVO;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.user.session.manager.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



/**
 * Created by wangdi on 16/10/9.
 */
@Controller
@RequestMapping("/order/status/")
public class OrderStatusChangeController {
    private static final Logger logger = LoggerFactory.getLogger(OrderStatusChangeController.class);


    private static final int count=100;
    @Autowired
    private SessionManager sessionManager;


    /**
     * 更改订单状态列表
     * @param model
     * @param orderStatusChangeParam
     * @return
     */
    @RequestMapping(value = "/queryList", method = RequestMethod.GET)
    public String queryList(Model model,OrderStatusChangeParam orderStatusChangeParam){
        if(StringUtils.isBlank(orderStatusChangeParam.getBizOrderIdStr())){
            //没有订单id 默认不显示列表
            return "";
        }
        if(orderStatusChangeParam.getBizOrderIds().size()>count){
            logger.error("每次最多查询100,为您选取💰100条进行展示");
            orderStatusChangeParam.setBizOrderIds(orderStatusChangeParam.getBizOrderIds().subList(0,count));
        }
        /**调用接口*/

        return "";
    }

    /**
     * 更改订单状态
     * @param model
     * @param orderStatusChangeParam
     * @return
     */
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    public BizResult<String>  updateStatus(Model model , OrderStatusChangeParam orderStatusChangeParam){
        if(orderStatusChangeParam==null){
            return BizResult.buildFailResult(PalaceReturnCode.PARAM_ERROR.getErrorCode(),PalaceReturnCode.PARAM_ERROR.getErrorMsg(),null);
        }
        if(orderStatusChangeParam.getOrderChangeStatus()==0){
            return BizResult.buildFailResult(PalaceReturnCode.PARAM_ERROR.getErrorCode(),"订单修改状态为空",null);
        }
        long sellerId = sessionManager.getUserId();

        return BizResult.buildSuccessResult(null);
    }


}
