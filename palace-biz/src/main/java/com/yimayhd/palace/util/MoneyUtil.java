package com.yimayhd.palace.util;

import java.text.NumberFormat;

import com.yimayhd.tradecenter.client.model.enums.TcPayChannel;

/**
 * 钱工具
 * 
 * @author yebin
 *
 */
public class MoneyUtil {
	/**
	 * 货币格式化
	 * 
	 * @param money
	 * @return
	 */
	public static String moneyFormat(Object money) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMaximumFractionDigits(2);
		return numberFormat.format(money);
	}

	/**
	 * 货币格式化
	 * 
	 * @param money
	 * @return
	 */
	public static String moneyFormatInInt(Object money) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMaximumFractionDigits(0);
		return numberFormat.format(money);
	}

	public static void main(String[] args) {
		System.out.println(moneyFormat("100"));
		System.out.println(moneyFormat(123123213.0163123));
		System.out.println(moneyFormatInInt(123123213.7163123));
	}

	public static String moneyY(Double money) {
		return new java.text.DecimalFormat("######0.00").format(money);
	}
	public static String centToYuanMoneyFormat(long money) {
		return moneyFormat(money * 0.01);
	}
	
	public static String getPayChannel(int payChannel) {
		if (payChannel <= 0) {
			return "";
		}
		TcPayChannel tcPayChannel = TcPayChannel.getPayChannel(payChannel);
		if (tcPayChannel == null) {
			return "";
		}
		return tcPayChannel.getDesc();
	}
}

