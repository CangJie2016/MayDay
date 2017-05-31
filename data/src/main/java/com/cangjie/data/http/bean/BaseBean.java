package com.cangjie.data.http.bean;

/**
 * 作者：CangJie on 2016/2/26 10:20
 * 邮箱：cangjie2016@gmail.com
 */
public class BaseBean<T> {
    public int returnCode;
    public int returnSize;
    public String returnInfo;
    public T returnData;

    @Override
    public String toString() {
        return "BaseBean{" +
                "returnCode=" + returnCode +
                ", returnSize=" + returnSize +
                ", returnInfo='" + returnInfo + '\'' +
                ", returnData=" + returnData +
                '}';
    }

}
