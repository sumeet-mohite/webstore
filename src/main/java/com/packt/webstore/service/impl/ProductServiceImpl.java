package com.packt.webstore.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.ProductRepository;
import com.packt.webstore.domain.repository.impl.ProductDao;
import com.packt.webstore.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	@Qualifier(value="ProductDao")
	private ProductRepository productRepository;
	
	public List<Product> getAllProducts() {
		List<Product> products=productRepository.getAllProducts();
		return products;
	}

	public Product getProductById(String productId) {
		return productRepository.getProductById(productId);
	}

	public List<Product> getProductsByCategory(String category) {
		// TODO Auto-generated method stub
		return productRepository.getProductsByCategory(category);
	}

	@Override
	public Set<Product> getProductByFilter(Map<String, List<String>> filterParam) {
		// TODO Auto-generated method stub
		return productRepository.getProductByFilter(filterParam);
	}

	@Override
	public List<Product> getProductsByManufacturer(String manufacturer) {
		// TODO Auto-generated method stub
		 return productRepository.getProductsByManufacturer(manufacturer);
	}

	@Override
	public void addProduct(Product p) {
		productRepository.addProduct(p);
		
	}

}
