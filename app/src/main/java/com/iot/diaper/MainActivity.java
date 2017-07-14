package com.iot.diaper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button button;
    Button btnToLogin;
    EditText textId;
    EditText textPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textId = (EditText) findViewById(R.id.inputId);
        textPw = (EditText) findViewById(R.id.inputPw);

        btnToLogin = (Button) findViewById(R.id.PressToLogin);
        btnToLogin.setOnClickListener(
                view -> {

                }
        );

        button = (Button) findViewById(R.id.PressToGoTosignupMenu);
        button.setOnClickListener(
                view -> {
                    Intent intent = new Intent(getApplicationContext(), SignUpMenuActivity.class);
                    startActivity(intent);
                }
        );
    }
}
