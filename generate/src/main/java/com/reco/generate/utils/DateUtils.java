package com.reco.generate.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static SimpleDateFormat simpleDateFormat;

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
