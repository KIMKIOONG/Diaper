package com.iot.diaper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by test2 on 2017-07-05.
 */

public class SignUpMenuActivity extends AppCompatActivity {
    EditText editTextId;
    EditText editTextPw;
    EditText editTextName;
    Button signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_menu);

        editTextId = (EditText) findViewById(R.id.IdToSignUp);
        editTextPw = (EditText) findViewById(R.id.PasswordToSignUp);
        editTextName = (EditText) findViewById(R.id.inputName);
        signUpButton = (Button) findViewById(R.id.PressToSignUp);

        signUpButton.setOnClickListener(
                v -> {
                    executeSendMessage(editTextId.getText().toString(), editTextPw.getText().toString(),
                            editTextName.getText().toString());
                }
        );
    }

    private void executeSendMessage(String id, String password, String name) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.10.14.77:3005/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<ResponseBody> call = apiService.postData(id, password, name);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}