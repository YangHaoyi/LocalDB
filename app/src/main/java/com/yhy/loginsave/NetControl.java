package com.yhy.loginsave;

/**
 * 作者 : YangHaoyi on 2016/7/18.
 * 邮箱 ：yanghaoyi@neusoft.com
 */
public class NetControl {

    private static String token;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        NetControl.token = token;
    }
}
