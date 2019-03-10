package com.zhaojy.onlineanswer.mvp.model;

import android.content.Context;
import android.util.Log;

import com.zhaojy.onlineanswer.bean.Question;
import com.zhaojy.onlineanswer.bean.QuestionDifficult;
import com.zhaojy.onlineanswer.bean.ResponseBody;
import com.zhaojy.onlineanswer.constant.SiteInfo;
import com.zhaojy.onlineanswer.data.Update;
import com.zhaojy.onlineanswer.data.question.BaikeHeroQuestionsPresenter;
import com.zhaojy.onlineanswer.data.question.GetErrorQuestionPresenter;
import com.zhaojy.onlineanswer.data.question.GetQuestionDifficultPresenter;
import com.zhaojy.onlineanswer.data.question.GetQuestionPresenter;
import com.zhaojy.onlineanswer.data.question.SubmitErrorQuestionPresenter;
import com.zhaojy.onlineanswer.data.question.SubmitQuestionsPresenter;
import com.zhaojy.onlineanswer.mvp.contract.DaTiActivityContract;

import java.util.List;

/**
 * @author: zhaojy
 * @data:On 2018/12/21.
 */
public class DaTiActivityModel implements DaTiActivityContract.Model {
    private final static String TAG = DaTiActivityModel.class.getSimpleName();
    private DaTiActivityContract.Presenter presenter;
    /**
     * 获取题目presenter
     */
    private GetQuestionPresenter getQuestionPresenter;
    /**
     * 获取错题presenter
     */
    private GetErrorQuestionPresenter getErrorQuestionPresenter;
    /**
     * 获取百科英雄题目presenter
     */
    private BaikeHeroQuestionsPresenter baikeHeroQuestionsPresenter;
    /**
     * 提交已完成题目presenter
     */
    private SubmitQuestionsPresenter submitQuestionsPresenter;
    /**
     * 提交错题更新presenter
     */
    private SubmitErrorQuestionPresenter submitErrorQuestionPresenter;

    /**
     * 获取题目难度presenter
     */
    private GetQuestionDifficultPresenter questionDifficultPresenter;


    public DaTiActivityModel(DaTiActivityContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getQuestions(Context context, int questionSortId, int difficultId) {
        if (getQuestionPresenter == null) {
            getQuestionPresenter = new GetQuestionPresenter(context);
        }
        getQuestionPresenter.setBaseUrl(SiteInfo.HOST_URL + SiteInfo.QUESTION);
        getQuestionPresenter.attachUpdate(new Update() {
            @Override
            public void onSuccess(Object object) {
                List<Question> questions = (List<Question>) object;
                presenter.updateQuestions(questions);

            }

            @Override
            public void onError(String result) {
                Log.e(TAG, result);
                presenter.giveQuestionFailure();
            }
        });
        getQuestionPresenter.onCreate();
        getQuestionPresenter.getQuestion(questionSortId, difficultId);
    }

    @Override
    public void getErrorQuestions(Context context, int questionSortId) {
        if (getErrorQuestionPresenter == null) {
            getErrorQuestionPresenter = new GetErrorQuestionPresenter(context);
        }
        getErrorQuestionPresenter.setBaseUrl(SiteInfo.HOST_URL + SiteInfo.QUESTION);
        getErrorQuestionPresenter.attachUpdate(new Update() {
            @Override
            public void onSuccess(Object object) {
                List<Question> questions = (List<Question>) object;
                presenter.updateQuestions(questions);

            }

            @Override
            public void onError(String result) {
                Log.e(TAG, result);
                presenter.giveQuestionFailure();
            }
        });
        getErrorQuestionPresenter.onCreate();
        getErrorQuestionPresenter.getErrorQuestions(questionSortId);
    }

    @Override
    public void getBaikeHeroQuestions(Context context, int difficultId) {
        if (baikeHeroQuestionsPresenter == null) {
            baikeHeroQuestionsPresenter = new BaikeHeroQuestionsPresenter(context);
        }
        baikeHeroQuestionsPresenter.setBaseUrl(SiteInfo.HOST_URL + SiteInfo.QUESTION);
        baikeHeroQuestionsPresenter.attachUpdate(new Update() {
            @Override
            public void onSuccess(Object object) {
                List<Question> questions = (List<Question>) object;
                presenter.updateQuestions(questions);
            }

            @Override
            public void onError(String result) {
                Log.e(TAG, result);
                presenter.giveQuestionFailure();
            }
        });
        baikeHeroQuestionsPresenter.onCreate();
        baikeHeroQuestionsPresenter.getBaikeHeroQuestions(difficultId);
    }

    @Override
    public void submitQuestion(List<Question> questionList, Context context) {
        //向后台提交已完成的题目
        if (submitQuestionsPresenter == null) {
            submitQuestionsPresenter = new SubmitQuestionsPresenter(context);
            submitQuestionsPresenter.setBaseUrl(SiteInfo.HOST_URL + SiteInfo.QUESTION);
        }

        submitQuestionsPresenter.attachUpdate(new Update() {
            @Override
            public void onSuccess(Object object) {
                ResponseBody responseBody = (ResponseBody) object;
                presenter.submitResult(responseBody);
            }

            @Override
            public void onError(String result) {
                Log.e(TAG, result);
                presenter.submitResult(null);
            }
        });
        submitQuestionsPresenter.onCreate();
        submitQuestionsPresenter.submitQuestion(questionList);
    }

    @Override
    public void submitErrorQuetstion(List<Question> questionList, Context context) {
        //向后台提交已完成的题目
        if (submitErrorQuestionPresenter == null) {
            submitErrorQuestionPresenter = new SubmitErrorQuestionPresenter(context);
            submitErrorQuestionPresenter.setBaseUrl(SiteInfo.HOST_URL + SiteInfo.QUESTION);
        }

        submitErrorQuestionPresenter.attachUpdate(new Update() {
            @Override
            public void onSuccess(Object object) {
                ResponseBody responseBody = (ResponseBody) object;
                presenter.submitResult(responseBody);
            }

            @Override
            public void onError(String result) {
                Log.e(TAG, result);
                presenter.submitResult(null);
            }
        });
        submitErrorQuestionPresenter.onCreate();
        submitErrorQuestionPresenter.submitErrorQuestion(questionList);
    }

    @Override
    public void getQuestionDifficult(Context context) {
        if (questionDifficultPresenter == null) {
            questionDifficultPresenter = new GetQuestionDifficultPresenter(context);
            questionDifficultPresenter.setBaseUrl(SiteInfo.HOST_URL + SiteInfo.QUESTION);
        }

        questionDifficultPresenter.attachUpdate(new Update() {
            @Override
            public void onSuccess(Object object) {
                List<QuestionDifficult> difficultList = (List<QuestionDifficult>) object;
                presenter.updateDifficult(difficultList);
            }

            @Override
            public void onError(String result) {
                Log.e(TAG, result);
            }
        });
        questionDifficultPresenter.onCreate();
        questionDifficultPresenter.getQuestionDifficult();
    }

}
