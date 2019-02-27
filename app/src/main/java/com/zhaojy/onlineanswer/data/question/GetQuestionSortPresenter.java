package com.zhaojy.onlineanswer.data.question;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.zhaojy.onlineanswer.bean.QuestionSort;
import com.zhaojy.onlineanswer.bean.User;
import com.zhaojy.onlineanswer.constant.Strings;
import com.zhaojy.onlineanswer.data.BaseUpdate;
import com.zhaojy.onlineanswer.data.DataManager;
import com.zhaojy.onlineanswer.data.Presenter;
import com.zhaojy.onlineanswer.data.Update;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @author: zhaojy
 * @data:On 2018/10/18.
 */

public class GetQuestionSortPresenter implements Presenter {
    private final String TAG = GetQuestionSortPresenter.class.getSimpleName();

    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private Update update;
    private String baseUrl;
    private List<QuestionSort> questionSortList;

    public GetQuestionSortPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {
        manager = new DataManager(mContext, baseUrl);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void attachUpdate(BaseUpdate baseUpdate) {
        update = (Update) baseUpdate;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {
    }

    @Override
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * 获取更多分类
     */
    public void getMoreSort() {
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse(
                Strings.MEDIATYPE_JSON), new Gson().toJson(User.getInstance()));
        mCompositeSubscription.add(manager.getMoreSort(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<QuestionSort>>() {
                    @Override
                    public void onCompleted() {
                        if (questionSortList != null) {
                            update.onSuccess(questionSortList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            update.onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(List<QuestionSort> questionSorts) {
                        questionSortList = questionSorts;
                    }
                })
        );
    }

}
