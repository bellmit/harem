package com.yimayhd.palace.model.jiuxiu.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.model.jiuxiu.JiuxiuTcBizOrder;
import com.yimayhd.palace.model.jiuxiu.JiuxiuTcDetailOrder;
import com.yimayhd.palace.model.query.JiuxiuMerchantListQuery;
import com.yimayhd.palace.model.query.JiuxiuOrderListQuery;
import com.yimayhd.palace.util.Common;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.tradecenter.client.model.enums.OrderBizType;
import com.yimayhd.tradecenter.client.model.param.order.OrderQueryDTO;
import com.yimayhd.tradecenter.client.model.result.order.create.TcBizOrder;
import com.yimayhd.tradecenter.client.model.result.order.create.TcDetailOrder;
import com.yimayhd.tradecenter.client.util.BizOrderUtil;
import com.yimayhd.user.client.enums.MerchantOption;
import com.yimayhd.user.client.enums.MerchantSort;
import com.yimayhd.user.client.query.MerchantPageQuery;

public class JiuxiuHelper {
	private static final Logger LOG = LoggerFactory.getLogger(JiuxiuHelper.class);
	
	public static void fillDetailOrder(JiuxiuTcDetailOrder jiuxiuTcDetailOrder,TcBizOrder tcBizOrder,TcDetailOrder tcDetailOrder){
		//子订单详情下添加子订单基本信息
		jiuxiuTcDetailOrder.setBizOrder(tcBizOrder);
//		jiuxiuTcDetailOrder.setJiuxiuTcBizOrder(jiuxiuTcBizOrder);
		jiuxiuTcDetailOrder.setItemPic(tcDetailOrder.getItemPic());
		jiuxiuTcDetailOrder.setItemTitle(tcDetailOrder.getItemTitle());
		jiuxiuTcDetailOrder.setStartDate(tcDetailOrder.getStartDate());
		jiuxiuTcDetailOrder.setLinePackage(tcDetailOrder.getLinePackage());
		jiuxiuTcDetailOrder.setPersonType(tcDetailOrder.getPersonType());
		jiuxiuTcDetailOrder.setItemPrice(tcDetailOrder.getItemPrice());
		jiuxiuTcDetailOrder.setActivityTime(tcDetailOrder.getActivityTime());
		jiuxiuTcDetailOrder.setActivityContent(tcDetailOrder.getActivityContent());
		//订单实付总额
        long total = BizOrderUtil.getSubOrderActualFee(tcDetailOrder.getBizOrder().getBizOrderDO());
		 //获取子订单实付金额
        if(tcDetailOrder.getBizOrder().getBuyAmount() > 0){
        	long act = total/tcDetailOrder.getBizOrder().getBuyAmount();
        	jiuxiuTcDetailOrder.setItemPrice_(act);
        }
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
	
	public static void fillMerchantListQuery(MerchantPageQuery merchantPageQuery,JiuxiuMerchantListQuery jiuxiuMerchantListQuery) throws ParseException{
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
			merchantPageQuery.setNameLike(jiuxiuMerchantListQuery.getMerchantName().trim());
		}
//		if(StringUtils.isNotEmpty(jiuxiuMerchantListQuery.getMerchantNo()) && Common.regularMatches("[0-9]{1,}", jiuxiuMerchantListQuery.getMerchantNo())){
//			merchantPageQuery.setSellerId(Long.parseLong(jiuxiuMerchantListQuery.getMerchantNo().trim()));
//		}
		if(StringUtils.isNotEmpty(jiuxiuMerchantListQuery.getMerchantNo())){
			if(!Common.regularMatches("[0-9]{1,}", jiuxiuMerchantListQuery.getMerchantNo().trim())){
				merchantPageQuery.setSellerId(Long.MAX_VALUE);
			}else{
				merchantPageQuery.setSellerId(Long.parseLong(jiuxiuMerchantListQuery.getMerchantNo().trim()));
			}
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
		if (jiuxiuMerchantListQuery.getGmtCreated() != null) {
			merchantPageQuery.setGmtCreatedStart(jiuxiuMerchantListQuery.getGmtCreated());
			long dateEnd = jiuxiuMerchantListQuery.getGmtCreated().getTime()+1000*60*60*24-1;
			merchantPageQuery.setGmtCreatedEnd(new Date(dateEnd));
		}
		merchantPageQuery.setSortOption(MerchantSort.GMT_CREATED_DESC.getCode());
	}
	
	public static void fillOrderQueryDTO(OrderQueryDTO dto, JiuxiuOrderListQuery jiuxiuOrderListQuery){
		dto.setDomain(Constant.DOMAIN_JIUXIU);
		dto.setPageNo(jiuxiuOrderListQuery.getPageNumber());
		dto.setPageSize(jiuxiuOrderListQuery.getPageSize());
		dto.setNeedDetailOrders(true);
		dto.setIsMain(1);
//		dto.setSellerId(sessionManager.getUserId());
		//商品类型
		if(StringUtils.isNotEmpty(jiuxiuOrderListQuery.getItemType())){
			int[] orderBizTypes;
			if(jiuxiuOrderListQuery.getItemType().equals(OrderBizType.HOTEL.getBizType()+"")){
				orderBizTypes = new int[]{OrderBizType.HOTEL.getBizType(),OrderBizType.HOTEL_OFFLINE.getBizType()};
				dto.setOrderBizTypes(orderBizTypes);
			}else{
				dto.setOrderBizTypes(new int[]{Integer.parseInt(jiuxiuOrderListQuery.getItemType())});
			}
		}
		if(StringUtils.isNotEmpty(jiuxiuOrderListQuery.getItemName())){
			dto.setItemName(jiuxiuOrderListQuery.getItemName());
		}
		
		//订单编号
		List<Long> bizOrderIds = new ArrayList<Long>();
		if(StringUtils.isNotEmpty(jiuxiuOrderListQuery.getOrderNO()) && Common.regularMatches("[0-9]{1,}", jiuxiuOrderListQuery.getOrderNO().trim())){
			bizOrderIds.add(Long.parseLong(jiuxiuOrderListQuery.getOrderNO().trim()));
			dto.setBizOrderIds(bizOrderIds);
		}
		if (StringUtils.isNotEmpty(jiuxiuOrderListQuery.getBeginDate())){
            try {
            	dto.setStartDate(DateUtil.convertStringToDate(jiuxiuOrderListQuery.getBeginDate()));
            } catch (ParseException e) {
            	LOG.error("dto.setStartDate(DateUtil.convertStringToDate(jiuxiuOrderListQuery.getBeginDate())); Exception:" + e);
                e.printStackTrace();
            }
        }
		if (StringUtils.isNotEmpty(jiuxiuOrderListQuery.getEndDate())){
            try {
            	dto.setEndDate(DateUtil.convertStringToDate(jiuxiuOrderListQuery.getEndDate()));
            } catch (ParseException e) {
            	LOG.error("dto.setStartDate(DateUtil.convertStringToDate(jiuxiuOrderListQuery.getBeginDate())); Exception:" + e);
                e.printStackTrace();
            }
        }
		if(StringUtils.isNotEmpty(jiuxiuOrderListQuery.getBuyerPhone())){
			dto.setPhone(jiuxiuOrderListQuery.getBuyerPhone());
		}
		if(StringUtils.isNotEmpty(jiuxiuOrderListQuery.getBuyerName())){
			dto.setBuyerNick(jiuxiuOrderListQuery.getBuyerName());
		}
		//订单状态
		if (StringUtils.isNotEmpty(jiuxiuOrderListQuery.getOrderStat())){
			dto.setBizOrderStatus(Integer.parseInt(jiuxiuOrderListQuery.getOrderStat()));
		}
		if(StringUtils.isNotEmpty(jiuxiuOrderListQuery.getMerchantName())){
			dto.setMerchantName(jiuxiuOrderListQuery.getMerchantName());
		}
		//商户编号
		if (StringUtils.isNotEmpty(jiuxiuOrderListQuery.getMerchantNo())){
			dto.setSellerId(Long.parseLong(jiuxiuOrderListQuery.getMerchantNo()));
		}
	}
}
