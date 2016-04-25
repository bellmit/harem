package com.yimayhd.palace.model.jiuxiu.helper;

import org.apache.commons.lang3.StringUtils;

import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.model.jiuxiu.JiuxiuTcBizOrder;
import com.yimayhd.palace.model.jiuxiu.JiuxiuTcDetailOrder;
import com.yimayhd.palace.model.query.JiuxiuMerchantListQuery;
import com.yimayhd.palace.util.Common;
import com.yimayhd.tradecenter.client.model.result.order.create.TcBizOrder;
import com.yimayhd.tradecenter.client.model.result.order.create.TcDetailOrder;
import com.yimayhd.user.client.enums.MerchantOption;
import com.yimayhd.user.client.query.MerchantPageQuery;

public class JiuxiuHelper {
	
	public static void fillDetailOrder(JiuxiuTcDetailOrder jiuxiuTcDetailOrder,JiuxiuTcBizOrder jiuxiuTcBizOrder,TcDetailOrder tcDetailOrder){
		//子订单详情下添加子订单基本信息
		jiuxiuTcDetailOrder.setJiuxiuTcBizOrder(jiuxiuTcBizOrder);
		jiuxiuTcDetailOrder.setItemPic(tcDetailOrder.getItemPic());
		jiuxiuTcDetailOrder.setItemTitle(tcDetailOrder.getItemTitle());
		jiuxiuTcDetailOrder.setStartDate(tcDetailOrder.getStartDate());
		jiuxiuTcDetailOrder.setLinePackage(tcDetailOrder.getLinePackage());
		jiuxiuTcDetailOrder.setPersonType(tcDetailOrder.getPersonType());
		jiuxiuTcDetailOrder.setItemPrice(tcDetailOrder.getItemPrice());
	}
	
	public static void fillBizOrder(JiuxiuTcBizOrder jiuxiuTcBizOrder,TcBizOrder tcBizOrder,String phone){
		//买家手机号
		jiuxiuTcBizOrder.setBuyerPhone(phone);
		jiuxiuTcBizOrder.setBuyerId(tcBizOrder.getBuyerId());
		jiuxiuTcBizOrder.setBuyerNick(tcBizOrder.getBuyerNick());
		jiuxiuTcBizOrder.setBuyAmount(tcBizOrder.getBuyAmount());
		jiuxiuTcBizOrder.setBizOrderId(tcBizOrder.getBizOrderId());
		jiuxiuTcBizOrder.setCreateTime(tcBizOrder.getCreateTime());
		jiuxiuTcBizOrder.setOrderType(tcBizOrder.getOrderType());
		jiuxiuTcBizOrder.setSellerId(tcBizOrder.getSellerId());
		jiuxiuTcBizOrder.setOrderStatus(tcBizOrder.getOrderStatus());
	}
	
	public static void fillMerchantListQuery(MerchantPageQuery merchantPageQuery,JiuxiuMerchantListQuery jiuxiuMerchantListQuery){
		merchantPageQuery.setPageNo(jiuxiuMerchantListQuery.getPageNumber());
		merchantPageQuery.setPageSize(jiuxiuMerchantListQuery.getPageSize());
		merchantPageQuery.setDomainId(Constant.DOMAIN_JIUXIU);
		long option = MerchantOption.addOption(MerchantOption.MERCHANT,MerchantOption.TALENT);
		if("4".equals(jiuxiuMerchantListQuery.getMerchantType())){
			option = MerchantOption.addOption(MerchantOption.TALENT);
		}else if("8".equals(jiuxiuMerchantListQuery.getMerchantType())){
			option = MerchantOption.addOption(MerchantOption.MERCHANT);
		}
		merchantPageQuery.setOption(option);
		merchantPageQuery.setOptionHandle(1);
		if(StringUtils.isNotEmpty(jiuxiuMerchantListQuery.getMerchantName())){
			merchantPageQuery.setName(jiuxiuMerchantListQuery.getMerchantName().trim());
		}
		if(StringUtils.isNotEmpty(jiuxiuMerchantListQuery.getMerchantNo()) && Common.regularMatches("[0-9]{1,}", jiuxiuMerchantListQuery.getMerchantNo())){
			merchantPageQuery.setSellerId(Long.parseLong(jiuxiuMerchantListQuery.getMerchantNo()));
		}
		if(StringUtils.isNotEmpty(jiuxiuMerchantListQuery.getMerchantPrincipal())){
			merchantPageQuery.setMerchantPrincipalLike(jiuxiuMerchantListQuery.getMerchantPrincipal().trim());
		}
		if(StringUtils.isNotEmpty(jiuxiuMerchantListQuery.getMerchantPrincipalTel())){
			merchantPageQuery.setMerchantPrincipalTelLike(jiuxiuMerchantListQuery.getMerchantPrincipalTel().trim());
		}
		if(StringUtils.isNotEmpty(jiuxiuMerchantListQuery.getStatus())){
			merchantPageQuery.setStatus(Integer.parseInt(jiuxiuMerchantListQuery.getStatus()));
		}
	}
}
