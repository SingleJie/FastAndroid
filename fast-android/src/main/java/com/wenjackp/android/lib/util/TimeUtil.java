package com.wenjackp.android.lib.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间相关操作类
 *
 * @author Single
 * @version 1.0
 */
public class TimeUtil {

    public static final String pattern = "yyyy-MM-dd HH:mm:ss";


    public static int compareTime(String time) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        Date tagetTime = sdf.parse(time);
        Date currentTime = new Date(getCurrentTime());

        Calendar tagetCalendar = Calendar.getInstance();
        Calendar currentCalendar = Calendar.getInstance();

        tagetCalendar.setTime(tagetTime);
        currentCalendar.setTime(currentTime);

        return currentCalendar.compareTo(tagetCalendar);

    }

    public static String getCurrentTime(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public static long getTimeDiff(String timeFirst, String timeSecond) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        long tFirst = sdf.parse(timeFirst).getTime();
        long sFirst = sdf.parse(timeSecond).getTime();
        return tFirst - sFirst;
    }

    public static long getCurrentTimeDiff(String tagetTime) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        long tTime = sdf.parse(tagetTime).getTime();
        return getCurrentTime() - tTime;
    }

    public static String getStringTime(String defaultTime, String pattern) {
        try {

            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            long time1 = sdf.parse(defaultTime).getTime();
            long tempTime = System.currentTimeMillis() - time1;
            int ss = (int) (tempTime / 1000);
            int mm = ss / 60;
            int hh = mm / 60;
            int dd = hh / 24;

            if (dd < 1) {
                if (hh > 0) {
                    return hh + "小时前";
                } else {
                    if (mm > 0) {
                        return mm + "分钟前";
                    } else {
                        return "刚刚";
                    }
                }
            } else if (dd == 1) {
                return "昨天 " + defaultTime.substring(defaultTime.lastIndexOf(" "), defaultTime.lastIndexOf(":")).trim();
            } else if (dd == 2) {
                return "前天 " + defaultTime.substring(defaultTime.lastIndexOf(" "), defaultTime.lastIndexOf(":")).trim();
            } else {
                return defaultTime.substring(defaultTime.indexOf("/") + 1, defaultTime.lastIndexOf(":")).replace("/", "月").replace(" ", "日 ").trim();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
