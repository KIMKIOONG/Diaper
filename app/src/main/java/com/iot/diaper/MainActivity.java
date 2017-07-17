package com.iot.diaper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    Button buttonSignUp;
    Button buttonLogin;
    EditText inputId, inputPw;
    CheckBox autoLogin;
    RetrofitBuilder retrofitBuilder;
    ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofitBuilder = new RetrofitBuilder(apiService, this);
        retrofitBuilder.build();
        inputId = (EditText) findViewById(R.id.inputId);
        inputPw = (EditText) findViewById(R.id.inputPw);
        autoLogin = (CheckBox) findViewById(R.id.checkBox);

        buttonSignUp = (Button) findViewById(R.id.PressToGoTosignupMenu);
        buttonSignUp.setOnClickListener(
                view -> {
                    Intent intent = new Intent(getApplicationContext(), SignUpMenuActivity.class);
                    startActivity(intent);
                }
        );

        buttonLogin = (Button) findViewById(R.id.PressToLogin);
        buttonLogin.setOnClickListener(
                view -> {
                    Toast.makeText(getApplicationContext(), "login button", Toast.LENGTH_LONG).show();
                    String id = inputId.getText().toString();
                    String pw = inputPw.getText().toString();
                    retrofitBuilder.getAuthLogin(id, pw);
                }

        );
    }
}





