package com.zhaojy.onlineanswer.mvp.contract;

import com.zhaojy.onlineanswer.bean.Question;

import java.util.List;

/**
 * @author: zhaojy
 * @data:On 2018/12/21.
 */
public interface ScantronFragmentContract {
    interface View {
        void setQuestionList(List<Question> questionList);

        void initPage();

        void setScantron();

        void scantronUpdate();

    }

    interface Presenter {
        void initData();

        void process();


    }

    interface Model {

    }
}
