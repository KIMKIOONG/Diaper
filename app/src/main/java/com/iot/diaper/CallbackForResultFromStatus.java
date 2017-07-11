package com.iot.diaper;

import android.os.Handler;

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

    private CompletionHandler<String, Void> callback =
            new CompletionHandler<String, Void>() {
                @Override
                public void completed(String result, Void aVoid) {
                    _handler.sendMessage();
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
