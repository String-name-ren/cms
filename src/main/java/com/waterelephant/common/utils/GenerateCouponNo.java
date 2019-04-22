package com.waterelephant.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 生成优惠券编号
 * @author tianhu
 *
 */
public class GenerateCouponNo {
	
	public static String getNextNo(Integer i) {
		String s = "00000";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		date = date.replaceAll("-", "");
		Integer n = i+1;
		s = (s + (n+""));
		return date + s.substring(s.length()-5);
	}
	
	public static String getDateStringByDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateS = sdf.format(date);
		return dateS;
	}
	
	
	public static void main(String[] args) {
		System.out.println(getNextNo(5));
		
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.HOUR, 2);
		Date date=calendar.getTime();
		System.out.println(getDateStringByDate(date));
		System.out.println(getDateStringByDate(new Date()));
	}
}
