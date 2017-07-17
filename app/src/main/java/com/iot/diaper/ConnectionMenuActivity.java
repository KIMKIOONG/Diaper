package com.iot.diaper;

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


    EditText editTextConnection;
    Button connectionButton;
    RetrofitBuilder retrofitBuilder;
    ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_menu);


        editTextConnection = (EditText) findViewById(R.id.editTextConnection);

        connectionButton = (Button) findViewById(R.id.buttonConnection);



        connectionButton.setOnClickListener(
                v -> {
                    String connectionData = editTextConnection.getText().toString();

                    Intent intent = new Intent(getApplicationContext(), GraphActivity.class);


                }
        );
    }
}
