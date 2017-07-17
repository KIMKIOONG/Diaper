package com.iot.diaper;

import android.os.Handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by laby on 2017-07-10.
 */

public class CallbackForResultFromStatus {
    private ExecutorService _executorService;
    private String id;
    private Handler _handler;
    RetrofitBuilder retrofitBuilder;
    ApiService apiService;

    public CallbackForResultFromStatus(String id, Handler handler) {
        _executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );
        this.id = id;
        this._handler = handler;
    }

    public void getResult() {
        Runnable runnable = () -> {
            retrofitBuilder = new RetrofitBuilder(apiService);
            retrofitBuilder.build();

            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                retrofitBuilder.getUpdateEvent(id, _handler);
            }
        };
        _executorService.submit(runnable);
    }

    public void finish() {
        _executorService.shutdown();
    }
}
