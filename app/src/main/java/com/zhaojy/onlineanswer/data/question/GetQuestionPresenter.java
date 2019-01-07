package com.zhaojy.onlineanswer.data.question;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.zhaojy.onlineanswer.bean.Question;
import com.zhaojy.onlineanswer.bean.QuestionRequestParams;
import com.zhaojy.onlineanswer.constant.Strings;
import com.zhaojy.onlineanswer.data.BaseUpdate;
import com.zhaojy.onlineanswer.data.DataManager;
import com.zhaojy.onlineanswer.data.Presenter;
import com.zhaojy.onlineanswer.data.Update;
import com.zhaojy.onlineanswer.utils.SharePreferUtils;

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

public class GetQuestionPresenter implements Presenter {
    private final String TAG = GetQuestionPresenter.class.getSimpleName();

    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private Update update;
    private String baseUrl;
    private List<Question> questionList;

    public GetQuestionPresenter(Context mContext) {
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
     * 获取题目
     *
     * @param sortId 题目分类id
     */
    public void getQuestion(int sortId) {
        QuestionRequestParams params = new QuestionRequestParams();
        params.setSortId(sortId);
        params.setLimit(Strings.MAX_READ_QUESTION_SUM);
        params.setOffset(0);
        String userPhone = SharePreferUtils.getString(mContext, Strings.USER_PHONE);
        params.setUserPhone(userPhone);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse(
                Strings.MEDIATYPE_JSON), new Gson().toJson(params));
        mCompositeSubscription.add(manager.getQuestion(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Question>>() {
                    @Override
                    public void onCompleted() {
                        if (questionList != null) {
                            update.onSuccess(questionList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            update.onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(List<Question> questions) {
                        questionList = questions;
                    }
                })
        );
    }

}
