package com.javapandeng.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期格式化类
 */
public class DateUtil extends java.util.Date {
    private static final long serialVersionUID = 1L;

    private static final DateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final String strFormat1 = "yyyy-MM-dd HH:mm";

    /**
     M	年中的月份	Month	July; Jul; 07
     w	年中的周数	Number	27
     W	月份中的周数	Number	2
     D	年中的天数	Number	189
     d	月份中的天数	Number	10
     F	月份中的星期	Number	2
     E	星期中的天数	Text	Tuesday; Tue
     a	Am/pm 标记	Text	PM
     H	一天中的小时数（0-23）	Number	0
     k	一天中的小时数（1-24）	Number	24
     K	am/pm 中的小时数（0-11）	Number	0
     h	am/pm 中的小时数（1-12）	Number	12
     m	小时中的分钟数	Number	30
     s	分钟中的秒数	Number	55
     S	毫秒数	Number	978
     z	时区	General time zone	Pacific Standard Time; PST; GMT-08:00
     Z	时区	RFC 822 time zone	-0800
     */

    /**
     * 构造函数
     */
    public DateUtil() {
        super(getSystemDate().getTime().getTime());
    }

    /**
     * 当前时间
     *
     * @return 时间Timestamp
     */
    public java.sql.Timestamp parseTime() {
        return new java.sql.Timestamp(this.getTime());
    }

    /**
     * 将Date类型转换为字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param Date
     * @return String
     */
    public static String format(Date date) {
        return format(date, null);
    }

    /**
     * 将Date类型转换为字符串
     *
     * @param Date
     * @param pattern 字符串格式
     * @return String
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return "";

        } else if (pattern == null || pattern.equals("") || pattern.equals("null")) {
            return datetimeFormat.format(date);

        } else {
            return new SimpleDateFormat(pattern).format(date);
        }
    }

    /**
     * 将Date类型转换为字符串 yyyy-MM-dd
     *
     * @param Date
     * @return String
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        return dateFormat.format(date);
    }

    /**
     * 将字符串转换为Date类型
     *
     * @param sDate yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date convert(String sDate) {

        try {
            if (sDate != null) {
                if (sDate.length() == 10) {
                    return dateFormat.parse(sDate);
                } else if (sDate.length() == 19) {
                    return datetimeFormat.parse(sDate);
                }
            }
        } catch (ParseException pe) {
        }
        return convert(sDate, null);
    }

    /**
     * 将字符串转换为Date类型
     *
     * @param sDate
     * @param pattern 格式
     * @return
     */
    public static Date convert(String sDate, String pattern) {
        Date date = null;
        try {
            if (sDate == null || sDate.equals("") || sDate.equals("null")) {
                return null;

            } else if (pattern == null || pattern.equals("") || pattern.equals("null")) {
                return datetimeFormat.parse(sDate);

            } else {
                return new SimpleDateFormat(pattern).parse(sDate);
            }
        } catch (ParseException pe) {
        }
        return date;
    }

    /**
     * String转换为Date
     *
     * @param sDate 日期"yyyy-MM-dd"
     * @return 日期Date
     */
    public static Date convertDate(String dateStr) {
        return convert(dateStr, "yyyy-MM-dd");
    }

    /**
     * String转换为Timestamp
     *
     * @param sDate 日期 "yyyy-MM-dd" / "yyyy-MM-dd HH:mm:ss"
     * @return 日期Timestamp
     */
    public static Timestamp convertTimestamp(String sDate) {
        if (sDate.length() == 10) {
            sDate = sDate + " 00:00:00";
        }
        if (sDate.length() == 16) {
            sDate = sDate + ":00";
        }
        return Timestamp.valueOf(sDate);
    }

    /**
     * Date转换为Timestamp
     */
    public static Timestamp convert(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * 取当前日期(yyyy-mm-dd)
     *
     * @return 时间Timestamp
     */
    public static String getTodayDate() {
        return formatDate(new Date());
    }

    /**
     * 取当前日期(yyyy-mm-dd hh:mm:ss)
     *
     * @return 时间Timestamp
     */
    public static String getTodayDateTime() {
        return format(new Date());
    }

    /**
     * 取得n分钟后的时间
     *
     * @param date 日期
     * @param afterMins
     * @return 时间Timestamp
     */
    public static Date getAfterMinute(Date date, long afterMins) {
        if (date == null)
            date = new Date();

        long longTime = date.getTime() + afterMins * 60 * 1000;

        return new Date(longTime);
    }

    
    /**
     * add by yinshengming start 2016-4-7
     * 取得n秒后的时间
     *
     * @param date 日期
     * @param afterMins
     * @return 时间Timestamp
     */
    public static Date getAfterSecond(Date date, long afterMins) {
        if (date == null)
            date = new Date();

        long longTime = date.getTime() + afterMins * 1000;

        return new Date(longTime);
    }
    // public static void main(String[] arg) {
    // System.err.println(format((new Date())));
    // System.err.println(format(getAfterMinute(new Date(), 3)));
    // }

    /**
     * 取得指定日期几天后的日期
     *
     * @param date 日期
     * @param afterDays 天数
     * @return 日期
     */
    public static Date getAfterDay(Date date, int afterDays) {
        if (date == null)
            date = new Date();

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(java.util.Calendar.DATE, afterDays);
        return cal.getTime();
    }

    /**
     * 取得指定日期几天后的日期
     *
     * @param sDate 日期 yyyy-MM-dd
     * @param afterDays 天数
     * @return 日期
     */
    public static String getAfterDay(String sDate, int afterDays) {
        Date date = convertDate(sDate);

        date = getAfterDay(date, afterDays);
        return formatDate(date);
    }

    /**
     * 取得指定日期几天前的日期
     *
     * @param date 日期
     * @param beforeDays 天数(大于0)
     * @return 日期
     */
    public static Date getBeforeDay(Date date, int beforeDays) {
        if (date == null)
            date = new Date();

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(java.util.Calendar.DATE, 0 - Math.abs(beforeDays));
        return cal.getTime();
    }

    /**
     * 取得指定日期几天前的日期
     *
     * @param sDate 日期 yyyy-MM-dd
     * @param beforeDays 天数(大于0)
     * @return 日期
     */
    public static String getBeforeDay(String sDate, int beforeDays) {
        Date date = convertDate(sDate);

        date = getBeforeDay(date, beforeDays);
        return formatDate(date);
    }

    /**
     * 获得几个月后的日期
     *
     * @param date 日期
     * @param afterMonth 月数
     * @return 日期Date
     */
    public static Date getAfterMonth(Date date, int afterMonth) {
        if (date == null)
            date = new Date();

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        cal.add(java.util.Calendar.MONTH, afterMonth);
        return cal.getTime();
    }

    /**
     * 获得几个月后的日期
     *
     * @param sDate 日期
     * @param afterMonth 月数
     * @return 日期"yyyy-MM-dd"
     */
    public static String getAfterMonth(String sDate, int afterMonth) {
        Date date = convertDate(sDate);

        date = getAfterMonth(date, afterMonth);
        return formatDate(date);
    }

    /**
     * 获得几年后的日期
     *
     * @param date 日期
     * @param afterYear 年数
     * @return 日期Date
     */
    public static Date getAfterYear(Date date, int afterYear) {
        if (date == null)
            date = new Date();

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        cal.add(java.util.Calendar.YEAR, afterYear);
        return cal.getTime();
    }

    /**
     * 获得几年后的日期
     *
     * @param date 日期
     * @param afterYear 年数
     * @return 日期Date
     */
    public static String getAfterYear(String sDate, int afterYear) {
        Date date = convertDate(sDate);

        date = getAfterYear(date, afterYear);
        return formatDate(date);
    }

    /**
     * 取得月份第一天日期
     *
     * @param sDate(yyyy-mm-dd) : 如为Null，默认取当前系统时间
     * @return yyyy-mm-dd
     */
    public static String getMonthFirstDay(String sDate) {
        Date date = null;
        if (sDate != null && sDate.length() > 0) {
            date = convertDate(sDate);
        }
        Calendar gc = Calendar.getInstance();
        if (date != null) {
            gc.setTime(date);
        }
        gc.set(Calendar.DATE, 1);

        return formatDate(gc.getTime());
    }

    /**
     * 取得月份最后一天日期
     *
     * @param sDate(yyyy-mm-dd) : 如为Null，默认取当前系统时间
     * @return yyyy-mm-dd
     */
    public static String getMonthLastDay(String sDate) {
        Date date = null;

        if (sDate != null && sDate.length() > 0) {
            date = convertDate(sDate);
        }
        Calendar gc = Calendar.getInstance();
        if (date != null) {
            gc.setTime(date);
        }
        gc.add(Calendar.MONTH, 1);
        gc.add(Calendar.DATE, -gc.get(Calendar.DAY_OF_MONTH));

        return formatDate(gc.getTime());
    }

    /**
     * 取得日期，星期几
     *
     * @param dateTime : dateTime
     * @return "星期日","星期一","星期二","星期三","星期四","星期五","星期六"
     */
    public static String getWeekDayName(String dateTime) throws ParseException {

        String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Date d = convertDate(dateTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        String week_day_name = dayNames[day - 1];
        return week_day_name;
    }

    /**
     * 传入的日期格式是否是yyyy-MM-dd
     *
     * @param in 被检查的日期
     * @return 是指定的日期格式:true,否则:false
     */
    public static boolean isFormatDate(String in) {
        return isFormatDate(in, "yyyy-MM-dd");
    }

    /**
     * 传入的日期格式是否是指定的日期格式
     *
     * @param in 被检查的日期
     * @param format 指定的日期格式
     * @return 是指定的日期格式:true,否则:false
     */
    private static boolean isFormatDate(String in, String format) {
        if (format != null & format.length() > 0) {
            return false;
        }

        final SimpleDateFormat sdFormat = new SimpleDateFormat(format);
        Date dateCompare = null;
        String sDate = "";

        try {
            dateCompare = sdFormat.parse(in, new ParsePosition(0));
            sDate = sdFormat.format(dateCompare);
        } catch (final Exception e) {
            return false;
        }

        if (dateCompare == null) {
            return false;
        } else {
            return sDate.equals(in);
        }
    }

    /**
     * 比较两个日期先后
     *
     * @param date1 : yyyy-MM-dd
     * @param date1 : yyyy-MM-dd
     * @return date1 > date2 : true， else : false
     */
    public static boolean compareDate(String date1, String date2) {
        if (isFormatDate(date1, "yyyy-MM-dd") && isFormatDate(date2, "yyyy-MM-dd")) {
            if (date1.compareTo(date2) > 0) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * 取当前系统时间
     *
     * @return 时间Calendar
     */
    public static Calendar getSystemDate() {
        return Calendar.getInstance();
    }

    /**
     * 转换类型
     *
     * @param sDate 日期"yyyy-MM-dd HH:mm"
     * @return 日期Date
     * @throws Exception
     */
    public static java.util.Date convertDate1(String sDate) throws Exception {
        SimpleDateFormat sFormat1 = new SimpleDateFormat(strFormat1);
        return sFormat1.parse(sDate);
    }

    /**
     * 取得输入时间n分钟前的时间
     *
     * @param date 日期
     * @param lminute
     * @return 时间Timestamp
     */
    public static Timestamp gettimebefore(java.util.Date date, long lminute) {

        long lngTime = date.getTime() - lminute * 60 * 1000;

        return new Timestamp(lngTime);

    }

    /**
     * 取得日期，星期几
     *
     * @param dateTime
     *            : dateTime
     * @return "周日","周一","周二","周三","周四","周五","周六"
     */
    public static String getWeekDays(Date dateTime){
        String dayNames[] = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };

        int day = getWeekDay(dateTime);
        String week_day_name = dayNames[day - 1];
        return week_day_name;
    }
    /**
     * 取得日期，星期几
     *
     * @param dateTime
     *            : dateTime
     * @return "周日","周一","周二","周三","周四","周五","周六"
     */
    public static String getWeekOfDate(Date dateTime){
        String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

        int day = getWeekDay(dateTime);
        String week_day_name = dayNames[day - 1];
        return week_day_name;
    }

    /**
     * 取得日期是星期的第几天
     *
     * @param dateTime
     *            : dateTime
     * @return :1： "星期日"，2："星期一"， 3："星期二"，,4："星期三", 5："星期四", 6："星期五", 7："星期六"
     */
    public static int getWeekDay(Date dateTime) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime);
        return cal.get(Calendar.DAY_OF_WEEK);
    }
    /**
     * 求2个日期之间的天数
     *
     * @param StartDay 开始日期
     * @param endDay 结束日期
     * @param IncludeEndFlag 是否把结束当天算在内,Y:是,N:否
     * @return
     * @throws Exception
     */
    public static long DayCountBetweenDays(String StartDay, String endDay,
                                           String IncludeEndFlag) throws Exception {
        long DAY = 24L * 60L * 60L * 1000L;
        long countBetweenDay = 0;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = df.parse(StartDay);
        Date d2 = df.parse(endDay);
        countBetweenDay = (d2.getTime() - d1.getTime()) / DAY;
        if (IncludeEndFlag == "Y") {
            countBetweenDay++;
        }
        return countBetweenDay;

    }
}
