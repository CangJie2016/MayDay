package com.cangjie.data.http.bean;

/**
 * 作者：${MXQ} on 2017/2/8 11:46
 * 邮箱：1299242483@qq.com
 */
public class VersionBean {
    public int codeNum;
    public String remarks;
    public String url;

    @Override
    public String toString() {
        return "VersionBean{" +
                "codeNum=" + codeNum +
                ", remarks='" + remarks + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
