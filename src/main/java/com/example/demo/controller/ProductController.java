package com.example.demo.controller;

import java.io.FileInputStream;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.example.core.mode.ResponseJson;
import com.example.core.utils.ImportUtil;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import com.example.demo.vo.ProductQuery;

@Controller
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Resource
	private ProductService productService;

	@RequestMapping(value = "/product", method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson<Product> add(Product product) {
		if (null == product || null == product.getDemandType() || null == product.getExternalRackName()
				|| null == product.getCluster() || null == product.getIpn() || null == product.getNeedByDate()) { 

			return null;
			// throw new Exception("非法参数"); //TODO:cq...Excption
		}

		logger.info("product:{}", JSON.toJSONString(product));
		Product person = productService.save(product);
		return ResponseJson.createResponse(person);
	}

	@RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseJson<Boolean> del(@PathVariable Long id) {
		logger.info("id:{}", id);
		productService.delete(id);
		
		return ResponseJson.createResponse(Boolean.TRUE);
	}
	
	@RequestMapping(value = "/product", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseJson<Product> edit(Product product) {
		if (null == product || null == product.getId() || null == product.getDemandType() || null == product.getExternalRackName()
				|| null == product.getCluster() || null == product.getIpn() || null == product.getNeedByDate()) {

			return null;
			// throw new Exception("非法参数"); //TODO:cq...Excption
		}

		logger.info("product:{}", JSON.toJSONString(product));
		Product person = productService.save(product);
		return ResponseJson.createResponse(person);
	}

	@RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseJson<Product> get(@PathVariable Long id) {
		logger.info("id:{}",id);
		Product person = productService.findOne(id);
		return ResponseJson.createResponse(person);
	}
	
	@RequestMapping(value = "/product", method = RequestMethod.GET)
	@ResponseBody
	public ResponseJson<Page<Product>> page(ProductQuery product, @RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="size", defaultValue="20") int size) {
		logger.info("product:{}", JSON.toJSONString(product));
		page = 0; //TODO:这就是坑货，没有打印sql的入参和返回数据。  还没测试翻页
		Page<Product> person = productService.findByCriteria(page, size, product);
		return ResponseJson.createResponse(person);
	}
	
	@RequestMapping(value = "/product/import", method = RequestMethod.POST)
	@ResponseBody
	public ResponseJson<Boolean> importStations(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> fileData = ImportUtil.upload(request);

		FileInputStream fis = (FileInputStream) fileData.get("fis");
		String fileName = (String) fileData.get("fileName");
		productService.importProduct(fis, fileName);
		return ResponseJson.createResponse(true);
	}
	
	
}
