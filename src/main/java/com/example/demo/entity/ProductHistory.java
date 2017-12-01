package com.example.demo.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ProductHistory implements Serializable {
	
	private static final long serialVersionUID = 1055307930577650244L;

	@Id
	@GeneratedValue
	private Long id; // 主键
	
	private String customer; // 客户 ？？？ TODO:目前暂时将 文件名作为 客户名称，一个文件就是一个customer
	private LocalDateTime uploadTime; //上传时间 
	
	private String demandType; // 需求类型
	private String externalRackName; // 产品名称model
	private String cluster; //airport code 机场编码
	private String ipn; // 编号
	private LocalDate needByDate;// 客户要求到货日期
	private LocalDate sunday;// 供应商期望生产完成日期以（即到达收获地日期）  默认为  needByDate的上周日      ？？？ TODO:如果needByDate本来就是周日呢？？？
	private Integer quantity; // 数量
	private String location; //工厂地址   (model+airportCode)

	private String vendor; // 供应商
	private String shippingRegion;
	private String cluster1;
	private LocalDate deliveryWindowStart;
	private LocalDate needByDateOld;
	private LocalDate dateStamp;
	private String testRack;
	private String Region;
	private String type;
	
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
	public LocalDateTime getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(LocalDateTime uploadTime) {
		this.uploadTime = uploadTime;
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
	public String getIpn() {
		return ipn;
	}
	public void setIpn(String ipn) {
		this.ipn = ipn;
	}
	public LocalDate getNeedByDate() {
		return needByDate;
	}
	public void setNeedByDate(LocalDate needByDate) {
		this.needByDate = needByDate;
	}
	public LocalDate getSunday() {
		return sunday;
	}
	public void setSunday(LocalDate sunday) {
		this.sunday = sunday;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getShippingRegion() {
		return shippingRegion;
	}
	public void setShippingRegion(String shippingRegion) {
		this.shippingRegion = shippingRegion;
	}
	public String getCluster1() {
		return cluster1;
	}
	public void setCluster1(String cluster1) {
		this.cluster1 = cluster1;
	}
	public String getCluster() {
		return cluster;
	}
	public void setCluster(String cluster) {
		this.cluster = cluster;
	}
	public LocalDate getDeliveryWindowStart() {
		return deliveryWindowStart;
	}
	public void setDeliveryWindowStart(LocalDate deliveryWindowStart) {
		this.deliveryWindowStart = deliveryWindowStart;
	}
	public LocalDate getNeedByDateOld() {
		return needByDateOld;
	}
	public void setNeedByDateOld(LocalDate needByDateOld) {
		this.needByDateOld = needByDateOld;
	}
	public LocalDate getDateStamp() {
		return dateStamp;
	}
	public void setDateStamp(LocalDate dateStamp) {
		this.dateStamp = dateStamp;
	}
	public String getTestRack() {
		return testRack;
	}
	public void setTestRack(String testRack) {
		this.testRack = testRack;
	}
	public String getRegion() {
		return Region;
	}
	public void setRegion(String region) {
		Region = region;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
	
	
}
