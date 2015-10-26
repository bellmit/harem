package com.yimayhd.util;


import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import java.lang.Exception;import java.lang.String;

/**
 * Created by wqy on 15-5-12.
 */
public class CryptUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CryptUtils.class);


    /**
     * MD5 加密算法（Common Codec实现）
     *
     * @param content 要加密的字符串
     * @param key 账户私钥
     * @return 加密后的字符串
     *
     * */
    public static String  Md5Crypt (String content, String key) {
        content += key;
        return DigestUtils.md5Hex(content);
    }

    /**
     * 签名字符串
     * @param preSignText 需要签名的字符串
     * @param signResult 签名结果
     * @param key 密钥
     * @return 签名结果
     */
    public static boolean verify(String preSignText, String signResult, String key) {
        preSignText = preSignText + key;
        String mysign = DigestUtils.md5Hex(preSignText);
        return mysign.equals(signResult);
    }

    public static void main(String[] args) {
        String preSignText = "_input_charset=utf-8&notify_url=http://127.0.0.1:8080/create_direct_pay_by_user-JAVA-UTF-8/notify_url.jsp&out_trade_no=20150611183916353&partner=2088911646876372&payment_type=1&return_url=http://127.0.0.1:8080/create_direct_pay_by_user-JAVA-UTF-8/return_url.jsp&seller_email=share@com.yimayhd.com&service=create_direct_pay_by_user&subject=产地武夷山大红袍&total_fee=0.01";
        String signResult = "44898e7d628576bc6ed5a3a60d86505f";
        String key = "hzkdg9sasdca8qrrh2kqws7gyckd6ohq";
        System.out.println(verify(preSignText, signResult, key));
    }

    /**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String input_charset) throws Exception{
        text = text + key;

        System.out.println("text:" + text);
        String result = DigestUtils.md5Hex(getContentBytes(text, input_charset));
        System.out.println(result);

        return result;
    }


    /**
     * @param content
     * @param charset
     * @return
     */
    private static byte[] getContentBytes(String content, String charset) throws Exception{
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        return content.getBytes(charset);
    }


}
