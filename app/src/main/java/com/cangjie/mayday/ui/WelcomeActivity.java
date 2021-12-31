package com.cangjie.mayday.ui;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import com.cangjie.basetool.apk.DownLoadApkHelper;
import com.cangjie.basetool.apk.InstallHelper;
import com.cangjie.basetool.utils.DebugLog;
import com.cangjie.data.http.HttpMethods;
import com.cangjie.data.http.bean.VersionBean;
import com.cangjie.mayday.R;
import com.cangjie.mayday.presenter.WelcomePresenter;
import com.cangjie.mayday.presenter.view.WelcomeView;
import com.cangjie.basetool.mvp.base.PresenterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

public class WelcomeActivity extends PresenterActivity<WelcomePresenter> implements WelcomeView {

    @BindView(R.id.img_welcome)
    ImageView imgWelcome;

    @Override
    protected WelcomePresenter createPresenter() {
        return new WelcomePresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        ButterKnife.bind(this);
        initView(imgWelcome);
    }

    public void initView(ImageView view) {
        // 初始化控件
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.4F, 0.9F);
        animationSet.setDuration(400);
        animationSet.addAnimation(alphaAnimation);
        // 监听动画过程
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                checkVersion();
            }
        });
        view.startAnimation(animationSet);
    }

    private void checkVersion() {

        HttpMethods.getInstance().checkVersion(new Subscriber<VersionBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(VersionBean versionBean) {
                DebugLog.w(versionBean.toString());
                downloadApk(versionBean.url);
            }
        }, null);

    }

    private void downloadApk(String url) {
        final String path = "zxw/";
        final String fileName = "test.apk";
        new DownLoadApkHelper(url, HttpMethods.BASE_URL, path, fileName, new DownLoadApkHelper.OnDownloadApkListener() {
            @Override
            public void downloadSuccess() {
                DebugLog.w("success");
                InstallHelper.installApk(WelcomeActivity.this, path, fileName);
            }

            @Override
            public void failure(String info) {
                DebugLog.w(info);
            }

            @Override
            public void process(long currentLength, long maxLength) {
                DebugLog.w("currentLength" + currentLength);
            }

            @Override
            public void sdCardError(String info) {
                DebugLog.w(info);
            }
        });
    }

}
