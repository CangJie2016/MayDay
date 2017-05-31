package com.cangjie.basetool.apk;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * 作者：${MXQ} on 2017/2/10 18:07
 * 邮箱：1299242483@qq.com
 */
public class InstallHelper {
    /**
     *
     * @param downloadLocalPath  xxx/
     * @param downloadLocalFileName  xxx.apk
     */
    public static void installApk(Activity activity, String downloadLocalPath, String downloadLocalFileName) {
        File foder = new File(Environment.getExternalStorageDirectory(), downloadLocalPath);
        File file = new File(foder, downloadLocalFileName);

        Intent intent = new Intent(Intent.ACTION_VIEW);

//判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(activity, "zxzs.ppgj.provider", file);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        activity.startActivity(intent);
        activity.finish();
    }
}