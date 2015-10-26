package com.yimayhd.util;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.lang.Exception;import java.lang.String;import java.util.ArrayList;import java.util.Collections;import java.util.HashMap;import java.util.List;import java.util.Map;

/**
 * Created by wqy on 15-5-13.
 */
public class AlipayUtils {


    /**
     * 验证消息是否是支付宝发出的合法消息
     * @param params 通知返回来的参数数组
     * @return 验证结果
     */
    /*public static boolean verify(Map<String, String> params) {

        //判断responsetTxt是否为true，isSign是否为true
        //responsetTxt的结果不是true，与服务器设置问题、合  作身份者ID、notify_id一分钟失效有关
        //isSign不是true，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
        String responseTxt = "true";
        if(params.get("notify_id") != null) {
            String notify_id = params.get("notify_id");
            responseTxt = verifyResponse(notify_id);
        }
        String sign = "";
        if(params.get("sign") != null) {sign = params.get("sign");}
        boolean isSign = getSignVeryfy(params, sign);

        //写日志记录（若要调试，请取消下面两行注释）
        //String sWord = "responseTxt=" + responseTxt + "\n isSign=" + isSign + "\n 返回回来的参数：" + AlipayCore.createLinkString(params);
        //AlipayCore.logResult(sWord);

        if (isSign && responseTxt.equals("true")) {
            return true;
        } else {
            return false;
        }
    }*/

    /**
     * 根据反馈回来的信息，生成签名结果
     * @param Params 通知返回来的参数数组
     * @param sign 比对的签名结果
     * @return 生成的签名结果
     */
    /*private static boolean getSignVeryfy(Map<String, String> Params, String sign) throws Exception {
        //过滤空值、sign与sign_type参数
        Map<String, String> sParaNew = AlipayCore.paraFilter(Params);
        //获取待签名字符串
        String preSignStr = AlipayCore.createLinkString(sParaNew);
        //获得签名验证结果
        boolean isSign = false;
        if(AlipayConfig.CRYPT_TYPE.equals("MD5") ) {
            isSign = CryptUtils.Md5Crypt(preSignStr, AlipayConfig.KEY);
        }
        return isSign;
    }*/



    /**
     * 除去数组中的空值和签名参数
     * @param params 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> params) {

        Map<String, String> result = new HashMap<String, String>();

        if (params == null || params.size() <= 0) {
            return result;
        }

        for (String key : params.keySet()) {
            String value = params.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                    || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String preStr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {         //拼接时，不包括最后一个&字符
                preStr = preStr + key + "=" + value;
            } else {
                preStr = preStr + key + "=" + value + "&";
            }
        }

        return preStr;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值1|参数值2 ”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String  createLinkStringArr(Map<String, String[]> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String preStr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String[] value = params.get(key);
            String val = "";

            if (i == keys.size() - 1) {

                for (int x = 0; x < value.length; x++) {
                    if (x == value.length - 1) {
                        val = val + value[x];
                    } else {
                        val = val + value[x] + "|" ;
                    }
                }
                preStr = preStr + key + "=" + val;
            } else {
                for (int x = 0; x < value.length; x++) {
                    if (x == value.length - 1) {
                        val = val + value[x];
                    } else {
                        val = val + value[x] + "|" ;
                    }
                }
                preStr = preStr + key + "=" + val + "&";
            }
        }
        return preStr;
    }

    /**
     * 把参数对象进行排序，并使用NameValuePair返回
     * @param params 需要排序并参与字符拼接的参数组
     * @return 处理后的参数List集合
     */
    public static List<NameValuePair> createParamsList(Map<String, String> params) throws Exception {

        List<String> keys = new ArrayList<String>(params.keySet());

        Collections.sort(keys);

        List<NameValuePair> list = new ArrayList<NameValuePair>();

        for (int i = 0; i < keys.size(); i++) {

            String key = keys.get(i);

            list.add(new BasicNameValuePair(keys.get(i), params.get(key)));

        }

        return list;
    }

    /**
     * 对参数进行加密
     * @param params 需要加密的参数
     * @return 加密后的字符串
     * */
    public static String buildSignString(Map<String, String> params) throws Exception {

        /**
         * 对参数进行格式化
         *（排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串）
         * */
        String paramString = createLinkString(params);

        return CryptUtils.Md5Crypt(paramString, AlipayConfig.KEY);
    }


    /**
     * 建立请求，以表单HTML形式构造（默认）
     * @param sParaTemp 请求参数数组
     * @param strMethod 提交方式。两个值可选：post、get
     * @param strButtonName 确认按钮显示文字
     * @return 提交表单HTML文本
     */
    public static String buildRequest(Map<String, String> sParaTemp, String strMethod, String strButtonName) throws Exception{
        //待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp);
        List<String> keys = new ArrayList<String>(sPara.keySet());

        StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\"></head><form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + AlipayConfig.GATEWAY
                + "_input_charset=" + AlipayConfig.INPUT_CHARSET + "\" method=\"" + strMethod
                + "\">");

        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sPara.get(name);

            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }

        //submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script></body></html>");

        return sbHtml.toString();
    }

    /**
     * 生成要请求给支付宝的参数数组
     * @param sParaTemp 请求前的参数数组
     * @return 要请求的参数数组
     */
    private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp) throws Exception{
        //除去数组中的空值和签名参数
        Map<String, String> sPara = AlipayUtils.paraFilter(sParaTemp);
        //生成签名结果
        String mysign = buildRequestMysign(sPara);

        //签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", mysign);
        sPara.put("sign_type", AlipayConfig.CRYPT_TYPE);

        return sPara;
    }

    /**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestMysign(Map<String, String> sPara) throws Exception{
        String preStr = AlipayUtils.createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mySign = "";
        if(AlipayConfig.CRYPT_TYPE.equals("MD5") ) {
            mySign = CryptUtils.sign(preStr, AlipayConfig.KEY, AlipayConfig.INPUT_CHARSET);
        }
        return mySign;
    }
}
