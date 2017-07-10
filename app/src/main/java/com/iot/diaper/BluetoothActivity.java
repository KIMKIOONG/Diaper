package com.iot.diaper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BluetoothActivity extends AppCompatActivity {

    // Debugging
    private static final String TAG = "Main";

    // Intent request code
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    private BluetoothService btService = null;

    private final Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }

    };
    private Button btn_connect;
    private TextView txt_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        btn_connect = (Button) findViewById(R.id.btn_connect);
        txt_result = (TextView) findViewById(R.id.txt_result);

        // BluetoothService 클래스 생성
        if(btService == null) {
            btService = new BluetoothService(this, mHandler);
        }

        btn_connect.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(btService.getDeviceState()) {
                            // 블루투스가 지원 가능한 기기일 때
                            btService.enableBluetooth();
                        } else {
                            finish();
                        }
                    }
                }
        );
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult " + resultCode);

        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // 확인 눌렀을 때
                    //Next Step
                } else {
                    // 취소 눌렀을 때
                    Log.d(TAG, "Bluetooth is not enabled");
                }
                break;
        }
    }
}
