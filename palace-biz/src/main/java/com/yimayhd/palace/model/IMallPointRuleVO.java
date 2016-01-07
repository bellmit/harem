package com.yimayhd.palace.model;

import com.yimayhd.tradecenter.client.model.param.imall.pointrule.IMallPointRuleDTO;
import com.yimayhd.tradecenter.client.model.result.imall.pointrule.IMallPointRuleResult;
import org.apache.commons.beanutils.BeanUtils;

/**
 * Created by Administrator on 2015/12/3.
 */
public class IMallPointRuleVO extends IMallPointRuleDTO {
    private double paymentY;//payment单位为分，对应的元

    public static IMallPointRuleVO getIMallPointRuleVO(IMallPointRuleResult iMallPointRuleResult) throws Exception{
        IMallPointRuleVO iMallPointRuleVO = new IMallPointRuleVO();
        BeanUtils.copyProperties(iMallPointRuleVO, iMallPointRuleResult);
        return iMallPointRuleVO;
    }
    public static IMallPointRuleDTO getIMallPointRuleDTO(IMallPointRuleVO iMallPointRuleVO){
        iMallPointRuleVO.setPayment((long) (iMallPointRuleVO.getPaymentY() * 100));
        return iMallPointRuleVO;
    }

    public double getPaymentY() {
        return paymentY;
    }

    public void setPaymentY(double paymentY) {
        this.paymentY = paymentY;
    }
}
