package com.iot.diaper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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
        retrofitBuilder = new RetrofitBuilder(apiService, list);
        retrofitBuilder.build();
        inputId = (EditText) findViewById(R.id.inputId);
        inputPw = (EditText) findViewById(R.id.inputPw);
        autoLogin = (CheckBox) findViewById(R.id.checkBox);

        button1 = (Button) findViewById(R.id.PressToGoTosignupMenu);
        button1.setOnClickListener(
                view -> {

                }
        );

        button2 = (Button) findViewById(R.id.PressToLogin);
        button2.setOnClickListener(
                view -> {
                    String id = inputId.getText().toString();
                    String pw = inputPw.getText().toString();
                    retrofitBuilder.getIdAndPw(id, pw);
                    ArrayList<String> listOfUser = retrofitBuilder.getArrayList();
                    if(listOfUser.get(1).length() > 0) {
                        Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
                        intent.putExtra("name", listOfUser.get(1));
                        startActivity(intent);
                        retrofitBuilder.clearList();
                        Toast.makeText(getApplicationContext(),
                                "아이디 비번이 맞았습니다", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "아이디 비밀번호가 틀렸습니다", Toast.LENGTH_LONG).show();
                    }
                }

        );
    }
}





