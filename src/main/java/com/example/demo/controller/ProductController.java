package com.example.demo.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;

@Controller
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Resource
	private ProductService productService;

	@RequestMapping(value = "/product", method = RequestMethod.POST)
	@ResponseBody
	public Product add(Product product) {
		if (null == product || null == product.getDemandType() || null == product.getExternalRackName()
				|| null == product.getCluster() || null == product.getIpn() || null == product.getNeedByDate()) {

			return null;
			// throw new Exception("非法参数"); //TODO:cq...Excption
		}

		logger.info("product:{}", product);
		Product person = productService.save(product);
		return person;
	}

	@RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void del(@PathVariable Long id) {
		logger.info("id:{}", id);
		productService.delete(id);
		
//		return person;
	}
	
	@RequestMapping(value = "/product", method = RequestMethod.PUT)
	@ResponseBody
	public Product edit(Product product) {
		if (null == product || null == product.getId() || null == product.getDemandType() || null == product.getExternalRackName()
				|| null == product.getCluster() || null == product.getIpn() || null == product.getNeedByDate()) {

			return null;
			// throw new Exception("非法参数"); //TODO:cq...Excption
		}

		logger.info("product:{}", product);
		Product person = productService.save(product);
		return person;
	}

	@RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Product get(@PathVariable Long id) {
		logger.info("id:{}",id);
		Product person = productService.findOne(id);
		return person;
	}
	
	/**
	 * TODO:cq...条件,分页
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/product", method = RequestMethod.GET)
	@ResponseBody
	public Product page(@PathVariable Long id) {
		logger.info("id:{}",id);
		Product person = productService.findOne(id);
		return person;
	}
	
	
	
}
