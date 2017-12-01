package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.demo.entity.Product;

public interface ProductDao extends JpaRepository<Product, Long>,JpaSpecificationExecutor<Product> {

	/**
	 * 根据customer + model + airportCode 查询工厂地址，
	 * TODO:这个方法名这么长。。。我也是醉了。   还不如直接写SQL 
	 * @return
	 */
	String findLocationTop1ByCustomerAndExternalRackNameAndclusterAndLocationNotNull();
}
