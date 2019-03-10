package com.zhaojy.onlineanswer.mvp.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.zhaojy.onlineanswer.bean.User;
import com.zhaojy.onlineanswer.constant.SiteInfo;
import com.zhaojy.onlineanswer.constant.Strings;
import com.zhaojy.onlineanswer.data.Update;
import com.zhaojy.onlineanswer.data.user.LoginPresenter;
import com.zhaojy.onlineanswer.mvp.contract.LoginActivityContract;
import com.zhaojy.onlineanswer.utils.SharePreferUtils;

/**
 * @author: zhaojy
 * @data:On 2019/1/7.
 */
public class LoginActivityModel implements LoginActivityContract.Model {
    private final static String TAG = LoginActivityModel.class.getSimpleName();
    private LoginActivityContract.Presenter presenter;
    private LoginPresenter loginPresenter;

    public LoginActivityModel(LoginActivityContract.Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void submitLoginRegisterInfo(final Context context, final String phoneNumber) {
        if (loginPresenter == null) {
            loginPresenter = new LoginPresenter(context);
        }
        loginPresenter.setBaseUrl(SiteInfo.HOST_URL + SiteInfo.USER);
        loginPresenter.attachUpdate(new Update() {
            @Override
            public void onSuccess(Object object) {
                //登录成功
                User temp = (User) object;
                temp.setUserChange(true);
                User.copy(temp);

                SharePreferUtils.storeDataByKey(context,
                        Strings.USER_PHONE, phoneNumber);

                Toast.makeText(context, Strings.LOGIN_SUCCESS
                        , Toast.LENGTH_SHORT).show();

                presenter.loginSuccess();
            }

            @Override
            public void onError(String result) {
                Log.e(TAG, result);
                Toast.makeText(context, Strings.LOGIN_FAILURE
                        , Toast.LENGTH_SHORT).show();

                SharePreferUtils.storeDataByKey(context,
                        Strings.USER_PHONE, null);

                User.reset();

                presenter.loginFailure();
            }
        });
        loginPresenter.onCreate();
        loginPresenter.login(phoneNumber);
    }

    @Override
    public void onDestory() {
        if (loginPresenter != null) {
            loginPresenter.onStop();
        }
    }

}
