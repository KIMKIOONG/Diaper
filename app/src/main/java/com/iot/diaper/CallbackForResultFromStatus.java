package com.iot.diaper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by laby on 2017-07-10.
 */

public class CallbackForResultFromStatus {
    private ExecutorService _executorService;
    private String id;
    private Handler _handler;

    public CallbackForResultFromStatus(String id, Handler handler) {
        _executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );
        this.id = id;
        this._handler = handler;
    }

    // CompletionHandler class 안드로이드 스튜디오 3.0버전으로 업데이트해서 진행해야됨.
    private CompletionHandler<String, Void> callback = new CompletionHandler<String, Void>() {
                // service가 동작하다가 response에 값이 있을때 실행되는 메소드
                @Override
                public void completed(String result, Void aVoid) {
                    // json 파싱해서 알림을 띄워줌.
                    try {
                        JSONArray jsonArray = new JSONArray(result);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String time = jsonObject.getString("wasteTime");
                        // string -> message 로 바꿈
                        Bundle timeBundle = new Bundle();
                        timeBundle.putString("time", time);

                        Message message_time = Message.obtain();
                        message_time.setData(timeBundle);

                        _handler.handleMessage(message_time);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void failed(Throwable throwable, Void aVoid) {}
            };

    public void getResult() {
        Runnable runnable = () -> {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("http://10.10.14.77:3005/")
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();
            ApiService apiService = retrofit.create(ApiService.class);
            while(true) {
                Call<ResponseBody> call = apiService.postDataForEventUpdate(id);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        // reponse로 값이 있을때 completed 실행함.
                        if(response.body().contentLength() > 0)
                            callback.completed(response.body().toString(), null);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {}
                });
            }
        };
        _executorService.submit(runnable);
    }

    public void finish() {
        _executorService.shutdown();
    }
}
