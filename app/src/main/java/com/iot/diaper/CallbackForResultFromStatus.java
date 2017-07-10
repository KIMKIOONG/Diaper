package com.iot.diaper;

import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by laby on 2017-07-10.
 */

public class CallbackForResultFromStatus {
    private ExecutorService _executorService;

    public CallbackForResultFromStatus() {
        _executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );
    }

    private CompletionHandler<String, Void> callback =
            new CompletionHandler<String, Void>() {
                @Override
                public void completed(String result, Void aVoid) {

                }

                @Override
                public void failed(Throwable throwable, Void aVoid) {

                }
            };

    public void getResult() {
        Runnable runnable = () -> {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("http://10.10.14.77:3005/")
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();
            ApiService apiService = retrofit.create(ApiService.class);
            Call<ResponseBody> call = apiService.postDataForEventUpdate();
        };
    }

    public void finish() {
        _executorService.shutdown();
    }
}
