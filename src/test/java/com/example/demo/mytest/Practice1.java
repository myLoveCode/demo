package com.example.demo.mytest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

class Product {
	private String name;
	private Long date;

	public Product(String name, Long date) {
		super();
		this.name = name;
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getDate() {
		return date;
	}
	public void setDate(Long date) {
		this.date = date;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Product [name=" + name + ", date=" + date + "]";
	}
	
	
}

public class Practice1 {
	public static void main(String... args) {
		final List<Product> list = new ArrayList<>();
		list.add(new Product("ABC", 1L));
		list.add(new Product("ABC", 1L));
		list.add(new Product("XYZ", 1L));
		list.add(new Product("ABC", 2L));

		Map<Long, Map<String, Long>> finalMap = list.stream()
				.collect(
						Collectors.groupingBy(
								Product::getDate,
								Collectors.collectingAndThen(
										Collectors.groupingBy(Product::getName, Collectors.counting()), 
										map -> {
											long sum = map.values().stream().reduce(0L, Long::sum);
											map.put("total", sum);
											return map;
										})
						)
				);
		
		System.out.println(finalMap);
		
		System.out.println("-----------------------------------------");
		
//		List<Product> people
//	              = list.stream().collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));

		List<Product> personList = new ArrayList<>();
        // 去重
		list.stream().forEach(
                p -> {
                	int i = personList.indexOf(p);//MMP，Excel解析出来是一个List<Map<>>，不是Model
                    if ( i < 0) {
                        personList.add(p);
                    }else {
                    	Long date = personList.get(i).getDate();
                    	personList.get(i).setDate(date+p.getDate());
                    }
                }
        );
        System.out.println(personList);		
		System.out.println("-----------------------------------------");
		
		
		
	}
}
