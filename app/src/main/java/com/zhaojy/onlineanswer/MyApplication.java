package com.zhaojy.onlineanswer;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.StrictMode;

import com.mob.MobSDK;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.zhaojy.onlineanswer.bean.MyObjectBox;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

/**
 * @author: zhaojy
 * @data:On 2018/9/29.
 */

public class MyApplication extends Application {
    private RefWatcher refWatcher;
    private static BoxStore mBoxStore;

    public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();

        return application.refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = null;
        builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        //短信验证码接口初始化
        MobSDK.init(this);

        //内存泄漏检测
        refWatcher = LeakCanary.install(this);
    }

    public BoxStore getBoxStore() {
       /* String inPath = getInnerSDCardPath() + "/db";
        File file = new File(inPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        mBoxStore = MyObjectBox.builder().androidContext(this).directory(new File(inPath)).build();*/
        mBoxStore = MyObjectBox.builder().androidContext(this).build();
        if (BuildConfig.DEBUG) {
            // 添加调试
            boolean start = new AndroidObjectBrowser(mBoxStore).start(this);
        }

        return mBoxStore;
    }

    /**
     * 获取内置SD卡路径
     *
     * @return
     */
    public String getInnerSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }
}
