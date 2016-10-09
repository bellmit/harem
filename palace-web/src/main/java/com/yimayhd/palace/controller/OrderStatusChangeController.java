package com.yimayhd.palace.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.yimayhd.ic.client.model.param.item.ItemSkuPubUpdateDTO;
import com.yimayhd.palace.controller.vo.OrderStatusChangeVO;
import com.yimayhd.palace.enums.OrderStatusChangeType;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.user.session.manager.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final int FINISH=1;
    private static final int CANCEL=2;

    private static final int count=100;

    private SessionManager sessionManager;
    /**
     * 更改订单状态列表
     * @param model
     * @param orderStatusChangeVO
     * @return
     */
    @RequestMapping(value = "/queryList", method = RequestMethod.GET)
    public String queryList(Model model,OrderStatusChangeVO orderStatusChangeVO){
        if(StringUtils.isBlank(orderStatusChangeVO.getBizOrderIdStr())){
            //没有订单id 默认不显示列表
            return "";
        }
        if(orderStatusChangeVO.getBizOrderIds().size()>count){
            logger.error("每次最多查询100,为您选取💰100条进行展示");
            orderStatusChangeVO.setBizOrderIds(orderStatusChangeVO.getBizOrderIds().subList(0,count));
        }

        /**调用接口*/

        return "";
    }

    /**
     * 更改订单状态
     * @param model
     * @param orderStatusChangeVO
     * @return
     */
    @RequestMapping(value = "/queryList", method = RequestMethod.POST)
    public BizResult<String>  updateStatus(Model model , OrderStatusChangeVO orderStatusChangeVO){
        if(orderStatusChangeVO==null){
            return BizResult.buildFailResult(PalaceReturnCode.PARAM_ERROR.getErrorCode(),PalaceReturnCode.PARAM_ERROR.getErrorMsg(),null);
        }
        long sellerId = sessionManager.getUserId();
        switch (orderStatusChangeVO.getOrderChangeStatus()) {
            case FINISH:

                break;

            case CANCEL:

                break;

            default:
                return BizResult.buildFailResult(PalaceReturnCode.PARAM_ERROR.getErrorCode(),PalaceReturnCode.PARAM_ERROR.getErrorMsg(),null);
        }
        return BizResult.buildSuccessResult(null);
    }


}
