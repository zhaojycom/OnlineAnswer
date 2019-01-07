package com.zhaojy.onlineanswer.data;

import android.content.Intent;

/**
 * @author: zhaojy
 * @data:On 2018/2/26.
 */

public interface Presenter {

    void onCreate();

    void onStart();

    void onStop();

    void pause();

    void attachUpdate(BaseUpdate update);

    void attachIncomingIntent(Intent intent);

    void setBaseUrl(String baseUrl);

}