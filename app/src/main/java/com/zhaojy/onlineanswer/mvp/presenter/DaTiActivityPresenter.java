package com.zhaojy.onlineanswer.mvp.presenter;

import android.content.Context;

import com.zhaojy.onlineanswer.bean.Question;
import com.zhaojy.onlineanswer.mvp.contract.DaTiActivityContract;
import com.zhaojy.onlineanswer.mvp.model.DaTiActivityModel;

import java.util.List;

/**
 * @author: zhaojy
 * @data:On 2018/12/21.
 */
public class DaTiActivityPresenter implements DaTiActivityContract.Presenter {
    private DaTiActivityContract.View view;
    private DaTiActivityContract.Model model;

    public DaTiActivityPresenter(DaTiActivityContract.View view) {
        this.view = view;
        initData();
    }

    @Override
    public void initData() {
        model = new DaTiActivityModel(this);
    }

    @Override
    public void process() {

    }

    @Override
    public void getQuestions(Context context, int questionSortId) {
        model.getQuestions(context, questionSortId);
    }

    @Override
    public void updateQuestions(List<Question> questions) {
        view.updateQuestions(questions);
    }
}
