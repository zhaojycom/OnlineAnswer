package com.zhaojy.onlineanswer.mvp.contract;

import android.content.Context;

import com.zhaojy.onlineanswer.bean.ErrorsSort;

import java.util.List;

/**
 * @author: zhaojy
 * @data:On 2019/1/26.
 */
public interface ErrorsPracticeActivityContract {
    interface View {
        void setSwipeRefreshLayout();

        void setRecyclerView();

        void updateMyErrorsSorts(List<ErrorsSort> errorsSortList);
    }

    interface Presenter {
        void initData();

        void process();

        void getMyErrorsSorts(Context context);

        void updateMyErrorsSorts(List<ErrorsSort> errorsSortList);
    }

    interface Model {
        void getMyErrorsSorts(Context context);
    }
}
