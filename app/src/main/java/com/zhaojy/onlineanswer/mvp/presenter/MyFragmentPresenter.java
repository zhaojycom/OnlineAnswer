package com.zhaojy.onlineanswer.mvp.presenter;

import android.content.Context;

import com.zhaojy.onlineanswer.mvp.contract.MyFragmentContract;
import com.zhaojy.onlineanswer.mvp.model.MyFragmentModel;

/**
 * @author: zhaojy
 * @data:On 2018/12/22.
 */
public class MyFragmentPresenter implements MyFragmentContract.Presenter {
    private MyFragmentContract.View view;
    private MyFragmentContract.Model model;

    public MyFragmentPresenter(MyFragmentContract.View view) {
        this.view = view;
        initData();
    }

    @Override
    public void initData() {
        model = new MyFragmentModel(this);
    }

    @Override
    public void process() {

    }

    @Override
    public void alterNickname(Context context) {
        model.alterNickname(context);
    }

    @Override
    public void clearPresenter() {
        model.clearPresenter();
    }

    @Override
    public void alterNickNameSuccess(String msg) {
        view.alterNickNameSuccess(msg);
    }

    @Override
    public void alterNickNameFailure() {
        view.alterNickNameFailure();
    }

    @Override
    public void login(Context context, String phoneNumber) {
        model.login(context, phoneNumber);
    }

    @Override
    public void loginSuccess() {
        view.loginSuccess();
    }

    @Override
    public void loginFailure() {
        view.loginFailure();
    }
}
