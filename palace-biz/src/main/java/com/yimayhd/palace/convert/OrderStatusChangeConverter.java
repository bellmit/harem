package com.yimayhd.palace.convert;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.model.param.OrderStatusChangeParam;
import com.yimayhd.palace.model.vo.TcDetailOrderVO;
import com.yimayhd.palace.model.vo.TcMainOrderVO;
import com.yimayhd.palace.repo.user.UserRepo;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.palace.service.JiuxiuOrderService;
import com.yimayhd.palace.util.SpringContextModel;
import com.yimayhd.sellerAdmin.client.model.orderLog.OrderOperationLogDTO;
import com.yimayhd.tradecenter.client.model.domain.order.VoucherInfo;
import com.yimayhd.tradecenter.client.model.domain.person.TcMerchantInfo;
import com.yimayhd.tradecenter.client.model.param.order.OrderQueryDTO;
import com.yimayhd.tradecenter.client.model.result.order.create.TcBizOrder;
import com.yimayhd.tradecenter.client.model.result.order.create.TcDetailOrder;
import com.yimayhd.tradecenter.client.model.result.order.create.TcMainOrder;
import com.yimayhd.tradecenter.client.util.BizOrderUtil;
import com.yimayhd.user.client.result.BaseResult;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdi on 16/10/9.
 */
public class OrderStatusChangeConverter {
    private static final Logger logger = LoggerFactory.getLogger(OrderStatusChangeConverter.class);
    private OrderStatusChangeParam orderStatusChangeParam;
    private BeanCopier beanCopier = BeanCopier.create(TcMainOrder.class, TcMainOrderVO.class, false);
    private BeanCopier tcDetailBean= BeanCopier.create(TcDetailOrderVO.class, TcDetailOrder.class, false);
    private List<TcMainOrderVO> TcMainOrderVOList;
    private UserRepo userRepo ;
    private JiuxiuOrderService jiuxiuOrderService;



    public OrderStatusChangeConverter(OrderStatusChangeParam orderStatusChangeParam){
        try{
            this.orderStatusChangeParam= orderStatusChangeParam;
            this.userRepo = (UserRepo)SpringContextModel.getBean("userRepo");
            this.jiuxiuOrderService = (JiuxiuOrderService)SpringContextModel.getBean("jiuxiuOrderService");
        }catch (Exception e){
            logger.error("获取 bean 信息异常",e);
        }
    }

    public OrderOperationLogDTO getLogDto(){
        OrderOperationLogDTO dto = new OrderOperationLogDTO();
        dto.setOperationId(orderStatusChangeParam.getUserId());
        dto.setBizNo(orderStatusChangeParam.getBizOrderIdStr());
        dto.setContent(orderStatusChangeParam.getDesc());
        dto.setStatus(orderStatusChangeParam.getOrderChangeStatus());
        return dto;
    }

    public OrderQueryDTO getOrderQueryDTO(){
        OrderQueryDTO dto = new OrderQueryDTO();
        //dto.setDomain(Constant.DOMAIN_JIUXIU);
        dto.setDomain(1000);
        dto.setBizOrderIds(orderStatusChangeParam.getBizOrderIds());
        return dto;
    }

    public List<TcMainOrderVO> getTcMainOrderVOList (List<TcMainOrder> bizOrderDOList){
        if(CollectionUtils.isEmpty(bizOrderDOList)){
            return null;
        }
        List<TcMainOrderVO> voList = new ArrayList<TcMainOrderVO>(bizOrderDOList.size());
        try{

            for(TcMainOrder tcMainOrder :bizOrderDOList){
                TcMainOrderVO vo = new TcMainOrderVO();
                beanCopier.copy(tcMainOrder,vo,null);
                voList.add(vo);
            }
        }catch (Exception e){
            logger.error("数据转换异常",e);
            return null;
        }

        return voList;
    }

    /**
     * 为页面展示,重新拼装主订单信息
     * @param tcMainOrderVOList
     * @return
     */
    public  List<TcMainOrderVO> secondaryTcMainOrder(List<TcMainOrderVO> tcMainOrderVOList ) throws Exception{
        if (CollectionUtils.isEmpty(tcMainOrderVOList)){
            return null;
        }
        for(TcMainOrderVO tcMainOrderVO :tcMainOrderVOList ){
            handleTcMainOrderVO(tcMainOrderVO);//处理主订单信息
            secondaryTcDetailOrder(tcMainOrderVO.getDetailOrders());//处理子订单信息
        }
        return tcMainOrderVOList;
    }
    /**
     * 为页面展示,重新拼装子订单信息
     * @param tcDetailOrderVOList
     * @return
     */
    public List<TcDetailOrderVO> secondaryTcDetailOrder(List<TcDetailOrderVO> tcDetailOrderVOList) throws Exception{

        if(CollectionUtils.isEmpty(tcDetailOrderVOList)){
            return null;
        }
        for(TcDetailOrderVO tcDetailOrderVO :tcDetailOrderVOList){
            handleTcDetailOrderVO(tcDetailOrderVO);
        }
        return tcDetailOrderVOList;

    }

    /**
     * 处理主订单
     * @param tcMainOrderVO
     * @return
     */
    public TcMainOrderVO handleTcMainOrderVO(TcMainOrderVO tcMainOrderVO) {

        if(tcMainOrderVO!=null&&tcMainOrderVO.getBizOrder()!=null){
            return tcMainOrderVO;
        }
        try{
            /**添加用户信息*/
            tcMainOrderVO.setUserDO(userRepo.getUserDOByUserId(tcMainOrderVO.getBizOrder().getBuyerId()));
            /**添加昵称*/
            long sellerId =  tcMainOrderVO.getBizOrder().getSellerId();
            BaseResult<TcMerchantInfo> resultTc =  jiuxiuOrderService.getTcMerchantInfo(sellerId);
            if(resultTc.isSuccess()&&resultTc.getValue()!=null){
                tcMainOrderVO.setUserNick(resultTc.getValue().getUserNick());
                tcMainOrderVO.setMerchantName(resultTc.getValue().getMerchantName());
            }
            /***添加优惠劵信息*/
            VoucherInfo voucherInfo = BizOrderUtil.getVoucherInfo(tcMainOrderVO.getBizOrder().getBizOrderDO());
            if(null!=voucherInfo){
                logger.error("优惠劵信息不存在,errMsg={}", JSON.toJSONString(tcMainOrderVO.getBizOrder().getBizOrderDO()));
                tcMainOrderVO.setRequirement(voucherInfo.getRequirement());
                tcMainOrderVO.setReValue(voucherInfo.getValue());
            }
        }catch(Exception e){
            logger.error("处理主订单信息异常",e);
        }

        return tcMainOrderVO;
    }

    /**
     * 处理子订单
     * @param tcDetailOrderVO
     * @return
     */
    public TcDetailOrderVO handleTcDetailOrderVO(TcDetailOrderVO tcDetailOrderVO ) {
        TcDetailOrder tcDetailOrder = new TcDetailOrder();
        try{
            logger.info("tcDetailOrderVO ={}",JSON.toJSONString(tcDetailOrderVO));
            tcDetailBean.copy(tcDetailOrderVO,tcDetailOrder,null);
            long totalFee = BizOrderUtil.getSubOrderActualFee(tcDetailOrder.getBizOrder().getBizOrderDO());//子订单实付金额
            tcDetailOrderVO.setSubOrderActualFee(totalFee);
        }catch (Exception e){
            logger.error("bean 转化异常",e);
        }

        return tcDetailOrderVO;
    }


    /**
     * 订单状态修改验证`
     * @return
     */
    public BizResult checkUpdateStatus(){
        if(orderStatusChangeParam==null){
            return BizResult.buildFailResult(PalaceReturnCode.PARAM_ERROR.getErrorCode(),PalaceReturnCode.PARAM_ERROR.getErrorMsg(),null);
        }
        if(orderStatusChangeParam.getOrderChangeStatus()==0){
            return BizResult.buildFailResult(PalaceReturnCode.PARAM_ERROR.getErrorCode(),"订单修改状态为空",null);
        }
        if(CollectionUtils.isEmpty(orderStatusChangeParam.getBizOrderIds())){
            return BizResult.buildFailResult(PalaceReturnCode.PARAM_ERROR.getErrorCode(),"订单编号不能为空",null);
        }
        return BizResult.buildSuccessResult(null);
    }

    /**
     * 查询修改订单状态列表
     * @return
     */
    public  BizResult checkQueryList(){
        if(orderStatusChangeParam==null){
            return BizResult.buildFailResult(PalaceReturnCode.PARAM_ERROR.getErrorCode(),PalaceReturnCode.PARAM_ERROR.getErrorMsg(),null);
        }

        if(CollectionUtils.isEmpty(orderStatusChangeParam.getBizOrderIds())){
            return BizResult.buildFailResult(PalaceReturnCode.PARAM_ERROR.getErrorCode(),"订单编号不能为空",null);
        }
        return BizResult.buildSuccessResult(null);
    }



    public List<TcMainOrderVO> getTcMainOrderVOList() {
        return TcMainOrderVOList;
    }

    public void setTcMainOrderVOList(List<TcMainOrderVO> tcMainOrderVOList) {
        TcMainOrderVOList = tcMainOrderVOList;
    }
    public OrderStatusChangeParam getOrderStatusChangeParam() {
        return orderStatusChangeParam;
    }

    public void setOrderStatusChangeParam(OrderStatusChangeParam orderStatusChangeParam) {
        this.orderStatusChangeParam = orderStatusChangeParam;
    }

    public UserRepo getUserRepo() {
        return userRepo;
    }

    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
}
