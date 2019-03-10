package com.zhaojy.onlineanswer.mvp.model;

import android.content.Context;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhaojy.onlineanswer.bean.QuestionSort;
import com.zhaojy.onlineanswer.bean.ResponseBody;
import com.zhaojy.onlineanswer.constant.SiteInfo;
import com.zhaojy.onlineanswer.data.Update;
import com.zhaojy.onlineanswer.data.question.GetQuestionSortPresenter;
import com.zhaojy.onlineanswer.data.user.AddUserSortPresenter;
import com.zhaojy.onlineanswer.data.user.DeleteUserSortPresenter;
import com.zhaojy.onlineanswer.mvp.contract.MoreSortActivityContract;

import java.util.List;

/**
 * @author: zhaojy
 * @data:On 2018/12/21.
 */
public class MoreSortActivityModel implements MoreSortActivityContract.Model {
    private final static String TAG = MoreSortActivityModel.class.getSimpleName();
    private MoreSortActivityContract.Presenter presenter;

    /**
     * 获取题目分类信息presenter
     */
    private GetQuestionSortPresenter questionSortPresenter;
    /**
     * 添加用户分类presenter
     */
    private AddUserSortPresenter addUserSortPresenter;
    /**
     * 删除用户分类presenter
     */
    private DeleteUserSortPresenter deleteUserSortPresenter;

    public MoreSortActivityModel(MoreSortActivityContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getMoreSort(Context context) {
        if (questionSortPresenter == null) {
            questionSortPresenter = new GetQuestionSortPresenter(context);
        }
        questionSortPresenter.setBaseUrl(SiteInfo.HOST_URL + SiteInfo.USER);
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
        questionSortPresenter.getMoreSort();
    }

    @Override
    public void clearPresenter() {
        if (questionSortPresenter != null) {
            questionSortPresenter.onStop();
        }
        if (addUserSortPresenter != null) {
            addUserSortPresenter.onStop();
        }
        if (deleteUserSortPresenter != null) {
            deleteUserSortPresenter.onStop();
        }
    }

    @Override
    public synchronized void deleteUserSort(final BaseQuickAdapter adapter, final QuestionSort sort, final int pos, Context context) {
        if (deleteUserSortPresenter == null) {
            deleteUserSortPresenter = new DeleteUserSortPresenter(context);
        }
        deleteUserSortPresenter.setBaseUrl(SiteInfo.HOST_URL + SiteInfo.USER);
        deleteUserSortPresenter.attachUpdate(new Update() {
            @Override
            public void onSuccess(Object object) {
                ResponseBody responseBody = (ResponseBody) object;
                presenter.deleteResult(adapter, sort, pos, responseBody);
            }

            @Override
            public void onError(String result) {
                Log.e(TAG, result);
                presenter.deleteResult(adapter, sort, pos, null);
            }
        });
        deleteUserSortPresenter.onCreate();
        deleteUserSortPresenter.deleteUserSort(sort.getId());
    }

    @Override
    public synchronized void addUserSort(final BaseQuickAdapter adapter, final QuestionSort sort, final int pos, Context context) {
        if (addUserSortPresenter == null) {
            addUserSortPresenter = new AddUserSortPresenter(context);
        }
        addUserSortPresenter.setBaseUrl(SiteInfo.HOST_URL + SiteInfo.USER);
        addUserSortPresenter.attachUpdate(new Update() {
            @Override
            public void onSuccess(Object object) {
                ResponseBody responseBody = (ResponseBody) object;
                presenter.addResult(adapter, sort, pos, responseBody);
            }

            @Override
            public void onError(String result) {
                Log.e(TAG, result);
                presenter.addResult(adapter, sort, pos, null);
            }
        });
        addUserSortPresenter.onCreate();
        addUserSortPresenter.addUserSort(sort.getId());
    }

}
