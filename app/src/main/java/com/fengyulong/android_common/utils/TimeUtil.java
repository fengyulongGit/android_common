package com.fengyulong.android_common.utils;

import android.text.TextUtils;

import com.fengyulong.android_common.modal.Times;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    /**
     * 时间类型
     */
    private static String formatType = "yyyy-MM-dd HH:mm:ss";
    private static String formatType2 = "yyyy-MM-dd HH:mm";
    private static String formatType3 = "yyyy-MM-dd";
    private static String formatType4 = "yyyy-MM";
    private static String formatType5 = "MM";
    private static String formatType6 = "MM-dd";
    private static String formatType7 = "HH-mm";
    private static String formatType8 = "MM-dd HH:mm";
    private static String formatType9 = "HH";
    private static String formatType10 = "mm";

    /**
     * 获取当前时间，格式yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(formatType);
        String time = sdf.format(new Date());
        return time;
    }

    /**
     * 获取当前时间，格式yyyy-MM-dd
     *
     * @return
     */
    public static String getCurrentTime2() {
        SimpleDateFormat sdf = new SimpleDateFormat(formatType3);
        String time = sdf.format(new Date());
        return time;
    }

    /**
     * 将字符串时间转化为 上午，下午时间,时间格式为 yyyy-MM-dd HH:mm:ss
     *
     * @param time
     * @return
     */
    public static String formatDateTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat(formatType);
        if (time == null || "".equals(time)) {
            return "";
        }
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance(); //今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        // Calendar.HOUR12小时制  Calendar.HOUR_OF_DAY24小时制
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance(); // 昨天

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH,
                current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);
        current.add(Calendar.SECOND, 1);//增加1秒
        if (current.after(today)) {
            String tt = time.split(" ")[1];
            String s = tt.split(":")[0];
            String prix = "今天";
            if (s.startsWith("0")) {
                prix = "上午 ";
            } else {
                prix = "下午  ";
                if (s.endsWith("0") || s.endsWith("1") || s.endsWith("2")) {
                    prix = "上午  ";
                }
            }
            return prix + time.split(" ")[1];
        } else if (current.before(today) && current.after(yesterday)) {

            return "昨天  " + time.split(" ")[1];
        } else {
            // int index = time.indexOf("-")+1;
            // RETURN TIME.SUBSTRING(INDEX, TIME.LENGTH());
            return time;
        }
    }

    /**
     * 将字符串时间转化为 上午，下午时间,时间格式为 yyyy-MM-dd HH:mm
     *
     * @param time
     * @return
     */
    public static String formatDateTime2(String time) {
        SimpleDateFormat format = new SimpleDateFormat(formatType2);
        if (time == null || "".equals(time)) {
            return "";
        }
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance(); //今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        // Calendar.HOUR12小时制  Calendar.HOUR_OF_DAY24小时制
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance(); // 昨天

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH,
                current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);
        current.add(Calendar.SECOND, 1);//增加1秒
        if (current.after(today)) {
            String tt = time.split(" ")[1];
            String s = tt.split(":")[0];
            String prix = "今天";
            if (s.startsWith("0")) {
                prix = "上午 ";
            } else {
                prix = "下午  ";
                if (s.endsWith("0") || s.endsWith("1") || s.endsWith("2")) {
                    prix = "上午  ";
                }
            }
            return prix + time.split(" ")[1];
        } else if (current.before(today) && current.after(yesterday)) {

            return "昨天  " + time.split(" ")[1];
        } else {
            // int index = time.indexOf("-")+1;
            // RETURN TIME.SUBSTRING(INDEX, TIME.LENGTH());
            return time;
        }
    }

    /**
     * 获取指定时间戳的时间
     */
    public static String getTimeString(long time) {
        Date nowDate = new Date(time);
        SimpleDateFormat sdp = new SimpleDateFormat(formatType);
        return sdp.format(nowDate);
    }

    /**
     * 将字符日期转换为date类型
     *
     * @throws ParseException
     */
    public static Date formateDateFromString(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        date = sdf.parse(time);
        return date;
    }

    /**
     * 根据用户生日计算年龄
     */
    public static String getAgeByBirthday(Date birthday) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthday)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        }
        return String.valueOf(age);
    }

    /**
     * 将字符串转换为时间戳
     *
     * @throws ParseException
     */
    public static long formateLongFromString(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
        Date date = sdf.parse(time);
        long timestamp = date.getTime();
        return timestamp;
    }

    // date类型转换为String类型
    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data) {
        return new SimpleDateFormat(formatType).format(data);
    }

    // long类型转换为String类型
    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public static String longToString(long currentTime) {
        Date date = null;
        try {
            date = longToDate(currentTime, formatType);
        } catch (ParseException e) {
            e.printStackTrace();
        } // long类型转成Date类型
        String strTime = dateToString(date); // date类型转成String
        return strTime;
    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd//yyyy年MM月dd日
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate2(String strTime)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType3);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM//yyyy年MM月
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate4(String strTime)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType4);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    // long转换为Date类型
    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime); // 把String类型转换为Date类型
        return date;
    }

    // string类型转换为long类型
    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime)
            throws ParseException {
        Date date = stringToDate(strTime); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    // string类型转换为long类型
    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType3的时间格式必须相同
    public static long stringToLong2(String strTime) {

        try {
            Date date = stringToDate2(strTime); // String类型转成date类型
            if (date == null) {
                return 0;
            } else {
                long currentTime = dateToLong(date); // date类型转成long类型
                return currentTime;
            }

        } catch (Exception e) {

        }

        return 0;

    }

    // date类型转换为long类型
    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     * 计算倒计时 时间
     *
     * @param time
     * @return
     */
    public static Times dealTime(long time) {
        Times ctime = new Times();
        long day = time / (24 * 60 * 60);
        long hours = (time % (24 * 60 * 60)) / (60 * 60);
        long minutes = ((time % (24 * 60 * 60)) % (60 * 60)) / 60;
        long second = ((time % (24 * 60 * 60)) % (60 * 60)) % 60;

        ctime.setDay(day);
        ctime.setHours(hours);
        ctime.setMinutes(minutes);
        ctime.setSecond(second);

        return ctime;
    }


    /**
     * 比较两个年月是否相等
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return true 为结束时间在开始时间之后，false 为结束时间在开始时间之前
     */
    public static boolean compareTime(String startTime, String endTime) {

        if (ValidateUtil.isEmpty(startTime) || ValidateUtil.isEmpty(endTime)) {
            return true;
        }

        boolean success = false;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(startTime));
            c2.setTime(df.parse(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result = c1.compareTo(c2);
        if (result == 0) {
            success = false; // 同一时间
        } else // startTime在endTime之前
// startTime在endTime之后
            success = result < 0;

        return success;
    }

    /**
     * 比较两个年月是否相等
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return true 为相等，false 为不相等
     */
    public static boolean isEqualMonth(String startTime, String endTime) {

        if (ValidateUtil.isEmpty(startTime) || ValidateUtil.isEmpty(endTime)) {
            return false;
        }

        boolean success = false;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(startTime));
            c2.setTime(df.parse(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result = c1.compareTo(c2);
        // 同一时间
// startTime在endTime之后
        success = result == 0;

        return success;
    }

    /**
     * 比较两个年月日是否相等，格式为yyyy-MM-dd
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return true 为结束时间在开始时间之后，false 为结束时间在开始时间之前 或者 时间相等
     */
    public static boolean compareTimeByDate(String startTime, String endTime) {

        if (ValidateUtil.isEmpty(startTime) || ValidateUtil.isEmpty(endTime)) {
            return true;
        }

        boolean success = false;

        SimpleDateFormat df = new SimpleDateFormat(formatType3);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(startTime));
            c2.setTime(df.parse(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result = c1.compareTo(c2);
        if (result == 0) {
            success = false; // 同一时间
        } else // startTime在endTime之前
// startTime在endTime之后
            success = result < 0;

        return success;
    }

    /**
     * 比较两个年月日是否相等，格式为yyyy-MM-dd
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return true 为结束时间在开始时间之后 或者 两个日期相等，false 为结束时间在开始时间之前
     */
    public static boolean compareTimeByDate2(String startTime, String endTime) {

        if (ValidateUtil.isEmpty(startTime) || ValidateUtil.isEmpty(endTime)) {
            return true;
        }

        boolean success = false;

        SimpleDateFormat df = new SimpleDateFormat(formatType3);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(startTime));
            c2.setTime(df.parse(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result = c1.compareTo(c2);
        if (result == 0) {
            success = true; // 同一时间
        } else // startTime在endTime之前
// startTime在endTime之后
            success = result < 0;

        return success;
    }

    /**
     * 比较两个年月日是否相等，格式为yyyy-MM-dd
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return true 为两个日期相等，false为不相等
     */
    public static boolean dateIsEqual(String startTime, String endTime) {

        if (ValidateUtil.isEmpty(startTime) || ValidateUtil.isEmpty(endTime)) {
            return false;
        }

        boolean success = false;

        SimpleDateFormat df = new SimpleDateFormat(formatType3);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(startTime));
            c2.setTime(df.parse(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result = c1.compareTo(c2);
        // 同一时间
//
        success = result == 0;

        return success;
    }

    /**
     * @param dateTime 格式为"yyyy-mm-dd"的日期格式
     * @return string类型的下一天的日期
     * @throws ParseException
     */
    public static String getNextDate(String dateTime) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        long dif = 0;
        try {
            dif = df.parse(dateTime).getTime() + 86400 * 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date = new Date();
        date.setTime(dif);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(date);
    }

    /**
     * @param dateTime 格式为"yyyy-mm-dd"的日期格式
     * @return string类型的前i天的日期
     * @throws ParseException
     */
    public static String getFormerDate(String dateTime, int i) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        long dif = 0;
        try {
            dif = df.parse(dateTime).getTime() - i * 86400 * 1000;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Date date = new Date();
        date.setTime(dif);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(date);
    }

    /**
     * @param formatType 传入需要转换成为的日期格式 ，日期格式可以为yyyy-MM-dd HH:mm:ss或者 yyyy-MM-dd等
     * @return yyyy-MM-dd类型的日期
     */
    public static String transformDate(String time, String formatType) {

        if (ValidateUtil.isEmpty(time)) {
            return null;
        }

        SimpleDateFormat df = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = df.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return df.format(date);
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort(Date time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(time);
        return dateString;
    }

    /**
     * 获取时间 小时:分;秒 HH:mm:ss
     *
     * @return
     */
    public static String getTimeShort(Date time) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(time);
        return dateString;
    }

    /**
     * 判断当前时间是白天or 夜晚
     * 6：00--19：00为白天，其他时间为黑夜
     *
     * @return
     */
    public static boolean isNight() {

        Date d = new Date();
        int hour = d.getHours();

        return !(hour >= 6 && hour < 19);

    }

    /**
     * 特殊的日期格式
     *
     * @param date 传入日期格式 yyyy-MM-dd
     * @return 返回日期格式 dd/MM
     */
    public static String getMonthAndDate(String date) {
        if (ValidateUtil.isEmpty(date)) {
            return null;
        } else {
            String mm = date.split("-")[1];
            String dd = date.split("-")[2];

            String newDate = mm + "-" + dd;
            return newDate;
        }

    }

    /**
     * 只返回年月格式的日期 yyyy-MM
     *
     * @param date
     * @return
     */
    public static String getMonth(String date) {
        if (ValidateUtil.isEmpty(date)) {
            return null;
        } else {
            String mm = date.split("-")[1];
            String yy = date.split("-")[0];

            String newDate = yy + "-" + mm;
            return newDate;
        }
    }

    /**
     * 将时间格式为 yyyy-MM-dd HH:mm:ss
     * 转化为:yyyy-MM-dd hh:mm
     *
     * @param time
     * @return
     */
    public static String String2String(String time) {

        SimpleDateFormat sdf = new SimpleDateFormat(formatType2);

        if (TextUtils.isEmpty(time)) {
            return "";
        }
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String time2 = sdf.format(date);
        return time2;

    }

    /**
     * 将时间格式为 yyyy-MM-dd HH:mm:ss
     * 转化为:MM
     *
     * @param time
     * @return
     */
    public static String getOnlyMonth(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatType5);
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String onlyMonth = sdf.format(date);
        return onlyMonth;
    }

    /**
     * 将时间格式为 yyyy-MM-dd HH:mm:ss
     * 转化为:MM-dd
     *
     * @param time
     * @return
     */

    public static String getMonthAndDay(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatType6);
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String monthAndDay = sdf.format(date);
        return monthAndDay;
    }

    /**
     * 将时间格式为 yyyy-MM-dd HH:mm:ss
     * 转化为:HH-mm
     *
     * @param time
     * @return
     */

    public static String getHourAndMinute(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatType7);
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String hourAndMinute = sdf.format(date);
        return hourAndMinute;
    }

    /**
     * 该方法有误
     *
     * @param time
     * @return
     */
    public static String getMdHm(String time) {
        SimpleDateFormat sdfmd = new SimpleDateFormat(formatType6);
        SimpleDateFormat sdfhm = new SimpleDateFormat(formatType9);
        SimpleDateFormat sdfm = new SimpleDateFormat(formatType10);
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        Date dateOne = null;
        try {
            dateOne = sdfmd.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String befOne = sdfmd.format(dateOne);

        Date dateTwo = null;
        try {
            dateTwo = sdfhm.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String afTwo = sdfhm.format(dateTwo);
        Date dateThree = null;
        try {
            dateThree = sdfm.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String lastThree = sdfm.format(dateThree);

        return befOne + " " + afTwo + ":" + lastThree;
    }

}
