package com.zhaojy.onlineanswer.mvp.presenter;

import android.content.Context;

import com.zhaojy.onlineanswer.mvp.contract.LoginActivityContract;
import com.zhaojy.onlineanswer.mvp.model.LoginActivityModel;

/**
 * @author: zhaojy
 * @data:On 2019/1/7.
 */
public class LoginActivityPresenter implements LoginActivityContract.Presenter {
    private LoginActivityContract.View view;
    private LoginActivityContract.Model model;

    public LoginActivityPresenter(LoginActivityContract.View view) {
        this.view = view;
        this.initData();
    }

    @Override
    public void initData() {
        model = new LoginActivityModel(this);
    }

    @Override
    public void process() {

    }

    @Override
    public void submitLoginRegisterInfo(Context context, String phoneNumber) {
        model.submitLoginRegisterInfo(context, phoneNumber);
    }

    @Override
    public void loginSuccess() {
        view.loginSuccess();
    }

    @Override
    public void loginFailure() {
        view.loginFailure();
    }

    @Override
    public void onDestory() {
        model.onDestory();
    }

}
