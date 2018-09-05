package me.chon.boot.exception;

public enum ExceptionEnum {
    UNKNOW_ERROR (-1, "未知错误"),
    DEPT_EMPTY (201,"查询员工的部门信息为空，请与管理员联系");

    private Integer code;

    private String message;

    ExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
