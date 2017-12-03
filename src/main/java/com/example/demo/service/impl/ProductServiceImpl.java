package com.example.demo.service.impl;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.core.exception.BusinessException;
import com.example.core.utils.ImportExcelUtil;
import com.example.demo.dao.ProductDao;
import com.example.demo.entity.ExcelTableFieldMapping;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import com.example.demo.vo.ProductQuery;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Resource
	private ProductDao productDao;

	@Override
	@Transactional(readOnly = false)
	public Product save(Product product) {
		return productDao.save(product);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		productDao.delete(id);
	}

	@Override
	@Transactional(readOnly = false)
	public Product findOne(Long id) {
		return productDao.findOne(id);
	}

	@Override
	public Page<Product> findByCriteria(int page, int size, ProductQuery conditon) {
		logger.info("page:{},size:{},ProductQuery:{}", page, size, conditon);
		Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "id");
		Page<Product> bookPage = productDao.findAll(new Specification<Product>() {
			@Override
			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (null != conditon.getCustomer() && !"".equals(conditon.getCustomer())) {
					list.add(criteriaBuilder.equal(root.get("customer").as(String.class), conditon.getCustomer())); // TODO:如果是模糊查询呢？
				}
				if (null != conditon.getDemandType() && !"".equals(conditon.getDemandType())) {
					list.add(criteriaBuilder.equal(root.get("demandType").as(String.class), conditon.getDemandType()));
				}
				if (null != conditon.getExternalRackName() && !"".equals(conditon.getExternalRackName())) {
					list.add(criteriaBuilder.equal(root.get("externalRackName").as(String.class),
							conditon.getExternalRackName()));
				}
				Predicate[] p = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(p));
			}
		}, pageable);
		return bookPage;
	}

	@Override
	@Transactional(readOnly = false)
	public void importProduct(FileInputStream fis, String fileName) {
		// TODO:cq...0.查询该客户的所有Excel自定义列头对应的表字段
		List<ExcelTableFieldMapping> excelTableFieldMappings = new ArrayList<>();
		// ... List<ExcelTableFieldMapping> 2 map 传到 parseExcel方法中map
		Map<String, String> parseExcelMappin = excelTableFieldMappings.stream()
				.collect(Collectors.toMap(ExcelTableFieldMapping::getExcelTile, ExcelTableFieldMapping::getTableField));
		
		Map<String, ExcelTableFieldMapping> validateExcelMapping = excelTableFieldMappings.stream()
				.filter(e->e.getFieldNotNull())
				.collect(Collectors.toMap(ExcelTableFieldMapping::getTableField, k->(k)));

		// 1.文件解析
		List<Map<String, Object>> excelDataList = null;
		try {
			excelDataList = ImportExcelUtil.parseExcel(fis, fileName, parseExcelMappin);
		} catch (Exception e) {
			logger.error("文件上传成功，但是文件解析失败");
			logger.error(e.getMessage(), e);
			throw new BusinessException("文件解析失败");
		}

		// 2.文件校验
		// 哪些字段是不能为空 等
//		excelData.forEach(item -> {
//			if (validateExcelMapping.get("demand_type").getFieldNotNull() && StringUtils.isEmpty(item.get("demand_type"))) {
//				String warningMsg = "第"+ 1 +"行，" + validateExcelMapping.get("demand_type").getExcelTile() +"不能为空";
//				//这里如何获取索引呢？第1，2，3，行
//			}
//		});
		
		String excelErrorMsg = null;
		for(int i=1; i<= excelDataList.size(); i++) {
			String lineWarningMsg = null;
			if (validateExcelMapping.get("demand_type").getFieldNotNull() && StringUtils.isEmpty(excelDataList.get(i).get("demand_type"))) {
				lineWarningMsg = lineWarningMsg + validateExcelMapping.get("demand_type").getExcelTile() +"不能为空，";
			}
			if (validateExcelMapping.get("external_rack_name").getFieldNotNull() && StringUtils.isEmpty(excelDataList.get(i).get("external_rack_name"))) {
				lineWarningMsg = lineWarningMsg + validateExcelMapping.get("external_rack_name").getExcelTile() +"不能为空，";
			}
			if (validateExcelMapping.get("cluster").getFieldNotNull() && StringUtils.isEmpty(excelDataList.get(i).get("cluster"))) {
				lineWarningMsg = lineWarningMsg + validateExcelMapping.get("cluster").getExcelTile() +"不能为空，";
			}
			if (validateExcelMapping.get("ipn").getFieldNotNull() && StringUtils.isEmpty(excelDataList.get(i).get("ipn"))) {
				lineWarningMsg = lineWarningMsg + validateExcelMapping.get("ipn").getExcelTile() +"不能为空，";
			}
			if (validateExcelMapping.get("need_by_date").getFieldNotNull() && StringUtils.isEmpty(excelDataList.get(i).get("need_by_date"))) {
				lineWarningMsg = lineWarningMsg + validateExcelMapping.get("need_by_date").getExcelTile() +"不能为空，";
			}
			if (validateExcelMapping.get("quantity").getFieldNotNull() && StringUtils.isEmpty(excelDataList.get(i).get("quantity"))) {
				lineWarningMsg = lineWarningMsg + validateExcelMapping.get("quantity").getExcelTile() +"不能为空，";
			}
			if(null != lineWarningMsg) {
				excelErrorMsg = excelErrorMsg + lineWarningMsg;
			}
		}
		
		//3.merge 相同类型产品，对数量进行相加
		//TODO：cq...3.1 保存文件时得判断文件是否已经存在？如果存在，那么是否会进行覆盖呢？
		List<Map<String, Object>> insertExcelDataList = new ArrayList<>();
        for (int i = 0; i < excelDataList.size(); i++) {  
        	Map<String, Object> excelData = excelDataList.get(i);  
        	String unique = ""+excelData.get("demand_type")+excelData.get("external_rack_name")+excelData.get("cluster")+excelData.get("need_by_date");
        	Integer quantity = (Integer)excelData.get("quantity");
        	
        	for(int j=0; j<insertExcelDataList.size(); j++) {
        		Map<String, Object> insertExcelData = insertExcelDataList.get(j); 
        		String distinct=""+insertExcelData.get("demand_type")+insertExcelData.get("external_rack_name")+insertExcelData.get("cluster")+insertExcelData.get("need_by_date");
        		
        		if(!unique.equals(distinct)) {
        			insertExcelDataList.add(excelData);
        		}else {
        			Integer insertQuantity = (Integer)insertExcelData.get("quantity");
        			insertExcelDataList.get(j).put("quantity", insertQuantity+quantity);
        		}
        	}
        }
		
		//4.老数据迁移到历史日志表，并批量插入新数据
		
		

	}

}
