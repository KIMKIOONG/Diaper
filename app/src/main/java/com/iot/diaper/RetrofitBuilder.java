package com.iot.diaper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by test2 on 2017-07-13.
 */

public class RetrofitBuilder {
    private ApiService _apiService;
    private Activity activity;

    public RetrofitBuilder(ApiService apiService) {
        _apiService = apiService;
    }

    public RetrofitBuilder(ApiService apiService, Activity activity) {
        _apiService = apiService;
        this.activity = activity;
    }

    public void build() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.100.172:3005/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        _apiService = retrofit.create(ApiService.class);
    }

    // 아기 가입 메소드
    public void postSignUp(String id, String password, String name) {
        Call<ResponseBody> call = _apiService.postBabyData(id, password, name);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    // 기기 등록 메소드
    public void postAddDevice(String id, String deviceNum) {
        Call<ResponseBody> call = _apiService.postDeviceData(id, deviceNum);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void getGraphData(String id, TextView txt_test, BarChart barChart) {

        ArrayList<Integer> arrayCount = new ArrayList<>();
        for(int i=0; i<24; i++) {
            arrayCount.add(0);
        }

        Call<ResponseBody> call = _apiService.getcountData(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                String responseData = null;
                try {
                    responseData = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseData);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int time;
                        int count;
                        time = jsonObject.getInt("time");
                        count = jsonObject.getInt("count");
                        txt_test.setText(id+"그래프");
                        arrayCount.set(time, count);
                    }

                    BarGraph graphData = new BarGraph(arrayCount);
                    graphData.createBarGraph(barChart);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void getUserData(String id, String pw) {

        Call<ResponseBody> call = _apiService.checkDataToLogin(id, pw);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String responseData = null;
                try {
                    if(response.body() == null) {
                        Toast.makeText(activity.getApplicationContext(),"no", Toast.LENGTH_LONG).show();
                    }
                    responseData = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseData);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String id;
                    String name;
                    id = jsonObject.getString("babyId");
                    name = jsonObject.getString("name");
                    Intent intent = new Intent(activity, GraphActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("name", name);
                    activity.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void getUpdateEvent(String id, Handler handler) {

            Call<ResponseBody> call = _apiService.postDataForEventUpdate(id);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    // reponse로 값이 있을때 completed 실행함.
                    String result = null;
                    try {
                        result = response.body().string();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if(result != null) {
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(result);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String time = jsonObject.getString("wasteTime");
                            // string -> message 로 바꿈
                            Message message_time = Message.obtain();
                            message_time.obj = time;
                            handler.sendMessage(message_time);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {}
            });
    }

    public void signupMenuForId(String id) {
        Call<ResponseBody> call = _apiService.checkforDuplicatedId(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response == null) {
                    Toast.makeText(activity.getApplicationContext(), "no", Toast.LENGTH_LONG).show();
                }
                String responseData = null;
                try {
                    responseData = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseData);
                    String num = jsonObject.getString("count");
                    LayoutInflater inflater = activity.getLayoutInflater();
                    ViewGroup view = (ViewGroup) inflater.inflate(R.layout.signup_menu, null);
                    EditText editTextId = (EditText) view.findViewById(R.id.IdToSignUp);
                    EditText editTextPw = (EditText) view.findViewById(R.id.PasswordToSignUp);
                    EditText editTextName = (EditText) view.findViewById(R.id.inputName);
                    if(num.equals("0")) {
                        postSignUp(editTextId.getText().toString(), editTextPw.getText().toString(), editTextName.getText().toString());
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                    } else {
                        editTextId.setTextColor(Color.RED);
                        Toast.makeText(activity.getApplicationContext(), "Id Duplicated", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
