package com.yimayhd.palace.constant;

/**
 * @author wenfeng zhang @ 10/21/14
 */
public enum ResponseStatus {

    SUCCESS(200, "执行成功！"),
    ERROR(500, "服务器未知错误！"),
    UNAUTHORIZED(401, "没有登录！"),
    FORBIDDEN(403, "没有权限！"),
    NOT_FOUND(404, "资源不存在！"),
    DATA_PARSE_ERROR(4001, "JSON解析错误！"),
    INVALID_DATA(4002, "数据校验错误，请检查输入！"),
    FILE_TO_BIG(4003, "文件过大！"),
    DATA_USER_DELETE(4004, "用户已删除！"),
    UNSUCCESSFUL(-200, "执行失败！"),
    ;

    public Integer VALUE;
    public String MESSAGE;

    ResponseStatus(Integer value, String message) {
        this.VALUE = value;
        this.MESSAGE = message;
    }

}
