package com.yimayhd.palace.controller;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.model.orderLog.OrderStatusChangeLogDTO;
import com.yimayhd.palace.model.orderLog.OrderStatusChangeLogQuery;
import com.yimayhd.palace.model.orderLog.OrderStatusChangeLogResult;
import com.yimayhd.palace.model.param.OrderStatusChangeParam;
import com.yimayhd.palace.model.vo.OrderStatusChangeVO;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.palace.service.OrderStatusChangeLogService;
import com.yimayhd.palace.service.OrderStatusChangeService;
import com.yimayhd.user.session.manager.SessionManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


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
    @Autowired
    private OrderStatusChangeService orderStatusChangeService;
    @Autowired
    private OrderStatusChangeLogService orderStatusChangeLogService;


    /**
     * 更改订单状态列表
     * @param model
     * @param orderStatusChangeParam
     * @return
     */
    @RequestMapping(value = "/queryList", method = RequestMethod.GET)
    public String queryList(Model model,OrderStatusChangeParam orderStatusChangeParam) throws Exception{
        logger.info("query list start");
        if(StringUtils.isBlank(orderStatusChangeParam.getBizOrderIdStr())){
            //没有订单id 默认不显示列表
            logger.info("orderIds is empty");
            model.addAttribute("queryModel", orderStatusChangeParam);
            return "/system/order/changeOrderStatus";
        }
        if(StringUtils.isBlank(orderStatusChangeParam.getBizOrderIdStr())&&orderStatusChangeParam.getBizOrderIds().size()>count){
            logger.error("每次最多查询100,为您选取100条进行展示");
            orderStatusChangeParam.setBizOrderIds(orderStatusChangeParam.getBizOrderIds().subList(0,count));
        }
        /**调用接口*/
        BizResult<OrderStatusChangeVO> bizResult = orderStatusChangeService.queryList(orderStatusChangeParam);
        if(bizResult==null||!bizResult.isSuccess()){
            logger.error("查询数据异常");
            return "/system/order/changeOrderStatus";
        }
        OrderStatusChangeVO changeVO = bizResult.getValue();
        model.addAttribute("tcMainOrderVOList", changeVO.getTcMainOrderVOList());
        model.addAttribute("totalCount", changeVO.getTotalCount());
        model.addAttribute("queryModel", orderStatusChangeParam);
        return "/system/order/changeOrderStatus";
    }

    /**
     * 更改订单状态
     * @param model
     * @param orderStatusChangeParam
     * @return
     */
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public BizResult<String>  updateStatus(Model model , OrderStatusChangeParam orderStatusChangeParam) throws Exception{
        logger.info("updateStatus start:"+JSON.toJSONString(orderStatusChangeParam));
        if(orderStatusChangeParam==null){
            logger.error("orderStatusChangeParam is null");
            return BizResult.buildFailResult(PalaceReturnCode.PARAM_ERROR.getErrorCode(),PalaceReturnCode.PARAM_ERROR.getErrorMsg(),null);
        }
        if(StringUtils.isBlank(orderStatusChangeParam.getOrderChangeStatus())){
            logger.error("订单修改状态为空");
            return BizResult.buildFailResult(PalaceReturnCode.PARAM_ERROR.getErrorCode(),"订单修改状态为空",null);
        }
        orderStatusChangeParam.setUserId(sessionManager.getUserId());
        BizResult<String> bizResult = orderStatusChangeService.updateStatus(orderStatusChangeParam);
        if(bizResult==null||!bizResult.isSuccess()){
            logger.error("更新状态失败,errMsg={}", JSON.toJSONString(bizResult));
            return BizResult.buildFailResult(PalaceReturnCode.SYSTEM_ERROR.getErrorCode(),bizResult.getMsg(),null);
        }

        return BizResult.buildSuccessResult(null);
    }

    /**
     * 查询修改状态日志
     * @param model
     * @param orderStatusChangeLogQuery
     * @return
     */
    @RequestMapping(value = "/queryLogList", method = RequestMethod.GET)
    public String queryLogList(Model model,OrderStatusChangeLogQuery orderStatusChangeLogQuery) throws Exception{
        logger.info("queryLogList start");
        List<OrderStatusChangeLogDTO> resultList = new ArrayList<OrderStatusChangeLogDTO>();
        model.addAttribute("queryModel", orderStatusChangeLogQuery);
        if(orderStatusChangeLogQuery!=null &&StringUtils.isNotBlank(orderStatusChangeLogQuery.getOperationId())){
            //验证getOperationId 是否数字
            if(!StringUtils.isNumeric(orderStatusChangeLogQuery.getOperationId())){
                model.addAttribute("pageVo", new PageVO());
                return "/system/order/changeHistory";
            }
        }
        BizResult<OrderStatusChangeLogResult> result=  orderStatusChangeLogService.queryOrderStatusChangeLogList(orderStatusChangeLogQuery);
        if(result==null||!result.isSuccess()){
            return "/system/order/changeHistory";
        }
        OrderStatusChangeLogResult orderLog =  result.getValue();
        if(orderLog.getOrderStatusChangeLogDTOList()!=null){
            resultList =orderLog.getOrderStatusChangeLogDTOList() ;
        }
        PageVO<OrderStatusChangeLogDTO> pageModel = new PageVO<OrderStatusChangeLogDTO>(orderStatusChangeLogQuery.getPageNumber(),
                orderStatusChangeLogQuery.getPageSize(),
                orderLog.getTotalCount(),
                resultList);
        int totalPage = 0;
        if (pageModel.getTotalCount()%pageModel.getPageSize() > 0) {
            totalPage += pageModel.getTotalCount()/pageModel.getPageSize()+1;
        }else {
            totalPage += pageModel.getTotalCount()/pageModel.getPageSize();
        }
        model.addAttribute("logList",resultList);
        model.addAttribute("pageVo", pageModel);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("totalCount", orderLog.getTotalCount());
        return "/system/order/changeHistory";
    }





}
