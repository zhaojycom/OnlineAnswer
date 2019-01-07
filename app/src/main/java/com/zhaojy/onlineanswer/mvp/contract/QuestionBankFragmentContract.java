package com.zhaojy.onlineanswer.mvp.contract;

import android.content.Context;

import com.zhaojy.onlineanswer.bean.QuestionSort;

import java.util.List;

/**
 * @author: zhaojy
 * @data:On 2018/12/21.
 */
public interface QuestionBankFragmentContract {
    interface View {
        void updateQuestionSort(List<QuestionSort> sortList);

    }

    interface Presenter {
        void initData();

        void process();

        void getQuestionSort(Context context);

        void updateQuestionSort(List<QuestionSort> sortList);

    }

    interface Model {
        void getQuestionSort(Context context);
    }
}
