package com.yimayhd.palace.helper;

import java.text.DecimalFormat;

public class NumberFormatHelper {

	public String formatNumber(long d) {
		DecimalFormat df = new DecimalFormat(".##");
		return df.format(d/100.00);
		
	}
}
