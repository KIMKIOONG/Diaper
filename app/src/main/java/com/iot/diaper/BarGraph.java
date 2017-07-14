package com.iot.diaper;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

/**
 * Created by hdj on 2017-07-14.
 */

public class BarGraph {

    ArrayList<String> times;
    ArrayList<Integer> count;
    ArrayList<BarEntry> barEntries;


    public BarGraph(ArrayList<Integer> count) {
        this.count = count;
    }

    public void createBarGraph(BarChart barChart){

        times = new ArrayList<>();
        for(int i=0; i<24; i++) {
            times.add(""+i);
        }

        barEntries = new ArrayList<>();
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
