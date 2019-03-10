package com.zhaojy.onlineanswer.data.user;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.zhaojy.onlineanswer.bean.AddUserSortParams;
import com.zhaojy.onlineanswer.bean.ResponseBody;
import com.zhaojy.onlineanswer.bean.User;
import com.zhaojy.onlineanswer.constant.Strings;
import com.zhaojy.onlineanswer.data.BaseUpdate;
import com.zhaojy.onlineanswer.data.DataManager;
import com.zhaojy.onlineanswer.data.Presenter;
import com.zhaojy.onlineanswer.data.Update;

import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @author: zhaojy
 * @data:On 2019/2/3.
 */
public class DeleteUserSortPresenter implements Presenter {
    private final String TAG = DeleteUserSortPresenter.class.getSimpleName();

    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private Update update;
    private String baseUrl;
    private ResponseBody responseBody;
    /**
     * 请求是否结束
     */
    private boolean end = true;

    public DeleteUserSortPresenter(Context mContext) {
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
     * 删除用户的题目分类
     */
    public synchronized void deleteUserSort(int sortId) {
        if (!end) {
            return;
        }
        end = false;
        AddUserSortParams params = new AddUserSortParams();
        params.setUserPhone(User.getInstance().getPhone());
        params.setSortId(sortId);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse(
                Strings.MEDIATYPE_JSON), new Gson().toJson(params));
        mCompositeSubscription.add(manager.deleteUserSort(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        if (responseBody != null) {
                            update.onSuccess(responseBody);
                        }

                        end = true;
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            update.onError(e.getMessage());
                        }
                        end = true;
                    }

                    @Override
                    public void onNext(ResponseBody body) {
                        responseBody = body;
                    }
                })
        );
    }

}


