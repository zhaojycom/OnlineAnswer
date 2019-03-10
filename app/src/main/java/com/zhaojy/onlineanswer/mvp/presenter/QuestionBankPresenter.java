package com.zhaojy.onlineanswer.mvp.presenter;

import android.content.Context;

import com.zhaojy.onlineanswer.bean.QuestionSort;
import com.zhaojy.onlineanswer.bean.Slideshow;
import com.zhaojy.onlineanswer.mvp.contract.QuestionBankFragmentContract;
import com.zhaojy.onlineanswer.mvp.model.QuestionBankFragmentModel;

import java.util.List;

/**
 * @author: zhaojy
 * @data:On 2018/12/28.
 */
public class QuestionBankPresenter implements QuestionBankFragmentContract.Presenter {
    private QuestionBankFragmentContract.View view;
    private QuestionBankFragmentContract.Model model;

    public QuestionBankPresenter(QuestionBankFragmentContract.View view) {
        this.view = view;
        this.initData();
    }

    @Override
    public void initData() {
        model = new QuestionBankFragmentModel(this);
    }

    @Override
    public void process() {

    }

    @Override
    public void getUserQuestionSort(Context context) {
        model.getUserQuestionSort(context);
    }

    @Override
    public void updateQuestionSort(List<QuestionSort> sortList) {
        view.updateQuestionSort(sortList);
    }

    @Override
    public void getSlideshow(Context context) {
        model.getSlideshow(context);
    }

    @Override
    public void updateSlideshow(List<Slideshow> bannerList) {
        view.updateSlideshow(bannerList);
    }
}
