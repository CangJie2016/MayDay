package com.cangjie.basetool.apk;

import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

// 负责下载apk
public class DownLoadApkHelper {
    public DownloadFile apiStore;
    private Call<ResponseBody> call;
    private String url;
    private OnDownloadApkListener onDownloadApkListener;

    /**
     *
     * @param downUrl FileDownloadUrl
     * @param baseUrl serviceHostUrl
     * @param downloadLocalPath  xxx/
     * @param downloadLocalFileName  xxx.apk
     * @param listener callback
     */
    public DownLoadApkHelper(String downUrl, String baseUrl, String downloadLocalPath, String downloadLocalFileName, OnDownloadApkListener listener){
        url = downUrl;
        onDownloadApkListener = listener;
        setHttp(baseUrl);
        setCallBack(downloadLocalPath, downloadLocalFileName);
    }

    private void setHttp(String baseUrl){
        Retrofit.Builder retrofitBuilder =
                // 获取一个实例
                new Retrofit.Builder()
                        // 使用RxJava作为回调适配器
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        // 添加Gson转换器
                        .addConverterFactory(GsonConverterFactory.create())
                        // 设置baseUrl{以"/"结尾}
                        .baseUrl(baseUrl);
        // Retrofit文件下载进度显示的解决方法
        OkHttpClient.Builder builder = ProgressHelper.addProgress(null);

        ProgressHelper.setProgressHandler(new DownloadProgressHandler() {

            @Override
            protected void onProgress(long bytesRead, long contentLength, boolean done) {
                onDownloadApkListener.process(bytesRead, contentLength);
            }
        });

        apiStore = retrofitBuilder
                .client(builder.build())
                .build().create(DownloadFile.class);
    }

    private void setCallBack(final String downloadLocalPath, final String downloadLocalFileName){
        call = apiStore.getFile(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    InputStream is = response.body().byteStream();
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//判断SD卡是否挂载
                        java.io.File foder = new java.io.File(Environment.getExternalStorageDirectory(), downloadLocalPath);
                        java.io.File file = new java.io.File(foder, downloadLocalFileName);
                        if (!foder.exists()) {
                            foder.mkdirs();
                        }
                        FileOutputStream fos = new FileOutputStream(file);
                        BufferedInputStream bis = new BufferedInputStream(is);
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = bis.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                            fos.flush();
                        }
                        fos.close();
                        bis.close();
                        is.close();

                        onDownloadApkListener.downloadSuccess();
                    }else {
                        onDownloadApkListener.sdCardError("请检查你的SD卡");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onDownloadApkListener.failure("更新失败");
            }
        });

    }



    public interface OnDownloadApkListener{
        void downloadSuccess();
        void failure(String info);
        void process(long currentLength, long maxLength);
        void sdCardError(String info);
    }

    public interface DownloadFile {
        /**
         * 下载文件
         */
        @GET
        Call<ResponseBody> getFile(@Url String url);
    }

}
