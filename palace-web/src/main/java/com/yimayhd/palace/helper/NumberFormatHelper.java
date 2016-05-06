package com.yimayhd.palace.helper;

import java.text.DecimalFormat;

public class NumberFormatHelper {

	public String formatNumber(long d) {
		DecimalFormat df = new DecimalFormat("0.##");
		return df.format(d/100.00);
		
	}
	public String formatDoubleNumber(double d) {
		DecimalFormat df = new DecimalFormat("0.######");
		return df.format(d);
		
	}
}
