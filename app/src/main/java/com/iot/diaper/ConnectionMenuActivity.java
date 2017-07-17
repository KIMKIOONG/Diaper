package com.iot.diaper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by LEE-PC on 2017-07-14.
 */

public class ConnectionMenuActivity extends AppCompatActivity
{

    String userId;
    EditText editTextConnection;
    Button connectionButton;
    RetrofitBuilder retrofitBuilder;
    ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection_menu);

        Intent userIntent = getIntent();
        userId = userIntent.getStringExtra("userId");

        editTextConnection = (EditText) findViewById(R.id.editTextConnection);
        connectionButton = (Button) findViewById(R.id.buttonConnection);

        retrofitBuilder = new RetrofitBuilder(apiService, this);
        retrofitBuilder.build();

        connectionButton.setOnClickListener(
                v -> {

                    // 1. 레트로핏으로 post 기기 정보
                    String deviceNumber = editTextConnection.getText().toString();

                    retrofitBuilder.postAddDevice(userId, deviceNumber);

                    Intent intentToGraph = new Intent();
                    setResult(Activity.RESULT_OK, intentToGraph);
                    finish();

                }
        );
    }
}
