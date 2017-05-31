package com.cangjie.basetool.apk;


/**
 * 作者：${MXQ} on 2017/2/10 15:20
 * 邮箱：1299242483@qq.com
 */
public class ProgressBean {
    private long bytesRead;    // 已下载的总字节数
    private long contentLength;// 总长度
    private boolean done;      // 是否已下载完

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public long getBytesRead() {
        return bytesRead;
    }

    public void setBytesRead(long bytesRead) {
        this.bytesRead = bytesRead;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }


}
