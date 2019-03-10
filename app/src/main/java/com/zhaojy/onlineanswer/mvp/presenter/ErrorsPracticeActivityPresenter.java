package com.zhaojy.onlineanswer.mvp.presenter;

import android.content.Context;

import com.zhaojy.onlineanswer.bean.ErrorsSort;
import com.zhaojy.onlineanswer.mvp.contract.ErrorsPracticeActivityContract;
import com.zhaojy.onlineanswer.mvp.model.ErrorsPracticeActivityModel;
import com.zhaojy.onlineanswer.utils.ChineseSortUtil;

import java.util.List;

/**
 * @author: zhaojy
 * @data:On 2019/1/26.
 */
public class ErrorsPracticeActivityPresenter implements ErrorsPracticeActivityContract.Presenter {
    private ErrorsPracticeActivityContract.View view;
    private ErrorsPracticeActivityContract.Model model;

    public ErrorsPracticeActivityPresenter(ErrorsPracticeActivityContract.View view) {
        this.view = view;
        this.initData();
    }

    @Override
    public void initData() {
        model = new ErrorsPracticeActivityModel(this);
    }

    @Override
    public void process() {

    }

    @Override
    public void getMyErrorsSorts(Context context) {
        model.getMyErrorsSorts(context);
    }

    @Override
    public void updateMyErrorsSorts(List<ErrorsSort> errorsSortList) {
        //对分类进行排序
        ChineseSortUtil.sortErrorSorts(errorsSortList);
        view.updateMyErrorsSorts(errorsSortList);
    }
}
