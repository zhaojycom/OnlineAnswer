package com.zhaojy.onlineanswer.mvp.contract;

import android.content.Context;

import com.zhaojy.onlineanswer.bean.Question;
import com.zhaojy.onlineanswer.bean.QuestionDifficult;
import com.zhaojy.onlineanswer.bean.ResponseBody;

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

        void getErrorQuestions();

        void getBaikeHeroQuestions();

        void updateQuestions(List<Question> questions);

        void showLoading();

        void hiddeLoading();

        void submitResult(ResponseBody responseBody);

        void updateDifficult(String[] items, List<QuestionDifficult> difficultList);

        void giveQuestionFailure();
    }

    interface Presenter {
        void initData();

        void process();

        void getQuestions(Context context, int questionSortId, int difficultId);

        void getErrorQuestions(Context context, int questionSortId);

        void getBaikeHeroQuestions(Context context, int difficultId);

        void updateQuestions(List<Question> questions);

        void submitQuestion(List<Question> questionList, Context context);

        void submitErrorQuetstion(List<Question> questionList, Context context);

        void submitResult(ResponseBody responseBody);

        void getQuestionDifficult(Context context);

        void updateDifficult(List<QuestionDifficult> difficultList);

        void giveQuestionFailure();
    }

    interface Model {
        void getQuestions(Context context, int questionSortId, int difficultId);

        void getErrorQuestions(Context context, int questionSortId);

        void getBaikeHeroQuestions(Context context, int difficultId);

        void submitQuestion(List<Question> questionList, Context context);

        void submitErrorQuetstion(List<Question> questionList, Context context);

        void getQuestionDifficult(Context context);
    }
}
