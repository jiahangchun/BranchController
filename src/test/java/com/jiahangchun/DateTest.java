package com.jiahangchun;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/11/28 上午11:15
 **/
public class DateTest {

    public static void main(String[] args) {

        // 上月开始 结结束
        Calendar lastCalendarStart = Calendar.getInstance();
        lastCalendarStart.add(Calendar.MONTH, -1);
        lastCalendarStart.set(Calendar.DAY_OF_MONTH, 1);
        lastCalendarStart.set(Calendar.HOUR_OF_DAY, 0);
        lastCalendarStart.set(Calendar.MINUTE, 0);
        lastCalendarStart.set(Calendar.SECOND, 0);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastCalendarStart.getTime()));

        Calendar lastCalendarEnd = Calendar.getInstance();
        lastCalendarEnd.set(Calendar.DAY_OF_MONTH, 0);
        lastCalendarEnd.set(Calendar.HOUR_OF_DAY, 23);
        lastCalendarEnd.set(Calendar.MINUTE, 59);
        lastCalendarEnd.set(Calendar.SECOND, 59);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastCalendarEnd.getTime()));


        //获取当前月的第一天 和最后一天
        Calendar currentBeforeDate = Calendar.getInstance();//获取当前日期
        currentBeforeDate.set(Calendar.DAY_OF_MONTH, 1);
        currentBeforeDate.set(Calendar.HOUR_OF_DAY, 0);
        currentBeforeDate.set(Calendar.MINUTE, 0);
        currentBeforeDate.set(Calendar.SECOND, 0);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentBeforeDate.getTime()));


        Calendar currentLastDate = Calendar.getInstance();
        currentLastDate.set(Calendar.DAY_OF_MONTH, currentLastDate.getActualMaximum(Calendar.DAY_OF_MONTH));
        currentLastDate.set(Calendar.HOUR_OF_DAY, 23);
        currentLastDate.set(Calendar.MINUTE, 59);
        currentLastDate.set(Calendar.SECOND, 59);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentLastDate.getTime()));


        //当天零点和最后点
        Calendar todayBeforeDate = Calendar.getInstance();
        todayBeforeDate.set(Calendar.HOUR_OF_DAY, 0);
        todayBeforeDate.set(Calendar.MINUTE, 0);
        todayBeforeDate.set(Calendar.SECOND, 0);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(todayBeforeDate.getTime()));


        Calendar todayLastDate = Calendar.getInstance();
        todayLastDate.add(Calendar.DAY_OF_MONTH, 1);
        todayLastDate.set(Calendar.HOUR_OF_DAY, 0);
        todayLastDate.set(Calendar.MINUTE, 0);
        todayLastDate.set(Calendar.SECOND, 0);
        todayLastDate.add(Calendar.SECOND, -1);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(todayLastDate.getTime()));


    }
}
