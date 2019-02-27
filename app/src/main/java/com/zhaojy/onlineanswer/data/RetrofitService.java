package com.zhaojy.onlineanswer.data;

import com.zhaojy.onlineanswer.bean.ErrorsSort;
import com.zhaojy.onlineanswer.bean.Question;
import com.zhaojy.onlineanswer.bean.QuestionDifficult;
import com.zhaojy.onlineanswer.bean.QuestionSort;
import com.zhaojy.onlineanswer.bean.ResponseBody;
import com.zhaojy.onlineanswer.bean.Slideshow;
import com.zhaojy.onlineanswer.bean.User;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author: zhaojy
 * @data:On 2018/2/26.
 */

public interface RetrofitService {
    /**
     * 登录
     *
     * @param requestBody
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("login")
    Observable<User> login(@Body RequestBody requestBody);

    /**
     * 修改昵称
     *
     * @param requestBody
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("alterNickname")
    Observable<ResponseBody> alterNickname(@Body RequestBody requestBody);

    /**
     * 获取题目分类信息
     *
     * @param requestBody
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("getMoreSort")
    Observable<List<QuestionSort>> getMoreSort(@Body RequestBody requestBody);


    /**
     * 获取题目
     *
     * @param requestBody
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("readQuestion")
    Observable<List<Question>> getQuestion(@Body RequestBody requestBody);

    /**
     * 提交已完成的题目
     *
     * @param requestBody
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("submitQuestion")
    Observable<ResponseBody> submitQuestion(@Body RequestBody requestBody);

    /**
     * 提交已完成的题目
     *
     * @param requestBody
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("getBannerImg")
    Observable<List<Slideshow>> getSlideshow();

    /**
     * 获取错题分类信息
     *
     * @param requestBody
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("getMyErrorsSorts")
    Observable<List<ErrorsSort>> getMyErrorsSorts(@Body RequestBody requestBody);


    /**
     * 获取题目
     *
     * @param requestBody
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("readErrorQuestion")
    Observable<List<Question>> getErrorQuestions(@Body RequestBody requestBody);

    /**
     * 获取题目
     *
     * @param requestBody
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("submitErrorQuestion")
    Observable<ResponseBody> submitErrorQuestion(@Body RequestBody requestBody);

    /**
     * 获取题目难度
     *
     * @param requestBody
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("getQuestionDifficult")
    Observable<List<QuestionDifficult>> getQuestionDifficult();


    /**
     * 获取当前用户的题目分类
     *
     * @param requestBody
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("getUserSort")
    Observable<List<QuestionSort>> getUserSort(@Body RequestBody requestBody);

    /**
     * 添加用户的题目分类
     *
     * @param requestBody
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("addUserSort")
    Observable<ResponseBody> addUserSort(@Body RequestBody requestBody);

    /**
     * 删除用户的题目分类
     *
     * @param requestBody
     * @return
     */
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("deleteUserSort")
    Observable<ResponseBody> deleteUserSort(@Body RequestBody requestBody);

}
