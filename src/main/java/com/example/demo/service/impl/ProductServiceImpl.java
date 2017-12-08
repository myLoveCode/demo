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
import com.example.core.utils.ExcelUtil;
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
	public Product save(Product product) throws BusinessException {
		logger.info("product:{}",JSON.toJSONString(product));
		return productDao.save(product);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) throws BusinessException {
		Product findOne = productDao.findOne(id);
		if(findOne == null) {
			throw new BusinessException("不存在的数据");
		}
		productDao.delete(id);
	}

	@Override
	public Product findOne(Long id) throws BusinessException {
		Product findOne = productDao.findOne(id);
		if(findOne == null) {
			throw new BusinessException("不存在的数据");
		}
		return findOne;
	}

	@Override
	public Page<Product> findByCriteria(int page, int size, ProductQuery conditon) throws BusinessException {
		logger.info("page:{},size:{},ProductQuery:{}", page-1, size, JSON.toJSONString(conditon));
		Pageable pageable = new PageRequest(page-1, size, Sort.Direction.DESC, "id");
		Page<Product> bookPage = productDao.findAll(new Specification<Product>() {
			@Override
			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (null != conditon.getCustomer() && !"".equals(conditon.getCustomer())) {
					list.add(criteriaBuilder.like(root.get("customer").as(String.class), "%"+conditon.getCustomer()+"%" )); // TODO:如果是模糊查询呢？
				}
				if (null != conditon.getDemandType() && !"".equals(conditon.getDemandType())) {
					list.add(criteriaBuilder.like(root.get("demandType").as(String.class), "%"+conditon.getDemandType()+"%"  ));
				}
				if (null != conditon.getExternalRackName() && !"".equals(conditon.getExternalRackName())) {
					list.add(criteriaBuilder.like(root.get("externalRackName").as(String.class),
							"%"+conditon.getExternalRackName()+"%"  ));
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

	@Override
	@Transactional(readOnly = false)
	public List<Map<String, String>> importProduct(FileInputStream fis, String fileName) {
        StopWatch clock = new StopWatch();
        clock.start("查询customer自定义的Excel列头");
		//0.查询该客户的所有Excel自定义列头对应的表字段
		List<ExcelTableFieldMapping> excelTableFieldMappings = excelTableFieldMappingDao.findByCustomer(fileName);
		if(excelTableFieldMappings == null) {
			throw new BusinessException("没找到该用户的数据，请联系管理员，添加Excel列头数据");
		}
		clock.stop();
		
		// ... List<ExcelTableFieldMapping> 2 map 传到 parseExcel方法中map
		clock.start("得到Excel列头和字段对应关系，用于后续解析Excel");
		Map<String, String> parseExcelMappin = excelTableFieldMappings.stream()
				.collect(Collectors.toMap(ExcelTableFieldMapping::getExcelTile, ExcelTableFieldMapping::getTableField));
		clock.stop();
		
		//目前的校验规则只是进行非空校验
		clock.start("得到数据库中配置了的Excel校验规则");
		Map<String, ExcelTableFieldMapping> validateExcelMapping = excelTableFieldMappings.stream()
				.filter(e->e.getFieldNotNull())
				.collect(Collectors.toMap(ExcelTableFieldMapping::getTableField, k->(k)));
		clock.stop();
		
		clock.start("解析Excel");
		// 1.文件解析
		List<Map<String, Object>> excelDataList = null;
		try {
			excelDataList = ExcelUtil.parseExcel(fis, fileName, parseExcelMappin);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException("文件解析失败");
		}
		if(excelDataList == null) {
			throw new BusinessException("文件解析失败");
		}
		clock.stop();
		
		clock.start("校验Excle");
		// 2.文件校验
		// 哪些字段是不能为空 等
		List<Map<String, String>> excelErrorMsg = validateExcelCell(validateExcelMapping, excelDataList);
		clock.stop();
		
		if(excelErrorMsg!=null && excelErrorMsg.size()>0) {
			return excelErrorMsg;
		}
		
		clock.start("Excel数据处理");
		//3.merge 相同类型产品，对数量进行相加
		//3.1 保存文件时得判断文件是否已经存在？如果存在，那么是否会进行覆盖呢？
		List<Product> insertExcelDataList = handleExcelCell(excelDataList,fileName); //要插入数据库的数据
		clock.stop();
		
		clock.start("Excel处理后的数据入库");
		//4.老数据迁移到历史日志表，并批量插入新数据
        String customer = fileName; //一个文件对应一个customer，这里做简单模拟。文件名就是客户公司名称
        productDao.moveToProductHistory(customer);
        productDao.deleteByCustomer(customer);
        productDao.save(insertExcelDataList); //好假。。。竟然是一条一条的保存。
        clock.stop();
//        logger.info(clock.prettyPrint());
//        logger.info("共耗费秒数={}" , clock.getTotalTimeSeconds());
        System.err.println(clock.prettyPrint());
        System.err.println("共耗费秒数"+clock.getTotalTimeSeconds());
        
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
		Map<String,Integer> map = new HashMap<>();
		
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
					
					map.put(unique, insertExcelDataList.size()-1);
					
				} catch (Exception e1) {
					logger.error(e1.getMessage(),e1);
				}
        	}else {
        		Integer index = map.get(unique);
				if(null == index) {
        			try {
						Product map2Bean = BeanMapChangeUtil.toBean(excelData, Product.class);
						String needByDate = (String)excelData.get("needByDate");
						map2Bean.setSunday(DateUtil.getFirstDayOfWeek(needByDate) );
						
						map2Bean.setCustomer(customer);
						map2Bean.setUploadTime(uploadTime);
						insertExcelDataList.add(map2Bean);
						map.put(unique, insertExcelDataList.size()-1);
					} catch (Exception e1) {
						logger.error(e1.getMessage(),e1);
					}
        		}else {
        			Product insertExcelData = insertExcelDataList.get(index); 
        			insertExcelDataList.get(index).setQuantity(insertExcelData.getQuantity()+quantity);
        		}
        		
//        		int count=0;
//        		for(int j=0; j<size; j++) {
//            		Product insertExcelData = insertExcelDataList.get(j); 
//            		String distinct = insertExcelData.getDemandType()+insertExcelData.getExternalRackName()
//            						+ insertExcelData.getCluster()+ DateUtil.getDateFormat( insertExcelData.getNeedByDate() );
//            		
//            		if(!unique.equals(distinct)) {
//    					try {
//    						Product map2Bean = BeanMapChangeUtil.toBean(excelData, Product.class);
//    						String needByDate = (String)excelData.get("needByDate");
//    						map2Bean.setSunday(DateUtil.getFirstDayOfWeek(needByDate) );
//    						
//    						map2Bean.setCustomer(customer);
//    						map2Bean.setUploadTime(uploadTime);
//    						count++;
//    						if(count == size) {
//    							insertExcelDataList.add(map2Bean);
//    						}
//    					} catch (Exception e1) {
//    						logger.error(e1.getMessage(),e1);
//    					}
//            		}else {
//            			Integer insertQuantity = insertExcelData.getQuantity();
//            			insertExcelDataList.get(j).setQuantity(insertQuantity+quantity);
//            		}
//            	}
        	}
        }
		return insertExcelDataList;
	}

	private List<Map<String, String>> validateExcelCell(Map<String, ExcelTableFieldMapping> validateExcelMapping,
			List<Map<String, Object>> excelDataList) {
		List<Map<String,String>> errorMsg = new ArrayList<>();
//		excelData.forEach(item -> {
//			if (validateExcelMapping.get("demandType").getFieldNotNull() && StringUtils.isEmpty(item.get("demandType"))) {
//				String warningMsg = "第"+ 1 +"行，" + validateExcelMapping.get("demandType").getExcelTile() +"不能为空";
//				//这里如何获取索引呢？第1，2，3，行
//			}
//		});
		
		for(int i=0; i< excelDataList.size(); i++) {
			String lineWarningMsg = "";
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
			if(!"".equals(lineWarningMsg)) {
				Map<String,String> lineErrorMap = new HashMap<>();
				lineErrorMap.put("lineNum", i+2+"");
				lineErrorMap.put("lineErrorMsg", lineWarningMsg);  //i+1 为行号，还有一个列头占一行所以还得再加1 ,+2
				errorMsg.add(lineErrorMap);
			}
		}
		return errorMsg;
	}

}
