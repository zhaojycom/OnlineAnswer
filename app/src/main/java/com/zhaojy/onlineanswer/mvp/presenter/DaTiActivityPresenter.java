package com.zhaojy.onlineanswer.mvp.presenter;

import android.content.Context;

import com.zhaojy.onlineanswer.bean.Question;
import com.zhaojy.onlineanswer.bean.QuestionDifficult;
import com.zhaojy.onlineanswer.bean.ResponseBody;
import com.zhaojy.onlineanswer.constant.Strings;
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
    public void getQuestions(Context context, int questionSortId, int difficultId) {
        model.getQuestions(context, questionSortId, difficultId);
    }

    @Override
    public void getErrorQuestions(Context context, int questionSortId) {
        model.getErrorQuestions(context, questionSortId);
    }

    @Override
    public void getBaikeHeroQuestions(Context context, int difficultId) {
        model.getBaikeHeroQuestions(context, difficultId);
    }

    @Override
    public void updateQuestions(List<Question> questions) {
        view.updateQuestions(questions);
    }

    @Override
    public void submitQuestion(List<Question> questionList, Context context) {
        model.submitQuestion(questionList, context);
    }

    @Override
    public void submitErrorQuetstion(List<Question> questionList, Context context) {
        model.submitErrorQuetstion(questionList, context);
    }

    @Override
    public void submitResult(ResponseBody responseBody) {
        view.submitResult(responseBody);
    }

    @Override
    public void getQuestionDifficult(Context context) {
        model.getQuestionDifficult(context);
    }

    @Override
    public void updateDifficult(List<QuestionDifficult> difficultList) {
        QuestionDifficult tail = new QuestionDifficult();
        tail.setName(Strings.RAND);
        difficultList.add(tail);
        String[] items = new String[difficultList.size()];
        for (int i = 0; i < difficultList.size(); i++) {
            QuestionDifficult difficult = difficultList.get(i);
            items[i] = difficult.getName();
        }

        view.updateDifficult(items, difficultList);
    }

    @Override
    public void giveQuestionFailure() {
        view.giveQuestionFailure();
    }

}
