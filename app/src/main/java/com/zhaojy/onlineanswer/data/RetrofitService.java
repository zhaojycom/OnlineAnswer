package com.zhaojy.onlineanswer.data;

import com.zhaojy.onlineanswer.bean.Question;
import com.zhaojy.onlineanswer.bean.QuestionSort;
import com.zhaojy.onlineanswer.bean.ResponseBody;
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
    @POST("getQuestionSort")
    Observable<List<QuestionSort>> getQuestionSort(@Body RequestBody requestBody);


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

}
