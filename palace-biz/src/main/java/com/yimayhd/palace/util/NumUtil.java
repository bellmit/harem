package com.yimayhd.palace.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2015/10/27.
 */
public class NumUtil {
    private static final Logger logger = LoggerFactory.getLogger(NumUtil.class);
    /**
     * 金额转换（分转换为元:#0.00）
     * @param money
     * @return
     */
    public static String moneyTransform(long money){
        double dn = money;
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        return decimalFormat.format(dn/100);
    }
    /**
     * 金额转换（分转换为元#0）
     * @param money
     * @return
     */
    public static long moneyTrans(long money){
        return money/100;
    }

    /**
     * 金额转换（分转换为元:#0.00）
     * @param money
     * @return
     */
    public static double moneyTransformDouble(long money){
        double dn = money;
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        return Double.parseDouble(decimalFormat.format(dn / 100));
    }

    /**
     * 金额转换（元转分）
     * @param d
     * @return
     */

    public static long round(double d){
        return Math.round(d * 100);
    }
    public static long doubleToLong(double d){
       return Math.round(d * 100);
    }

    public static double totalFee(long num,long price){
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        double realPrice = Double.parseDouble(decimalFormat.format(((double)price) / 100));
        return num * realPrice;
    }
    public static long moneyTransformString(String money){
        try {
            double dn = Double.parseDouble(money)*100;
            DecimalFormat decimalFormat = new DecimalFormat("#0");
            String str = decimalFormat.format(dn);
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取商品单价
      * @param money 订单金额(分)
     * @param num 总数量
     * @return
     */
    public static String getOrderUnitPrice(long money,long num){
        BigDecimal bmoney = new BigDecimal(money);
        BigDecimal bnum = new BigDecimal(num);
        //logger.info("money={},bmoney={},num={},bnum={}",money,bmoney,num,bnum);
        BigDecimal amount =bmoney.divide(bnum.multiply(new BigDecimal(100)),BigDecimal.ROUND_HALF_UP);
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        return  decimalFormat.format(amount).toString();
    }


    public static void main(String[] args){
        System.out.println(moneyTransformString("1.99"));
        //System.out.println(totalFee(2,100));
    }


}
