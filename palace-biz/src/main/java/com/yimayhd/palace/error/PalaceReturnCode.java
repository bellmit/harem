package com.yimayhd.palace.error;

import java.io.Serializable;

/**
 * 取值[25000000 , 26000000)
 *
 * @author wzf
 */
public class PalaceReturnCode implements Serializable {
    private static final long serialVersionUID = 1L;
    private int errorCode;
    private String errorMsg;

    public PalaceReturnCode(int errorCode, String errorMsg) {
        super();
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    /******************************************* 系统相关 ******************************************************/
    public static final PalaceReturnCode REMOTE_CALL_FAILED = new PalaceReturnCode(25000000, "远程调用失败!");
    public static final PalaceReturnCode SYSTEM_ERROR = new PalaceReturnCode(25000001, "系统错误!");
    public static final PalaceReturnCode PARAM_ERROR = new PalaceReturnCode(25000002, "参数错误!");
    public static final PalaceReturnCode DATA_NOT_FOUND = new PalaceReturnCode(25000002, "数据不存在");


    /******************************************* 入驻审批 ******************************************************/
    public static final PalaceReturnCode APPROVE_FAILED = new PalaceReturnCode(25001000, "审批失败");
    public static final PalaceReturnCode APPROVE_REJECT_REASON_EMPTY = new PalaceReturnCode(25001001, "审批不通过需要有原因");
    public static final PalaceReturnCode APPLY_RECORD_NOT_EXIT = new PalaceReturnCode(25001002, "申请记录不存在");
    public static final PalaceReturnCode APPLY_APPROVE_STATUS_ERROR = new PalaceReturnCode(25001003, "记录已被修改了，目前不能审批，请刷新页面");
    public static final PalaceReturnCode MERCHANT_BIND_FAILED = new PalaceReturnCode(25001004, "商家绑定商品类目出错,请刷新页面");

    /***************************************** 商户相关 ********************************************************/
    public static final PalaceReturnCode ADD_MERCHANT_ERROR = new PalaceReturnCode(25001004, "新增美食商户失败");
    public static final PalaceReturnCode UPDATE_MERCHANT_ERROR = new PalaceReturnCode(25001005, "修改美食商户失败");


    public static final PalaceReturnCode FILE_TO_BIG = new PalaceReturnCode(25002000, "文件超过最大限制");
    public static final PalaceReturnCode UPLOAD_FILE_FAILED = new PalaceReturnCode(25002001, "上传文件失败");

    public static final PalaceReturnCode MUTI_MERCHANT = new PalaceReturnCode(25003000, "重复的店铺名称");
    public static final PalaceReturnCode MUTI_MERCHANT_FAILED = new PalaceReturnCode(25003001, "系统中已存在该店铺名称");

    public static final PalaceReturnCode QUERY_FAILED = new PalaceReturnCode(25003002, "查询失败");
    public static final PalaceReturnCode VERIFY_BANK_INFO_ERROR = new PalaceReturnCode(25003003, "银行账户信息验证不通过");

    public static final PalaceReturnCode UPDATE_WEIGHT_FAILED = new PalaceReturnCode(25003004, "设置权重失败");

    /***************************************** 导览相关********************************************************/

    public static final PalaceReturnCode ADD_GUIDE_ERROR = new PalaceReturnCode(25005001, "新增导览失败");
    public static final PalaceReturnCode ADD_GUIDE_ERROR_SCENICID = new PalaceReturnCode(25005002, "参数错误,景区id必须>0");
    public static final PalaceReturnCode ADD_GUIDE_ERROR_SCENICID_REPEATED = new PalaceReturnCode(25005003, "参数错误,景区重复");
    public static final PalaceReturnCode EDIT_GUIDE_ERROR = new PalaceReturnCode(25005104, "修改导览失败");
    public static final PalaceReturnCode UP_GUIDE_STATUS_ATTRACTION_ERROR = new PalaceReturnCode(25005201, "导览缺少景点信息");
    public static final PalaceReturnCode UP_GUIDE_STATUS_LINE_ERROR = new PalaceReturnCode(25005201, "导览缺少景点线路信息");
    public static final PalaceReturnCode UP_GUIDE_STATUS_SCENIC_ERROR = new PalaceReturnCode(25005201, "导览缺少景区信息");
}
