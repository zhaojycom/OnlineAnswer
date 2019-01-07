package com.zhaojy.onlineanswer.mvp.contract;

/**
 * @author: zhaojy
 * @data:On 2018/12/21.
 */
public interface MainActivityContract {

    interface View {
        void hiddenBottomView();

        void showBottomView();

        void setBottomNavigation();

        void applyPermission();

    }

    interface Presenter {
        void initData();

        void process();

    }

    interface Model {

    }
}
