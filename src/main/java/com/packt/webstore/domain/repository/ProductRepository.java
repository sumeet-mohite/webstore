package com.packt.webstore.domain.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.packt.webstore.domain.Product;

public interface ProductRepository {

	public List<Product> getAllProducts();
	public  Product getProductById(String productId);
	List<Product> getProductsByManufacturer(String manufacturer); 
	List<Product> getProductsByCategory(String category);
	Set<Product> getProductByFilter(Map<String,List<String>> filterParam);
	void addProduct(Product p);
}
