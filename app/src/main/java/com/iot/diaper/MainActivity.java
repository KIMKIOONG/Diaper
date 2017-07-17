package com.iot.diaper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    Button button1;
    Button button2;
    EditText inputId, inputPw;
    CheckBox autoLogin;
    RetrofitBuilder retrofitBuilder;
    ApiService apiService;
    ArrayList<String> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();
        retrofitBuilder = new RetrofitBuilder(apiService, this);
        retrofitBuilder.build();
        inputId = (EditText) findViewById(R.id.inputId);
        inputPw = (EditText) findViewById(R.id.inputPw);
        autoLogin = (CheckBox) findViewById(R.id.checkBox);

        button1 = (Button) findViewById(R.id.PressToGoTosignupMenu);
        button1.setOnClickListener(
                view -> {
                    Intent intent = new Intent(getApplicationContext(), SignUpMenuActivity.class);
                    startActivity(intent);
                }
        );

        button2 = (Button) findViewById(R.id.PressToLogin);
        button2.setOnClickListener(
                view -> {
                    String id = inputId.getText().toString();
                    String pw = inputPw.getText().toString();
                    retrofitBuilder.getUserData(id, pw);
                }

        );
    }
}





