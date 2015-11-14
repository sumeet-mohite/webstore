package com.packt.webstore.domain.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.packt.webstore.domain.Customer;
import com.packt.webstore.domain.repository.CustomerRepository;

@Repository
public class InMemoryCustomerRepository implements CustomerRepository{

	List<Customer> customer=new ArrayList<Customer>();
	
	public InMemoryCustomerRepository(){
		Customer c=new Customer(123,"sumeet");
		c.setAddress("bandra east");
		c.setNoOfOrderMade(1);
		Customer c1=new Customer(124,"ranjit");
		c1.setAddress("andheri west");
		c1.setNoOfOrderMade(2);
		Customer c2=new Customer(125,"mahendra");
		c2.setAddress("vasai");
		c2.setNoOfOrderMade(3);
		Customer c3=new Customer(126,"danda");
		c3.setAddress("borivali");
		c3.setNoOfOrderMade(4);
		customer.add(c);
		customer.add(c1);
		customer.add(c2);
		customer.add(c3);
	}

	
	public List<Customer> getAllCustomers() {
		System.out.println("in repo"+customer);
		return customer;
	}

}
