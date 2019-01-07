package com.zhaojy.onlineanswer.mvp.presenter;

import android.content.Context;

import com.zhaojy.onlineanswer.bean.QuestionSort;
import com.zhaojy.onlineanswer.mvp.contract.MoreSortActivityContract;
import com.zhaojy.onlineanswer.mvp.model.MoreSortActivityModel;
import com.zhaojy.onlineanswer.utils.ChineseSortUtil;

import java.util.List;

/**
 * @author: zhaojy
 * @data:On 2018/12/21.
 */
public class MoreSortActivityPresenter implements MoreSortActivityContract.Presenter {
    private MoreSortActivityContract.View view;
    private MoreSortActivityContract.Model model;

    public MoreSortActivityPresenter(MoreSortActivityContract.View view) {
        this.view = view;
        this.initData();
    }

    @Override
    public void initData() {
        model = new MoreSortActivityModel(this);
    }

    @Override
    public void process() {

    }

    @Override
    public void getQuestionSortInfo(Context context) {
        model.getQuestionSortInfo(context);
    }

    @Override
    public void refreshSort(List<QuestionSort> questionSorts) {
        //对分类进行排序
        ChineseSortUtil.sort(questionSorts);
        view.refreshSort(questionSorts);
    }

    @Override
    public void clearPresenter() {
        model.clearPresenter();
    }

}
