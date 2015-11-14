package com.packt.webstore.domain;

public class Customer {

	private int customerId;
	private String name;
	private String address;
	private int noOfOrderMade;
	
	public Customer(){
		super();
	}
	public Customer(int customerId,String name){
		this.customerId=customerId;
		this.name=name;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getNoOfOrderMade() {
		return noOfOrderMade;
	}
	public void setNoOfOrderMade(int noOfOrderMade) {
		this.noOfOrderMade = noOfOrderMade;
	}
	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", name=" + name + ", address=" + address + ", noOfOrderMade="
				+ noOfOrderMade + "]";
	}
	
}
