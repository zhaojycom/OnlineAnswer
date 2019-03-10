package com.zhaojy.onlineanswer.mvp.contract;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhaojy.onlineanswer.bean.QuestionSort;
import com.zhaojy.onlineanswer.bean.ResponseBody;

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

        void deleteSuccess(BaseQuickAdapter adapter, QuestionSort sort, int pos);

        void deleteFailure();

        void addSuccess(BaseQuickAdapter adapter, QuestionSort sort, int pos);

        void addFailure();

    }

    interface Presenter {
        void initData();

        void process();

        void getMoreSort(Context context);

        void refreshSort(List<QuestionSort> questionSorts);

        void clearPresenter();

        void deleteUserSort(BaseQuickAdapter adapter, QuestionSort sort, int pos, Context context);

        void addUserSort(BaseQuickAdapter adapter, QuestionSort sort, int pos, Context context);

        void deleteResult(BaseQuickAdapter adapter, QuestionSort sort, int pos, ResponseBody body);

        void addResult(BaseQuickAdapter adapter, QuestionSort sort, int pos, ResponseBody body);
    }

    interface Model {
        void getMoreSort(Context context);

        void clearPresenter();

        void deleteUserSort(BaseQuickAdapter adapter, QuestionSort sort, int pos, Context context);

        void addUserSort(BaseQuickAdapter adapter, QuestionSort sort, int pos, Context context);
    }
}
