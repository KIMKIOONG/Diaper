package com.iot.diaper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.PressToGoTosignupMenu);
        button.setOnClickListener(
                view -> {
                    Intent intent = new Intent(getApplicationContext(), SignUpMenu.class);
                    startActivity(intent);
                }
        );
    }
}
