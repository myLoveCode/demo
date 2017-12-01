package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

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

import com.example.demo.dao.ProductDao;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import com.example.demo.vo.ProductQuery;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Resource
	private ProductDao productDao;

	@Override
	public Product save(Product product) {
		return productDao.save(product);
	}

	@Override
	public void delete(Long id) {
		productDao.delete(id);
	}

	@Override
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
					list.add(criteriaBuilder.equal(root.get("customer").as(String.class), conditon.getCustomer()));
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

}
