package com.cangjie.basetool.apk;


/**
 * 作者：${MXQ} on 2017/2/10 14:54
 * 邮箱：1299242483@qq.com
 * 描述： 监听文件下载的进度{运行在子线程}
 * 玩
 */
public interface ProgressListener {
    /**
     *
     * @param progress 已经下载后上传字节数
     * @param total 总字节数
     * @param done  是否完成
     */
    void onProgress(long progress, long total, boolean done);
}
