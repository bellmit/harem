package com.yimayhd.util;

/**
 * Created by wqy on 15-5-13.
 */
public class AlipayConfig {

    public static final String GATEWAY = "https://mapi.alipay.com/gateway.do?";      // Alipay服务网关

    public static final String SERVICE_CREATE_DIRECT_PAY = "create_direct_pay_by_user";     // Alipay即时到帐服务接口

    public static final String PARTNER_ID = "2088911646876372";         //Alipay 合作商ID

    public static final String KEY = "hzkdg9sasdca8qrrh2kqws7gyckd6ohq";         // 账号私钥

    public static final String SELLER_EMAIL = "share@com.yimayhd.com";       // 卖家支付宝账号

    public static final String PAYMENT_TYPE_BUY = "1";       // Alipay 支付类型

    public static final String CRYPT_TYPE = "MD5";

    public static final String INPUT_CHARSET = "utf-8";     // 参数字符集编码 上一个客户帮我查的日志说 连接超时好的

    public static final String SELLER_ID = "";     // 卖家支付宝账号的用户号，以2088开头

    public static final String NOTIFY_URL = "http://119.254.102.238:18080/alipay/async_return";         // 异步通知地址

    public static final String RETURN_URL = "http://119.254.102.238:18082/#payment";         // 同步通知地址

    public static final String NOTIFY_URL_ORDER_INVEST = "http://119.254.102.238:18080/alipay/orderInvest/async_return"; // 异步通知地址(茶山投资)

}
