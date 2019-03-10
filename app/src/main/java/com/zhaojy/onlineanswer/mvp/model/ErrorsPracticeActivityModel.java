package com.zhaojy.onlineanswer.mvp.model;

import android.content.Context;

import com.zhaojy.onlineanswer.bean.ErrorsSort;
import com.zhaojy.onlineanswer.constant.SiteInfo;
import com.zhaojy.onlineanswer.data.Update;
import com.zhaojy.onlineanswer.data.question.GetMyErrorsPresenter;
import com.zhaojy.onlineanswer.mvp.contract.ErrorsPracticeActivityContract;

import java.util.List;

/**
 * @author: zhaojy
 * @data:On 2019/1/26.
 */
public class ErrorsPracticeActivityModel implements ErrorsPracticeActivityContract.Model {
    private ErrorsPracticeActivityContract.Presenter presenter;
    /**
     * 获取错题分类信息presenter
     */
    private GetMyErrorsPresenter myErrorsPresenter;

    public ErrorsPracticeActivityModel(ErrorsPracticeActivityContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getMyErrorsSorts(Context context) {
        if (myErrorsPresenter == null) {
            myErrorsPresenter = new GetMyErrorsPresenter(context);
        }
        myErrorsPresenter.setBaseUrl(SiteInfo.HOST_URL + SiteInfo.QUESTION);
        myErrorsPresenter.attachUpdate(new Update() {
            @Override
            public void onSuccess(Object object) {
                List<ErrorsSort> errorsSorts = (List<ErrorsSort>) object;
                presenter.updateMyErrorsSorts(errorsSorts);
            }

            @Override
            public void onError(String result) {
                presenter.updateMyErrorsSorts(null);
            }
        });
        myErrorsPresenter.onCreate();
        myErrorsPresenter.getMyErrorsSorts();
    }
}
