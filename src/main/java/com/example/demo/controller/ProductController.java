package com.example.demo.controller;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.example.core.exception.InvalidRequestException;
import com.example.core.mode.ResponseJson;
import com.example.core.utils.ImportUtil;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import com.example.demo.vo.ProductQuery;

@RestController
@RequestMapping(value="/api/")
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Resource
	private ProductService productService;

	@RequestMapping(value = "/product", method = RequestMethod.POST, consumes = "application/json")
	public ResponseJson<Product> add(@RequestBody Product product) {
		if (null == product || null == product.getDemandType() || null == product.getExternalRackName()
				|| null == product.getCluster() || null == product.getIpn() || null == product.getNeedByDate()) {
			throw new InvalidRequestException("需求类型，产品名称，机场编码，编号，到货日期不能为空");
		}

		logger.info("product:{}", JSON.toJSONString(product));
		Product person = productService.save(product);
		return ResponseJson.createResponse(person);
	}

	@RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
	public ResponseJson<Boolean> del(@PathVariable Long id) {
		logger.info("id:{}", id);
		productService.delete(id);
		return ResponseJson.createResponse(Boolean.TRUE);
	}

	@RequestMapping(value = "/product", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseJson<Product> edit(@RequestBody Product product) {
		if (null == product || null == product.getId() || null == product.getDemandType()
				|| null == product.getExternalRackName() || null == product.getCluster() || null == product.getIpn()
				|| null == product.getNeedByDate()) {
			throw new InvalidRequestException("需求类型，产品名称，机场编码，编号，到货日期不能为空");
		}

		logger.info("product:{}", JSON.toJSONString(product));
		Product person = productService.save(product);
		return ResponseJson.createResponse(person);
	}

	@RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
	public ResponseJson<Product> get(@PathVariable Long id) {
		logger.info("id:{}", id);
		Product person = productService.findOne(id);
		return ResponseJson.createResponse(person);
	}

	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public Object page(ProductQuery product,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "20") int rows) {
		logger.info("product:{}", JSON.toJSONString(product));
		// page = 0; //TODO:这就是坑货，没有打印sql的入参和返回数据。
		page = page - 1;
		Page<Product> person = productService.findByCriteria(page, rows, product);
		Map<String,Object> map = new HashMap<>();
		map.put("total", person.getTotalElements());
		map.put("rows", person.getContent());
		return map;
	}

	@RequestMapping(value = "/product/import", method = RequestMethod.POST)
	public ResponseJson<Map<Integer, String>> importStations(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> fileData = ImportUtil.upload(request);

		FileInputStream fis = (FileInputStream) fileData.get("fis");
		String fileName = (String) fileData.get("fileName");
		Map<Integer, String> errorMsg = productService.importProduct(fis, fileName);
		return ResponseJson.createResponse(errorMsg);
	}
	

}
