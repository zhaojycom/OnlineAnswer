package com.zhaojy.onlineanswer.data;

import android.content.Context;

import com.zhaojy.onlineanswer.bean.Question;
import com.zhaojy.onlineanswer.bean.QuestionSort;
import com.zhaojy.onlineanswer.bean.ResponseBody;
import com.zhaojy.onlineanswer.bean.User;

import java.util.List;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * @author: zhaojy
 * @data:On 2018/9/23.
 */

public class DataManager {
    private RetrofitService mRetrofitService;

    /**
     * @param context 上下文
     * @param baseUrl 请求基地址
     */
    public DataManager(Context context, String baseUrl) {
        RetrofitHelper retrofitHelper = new RetrofitHelper(context, baseUrl);
        this.mRetrofitService = retrofitHelper.getServer();
    }

    /**
     * 登录
     *
     * @param requestBody 请求体
     * @return
     */
    public Observable<User> login(RequestBody requestBody) {
        return mRetrofitService.login(requestBody);
    }

    /**
     * 修改昵称
     *
     * @param requestBody 请求体
     * @return
     */
    public Observable<ResponseBody> alterNickname(RequestBody requestBody) {
        return mRetrofitService.alterNickname(requestBody);
    }

    /**
     * 获取题目分类信息
     *
     * @param requestBody 请求体
     * @return
     */
    public Observable<List<QuestionSort>> getQuestionSort(RequestBody requestBody) {
        return mRetrofitService.getQuestionSort(requestBody);
    }

    /**
     * 获取题目
     *
     * @param requestBody 请求体
     * @return
     */
    public Observable<List<Question>> getQuestion(RequestBody requestBody) {
        return mRetrofitService.getQuestion(requestBody);
    }

    /**
     * 提交已完成的题目
     *
     * @param requestBody 请求体
     * @return
     */
    public Observable<ResponseBody> submitQuestion(RequestBody requestBody) {
        return mRetrofitService.submitQuestion(requestBody);
    }

}
