package com.packt.webstore.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.packt.webstore.domain.Product;

public interface ProductService {

	List<Product> getAllProducts();
	Product getProductById(String productId);
	List<Product> getProductsByCategory(String category);
	Set<Product> getProductByFilter(Map<String,List<String>> filterParam);
	List<Product> getProductsByManufacturer(String manufacturer);
	void addProduct(Product p);
}
