package com.iot.diaper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;

public class GraphActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // testid, name 넣어놓은것
    String userId = "111";
    String userName = "이원희";
    private TextView txt_test;
    private Button button_test;
    private Intent userintent;
    ApiService apiService;
    RetrofitBuilder retrofitBuilder;
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 차트 그리는 곳
        barChart = (BarChart) findViewById(R.id.bargraph);

        retrofitBuilder = new RetrofitBuilder(apiService);
        retrofitBuilder.build();

        // useIntent 생성
        userintent = new Intent(getApplicationContext(), RequestHandler.class);
        userintent.putExtra("userId", userId);
        userintent.putExtra("userName", userName);
        userintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        txt_test = (TextView) findViewById(R.id.txt_test);
        txt_test.setText("result 출력");

        button_test = (Button) findViewById(R.id.test_button);
        button_test.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        retrofitBuilder.getGraphData(userId, txt_test, barChart);
                    }
                }
        );
        // 그래프 그리기
        retrofitBuilder.getGraphData(userId, txt_test, barChart);

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
        // switch 어떻게 코드 작성하냐? http://blog.naver.com/PostView.nhn?blogId=cosmosjs&logNo=220728864491
//        else if (id == R.id.switch_test) {
//            if()
//            Toast.makeText(getApplicationContext(), "알림 오프", Toast.LENGTH_LONG).show();
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
