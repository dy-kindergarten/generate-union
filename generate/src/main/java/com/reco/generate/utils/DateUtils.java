package com.reco.generate.utils;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {

    private static SimpleDateFormat simpleDateFormat;

    private static final String patten = "yyyy-MM-dd";

    public static final int AFTER = 1;

    public static final int BEFORE = -1;

    /**
     * 当前日期（Y-M-d）
     *
     * @return
     */
    public static String date2Str() {
        return date2Str(patten);
    }

    /**
     * 当前日期（patten）
     *
     * @return
     */
    public static String date2Str(String patten) {
        return date2Str(new Date(), patten);
    }

    /**
     * 某日的日期（Y-M-d）
     *
     * @return
     */
    public static String date2Str(Date date) {
        return date2Str(date, patten);
    }

    /**
     * 某日的日期（patten）
     *
     * @return
     */
    public static String date2Str(Date date, String patten) {
        simpleDateFormat = new SimpleDateFormat(patten);
        return simpleDateFormat.format(date);
    }

    /**
     * 某日的日期（patten）
     *
     * @return
     */
    @SneakyThrows
    public static Date str2Date(String dateStr, String patten) {
        simpleDateFormat = new SimpleDateFormat(patten);
        return simpleDateFormat.parse(dateStr);
    }

    /**
     * 两个日期的天数差值 (+1)
     *
     * @param startDateStr
     * @param endDateStr
     * @param patten
     * @return
     */
    public static long dayDiff(String startDateStr, String endDateStr, String patten) {
        Date startDate = str2Date(startDateStr, patten);
        Date endDate = str2Date(endDateStr, patten);
        return (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);
    }

    /**
     * 两个日期的天数差值
     *
     * @param startDateStr
     * @param endDateStr
     * @return
     */
    public static long dayDiff(String startDateStr, String endDateStr) {
        return dayDiff(startDateStr, endDateStr, patten);
    }

    /**
     * 获取两个日期之间的全部日期
     *
     * @param startDateStr
     * @param endDateStr
     * @param patten
     * @return
     */
    public static List<String> getDateStrs(String startDateStr, String endDateStr, String patten) {
        List<String> dateStrs = Lists.newArrayList();
        long dayDiff = dayDiff(startDateStr, endDateStr, patten);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(str2Date(startDateStr, patten));
        dateStrs.add(startDateStr);
        for (int i = 0; i < dayDiff; i++) {
            calendar.add(5, 1);
            dateStrs.add(date2Str(calendar.getTime(), patten));
        }
        return dateStrs;
    }

    /**
     * 获取两个日期之间的全部日期
     *
     * @param startDate
     * @param endDate
     * @param patten
     * @return
     */
    public static List<String> getDateStrs(Date startDate, Date endDate, String patten) {
        List<String> dateStrs = Lists.newArrayList();
        long dayDiff = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        dateStrs.add(date2Str(startDate, patten));
        for (int i = 0; i < dayDiff; i++) {
            calendar.add(5, 1);
            dateStrs.add(date2Str(calendar.getTime(), patten));
        }
        return dateStrs;
    }

    /**
     * 获取两个日期之间的全部日期
     *
     * @param startDateStr
     * @param endDateStr
     * @return
     */
    public static List<String> getDateStrs(String startDateStr, String endDateStr) {
        return getDateStrs(startDateStr, endDateStr, patten);
    }

    /**
     * 获取两个日期之间的全部日期
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<String> getDateStrs(Date startDate, Date endDate) {
        return getDateStrs(startDate, endDate, patten);
    }

    /**
     * 获取距离当前day天的日期
     *
     * @param day  单位（天）
     * @param type 1: 现在之后的时间，-1：现在之前的时间
     * @return
     */
    public static Date getDate(int day, int type) {
        return getDate(new Date(), day, type);
    }

    /**
     * 获取距离date，day天的日期
     *
     * @param date
     * @param day  单位（天）
     * @param type 1: 现在之后的时间，-1：现在之前的时间
     * @return
     */
    public static Date getDate(Date date, int day, int type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (type > 0) {
            calendar.add(Calendar.DATE, day);
        } else {
            calendar.add(Calendar.DATE, -day);
        }
        return calendar.getTime();
    }

    /**
     * 获取本周一的日期
     *
     * @return
     */
    public static Date getThisWeekMonday() {
        Calendar calendar = Calendar.getInstance();
        // 获得当前日期是一个星期的第几天
        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.add(Calendar.DATE, -dayWeek);
        calendar.add(Calendar.WEEK_OF_MONTH, -1);
        return calendar.getTime();
    }

    /**
     * 获取日期的周
     *
     * @param date
     * @return
     */
    public static String getWeekNumStr(Date date) {
        StringBuffer stringBuffer = new StringBuffer(getDate("yyyy年MM月"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 第几周
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        switch (week) {
            case 1:
                stringBuffer.append("第一周");
                break;
            case 2:
                stringBuffer.append("第二周");
                break;
            case 3:
                stringBuffer.append("第三周");
                break;
            case 4:
                stringBuffer.append("第四周");
                break;
            case 5:
                stringBuffer.append("第五周");
                break;
            default:
                return "";
        }
        return stringBuffer.toString();
    }

    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    public static String getDate(String patten) {
        return getDate(new Date(), patten);
    }

    public static String getDate(Date date, String patten) {
        simpleDateFormat = new SimpleDateFormat(patten);
        return simpleDateFormat.format(date);
    }
}
