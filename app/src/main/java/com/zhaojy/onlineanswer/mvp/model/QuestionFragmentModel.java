package com.zhaojy.onlineanswer.mvp.model;

import com.zhaojy.onlineanswer.mvp.contract.QuestionFragmentContract;

/**
 * @author: zhaojy
 * @data:On 2018/12/28.
 */
public class QuestionFragmentModel implements QuestionFragmentContract.Model {
    private QuestionFragmentContract.Presenter presenter;

    public QuestionFragmentModel(QuestionFragmentContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
