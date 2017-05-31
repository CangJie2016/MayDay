package com.cangjie.basetool.apk;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * 作者：${MXQ} on 2017/2/10 15:18
 * 邮箱：1299242483@qq.com
 * 描述：通过OkHttpClient添加1个拦截器来使用ProgressResponseBody
 */
public class ProgressHelper {
    private static ProgressBean mProgressBean = new ProgressBean();
    private static ProgressHandler mProgressHandler;

    public static OkHttpClient.Builder addProgress(OkHttpClient.Builder builder) {
        if (builder == null) {
            builder = new OkHttpClient.Builder();
        }

        final ProgressListener mProgressListener = new ProgressListener() {
            @Override
            public void onProgress(long progress, long total, boolean done) {
                if(mProgressHandler == null){
                    return;
                }
                   /*由0.处的回调，获取: 下载进度*/
                mProgressBean.setBytesRead(progress);
                mProgressBean.setContentLength(total);
                mProgressBean.setDone(done);
                   /*由于在非主线程运行，则利用Handler传递mProgressBean对象至WelcomeActivity*/
                mProgressHandler.sendMessage(mProgressBean);


            }
        };

        /*OkHttpClient.Builder添加网络拦截器*/
        builder.networkInterceptors().add(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                /*获得ResponseBody*/
                okhttp3.Response mResponse = chain.proceed(chain.request());
                /*重写ResponseBody{0.传入"获取下载进度"接口对象}*/
                return mResponse.newBuilder().body(new ProgressResponseBody(mResponse.body(),mProgressListener))
                        .build();
            }
        });
        return builder;
    }

    public static void setProgressHandler(ProgressHandler progressHandler){
        mProgressHandler = progressHandler;
    }
}
