package com.laoxin.core.utils;

import com.laoxin.core.enums.ResultStateEnum;
import com.laoxin.core.exception.ServerException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
    private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DateUtil.class);

    public static final String DAY = "yyyy-MM-dd";
    public static final String DAY2 = "yyyy/M/dd";
    public static final String DAY3 = "yyyyMMdd";
    public static final String DAY4 = "yyyy/M/d";
    public static final String TIME = "HH:mm:ss";
    public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME2 = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String DATETIME3 = "yyyyMMddHHmmss";
    public static final String DATETIME4 = "MM月dd日HH:mm";
    public static final String MONTH = "yyyy-MM";
    public static final String MONTH2 = "yyyyMM";
    public static final String MIN = "yyyy-MM-dd HH:mm";
    public static final String DATE = "MM月dd日";
    public static final String CN_YMD = "yyyy年MM月dd日";

    public static final LocalDateTime LOCAL_DATE_TIME_MIN = toLocalDateTime(new Date(0L));

    public static int getMonth(LocalDateTime localDateTime){
        String format = DateTimeFormatter.ofPattern(MONTH2).format(localDateTime);
        int result = Integer.parseInt(format);
        return result;
    }
    public static int getMonth(LocalDate localDate){
        String format = DateTimeFormatter.ofPattern(MONTH2).format(localDate);
        int result = Integer.parseInt(format);
        return result;
    }


    public static Date toDate(LocalDateTime localDateTime){
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDateTime toLocalDateTime(String time,String format) {
        return LocalDateTime.parse(time,DateTimeFormatter.ofPattern(format));
    }

    public static LocalDate toLocalDate(String time, String format) {
        return LocalDate.parse(time,DateTimeFormatter.ofPattern(format));
    }

    public static LocalDateTime toLocalDateTime(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static long toTimestamp(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    public static LocalDateTime parse(String timeStr,String format){
        try {
            return LocalDateTime.parse(timeStr, DateTimeFormatter.ofPattern(format));
        }catch (Exception e){
            throw new ServerException(ResultStateEnum.TIME_FORMAT_ERROR,timeStr,format);
        }
    }

    public static boolean isParse(String str, String sdf) {
        try {
            parse(str, sdf);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static Date parse(String str) throws ParseException {
        if (null == str || str == "") {
            return null;
        }
        return new SimpleDateFormat(DATETIME).parse(str);
    }

    public static String format(LocalDateTime localDateTime,String format){

        return DateTimeFormatter.ofPattern(format).format(localDateTime);
    }

}
