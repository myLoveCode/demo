package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.demo.entity.ExcelTableFieldMapping;

public interface ExcelTableFieldMappingDao extends JpaRepository<ExcelTableFieldMapping, Long>, JpaSpecificationExecutor<ExcelTableFieldMapping> {

	List<ExcelTableFieldMapping> findByCustomer(String fileName);

}
