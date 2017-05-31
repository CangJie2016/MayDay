package com.cangjie.data.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author：CangJie on 2016/8/18 11:36
 * email：cangjie2016@gmail.com
 */
public class RetrofitSetting {
    private static final int DEFAULT_TIMEOUT = 120;
    private static Retrofit retrofit;

    public static Retrofit getInstance(){
        if(retrofit == null){
            //手动创建一个OkHttpClient并设置超时时间`
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
//            builder.addInterceptor(new LogInterceptor());
            retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(HttpMethods.BASE_URL)
                    .build();
        }
        return retrofit;
    }

}
