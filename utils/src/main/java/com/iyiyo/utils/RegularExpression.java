package com.iyiyo.utils;

/**
 * 正则表达式
 * Created by liu-feng on 2016/7/6.
 * 邮箱:w710989327@foxmail.com
 */
public class RegularExpression {

    public static boolean isEmail(String arg) {
        return arg.matches("\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}");
    }

    public static boolean isChinese(String arg) {
        return arg.matches("[\\u4e00-\\u9fa5]");
    }

    /**
     * 匹配时间    时：分：秒
     */
    public static boolean isTimeFormat(String arg) {
        return arg.matches("([01]?\\d|2[0-3]):[0-5]?\\d:[0-5]?\\d");
    }

    /**
     * 匹配IP
     */
    public static boolean isIPv4(String arg) {
        return arg.matches("(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)");
    }

    /**
     * 匹配身份证
     */
    public static boolean isIDcard(String arg) {
        return arg.matches("\\d{15}|\\d{17}[0-9Xx]");
    }

    /**
     * 匹配日期（年-月-日）
     */
    public static boolean isDateFormat_(String arg) {
        return arg.matches("((((1[6-9]|[2-9]\\d)\\d{2})-(1[02]|0?[13578])-([12]\\d|3[01]|0?[1-9]))|(((1[6-9]|[2-9]\\d)\\d{2})-(1[012]|0?[13456789])-([12]\\d|30|0?[1-9]))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(1\\d|2[0-8]|0?[1-9]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))");
    }

    /**
     * 匹配日期（年/月/日）
     */
    public static boolean isDateFormat(String arg) {
        return arg.matches("((((1[6-9]|[2-9]\\d)\\d{2})/(1[02]|0?[13578])/([12]\\d|3[01]|0?[1-9]))|(((1[6-9]|[2-9]\\d)\\d{2})/(1[012]|0?[13456789])/([12]\\d|30|0?[1-9]))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(1\\d|2[0-8]|0?[1-9]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))");
    }

    /**
     * 匹配数字
     */
    public static boolean isNumber(String arg) {
        return arg.matches("[1-9]\\d*");
    }

    /**
     * 匹配HTML标记
     */
    public static boolean isHTMLTag(String arg) {
        return arg.matches("<(S*?)[^>]*>.*?|<.*? />");
    }

    /**
     * 匹配QQ号码
     */
    public static boolean isQQNumber(String arg) {
        return arg.matches("[1-9][0-9]{4,}");
    }

    /**
     * 匹配邮政编码
     */
    public static boolean isPostalCode(String arg) {
        return arg.matches("[1-9]d{5}(?!d)");
    }

    /**
     * 匹配URL地址
     */
    public static boolean isURLAddress(String arg) {
        return arg.matches("((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?");
    }

}
