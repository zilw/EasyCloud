package com.pdwu.easycloud.common.bean;

/**
 * Created by pdwu on 2017/11/22.
 */
public class ResultBean {
    private String msg;
    private int code;
    private Object data;

    //error
    public static final ResultBean ARG_ERROR = fail("参数异常");
    public static final ResultBean SERVER_ERROR = fail(ResultCode.server_error, "服务器异常");

    public ResultBean() {
    }

    public ResultBean(int code, Object data, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public static ResultBean success(Object data) {
        return new ResultBean(ResultCode.ok, data, "");
    }

    public static ResultBean fail(String errorMsg) {
        return new ResultBean(ResultCode.fail, null, errorMsg);
    }

    public static ResultBean fail(int resultCode, String errorMsg) {
        return new ResultBean(resultCode, null, errorMsg);
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
