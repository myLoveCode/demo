package com.example.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
	 * 日期格式化
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateFormat(String date) {
		if(null == date || "null".equals(date.trim())) {
			return null;
		}
		try {
			return new SimpleDateFormat("dd/MM/yyyy").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date getFirstDayOfWeek(String dateStr) {
		Calendar cal = Calendar.getInstance();

		try {
			cal.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(dateStr));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// System.out.println(cal.get(Calendar.DAY_OF_WEEK));
		int d = 0;
		if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
			d = 0;
		} else {
			d = 1 - cal.get(Calendar.DAY_OF_WEEK);
		}
		cal.add(Calendar.DAY_OF_WEEK, d);
		// 所在周开始日期
		// System.out.println(new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime()));
		// cal.add(Calendar.DAY_OF_WEEK, 6);
		// //所在周结束日期
		// System.out.println(new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime()));

		return cal.getTime();
	}

	public static void main(String[] args) {
		// LocalDate now = LocalDate.of(2017,12,11);
		// System.out.println(getFirstDayOfWeek(now));

		System.out.println(getFirstDayOfWeek("4/12/2017"));
	}

	public static LocalDate getFirstDayOfWeek(LocalDate now) {
		DayOfWeek i = now.getDayOfWeek();
		// System.out.println(i.getValue());

		LocalDate first = now.plusDays(0 - i.getValue());
		// System.out.println(first.getDayOfWeek());
		// System.out.println(first);
		return first;
	}
}
