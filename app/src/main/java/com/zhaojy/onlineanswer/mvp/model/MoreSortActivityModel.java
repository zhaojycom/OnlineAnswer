package com.zhaojy.onlineanswer.mvp.model;

import android.content.Context;

import com.zhaojy.onlineanswer.bean.QuestionSort;
import com.zhaojy.onlineanswer.bean.RequestParams;
import com.zhaojy.onlineanswer.constant.SiteInfo;
import com.zhaojy.onlineanswer.data.Update;
import com.zhaojy.onlineanswer.data.question.GetQuestionSortPresenter;
import com.zhaojy.onlineanswer.mvp.contract.MoreSortActivityContract;

import java.util.List;

/**
 * @author: zhaojy
 * @data:On 2018/12/21.
 */
public class MoreSortActivityModel implements MoreSortActivityContract.Model {
    private MoreSortActivityContract.Presenter presenter;

    /**
     * 获取题目分类信息presenter
     */
    private GetQuestionSortPresenter questionSortPresenter;

    public MoreSortActivityModel(MoreSortActivityContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getQuestionSortInfo(Context context) {
        if (questionSortPresenter == null) {
            questionSortPresenter = new GetQuestionSortPresenter(context);
        }
        questionSortPresenter.setBaseUrl(SiteInfo.HOST_URL + SiteInfo.QUESTION);
        questionSortPresenter.attachUpdate(new Update() {
            @Override
            public void onSuccess(Object object) {
                List<QuestionSort> questionSorts = (List<QuestionSort>) object;
                presenter.refreshSort(questionSorts);
            }

            @Override
            public void onError(String result) {
                presenter.refreshSort(null);
            }
        });
        questionSortPresenter.onCreate();
        RequestParams params = new RequestParams();
        params.setLimit(1000);
        params.setOffset(0);
        questionSortPresenter.getQuestionSort(params);
    }

    @Override
    public void clearPresenter() {
        if (questionSortPresenter != null) {
            questionSortPresenter.onStop();
        }
    }

}
