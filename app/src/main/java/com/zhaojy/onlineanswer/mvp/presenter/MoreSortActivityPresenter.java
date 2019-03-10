package com.zhaojy.onlineanswer.mvp.presenter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhaojy.onlineanswer.bean.QuestionSort;
import com.zhaojy.onlineanswer.bean.ResponseBody;
import com.zhaojy.onlineanswer.constant.Strings;
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
    public void getMoreSort(Context context) {
        model.getMoreSort(context);
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

    @Override
    public void deleteUserSort(BaseQuickAdapter adapter, QuestionSort sort, int pos, Context context) {
        model.deleteUserSort(adapter, sort, pos, context);
    }

    @Override
    public void addUserSort(BaseQuickAdapter adapter, QuestionSort sort, int pos, Context context) {
        model.addUserSort(adapter, sort, pos, context);
    }

    @Override
    public void deleteResult(BaseQuickAdapter adapter, QuestionSort sort, int pos, ResponseBody body) {
        if (body == null) {
            view.deleteFailure();
        } else if (body.getCode() == Strings.DELETE_SUCCESS) {
            //删除成功
            view.deleteSuccess(adapter, sort, pos);
        } else if (body.getCode() == Strings.DELETE_FAILURE) {
            //删除失败
            view.deleteFailure();
        }
    }

    @Override
    public void addResult(BaseQuickAdapter adapter, QuestionSort sort, int pos, ResponseBody body) {
        if (body == null) {
            view.addFailure();
        } else if (body.getCode() == Strings.ADD_SUCCESS) {
            //添加成功
            view.addSuccess(adapter, sort, pos);
        } else if (body.getCode() == Strings.ADD_FAILURE) {
            //添加失败
            view.addFailure();
        }
    }

}
