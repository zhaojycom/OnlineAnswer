package com.zhaojy.onlineanswer.mvp.contract;

import android.content.Context;

/**
 * @author: zhaojy
 * @data:On 2018/12/21.
 */
public interface LoginActivityContract {
    interface View {
        void setMessageSender();

        void dimBg();

        void generateVerCode();

        void submitLoginRegisterInfo();

        void clear();

        void immediateAccess();

        void login();

        void loginSuccess();

        void loginFailure();
    }

    interface Presenter {
        void initData();

        void process();

        void submitLoginRegisterInfo(Context context, String phoneNumber);

        void loginSuccess();

        void loginFailure();

        void onDestory();
    }

    interface Model {
        void submitLoginRegisterInfo(Context context, String phoneNumber);

        void onDestory();
    }

    interface UpdateUserInfo{
        void setUserInfo();
    }
}
