package com.sticket.app.sticket.retrofit.message;

/**
 * Created by YangHC on 2017-09-06.
 */

public class ApiMessasge {
    public static final int SUCCESS = 1;
    public static final int ERROR = -1;
    public static final int CANNOT_FIND_USER = -1512;
    public static final int LESS_THAN_6_MONTH = -1513;

    private int code;
    private String message;

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
}
