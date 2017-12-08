package com.example.demo;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import com.example.demo.vo.ProductQuery;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductServiceTest {

	@Resource
	private ProductService productService;

	private static Long id = 0L;

	@Test
	public void atestSave() {
		Product product = new Product();
		product.setCustomer("单元测试customer");
		product.setUploadTime(new Date());
		product.setDemandType("单元测试demandType");
		product.setExternalRackName("单元测试model");
		product.setCluster("单元测试cluster");
		// ...
		Product saveProduct = productService.save(product);
		id = saveProduct.getId();
		Assert.notNull(saveProduct, "保存失败");

	}

	@Test
	public void btestFindOne() {
		Product product = productService.findOne(id);
		Assert.notNull(product, "数据不存在");
	}

	@Test
	public void ctestFindByCriteria() {
		ProductQuery conditon = new ProductQuery();
		conditon.setCustomer("单元测试customer");
		Page<Product> productPage = productService.findByCriteria(1, 1, conditon);
		Assert.isTrue(productPage.getTotalElements() > 0, "数据不存在");
	}

	@Test
	public void ztestDelete() {
		productService.delete(id);
	}

}
