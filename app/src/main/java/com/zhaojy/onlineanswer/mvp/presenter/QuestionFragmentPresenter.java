package com.zhaojy.onlineanswer.mvp.presenter;

import com.zhaojy.onlineanswer.mvp.contract.QuestionFragmentContract;
import com.zhaojy.onlineanswer.mvp.model.QuestionFragmentModel;

/**
 * @author: zhaojy
 * @data:On 2018/12/28.
 */
public class QuestionFragmentPresenter implements QuestionFragmentContract.Presenter {
    private QuestionFragmentContract.View view;
    private QuestionFragmentContract.Model model;

    public QuestionFragmentPresenter(QuestionFragmentContract.View view) {
        this.view = view;
        this.initData();
    }

    @Override
    public void initData() {
        model = new QuestionFragmentModel(this);
    }

    @Override
    public void process() {

    }
}
