package com.yimayhd.palace.model;

import java.text.DecimalFormat;

import org.springframework.beans.BeanUtils;

import com.yimayhd.palace.util.NumUtil;
import com.yimayhd.pay.client.model.domain.audit.PayAuditResultDO;

/**
 * Created by hongfei.guo on 2016/07/19.
 */
public class PayAuditResultVO extends PayAuditResultDO {

	private static final long serialVersionUID = 1L;

	/**平台总金额 - 元*/
    private double platformTotalAmountYuan;

    /**平台收入总金额 - 元*/
    private double platformIncomeAmountYuan;

    /**平台支出总金额 - 元*/
    private double platformExpendAmountYuan;

    /**平台渠道费 - 元*/
    private double platformChannelFeeYuan;

	/**对方总金额 - 元*/
    private double oppositeTotalAmountYuan;

    /**对方收入总金额 - 元*/
    private double oppositeIncomeAmountYuan;

    /**对方支出总金额 - 元*/
    private double oppositeExpendAmountYuan;

    /**对方渠道费 - 元*/
    private double oppositeChannelFeeYuan;
    
    /**差异金额 - 元： 平台 - 第三方 **/
    private double diffAmountYuan;
	
    public static PayAuditResultVO getPayAuditResultVO(PayAuditResultDO payAuditResultDO){
    	if(payAuditResultDO == null){
    		return null;
    	}
    	
        PayAuditResultVO vo = new PayAuditResultVO();
        BeanUtils.copyProperties(payAuditResultDO, vo);
        vo.setPlatformChannelFeeYuan(NumUtil.moneyTransformDouble(vo.getPlatformChannelFee()));
        vo.setPlatformExpendAmountYuan(NumUtil.moneyTransformDouble(vo.getPlatformExpendAmount()));
        vo.setPlatformIncomeAmountYuan(NumUtil.moneyTransformDouble(vo.getPlatformIncomeAmount()));
        vo.setPlatformTotalAmountYuan(NumUtil.moneyTransformDouble(vo.getPlatformTotalAmount()));
        
        vo.setOppositeTotalAmountYuan(NumUtil.moneyTransformDouble(vo.getOppositeTotalAmount()));
        vo.setOppositeIncomeAmountYuan(NumUtil.moneyTransformDouble(vo.getOppositeTotalAmount()));
        vo.setOppositeExpendAmountYuan(NumUtil.moneyTransformDouble(vo.getOppositeTotalAmount()));
        vo.setOppositeChannelFeeYuan(NumUtil.moneyTransformDouble(vo.getOppositeTotalAmount()));
        
        double diffAmountYuan = vo.getPlatformTotalAmountYuan() - vo.getOppositeTotalAmountYuan();
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        vo.setDiffAmountYuan(Double.parseDouble(decimalFormat.format(diffAmountYuan)));
        
        return vo;
    }
    
    public double getPlatformTotalAmountYuan() {
		return platformTotalAmountYuan;
	}

	public void setPlatformTotalAmountYuan(double platformTotalAmountYuan) {
		this.platformTotalAmountYuan = platformTotalAmountYuan;
	}

	public double getPlatformIncomeAmountYuan() {
		return platformIncomeAmountYuan;
	}

	public void setPlatformIncomeAmountYuan(double platformIncomeAmountYuan) {
		this.platformIncomeAmountYuan = platformIncomeAmountYuan;
	}

	public double getPlatformExpendAmountYuan() {
		return platformExpendAmountYuan;
	}

	public void setPlatformExpendAmountYuan(double platformExpendAmountYuan) {
		this.platformExpendAmountYuan = platformExpendAmountYuan;
	}

	public double getPlatformChannelFeeYuan() {
		return platformChannelFeeYuan;
	}

	public void setPlatformChannelFeeYuan(double platformChannelFeeYuan) {
		this.platformChannelFeeYuan = platformChannelFeeYuan;
	}
	
	public double getOppositeTotalAmountYuan() {
		return oppositeTotalAmountYuan;
	}

	public void setOppositeTotalAmountYuan(double oppositeTotalAmountYuan) {
		this.oppositeTotalAmountYuan = oppositeTotalAmountYuan;
	}

	public double getOppositeIncomeAmountYuan() {
		return oppositeIncomeAmountYuan;
	}

	public void setOppositeIncomeAmountYuan(double oppositeIncomeAmountYuan) {
		this.oppositeIncomeAmountYuan = oppositeIncomeAmountYuan;
	}

	public double getOppositeExpendAmountYuan() {
		return oppositeExpendAmountYuan;
	}

	public void setOppositeExpendAmountYuan(double oppositeExpendAmountYuan) {
		this.oppositeExpendAmountYuan = oppositeExpendAmountYuan;
	}

	public double getOppositeChannelFeeYuan() {
		return oppositeChannelFeeYuan;
	}

	public void setOppositeChannelFeeYuan(double oppositeChannelFeeYuan) {
		this.oppositeChannelFeeYuan = oppositeChannelFeeYuan;
	}

	public double getDiffAmountYuan() {
		return diffAmountYuan;
	}

	public void setDiffAmountYuan(double diffAmountYuan) {
		this.diffAmountYuan = diffAmountYuan;
	}
}
