package com.example.core.utils;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanMapChangeUtil {
	private static final Logger logger = LoggerFactory.getLogger(BeanMapChangeUtil.class);

	/**
	 * map 转成 bean
	 * 
	 * @param map
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static <T> T toBean(Map map, Class<T> clazz) throws Exception {
		T bean = clazz.newInstance();
		try {
			for (Object object : map.keySet()) {
				String s = object + "";
				Field field = clazz.getDeclaredField(s);
				Class c = field.getType();
				if (field != null) {
					field.setAccessible(true);
					if (String.class.equals(c)) {
						field.set(bean, map.get(object));
					} else if (Integer.class.equals(c) || int.class == c) {
						field.set(bean, Integer.parseInt(map.get(object) + ""));
					} else if (Long.class.equals(c) || long.class == c) {
						field.set(bean, Long.parseLong(map.get(object) + ""));
					} else if (Double.class.equals(c) || double.class == c) {
						field.set(bean, Double.parseDouble(map.get(object) + ""));
					} else if (Float.class.equals(c) || float.class == c) {
						field.set(bean, Float.parseFloat(map.get(object) + ""));
					} else if (Short.class.equals(c) || short.class == c) {
						field.set(bean, Short.parseShort(map.get(object) + ""));
					} else if (Date.class.equals(c) ) {
						field.set(bean, DateUtil.getDateFormat(map.get(object) + ""));
					}
				}
			}
			return bean;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return bean;
	}

	/**
	 * 通过私有变量，javaBean转成map
	 * 
	 * @param domain
	 * @return
	 */
	public static Map<String, String> toMap(Object domain) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Class<?> clazz = Class.forName(domain.getClass().getName());
			Field[] fileds = clazz.getDeclaredFields(); // 得到catClass类所有的属性（包括私有属性）
			for (Field field : fileds) {
				// 取消java语言访问检查,允许获取私有变量
				field.setAccessible(true);
				// 获取变量的类型名称
				// String returnType = field.getType().getName();
				// 获取变量的名称
				String fieldName = field.getName();
				// 获取当前对象的对应字段的值
				Object value = field.get(domain);
				map.put(fieldName, value + "");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return map;
	}
}
