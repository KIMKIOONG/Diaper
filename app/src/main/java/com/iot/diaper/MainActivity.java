package com.iot.diaper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    Button button1;
    Button button2;
    EditText inputId, inputPw;
    CheckBox autoLogin;
    Boolean loginChecked;
    SharedPreferences pref;
    SharedPreferences.Editor editor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    Intent intent = new Intent(getApplicationContext(), InputInterWorkingNumber.class);
                    startActivity(intent);
                }
        );



        if (pref.getBoolean("autoLogin", false)) {
            inputId.setText(pref.getString("id", ""));
            inputPw.setText(pref.getString("pw", ""));
            autoLogin.setChecked(true);
            // goto mainActivity

        } else {
            // if autoLogin unChecked
            String id = inputId.getText().toString();
            String password = inputPw.getText().toString();
            Boolean validation = loginValidation(id, password);

            if(validation) {
                Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                // save id, password to Database

                if(loginChecked) {
                    // if autoLogin Checked, save values
                    editor.putString("id", id);
                    editor.putString("pw", password);
                    editor.putBoolean("autoLogin", true);
                    editor.commit();
                }
                // goto mainActivity

            } else {
                Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                // goto LoginActivity
            }
        }
        autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    loginChecked = true;
                } else {
                    // if unChecked, removeAll
                    loginChecked = false;
                    editor.clear();
                    editor.commit();
                }
            }
        });
    }

    private boolean loginValidation(String id, String password) {
        if(pref.getString("id","").equals(id) && pref.getString("pw","").equals(password)) {
            // login success
            return true;
        } else if (pref.getString("id","").equals(null)){
            // sign in first
            Toast.makeText(MainActivity.this, "Please Sign in first", Toast.LENGTH_LONG).show();
            return false;
        } else {
            // login failed
            return false;
        }
    }



    }





