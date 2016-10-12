package com.yimayhd.palace.enums;

import com.yimayhd.commentcenter.client.enums.BaseStatus;
import com.yimayhd.ic.client.model.enums.OrderNumFilterEnum;

/**
 * @author create by yushengwei on 2016/10/11
 * @Description excel文件导出类型
 */
public enum ExportType {
    EXPORT_GF(1, "gf订单导出"), EXPORT_MSG_MOBILE(2, "短信推送-手机号");
    private int type;
    private String desc;

    private ExportType(int type,String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static ExportType getByType(int type){
        for (ExportType em : ExportType.values()) {
            if (em.getType() == type) {
                return em;
            }
        }
        return null;
    }

}
