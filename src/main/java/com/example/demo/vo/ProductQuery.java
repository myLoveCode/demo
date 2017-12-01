package com.example.demo.vo;

import java.io.Serializable;

public class ProductQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2646977703062178562L;
	
	private String customer; 
	private String demandType;
	private String externalRackName;
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getDemandType() {
		return demandType;
	}
	public void setDemandType(String demandType) {
		this.demandType = demandType;
	}
	public String getExternalRackName() {
		return externalRackName;
	}
	public void setExternalRackName(String externalRackName) {
		this.externalRackName = externalRackName;
	}
	
	
}
