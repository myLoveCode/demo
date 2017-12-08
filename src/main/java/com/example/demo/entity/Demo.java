package com.example.demo.entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.alibaba.fastjson.JSON;

@Entity
public class Demo {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private Integer age;
	private String address;

	public static void main(String[] args) {
		LinkedHashMap<String, String> map = null;
		List<String> list = Arrays.asList("gao", "tian", "yue", "yue");
		map = list.stream().distinct().collect(Collectors.toMap(a -> null, Function.identity(), (a, b) -> {
			return a + b;
		}, LinkedHashMap::new));
		System.out.println(map);
		System.out.println("-------------");
		map.put(null, null);
		System.out.println(map);
	}

	public Demo() {
	}

	public Demo(Long id, String name, Integer age, String address) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}