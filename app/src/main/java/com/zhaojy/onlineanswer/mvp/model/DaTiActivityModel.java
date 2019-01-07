package com.zhaojy.onlineanswer.mvp.model;

import android.content.Context;

import com.zhaojy.onlineanswer.bean.Question;
import com.zhaojy.onlineanswer.constant.SiteInfo;
import com.zhaojy.onlineanswer.data.Update;
import com.zhaojy.onlineanswer.data.question.GetQuestionPresenter;
import com.zhaojy.onlineanswer.mvp.contract.DaTiActivityContract;

import java.util.List;

/**
 * @author: zhaojy
 * @data:On 2018/12/21.
 */
public class DaTiActivityModel implements DaTiActivityContract.Model {
    private DaTiActivityContract.Presenter presenter;
    /**
     * 获取题目presenter
     */
    private GetQuestionPresenter getQuestionPresenter;

    public DaTiActivityModel(DaTiActivityContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getQuestions(Context context, int questionSortId) {
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

            }
        });
        getQuestionPresenter.onCreate();
        getQuestionPresenter.getQuestion(questionSortId);
    }

}
