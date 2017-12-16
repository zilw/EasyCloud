package com.pdwu.easycloud.common.bean;

/**
 * Created by pdwu on 2017/11/22.
 */
public class ResultBean {
    private final String msg;
    private final int code;
    private final Object data;

    //error
    public static final ResultBean ARG_ERROR = fail("参数异常");

    private static final Object EMPTY_OBJECT = new Object();

    public ResultBean(int code, Object data, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public static ResultBean success(Object data) {
        return new ResultBean(ResultCode.ok, data, "");
    }

    public static ResultBean success(int resultCode, Object data) {
        return new ResultBean(resultCode, data, "");
    }

    public static ResultBean fail(String errorMsg) {
        return new ResultBean(ResultCode.fail, EMPTY_OBJECT, errorMsg);
    }

    public static ResultBean fail(int resultCode, String errorMsg) {
        return new ResultBean(resultCode, EMPTY_OBJECT, errorMsg);
    }


    public String getMsg() {
        return msg;
    }


    public int getCode() {
        return code;
    }


    public Object getData() {
        return data;
    }


    @Override
    public String toString() {
        return "ResultBean{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
