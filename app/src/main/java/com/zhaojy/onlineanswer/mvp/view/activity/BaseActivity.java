package com.zhaojy.onlineanswer.mvp.view.activity;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.zhaojy.onlineanswer.ActivityManager;

import butterknife.ButterKnife;

/**
 * @author: zhaojy
 * @data:On 2018/10/14.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public final String TAG = this.getClass().getSimpleName();

    protected Context mContext;

    /**
     * 设置状态栏透明
     */
    public void setStatusBarTranparent() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getPageLayoutID());

        ActivityManager.addActivity(this);
        ButterKnife.bind(this);
        initData();
        initView();
        initViewListener();
        process();
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void initViewListener();

    protected abstract void process();

    protected abstract int getPageLayoutID();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }
}
