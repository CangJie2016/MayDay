package com.cangjie.data.http;

/**
 * author：CangJie on 2016/8/17 17:10
 * email：cangjie2016@gmail.com
 */
public class ApiException extends RuntimeException {

    public static final int OVER_LOGIN = 510;
    public static final int FAIL = 505;

    public ApiException(int resultCode, String returnInfo) {
        this(getApiExceptionMessage(resultCode , returnInfo));
    }

    private ApiException(String detailMessage) {
        super(detailMessage);
    }

    private static String getApiExceptionMessage(int code, String returnInfo){
        String message = "";
        switch (code) {
            case OVER_LOGIN:
                message = "重复登录";
                break;
            case FAIL:
                message = returnInfo;
                break;
            default:
                message = "未知错误";
        }
        return message;
    }
}
