package com.iot.diaper;

import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
    private ArrayList<String> _arrayList;

    public RetrofitBuilder(ApiService apiService) {
        _apiService = apiService;
    }

    public RetrofitBuilder(ApiService apiService, ArrayList<String> arrayList) {
        _apiService = apiService;
        _arrayList = arrayList;
    }

    public ArrayList<String> getArrayList()
    {
        return _arrayList;
    }

    public void clearList() {
        _arrayList.clear();
    }

    public void build() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.100.188:3005/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        _apiService = retrofit.create(ApiService.class);
    }

    public void executeSendMessage(String id, String password, String name) {
        Call<ResponseBody> call = _apiService.postData(id, password, name);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void getGraphData(String id, TextView txt_test) {
        Call<ResponseBody> call = _apiService.getcountData(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseData = response.body().string();
                    txt_test.setText(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void getIdAndPw(String id, String pw) {

        Call<ResponseBody> call = _apiService.checkDataToLogin(id, pw);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONArray jsonArray = null;
                try
                {
                    jsonArray = new JSONArray(response.body().string());
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String did = jsonObject.getString("babyId");
                    String dname = jsonObject.getString("name");
                    _arrayList.add(did);
                    _arrayList.add(dname);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
