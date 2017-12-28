package com.ngocthach.taskmanager.common;

import java.util.Calendar;
import java.util.Date;

/**
 * ${CLASS}
 * Created by ThachPham on 28/12/2017.
 */

public class TimeUtils {

    public static long getRemainTimeToDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        // By millisecond
        long currentTime = calendar.get(Calendar.HOUR) * 3600000 + calendar.get(Calendar.MINUTE) * 60000
                + calendar.get(Calendar.SECOND) * 1000 + calendar.get(Calendar.MILLISECOND);
        long dayTime = 24 * 3600000;
        return dayTime - currentTime;
    }

    public static boolean isSameDay(Date date1, Date date2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        int year1 = calendar.get(Calendar.YEAR);
        int month1 = calendar.get(Calendar.MONTH);
        int day1 = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTime(date2);
        int year2 = calendar.get(Calendar.YEAR);
        int month2 = calendar.get(Calendar.MONTH);
        int day2 = calendar.get(Calendar.DAY_OF_MONTH);
        return (year1 == year2 && month1 == month2 && day1 == day2);
    }

}
