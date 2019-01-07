package com.zhaojy.onlineanswer.mvp.contract;

import android.content.Context;

/**
 * @author: zhaojy
 * @data:On 2018/12/21.
 */
public interface MyFragmentContract {
    interface View {
        void avatar();

        void showAlterNickname();

        void hiddenAlterNickname();

        void alterNickname();

        void selectAvatar();

        void login();

        void loginSuccess();

        void loginFailure();

        void setPersonalInfoBox();

        void setRecyclerView();

        void alterNickNameSuccess(String msg);

        void alterNickNameFailure();

    }

    interface Presenter {
        void initData();

        void process();

        void alterNickname(Context context);

        void clearPresenter();

        void alterNickNameSuccess(String msg);

        void alterNickNameFailure();

        void login(Context context, String phoneNumber);

        void loginSuccess();

        void loginFailure();
    }

    interface Model {
        void alterNickname(Context context);

        void clearPresenter();

        void login(Context context, String phoneNumber);
    }
}
