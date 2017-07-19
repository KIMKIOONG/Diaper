package com.iot.diaper;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hdj on 2017-07-14.
 */

public class BarGraph {
    ArrayList<EventCount> eventCounts;
    ArrayList<String> times = new ArrayList<>();
    ArrayList<Integer> count = new ArrayList<>();
    ArrayList<BarEntry> barEntries = new ArrayList<>();



    public BarGraph(ArrayList<EventCount> eventCounts) {
        this.eventCounts = eventCounts;

        // count list의 인덱스가 시간인 셈
        for(int i=0; i<24; i++) {
            count.add(0);
        }
    }

    public void setUpDataBetweenDate(Date start, Date end) {
        for(int i=0; i<eventCounts.size(); i++) {
            EventCount eventCount = eventCounts.get(i);
            // 데이터가 스타트 이후일때 || 데이터가 엔드 이전일때
            if((eventCount.getDate().compareTo(start)==1) && (eventCount.getDate().compareTo(end)==-1)) {
                int timecount = count.get(eventCount.getTime());
                count.set(eventCount.getTime(), timecount+eventCount.getCount());
            }
        }
    }

    public void createBarGraph(BarChart barChart){

        for(int i=0; i<24; i++) {
            times.add(""+i);
        }

        int max = 100;
        int value;
        for(int j = 0; j< 24;j++){
            value = count.get(j);
            barEntries.add(new BarEntry(value,j));
        }

        BarDataSet barDataSet = new BarDataSet(barEntries,"time");
        BarData barData = new BarData(times,barDataSet);
        barChart.setData(barData);
        barChart.setDescription("Baby Diaper");
    }
}
