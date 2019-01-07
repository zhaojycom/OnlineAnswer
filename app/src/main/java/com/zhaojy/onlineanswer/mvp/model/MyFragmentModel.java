package com.zhaojy.onlineanswer.mvp.model;

import android.content.Context;
import android.util.Log;

import com.zhaojy.onlineanswer.bean.ResponseBody;
import com.zhaojy.onlineanswer.bean.User;
import com.zhaojy.onlineanswer.constant.SiteInfo;
import com.zhaojy.onlineanswer.constant.Strings;
import com.zhaojy.onlineanswer.data.Update;
import com.zhaojy.onlineanswer.data.user.AlterNicknamePresenter;
import com.zhaojy.onlineanswer.data.user.LoginPresenter;
import com.zhaojy.onlineanswer.mvp.contract.MyFragmentContract;
import com.zhaojy.onlineanswer.utils.SharePreferUtils;

/**
 * @author: zhaojy
 * @data:On 2018/12/22.
 */
public class MyFragmentModel implements MyFragmentContract.Model {
    private static final String TAG = MyFragmentModel.class.getSimpleName();
    private MyFragmentContract.Presenter presenter;
    /**
     * 修改昵称presenter
     */
    private AlterNicknamePresenter alterNicknamePresenter;

    private LoginPresenter loginPresenter;

    public MyFragmentModel(MyFragmentContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void alterNickname(Context context) {
        if (alterNicknamePresenter == null) {
            alterNicknamePresenter = new AlterNicknamePresenter(context);
        } else {
            alterNicknamePresenter.onStop();
        }
        alterNicknamePresenter.setBaseUrl(SiteInfo.HOST_URL + SiteInfo.USER);
        alterNicknamePresenter.attachUpdate(new Update() {
            @Override
            public void onSuccess(Object object) {
                ResponseBody responseBody = (ResponseBody) object;
                String msg = responseBody.getMessage();
                presenter.alterNickNameSuccess(msg);
            }

            @Override
            public void onError(String result) {
                Log.e(TAG, result);
                presenter.alterNickNameFailure();
            }
        });
        alterNicknamePresenter.onCreate();
        alterNicknamePresenter.alterNickname();
    }

    @Override
    public void clearPresenter() {
        if (alterNicknamePresenter != null) {
            alterNicknamePresenter.onStop();
        }
        if (loginPresenter != null) {
            loginPresenter.onStop();
        }
    }

    @Override
    public void login(final Context context, final String phoneNumber) {
        if (loginPresenter == null) {
            loginPresenter = new LoginPresenter(context);
            loginPresenter.setBaseUrl(SiteInfo.HOST_URL + SiteInfo.USER);
        }
        loginPresenter.attachUpdate(new Update() {
            @Override
            public void onSuccess(Object object) {
                User temp = (User) object;
                User.copy(temp);

                SharePreferUtils.storeDataByKey(context,
                        Strings.USER_PHONE, phoneNumber);

                presenter.loginSuccess();
                User.getInstance().setUserChange(false);
            }

            @Override
            public void onError(String result) {
                presenter.loginFailure();
            }
        });
        loginPresenter.onCreate();
        loginPresenter.login(phoneNumber);
    }
}
