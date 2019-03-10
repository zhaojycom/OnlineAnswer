package com.zhaojy.onlineanswer.mvp.model;

import android.content.Context;
import android.util.Log;

import com.zhaojy.onlineanswer.bean.QuestionSort;
import com.zhaojy.onlineanswer.bean.Slideshow;
import com.zhaojy.onlineanswer.constant.SiteInfo;
import com.zhaojy.onlineanswer.data.Update;
import com.zhaojy.onlineanswer.data.system.GetSlideshowPresenter;
import com.zhaojy.onlineanswer.data.user.GetUserQuestionSortPresenter;
import com.zhaojy.onlineanswer.mvp.contract.QuestionBankFragmentContract;

import java.util.List;

/**
 * @author: zhaojy
 * @data:On 2018/12/28.
 */
public class QuestionBankFragmentModel implements QuestionBankFragmentContract.Model {
    private final static String TAG = QuestionBankFragmentModel.class.getSimpleName();
    private QuestionBankFragmentContract.Presenter presenter;
    /**
     * 获取题目分类信息presenter
     */
    private GetUserQuestionSortPresenter userQuestionSortPresenter;

    /**
     * 获取轮播图presenter
     */
    private GetSlideshowPresenter slideshowPresenter;

    public QuestionBankFragmentModel(QuestionBankFragmentContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getUserQuestionSort(Context context) {
        if (userQuestionSortPresenter == null) {
            userQuestionSortPresenter = new GetUserQuestionSortPresenter(context);
            userQuestionSortPresenter.setBaseUrl(SiteInfo.HOST_URL + SiteInfo.USER);
        }

        userQuestionSortPresenter.attachUpdate(new Update() {
            @Override
            public void onSuccess(Object object) {
                List<QuestionSort> questionSorts = (List<QuestionSort>) object;
                presenter.updateQuestionSort(questionSorts);
            }

            @Override
            public void onError(String result) {

            }
        });
        userQuestionSortPresenter.onCreate();
        userQuestionSortPresenter.getUserSort();
    }

    @Override
    public void getSlideshow(Context context) {
        if (slideshowPresenter == null) {
            slideshowPresenter = new GetSlideshowPresenter(context);
            slideshowPresenter.setBaseUrl(SiteInfo.HOST_URL + SiteInfo.SYSTEM);
        }

        slideshowPresenter.attachUpdate(new Update() {
            @Override
            public void onSuccess(Object object) {
                List<Slideshow> bannerList = (List<Slideshow>) object;
                presenter.updateSlideshow(bannerList);
            }

            @Override
            public void onError(String result) {
                Log.e(TAG, result);
            }
        });
        slideshowPresenter.onCreate();
        slideshowPresenter.getSlideshow();
    }

}
