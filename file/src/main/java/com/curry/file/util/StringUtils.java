package com.curry.file.util;


import android.text.TextUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理类 atp 为基础
 */
public class StringUtils {

    private static final String TAG = "StringUtils";


    /**
     * 自动生成32位的UUid，对应数据库的主键id进行插入用。
     *
     * @return
     */
    public static String getRandomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static boolean isEmpty(String string) {
        return (string == null) || (string.trim().length() == 0);
    }

    private static boolean isMatch(String regex, String orginal) {
        if (orginal == null || orginal.trim().equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(orginal);
        return isNum.matches();
    }

    public static boolean isPositiveInteger(String orginal) {
        return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
    }

    public static boolean isNegativeInteger(String orginal) {
        return isMatch("^-[1-9]\\d*", orginal);
    }


    //下面三个方法都是判断是不是都是数字的，区别？？？？
    public static boolean isWholeNumber(String orginal) {
        return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isNegativeInteger(orginal);
    }

    public static boolean isDecimal(String orginal) {
        return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
    }

    /**
     * 判断字符串为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^-?[0-9]+");
        return pattern.matcher(str).matches();
    }


    /**
     * 字符串转double,(默认两位小数,pointRule 传null)
     */
    public static double getDobFromStr(String str, String pointRule) {
        if (isEmpty(str) || (!isDecimal(str) && !isWholeNumber(str))) {
            return 0.0;
        } else {
            if (isEmpty(pointRule)) {
                pointRule = "#.00";
            }
            String result = new DecimalFormat(pointRule).format(Double.valueOf(str));
            return Double.valueOf(result);
        }
    }


    //字符串转Int
    public static int getIntFromStr(String str) {
        if (isEmpty(str) || !isWholeNumber(str)) {
            return 0;
        } else {
            return Integer.valueOf(str);
        }
    }

    /**
     * 得到特定格式的当前时间戳
     * yyyy-MM-dd 的当前时间戳和 yyyy-MM 的当前时间戳 的大小是不同的
     *
     * @param sdf
     * @return
     */
    public static long getCurrentTimeStamp(SimpleDateFormat sdf) {
        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        if (sdf == null) {
            return timeInMillis;
        }
        String format = sdf.format(timeInMillis);
        Date date;
        try {
            date = sdf.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
            return timeInMillis;
        }
        return date.getTime();
    }

    /**
     * 得到特定格式时间的时间戳
     *
     * @param dateStr
     * @param sdf
     * @return
     */
    public static long getFormatTimeStamp(String dateStr, SimpleDateFormat sdf) {
        if (TextUtils.isEmpty(dateStr) || sdf == null) {
            return 0;
        }
        try {
            Date date = sdf.parse(dateStr);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将format1格式的时间转成format2格式的时间
     *
     * @param dateStr
     * @param sdf1
     * @param sdf2
     * @return
     */
    public static String formatToFormat(String dateStr, SimpleDateFormat sdf1, SimpleDateFormat sdf2) {
        if (TextUtils.isEmpty(dateStr)) {
            return "";
        }
        try {
            Date date = sdf1.parse(dateStr);
            return sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateStr;
        }
    }

    /**
     * 邮箱验证工具
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null) return false;
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher m = p.matcher(email);
        return m.find();
    }

    /**
     * 是否是合法的手机号 *
     * todo 有待检验
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobile(String mobiles) {
        if (isEmpty(mobiles))
            return false;

        Pattern p = Pattern.compile("^(13|15|17|18|14)\\d{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

}
