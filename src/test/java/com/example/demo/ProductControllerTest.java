package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.controller.ProductController;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductControllerTest {
	@Resource
	private ProductController productController;
	@Resource
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;

	private static Long id =0L;
	@Before
	public void setup() {
		// MockMvcBuilders使用构建MockMvc对象 （项目拦截器有效）
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		// 单个类 拦截器无效
		// mockMvc = MockMvcBuilders.standaloneSteup(userController).build();
	}

	@Test
	public void atestAdd() throws Exception {
		JSONObject param = new JSONObject();
		param.put("demandType", "demandType");
		param.put("externalRackName", "externalRackName");
		param.put("cluster", "cluster");
		param.put("ipn", "ipn");
		param.put("needByDate", new Date());

		MvcResult result = mockMvc
				.perform(post("/api/product").contentType(MediaType.APPLICATION_JSON).content(param.toJSONString()))
				.andExpect(status().isOk())// 模拟向testRest发送get请求
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
				.andReturn();// 返回执行请求的结果

		String contentAsString = result.getResponse().getContentAsString();
		JSONObject jsonObj = JSONObject.parseObject(contentAsString);
		if(jsonObj.containsKey("data")) {
			JSONObject data = jsonObj.getJSONObject("data");
			id = Long.parseLong(data.get("id")+"");
		}
		
		System.out.println(contentAsString);

	}

	@Test
	public void btestEdit() throws Exception {
		JSONObject param = new JSONObject();
		param.put("id", id);
		param.put("demandType", "demandType");
		param.put("externalRackName", "externalRackName");
		param.put("cluster", "cluster");
		param.put("ipn", "ipn");
		param.put("needByDate", new Date());

		MvcResult result = mockMvc
				.perform(put("/api/product").contentType(MediaType.APPLICATION_JSON).content(param.toJSONString()))
				.andExpect(status().isOk())// 模拟向testRest发送get请求
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
				.andReturn();// 返回执行请求的结果

		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	public void ctestGet() throws Exception {
		MvcResult result = mockMvc
				.perform( get("/api/product/"+id) )
				.andExpect(status().isOk())// 模拟向testRest发送get请求
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
				.andReturn();// 返回执行请求的结果

		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	public void dtestPage() throws Exception {
		MvcResult result = mockMvc
				.perform( get("/api/product" ) )
				.andExpect(status().isOk())// 模拟向testRest发送get请求
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
				.andReturn();// 返回执行请求的结果

		System.out.println(result.getResponse().getContentAsString());
	}

	// @Test
	// public void testImportStations() {
	//
	// }

	@Test
	public void ztestDel() throws Exception {
		MvcResult result = mockMvc
				.perform( delete("/api/product/"+id) )
				.andExpect(status().isOk())// 模拟向testRest发送get请求
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))// 预期返回值的媒体类型text/plain;charset=UTF-8
				.andReturn();// 返回执行请求的结果

		System.out.println(result.getResponse().getContentAsString());
	}
}
