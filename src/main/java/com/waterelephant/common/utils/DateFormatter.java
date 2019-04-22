package com.waterelephant.common.utils;

/**
 * @program: cms
 * @Description:
 * @Date: 2019/3/14 14:28
 * @Author: 齐腾杰
 */
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter implements Formatter<Date>{


    @Override
    public String print(Date object, Locale locale) {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Date转String类型执行中。。。。");
        return dateFormat.format(object);
    }

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(text);
        } catch (Exception e) {
            format = new SimpleDateFormat("yyyy-MM-dd");
            date = format.parse(text);
        }
        return date;
    }
}