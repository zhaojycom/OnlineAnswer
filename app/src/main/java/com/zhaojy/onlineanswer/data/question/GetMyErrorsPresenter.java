package com.zhaojy.onlineanswer.data.question;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.zhaojy.onlineanswer.bean.ErrorsSort;
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
 * @data:On 2019/1/26.
 */
public class GetMyErrorsPresenter implements Presenter {
    private final String TAG = GetMyErrorsPresenter.class.getSimpleName();

    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private Update update;
    private String baseUrl;
    private List<ErrorsSort> errorsSortList;

    public GetMyErrorsPresenter(Context mContext) {
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
     * 获取错题分类信息
     */
    public void getMyErrorsSorts() {
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse(
                Strings.MEDIATYPE_JSON), new Gson().toJson(User.getInstance()));
        mCompositeSubscription.add(manager.getMyErrorsSorts(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ErrorsSort>>() {
                    @Override
                    public void onCompleted() {
                        if (errorsSortList != null) {
                            update.onSuccess(errorsSortList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            update.onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(List<ErrorsSort> errorsSorts) {
                        errorsSortList = errorsSorts;
                    }
                })
        );
    }

}
