package com.example.demo.service;

import org.springframework.data.domain.Page;

import com.example.demo.entity.Product;
import com.example.demo.vo.ProductQuery;

public interface ProductService {

	/**
	 * 新增或修改
	 * 
	 * @param product
	 * @return
	 */
	Product save(Product product);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delete(Long id);

	/**
	 * 根据ID进行查询
	 * 
	 * @param id
	 * @return
	 */
	Product findOne(Long id);

	/**
	 * 根据查询条件进行分页查询
	 * 
	 * @param page
	 * @param size
	 * @param e
	 * @return
	 */
	Page<Product> findByCriteria(int page, int size, ProductQuery e);

}
