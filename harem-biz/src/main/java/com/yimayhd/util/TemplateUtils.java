package com.yimayhd.util;


import java.util.List;

/**
 * Created by wqy on 15-4-30.
 * 模板工具类
 */

public class TemplateUtils {

    /**
     * 关键字替换
     * @param templateText 模板内容字符串
     * @param list 模板关键字对象 类似key-value 但key可重复不可为空
     *             key为关键字，value为应替换的字符串
     * @return 返回已替换的模板内容
     * */
    public static String replaceKeyWord(String templateText, List<NameValueBlock> list) {

        for (NameValueBlock nameValueBlock : list) {
            templateText = templateText.replaceAll(nameValueBlock.getName(), nameValueBlock.getValue()).replaceAll(nameValueBlock.getName(), nameValueBlock.getValue());
        }
        return templateText;
    }

}
