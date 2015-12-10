package com.yimayhd.harem.util;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2015/10/27.
 */
public class NumUtil {

    /**
     * 金额转换（分转换为元）
     * @param money
     * @return
     */
    public static String moneyTransform(long money){
        double dn = money;
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        return decimalFormat.format(dn/100);
    }

}
