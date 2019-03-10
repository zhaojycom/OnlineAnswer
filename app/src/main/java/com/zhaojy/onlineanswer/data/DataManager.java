package com.zhaojy.onlineanswer.data;

import android.content.Context;

import com.zhaojy.onlineanswer.bean.ErrorsSort;
import com.zhaojy.onlineanswer.bean.Question;
import com.zhaojy.onlineanswer.bean.QuestionDifficult;
import com.zhaojy.onlineanswer.bean.QuestionSort;
import com.zhaojy.onlineanswer.bean.ResponseBody;
import com.zhaojy.onlineanswer.bean.Slideshow;
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
    public Observable<List<QuestionSort>> getMoreSort(RequestBody requestBody) {
        return mRetrofitService.getMoreSort(requestBody);
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

    /**
     * 提交已完成的题目
     *
     * @return
     */
    public Observable<List<Slideshow>> getSlideshow() {
        return mRetrofitService.getSlideshow();
    }

    /**
     * 获取错题分类信息
     *
     * @return
     */
    public Observable<List<ErrorsSort>> getMyErrorsSorts(RequestBody requestBody) {
        return mRetrofitService.getMyErrorsSorts(requestBody);
    }

    /**
     * 获取错题
     *
     * @param requestBody 请求体
     * @return
     */
    public Observable<List<Question>> getErrorQuestions(RequestBody requestBody) {
        return mRetrofitService.getErrorQuestions(requestBody);
    }

    /**
     * 获取错题
     *
     * @param requestBody 请求体
     * @return
     */
    public Observable<ResponseBody> submitErrorQuestion(RequestBody requestBody) {
        return mRetrofitService.submitErrorQuestion(requestBody);
    }

    /**
     * 获取题目难度
     *
     * @return
     */
    public Observable<List<QuestionDifficult>> getQuestionDifficult() {
        return mRetrofitService.getQuestionDifficult();
    }

    /**
     * 获取当前用户的题目分类
     *
     * @return
     */
    public Observable<List<QuestionSort>> getUserSort(RequestBody requestBody) {
        return mRetrofitService.getUserSort(requestBody);
    }

    /**
     * 添加用户的题目分类
     *
     * @return
     */
    public Observable<ResponseBody> addUserSort(RequestBody requestBody) {
        return mRetrofitService.addUserSort(requestBody);
    }

    /**
     * 删除用户的题目分类
     *
     * @return
     */
    public Observable<ResponseBody> deleteUserSort(RequestBody requestBody) {
        return mRetrofitService.deleteUserSort(requestBody);
    }

    /**
     * 获取百科英雄题目
     *
     * @return
     */
    public Observable<List<Question>> getBaikeHeroQuestions(RequestBody requestBody) {
        return mRetrofitService.getBaikeHeroQuestions(requestBody);
    }
}
