package com.zhaojy.onlineanswer.mvp.contract;

/**
 * @author: zhaojy
 * @data:On 2018/12/21.
 */
public interface QuestionFragmentContract {
    interface View {
        void initPage();

        void setOptionRecycler();

    }

    interface Presenter {
        void initData();

        void process();


    }

    interface Model {

    }
}
