//package com.example.config;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DateConverter implements Converter<String, LocalDate> {
//	//http://blog.csdn.net/qq_31871785/article/details/72863289
//	
//	@Override
//	public LocalDate convert(String source) {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		return LocalDate.parse(source, formatter);
//		
////		try {
////			// 1. 定义日期格式
////			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
////			// 2. 解析日期
////			Date date = format.parse(source);
////			return date;
////		} catch (ParseException e) {
////			e.printStackTrace();
////		}
////		return null;
//	}
//
//}
