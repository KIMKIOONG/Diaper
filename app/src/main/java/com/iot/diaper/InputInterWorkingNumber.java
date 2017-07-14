package com.iot.diaper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by test2 on 2017-07-13.
 */

public class InputInterWorkingNumber extends AppCompatActivity{
    EditText arduinoId;
    Button connectionToAndroid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection_menu);

        arduinoId = (EditText) findViewById(R.id.editText);
        connectionToAndroid = (Button) findViewById(R.id.button);
        connectionToAndroid.setOnClickListener(
                view -> {

                }
        );
    }
}
