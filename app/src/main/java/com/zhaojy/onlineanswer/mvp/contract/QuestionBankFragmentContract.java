package com.zhaojy.onlineanswer.mvp.contract;

import android.content.Context;

import com.zhaojy.onlineanswer.bean.QuestionSort;
import com.zhaojy.onlineanswer.bean.Slideshow;

import java.util.List;

/**
 * @author: zhaojy
 * @data:On 2018/12/21.
 */
public interface QuestionBankFragmentContract {
    interface View {
        void updateQuestionSort(List<QuestionSort> sortList);

        void updateSlideshow(List<Slideshow> bannerList);

void setFunDaTi();

    }

    interface Presenter {
        void initData();

        void process();

        void getUserQuestionSort(Context context);

        void updateQuestionSort(List<QuestionSort> sortList);

        void getSlideshow(Context context);

        void updateSlideshow(List<Slideshow> bannerList);

    }

    interface Model {
        void getUserQuestionSort(Context context);

        void getSlideshow(Context context);

    }
}
