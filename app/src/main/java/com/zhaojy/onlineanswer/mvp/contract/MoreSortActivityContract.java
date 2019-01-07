package com.zhaojy.onlineanswer.mvp.contract;

import android.content.Context;

import com.zhaojy.onlineanswer.bean.QuestionSort;

import java.util.List;

/**
 * @author: zhaojy
 * @data:On 2018/12/21.
 */
public interface MoreSortActivityContract {
    interface View {
        void setSwipeRefreshLayout();

        void getQuestionSortInfo();

        void setRecyclerView();

        void refreshSort(List<QuestionSort> questionSorts);

        void search();

    }

    interface Presenter {
        void initData();

        void process();

        void getQuestionSortInfo(Context context);

        void refreshSort(List<QuestionSort> questionSorts);

        void clearPresenter();

    }

    interface Model {
        void getQuestionSortInfo(Context context);

        void clearPresenter();
    }
}
