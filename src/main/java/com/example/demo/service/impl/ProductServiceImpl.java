package com.example.demo.service.impl;

import java.io.FileInputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.example.core.exception.BusinessException;
import com.example.core.utils.BeanMapChangeUtil;
import com.example.core.utils.DateUtil;
import com.example.core.utils.ImportExcelUtil;
import com.example.demo.dao.ExcelTableFieldMappingDao;
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
	@Resource
	private ExcelTableFieldMappingDao excelTableFieldMappingDao;

	@Override
	@Transactional(readOnly = false)
	public Product save(Product product) {
		logger.info("product:{}",JSON.toJSONString(product));
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
		logger.info("page:{},size:{},ProductQuery:{}", page, size, JSON.toJSONString(conditon));
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
		
		for(int i=0; i<bookPage.getContent().size(); i++) {
			Product product = bookPage.getContent().get(i);
			if(null == product.getLocation()) {
				String customer = product.getCustomer();
				String externalRackName = product.getExternalRackName();
				String cluster = product.getCluster();
				if( null!=customer && null!=externalRackName && null!=cluster ) {
					String location = productDao.getLocation(customer, externalRackName, cluster);
					bookPage.getContent().get(i).setLocation(location);
				}
			}
		}
		return bookPage;
	}

	/* (non-Javadoc)
	 * @see com.example.demo.service.ProductService#importProduct(java.io.FileInputStream, java.lang.String)
	 */
	@Override
	@Transactional(readOnly = false)
	public Map<Integer,String> importProduct(FileInputStream fis, String fileName) {
<<<<<<< .mine
		logger.info("导入Excel");
        StopWatch clock = new StopWatch();
=======
		StopWatch clock = new StopWatch();  
		clock.start("excelTableFieldMappings");
>>>>>>> .theirs
		StopWatch clock = new StopWatch();  
		clock.start("excelTableFieldMappings");		// TODO:cq...0.查询该客户的所有Excel自定义列头对应的表字段
		List<ExcelTableFieldMapping> excelTableFieldMappings = excelTableFieldMappingDao.findByCustomer(fileName);
		clock.stop();<<<<<<< .mine
		clock.stop();
		
		clock.start("parseExcelMappin");
=======
		clock.stop();


>>>>>>> .theirs
		// ... List<ExcelTableFieldMapping> 2 map 传到 parseExcel方法中map
		
		clock.start("parseExcelMappin");
		Map<String, String> parseExcelMappin = excelTableFieldMappings.stream()
				.collect(Collectors.toMap(ExcelTableFieldMapping::getExcelTile, ExcelTableFieldMapping::getTableField));
		clock.stop();
		
		clock.start("validateExcelMapping");
		Map<String, ExcelTableFieldMapping> validateExcelMapping = excelTableFieldMappings.stream()
				.filter(e->e.getFieldNotNull())
				.collect(Collectors.toMap(ExcelTableFieldMapping::getTableField, k->(k)));
<<<<<<< .mine
		clock.stop();
		
		clock.start("parseExcel");
=======
		clock.stop();


>>>>>>> .theirs
		clock.start("parseExcel");
		// 1.文件解析
		List<Map<String, Object>> excelDataList = null;
		try {
			excelDataList = ImportExcelUtil.parseExcel(fis, fileName, parseExcelMappin);
		} catch (Exception e) {
			logger.error("文件上传成功，但是文件解析失败");
			logger.error(e.getMessage(), e);
			throw new BusinessException("文件解析失败");
		}
		clock.stop();
		
		clock.start("validateExcelCell");
		// 2.文件校验
		// 哪些字段是不能为空 等
		Map<Integer, String> excelErrorMsg = validateExcelCell(validateExcelMapping, excelDataList);
<<<<<<< .mine
		clock.stop();
		
=======
		clock.stop();

>>>>>>> .theirs
		if(excelErrorMsg.size()>0) {
			return excelErrorMsg;
		}
		
		clock.start("handleExcelCell");
		//3.merge 相同类型产品，对数量进行相加
		//TODO：cq...3.1 保存文件时得判断文件是否已经存在？如果存在，那么是否会进行覆盖呢？
		List<Product> insertExcelDataList = handleExcelCell(excelDataList,fileName); //要插入数据库的数据
		clock.stop();
		
<<<<<<< .mine
		clock.start("DBoperration");
=======
		clock.start("DBoperation");
>>>>>>> .theirs
		//4.老数据迁移到历史日志表，并批量插入新数据
        String customer = fileName; //一个文件对应一个customer，这里做简单模拟。文件名就是客户公司名称
        productDao.moveToProductHistory(customer);
        productDao.deleteByCustomer(customer);
        productDao.save(insertExcelDataList); //好假。。。竟然是一条一条的保存。
<<<<<<< .mine
        clock.stop();
        logger.info("导入Excel任务全部执行结束");
        logger.info(clock.prettyPrint());
        logger.info("共耗费秒数={}" , clock.getTotalTimeSeconds());
=======
        clock.stop();
        logger.info(clock.prettyPrint());


>>>>>>> .theirs
        
        return null;
	}

	/**
	 * 处理日期为第一天（周日）
	 * 组合条件有，则数量累加。无，则插入。
	 * @param excelDataList
	 * @return
	 */
	private List<Product> handleExcelCell(List<Map<String, Object>> excelDataList, String customer) {
		Timestamp uploadTime = new Timestamp(System.currentTimeMillis());
		List<Product> insertExcelDataList = new ArrayList<>(); //要插入数据库的数据
        for (int i = 0; i < excelDataList.size(); i++) {  
        	Map<String, Object> excelData = excelDataList.get(i);  
        	Date dateFormat = DateUtil.getDateFormat((String)excelData.get("needByDate"));
			String unique = ""+excelData.get("demandType")+excelData.get("externalRackName")+excelData.get("cluster")
        				  + DateUtil.getDateFormat(dateFormat);
        	Integer quantity = Integer.parseInt(""+excelData.get("quantity"));
        	
        	int size = insertExcelDataList.size();
			
			if(0 == size) {
        		try {
					Product map2Bean = BeanMapChangeUtil.toBean(excelData, Product.class);
					String needByDate = (String)excelData.get("needByDate");
					map2Bean.setSunday(DateUtil.getFirstDayOfWeek(needByDate) );
					
					map2Bean.setCustomer(customer);
					map2Bean.setUploadTime(uploadTime);
					insertExcelDataList.add(map2Bean);
				} catch (Exception e1) {
					logger.error(e1.getMessage(),e1);
				}
        	}else {
        		int count=0;
        		for(int j=0; j<size; j++) {
            		Product insertExcelData = insertExcelDataList.get(j); 
            		String distinct = insertExcelData.getDemandType()+insertExcelData.getExternalRackName()
            						+ insertExcelData.getCluster()+ DateUtil.getDateFormat( insertExcelData.getNeedByDate() );
            		
            		if(!unique.equals(distinct)) {
    					try {
    						Product map2Bean = BeanMapChangeUtil.toBean(excelData, Product.class);
    						String needByDate = (String)excelData.get("needByDate");
    						map2Bean.setSunday(DateUtil.getFirstDayOfWeek(needByDate) );
    						
    						map2Bean.setCustomer(customer);
    						map2Bean.setUploadTime(uploadTime);
    						count++;
    						if(count == size) {
    							insertExcelDataList.add(map2Bean);
    						}
    					} catch (Exception e1) {
    						logger.error(e1.getMessage(),e1);
    					}
            		}else {
            			Integer insertQuantity = insertExcelData.getQuantity();
            			insertExcelDataList.get(j).setQuantity(insertQuantity+quantity);
            		}
            	}
        	}
        }
		return insertExcelDataList;
	}

	private Map<Integer, String> validateExcelCell(Map<String, ExcelTableFieldMapping> validateExcelMapping,
			List<Map<String, Object>> excelDataList) {
		Map<Integer,String> excelErrorMsg = new HashMap<>();
//		excelData.forEach(item -> {
//			if (validateExcelMapping.get("demandType").getFieldNotNull() && StringUtils.isEmpty(item.get("demandType"))) {
//				String warningMsg = "第"+ 1 +"行，" + validateExcelMapping.get("demandType").getExcelTile() +"不能为空";
//				//这里如何获取索引呢？第1，2，3，行
//			}
//		});
		
		for(int i=0; i< excelDataList.size(); i++) {
			String lineWarningMsg = null;
			if (validateExcelMapping.get("demandType").getFieldNotNull() && StringUtils.isEmpty(excelDataList.get(i).get("demandType"))) {
				lineWarningMsg += validateExcelMapping.get("demandType").getExcelTile() +"不能为空，";
			}
			if (validateExcelMapping.get("externalRackName").getFieldNotNull() && StringUtils.isEmpty(excelDataList.get(i).get("externalRackName"))) {
				lineWarningMsg += validateExcelMapping.get("externalRackName").getExcelTile() +"不能为空，";
			}
			if (validateExcelMapping.get("cluster").getFieldNotNull() && StringUtils.isEmpty(excelDataList.get(i).get("cluster"))) {
				lineWarningMsg += validateExcelMapping.get("cluster").getExcelTile() +"不能为空，";
			}
			if (validateExcelMapping.get("ipn").getFieldNotNull() && StringUtils.isEmpty(excelDataList.get(i).get("ipn"))) {
				lineWarningMsg += validateExcelMapping.get("ipn").getExcelTile() +"不能为空，";
			}
			if (validateExcelMapping.get("needByDate").getFieldNotNull() && StringUtils.isEmpty(excelDataList.get(i).get("needByDate"))) {
				lineWarningMsg += validateExcelMapping.get("needByDate").getExcelTile() +"不能为空，";
			}
			if (validateExcelMapping.get("quantity").getFieldNotNull() && StringUtils.isEmpty(excelDataList.get(i).get("quantity"))) {
				lineWarningMsg += validateExcelMapping.get("quantity").getExcelTile() +"不能为空，";
			}
			if(null != lineWarningMsg) {
				excelErrorMsg.put(i+i, lineWarningMsg);
			}
		}
		return excelErrorMsg;
	}

}
