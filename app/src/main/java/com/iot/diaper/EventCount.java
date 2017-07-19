package com.iot.diaper;

import java.util.Date;

/**
 * Created by hdj on 2017-07-19.
 */

public class EventCount {
    private Date date;
    private int time;
    private int count;

    public EventCount(Date date, int time, int count) {
        this.date = date;
        this.time = time;
        this.count = count;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
