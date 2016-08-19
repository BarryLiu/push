package com.rgk.push.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kui.li on 2014/9/5.
 */
public class DateTools {

    public static final String FORSTR_YYYYHHMMHHMMSS="yyyy-MM-dd HH:mm:ss";
    public static final String FORSTR_YYYYHHMM="yyyy-MM-dd";
    public static final String FORSTR_MMDD="MMdd";
    public static final String FORSTR_YYYY="yyyy-01-01";

    /**
     * 格式化日期时间
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDateToString(Date date,String pattern) {
        SimpleDateFormat sFormat = new SimpleDateFormat(pattern);
        return sFormat.format(date);
    }

    /**
     * 字符串转日期
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date formatStringToDate(String dateStr, String pattern) {
        SimpleDateFormat sFormat = new SimpleDateFormat(pattern);
        try {
            return sFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            try {
                Date date = new Date();
                date.setTime(Long.parseLong(dateStr));
                return date;
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

}
