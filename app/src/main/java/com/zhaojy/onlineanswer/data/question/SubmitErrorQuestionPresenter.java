package com.zhaojy.onlineanswer.data.question;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.zhaojy.onlineanswer.bean.FinishedQuestion;
import com.zhaojy.onlineanswer.bean.Question;
import com.zhaojy.onlineanswer.bean.ResponseBody;
import com.zhaojy.onlineanswer.constant.Strings;
import com.zhaojy.onlineanswer.data.BaseUpdate;
import com.zhaojy.onlineanswer.data.DataManager;
import com.zhaojy.onlineanswer.data.Presenter;
import com.zhaojy.onlineanswer.data.Update;
import com.zhaojy.onlineanswer.utils.SharePreferUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @author: zhaojy
 * @data:On 2019/1/27.
 */
public class SubmitErrorQuestionPresenter implements Presenter {
    private final String TAG = SubmitErrorQuestionPresenter.class.getSimpleName();

    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private Update update;
    private String baseUrl;
    private ResponseBody responseBody;

    public SubmitErrorQuestionPresenter(Context mContext) {
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
     * 提交错题更新
     *
     * @param questionList 问题list
     */
    public void submitErrorQuestion(List<Question> questionList) {
        List<FinishedQuestion> finishedQuestionList = new ArrayList<>();
        String userPhone = SharePreferUtils.getString(mContext, Strings.USER_PHONE);
        for (Question question : questionList) {
            FinishedQuestion finishedQuestion = new FinishedQuestion();
            finishedQuestion.setUserPhone(userPhone);
            finishedQuestion.setMyoption(question.getMyOption());
            finishedQuestion.setQuestionId(question.getId());

            finishedQuestionList.add(finishedQuestion);
        }

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse(
                Strings.MEDIATYPE_JSON), new Gson().toJson(finishedQuestionList));

        mCompositeSubscription.add(manager.submitErrorQuestion(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        if (responseBody != null) {
                            update.onSuccess(responseBody);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            update.onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(ResponseBody body) {
                        responseBody = body;
                    }
                })
        );
    }

}
