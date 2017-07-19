package com.iot.diaper;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;

import java.util.Date;

public class GraphActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // testid, name 넣어놓은것
    String userId;
    String userName;
    private TextView txt_test;
    private Intent userintent;
    ApiService apiService;
    RetrofitBuilder retrofitBuilder;
    BarChart barChart;
    final int REQUEST_ACT = 111;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private Date startDate;
    private Date endDate;
    private TextView txt_startDate;
    private TextView txt_endDate;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == event.KEYCODE_BACK) {
            showMessage();
            return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrofitBuilder.getDailyGraphData(startDate, endDate, userId, userName, txt_test, barChart);
        txt_startDate.setText(startDate.getYear()+"/"+startDate.getMonth()+"/"+startDate.getDate());
        txt_endDate.setText(endDate.getYear()+"/"+endDate.getMonth()+"/"+endDate.getDate());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startDate = new Date(2017, 1, 1);
        endDate = new Date(2017, 11, 31);

        Intent intent = getIntent();
        userId = intent.getStringExtra("id");
        userName = intent.getStringExtra("name");

        // 차트 그리는 곳
        barChart = (BarChart) findViewById(R.id.bargraph);

        // 레트로핏 빌더
        retrofitBuilder = new RetrofitBuilder(apiService);
        retrofitBuilder.build();

        // useIntent 생성 (RequestHandler쪽으로 넘어갈꺼임)
        userintent = new Intent(getApplicationContext(), RequestHandler.class);
        userintent.putExtra("userId", userId);
        userintent.putExtra("userName", userName);
        userintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        txt_test = (TextView) findViewById(R.id.txt_test);
        txt_test.setText(userName+" 그래프");

        // 날짜 출력
        txt_startDate = (TextView) findViewById(R.id.textStartDate);
        txt_endDate = (TextView) findViewById(R.id.textEndDate);
        txt_startDate.setText(""+startDate);
        txt_endDate.setText(""+endDate);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.graph, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.alertOn) {
            // 알림이 켜졌을때
            Toast.makeText(getApplicationContext(), "알림 온", Toast.LENGTH_LONG).show();
            startService(userintent);

        } else if (id == R.id.alertOff) {
            Toast.makeText(getApplicationContext(), "알림 오프", Toast.LENGTH_LONG).show();
            stopService(userintent);
        }
        else if (id == R.id.deviceConnect) {
            Toast.makeText(getApplicationContext(), "기기등록 화면", Toast.LENGTH_LONG).show();
            Intent intentToConnection = new Intent(getApplicationContext(), ConnectionMenuActivity.class);
            intentToConnection.putExtra("userId", userId);
            startActivityForResult(intentToConnection, REQUEST_ACT);
        } else if (id == R.id.log_out) {
            pref = getSharedPreferences("setting", Activity.MODE_PRIVATE);
            editor = pref.edit();
            editor.clear();
            editor.putBoolean("Auto_Login_Enabled", false);
            editor.commit();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if(id == R.id.finish_app) {
            finishAffinity();
        } else if (id == R.id.countTerm) {
            Toast.makeText(getApplicationContext(), "통계 기간설정 화면", Toast.LENGTH_LONG).show();
            Intent intentToTerm = new Intent(getApplicationContext(), TermActivity.class);
            startActivityForResult(intentToTerm, REQUEST_ACT);
        }
        // switch 어떻게 코드 작성하냐? http://blog.naver.com/PostView.nhn?blogId=cosmosjs&logNo=220728864491
//        else if (id == R.id.switch_test) {
//            if()
//            Toast.makeText(getApplicationContext(), "알림 오프", Toast.LENGTH_LONG).show();
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // 기간을 받아왔을때 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            Toast.makeText(GraphActivity.this, "결과가 성공이 아님.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (requestCode == REQUEST_ACT) {
            // 기간 받아온 것들 처리.
            int startYear = data.getIntExtra("startyear",0);
            int startMonth = data.getIntExtra("startmonth",0);
            int startDay = data.getIntExtra("startday",0);

            int endYear = data.getIntExtra("endyear",0);
            int endMonth = data.getIntExtra("endmonth",0);
            int endDay = data.getIntExtra("endday",0);

            startDate = new Date(startYear, startMonth, startDay);
            endDate = new Date(endYear, endMonth, endDay);

        } else {
            Toast.makeText(GraphActivity.this, "REQUEST_ACT가 아님", Toast.LENGTH_SHORT).show();
        }

    }

    private void showMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("안내");
        builder.setMessage("앱을 종료하시겠습니까?");
        builder.setIcon(R.drawable.option);

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
            }
        });

        builder.setNeutralButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
