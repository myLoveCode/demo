package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Product;

public interface ProductDao extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

	/**
	 * 根据customer + model + airportCode 查询工厂地址
	 * 
	 * @return location
	 */
	@Query(value = "SELECT p.location FROM person p where p.customer = :customer and p.externalRackName = :externalRackName "
			+ "and p.cluster = :cluster and p.location is not null limit 1", nativeQuery = true)
	String getLocation(@Param("customer") String customer, @Param("externalRackName") String externalRackName,
			@Param("cluster") String cluster);
	
	/**
	 * 迁移老数据到历史表中
	 * @param customer
	 */
	@Query(value=" INSERT INTO product_history (" + 
			"	region,cluster,cluster1,customer,date_stamp,delivery_window_start,demand_type,external_rack_name,ipn,location,need_by_date,need_by_date_old,quantity,shipping_region,sunday,test_rack,type,upload_time,vendor" + 
			" ) SELECT" + 
			"	region,cluster,cluster1,customer,date_stamp,delivery_window_start,demand_type,external_rack_name,ipn,location,need_by_date,need_by_date_old,quantity,shipping_region,sunday,test_rack,type,upload_time,vendor" + 
			" FROM" + 
			"	product" + 
			" WHERE" + 
			" 	customer = :customer",
			nativeQuery = true )
	void moveToProductHistory(@Param("customer") String customer);
	
	/**
	 * 删除老数据
	 * @param customer
	 */
	void deleteByCustomer(String customer);
	
	
	
}
