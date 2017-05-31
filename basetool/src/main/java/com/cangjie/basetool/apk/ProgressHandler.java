package com.cangjie.basetool.apk;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * 作者：${MXQ} on 2017/2/10 15:51
 * 邮箱：1299242483@qq.com
 * 描述：把从ProgressHelper的子类，获取的ProgressBean对象传递至Activity
 */
public abstract class ProgressHandler {

    /*发送消息*/
    protected abstract void sendMessage(ProgressBean progressBean);
    /*处理消息*/
    protected abstract void handleMessage(Message message);

    protected abstract void onProgress(long progress, long total, boolean done);

    protected static class ResponseHandler extends Handler {

        private ProgressHandler mProgressHandler;
        public ResponseHandler(ProgressHandler mProgressHandler, Looper looper) {
            super(looper);
            this.mProgressHandler = mProgressHandler;
        }

        @Override
        public void handleMessage(Message msg) {
            mProgressHandler.handleMessage(msg);
        }
    }




}