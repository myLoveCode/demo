package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 
 * excel列头 和 表字段对应关系
 *
 */
@Entity
public class ExcelTableFieldMapping implements Serializable {

	private static final long serialVersionUID = -475254150861172103L;

	@Id
	@GeneratedValue
	private Long id;
	private String customer;
	private String excelTile; //Excel列头
	private String tableField; //表字段
	private Boolean fieldNotNull=false; //表字段不允许空，默认可以为空
//	private String fieldLength; //表字段长度
//	private String fieldFormat; //表字段格式化样式
	
	public ExcelTableFieldMapping() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getExcelTile() {
		return excelTile;
	}

	public void setExcelTile(String excelTile) {
		this.excelTile = excelTile;
	}

	public String getTableField() {
		return tableField;
	}

	public void setTableField(String tableField) {
		this.tableField = tableField;
	}

	public Boolean getFieldNotNull() {
		return fieldNotNull;
	}

	public void setFieldNotNull(Boolean fieldNotNull) {
		this.fieldNotNull = fieldNotNull;
	}
	
}
