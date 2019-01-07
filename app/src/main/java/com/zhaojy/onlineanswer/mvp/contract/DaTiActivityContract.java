package com.zhaojy.onlineanswer.mvp.contract;

import android.content.Context;

import com.zhaojy.onlineanswer.bean.Question;

import java.util.List;

/**
 * @author: zhaojy
 * @data:On 2018/12/21.
 */
public interface DaTiActivityContract {
    interface View {
        void back();

        void preQuestion();

        void nextQuestion();

        void submitAnswer();

        void scantron();

        void getIntentInfo();

        void setQuestionSortName();

        void setViewPager();

        void setChronometer();

        void showScantronPage();

        void showQuestionPage();

        void scantronUpdate();

        void skipToQuestionByPos(int pos);

        void getQuestions();

        void updateQuestions(List<Question> questions);

        void showLoading();

        void hiddeLoading();
    }

    interface Presenter {
        void initData();

        void process();

        void getQuestions(Context context, int questionSortId);

        void updateQuestions(List<Question> questions);
    }

    interface Model {
        void getQuestions(Context context, int questionSortId);
    }
}
