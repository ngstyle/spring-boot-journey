package me.chon.boot.bean;

public class HttpResult<T> {

    private int code;

    private String message;

    private T data;

    public static HttpResult success() {
        HttpResult httpResult = new HttpResult();
        httpResult.setCode(100);
        httpResult.setMessage("请求成功");
        return httpResult;
    }

    public static HttpResult fail() {
        HttpResult httpResult = new HttpResult();
        httpResult.setCode(200);
        httpResult.setMessage("请求失败");
        return httpResult;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}