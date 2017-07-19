package com.iot.diaper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TermActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);

        EditText startYear = (EditText) findViewById(R.id.startYear);
        EditText startMonth = (EditText) findViewById(R.id.startMonth);
        EditText startDay = (EditText) findViewById(R.id.startDay);

        EditText endYear = (EditText) findViewById(R.id.endYear);
        EditText endMonth = (EditText) findViewById(R.id.endMonth);
        EditText endDay = (EditText) findViewById(R.id.endDay);


        Button btn_Term = (Button) findViewById(R.id.btn_term);
        btn_Term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intStartYear = Integer.parseInt(startYear.getText().toString());
                int intStartMonth = Integer.parseInt(startMonth.getText().toString());
                int intStartDay = Integer.parseInt(startDay.getText().toString());

                int intEndYear = Integer.parseInt(endYear.getText().toString());
                int intEndMonth = Integer.parseInt(endMonth.getText().toString());
                int intEndDay = Integer.parseInt(endDay.getText().toString());

                Intent term_intent = new Intent();
                term_intent.putExtra("startyear", intStartYear);
                term_intent.putExtra("startmonth", intStartMonth);
                term_intent.putExtra("startday", intStartDay);

                term_intent.putExtra("endyear", intEndYear);
                term_intent.putExtra("endmonth", intEndMonth);
                term_intent.putExtra("endday", intEndDay);

                setResult(RESULT_OK, term_intent);
                finish();
            }
        });
    }
}
