package com.clouddo.commons.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author zhongming
 * @since 3.0
 * 2018/7/8下午1:53
 */
public class DateUtil {

    /**
     * 以小时作为事件间隔
     */
    public static final String INTERVAL_TYPE_HOUR = "hour";

    /**
     * 格式化日期
     * @param dateStr String 字符型日期
     * @param format String 格式
     * @return Date 日期
     */
    public static Date parseDate(String dateStr, String format) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(dateStr);
    }

    /**
     * 时间格式化
     * @param date 需要格式化的时间
     * @param formatValue 格式化
     * @return 格式化后的时间字符串
     */
    public static String formatTime(Date date, String formatValue) {
        List<Date> dateList = new ArrayList<Date>();
        dateList.add(date);
        return batchFormatTime(dateList, formatValue).get(0);
    }

    /**
     * 批量格式化格式
     * @param dateList 时间列表
     * @param formatValue 格式化
     * @return 格式化后的时间字符串集合
     */
    public static List<String> batchFormatTime(List<Date> dateList, String formatValue) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatValue);
        List<String> result = new ArrayList<String>();
        for(Date date : dateList) {
            result.add(simpleDateFormat.format(date));
        }
        return result;
    }

    /**
     * 生成时间间隔
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param intervalNumber 间隔数
     * @return
     */
    public static List<Date> createHourInterval(Date startTime, Date endTime, Integer intervalNumber) {
        List<Date> result = new ArrayList<Date>();
        //获取第一个时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        while (calendar.getTime().before(endTime)) {
            result.add(calendar.getTime());
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + intervalNumber);

        }
        endTime = convertDateHour(endTime);
        if(result.size() > 0 && result.get(result.size() - 1).before(endTime)) {
            result.add(endTime);
        }
        return result;
    }

    public static List<Date> createDateInterval(Date startTime, Date endTime, Integer intervalNumber, String type) {
        if(INTERVAL_TYPE_HOUR.equals(type)) {
            //小时时间间隔
            return createHourInterval(startTime, endTime, intervalNumber);
        }
        return null;
    }

    /**
     * 转换时间
     * 从小时之后全为0
     * @param date
     * @return
     */
    private static Date convertDateHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
