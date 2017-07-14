package com.iot.diaper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by test2 on 2017-07-05.
 */

public class SignUpMenuActivity extends AppCompatActivity {
    EditText editTextId;
    EditText editTextPw;
    EditText editTextName;
    Button signUpButton;
    RetrofitBuilder retrofitBuilder;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_menu);

        retrofitBuilder = new RetrofitBuilder(apiService);
        editTextId = (EditText) findViewById(R.id.IdToSignUp);
        editTextPw = (EditText) findViewById(R.id.PasswordToSignUp);
        editTextName = (EditText) findViewById(R.id.inputName);
        signUpButton = (Button) findViewById(R.id.PressToSignUp);

        retrofitBuilder.build();

        signUpButton.setOnClickListener(
                v -> {
                    retrofitBuilder.executeSendMessage(editTextId.getText().toString(), editTextPw.getText().toString(),
                            editTextName.getText().toString());
                }
        );
    }
}
