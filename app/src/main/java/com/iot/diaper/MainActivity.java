package com.iot.diaper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    Button buttonSignUp;
    Button buttonLogin;
    EditText inputId, inputPw;
    CheckBox autoLogin;
    RetrofitBuilder retrofitBuilder;
    ApiService apiService;;
    SharedPreferences setting;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofitBuilder = new RetrofitBuilder(apiService, this);
        retrofitBuilder.build();
        setting = getSharedPreferences("setting", Activity.MODE_PRIVATE);
        editor = setting.edit();
        Intent intentFromGraph = getIntent();

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
                        String id = inputId.getText().toString();
                        String pw = inputPw.getText().toString();
                        retrofitBuilder.getUserUpdate(id, pw);
                }
        );

        if(setting.getBoolean("Auto_Login_enabled", false)) {
            inputId.setText(setting.getString("id", ""));
            inputPw.setText(setting.getString("pw", ""));
            retrofitBuilder.getUserData(setting.getString("id", ""), setting.getString("pw", ""));
            autoLogin.setChecked(true);
        }

        autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    String id = inputId.getText().toString();
                    String pw = inputPw.getText().toString();

                    editor.putString("id", id);
                    editor.putString("pw", pw);
                    editor.putBoolean("Auto_Login_enabled", true);
                    editor.commit();
                } else {
                    editor.clear();
                    editor.commit();
                }
            }
        });
    }
}





