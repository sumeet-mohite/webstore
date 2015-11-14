package com.packt.webstore.domain.repository.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.ProductRepository;

@Repository(value="ProductDao")
public class ProductDao implements ProductRepository{

	@Autowired
	private DataSource datasource;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private Connection con;
	private PreparedStatement prepareStatement;
	private ResultSet resultSet;
	private List<Product> product;
	public ProductDao() {
	try {
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	public List<Product> getAllProducts() {
		try{
			this.con=datasource.getConnection();
			product=new ArrayList<Product>();
		String sql="SELECT * from product";
		prepareStatement=con.prepareStatement(sql);
		resultSet=prepareStatement.executeQuery();
		while(resultSet.next())
		{
			Product p=new Product();
			p.setProductId(resultSet.getString(1));
			p.setName(resultSet.getString(2));
			p.setUnitPrice(new BigDecimal(resultSet.getInt(3)));
			p.setDescription(resultSet.getString(4));
			p.setManufacturer(resultSet.getString(5));
			p.setCategory(resultSet.getString(6));
			p.setUnitsInStock(resultSet.getInt(7));
			p.setUnitsInOrder(resultSet.getInt(8));
			product.add(p);
			
		}
	}catch (Exception e) {
				e.printStackTrace();
				System.out.println("connection not establish");
			}
		closeConnections();
		return product;
	}

	public Product getProductById(String productId) {
		String sql="select * from product where productId=?";
		
		return jdbcTemplate.queryForObject(sql,new Object[]{productId}, new ProductRowMaper());
		
	}

	public List<Product> getProductsByCategory(String category) {
		try{
			this.con=datasource.getConnection();
			product=new ArrayList<Product>();
		String sql="SELECT * from product where category=?";
		prepareStatement=con.prepareStatement(sql);
		prepareStatement.setString(1, category);
		resultSet=prepareStatement.executeQuery();
		while(resultSet.next())
		{
			Product p=new Product();
			p.setProductId(resultSet.getString(1));
			p.setName(resultSet.getString(2));
			p.setUnitPrice(new BigDecimal(resultSet.getInt(3)));
			p.setDescription(resultSet.getString(4));
			p.setManufacturer(resultSet.getString(5));
			p.setCategory(resultSet.getString(6));
			p.setUnitsInStock(resultSet.getInt(7));
			p.setUnitsInOrder(resultSet.getInt(8));
			System.out.println("product value"+p);
			product.add(p);
			
		}
	}catch (Exception e) {
				e.printStackTrace();
				System.out.println("connection not establish");
			}
		System.out.println("inproductcategory......."+product);
		return product;
		
	}
	public void closeConnections()
	{
		try {
		
			con.close();
			prepareStatement.close();
			resultSet.close();
		} catch (Exception e) {
			System.out.println("connection not close properly");
		}
		
	}

	@Override
	public Set<Product> getProductByFilter(Map<String, List<String>> filterParam) {
		System.out.println("in getProductByFilter");
		String low="";
		String high="";
		
		Set<Product> productByBrand=new HashSet<Product>();
		Set<Product> productByCategory=new HashSet<Product>();
		
		Set<String> criteria=filterParam.keySet();
		if(criteria.contains("low"))
		{
			for(String brandName:filterParam.get("low"))
				low=brandName;
			/*for(Product product:getAllProducts())
				{
					if(brandName.equalsIgnoreCase(product.getManufacturer()))
					{
						productByBrand.add(product);
					}
				}*/
			 
		}
		if(criteria.contains("high"))
		{
			for(String categoryName:filterParam.get("high"))
			{
				high=categoryName;
				//productByCategory.addAll(getProductsByCategory(categoryName));	
			}
		}
		String sql="select * from product where unitprice>=? and unitprice<=?";
		productByCategory.addAll(jdbcTemplate.query(sql, new Object[]{low,high}, new ProductRowMaper()));
		System.out.println("checking before "+productByCategory);
		//productByCategory.retainAll(productByBrand);
		System.out.println("getProductByFilter......"+productByCategory);
		return productByCategory;
	}

	
	@Override
	public List<Product> getProductsByManufacturer(String manufacturer) {
		System.out.println(manufacturer);
		String sql="select * from product where manufacturer=?";
		List<Product> p= jdbcTemplate.query(sql, new Object[]{manufacturer}, new ProductRowMaper());
		System.out.println(p);
		return p;
	}
	
	private static class ProductRowMaper implements RowMapper<Product>{

		@Override
		public Product mapRow(ResultSet resultSet, int arg1) throws SQLException {
			Product p=new Product();
			p.setProductId(resultSet.getString(1));
			p.setName(resultSet.getString(2));
			p.setUnitPrice(new BigDecimal(resultSet.getInt(3)));
			p.setDescription(resultSet.getString(4));
			p.setManufacturer(resultSet.getString(5));
			p.setCategory(resultSet.getString(6));
			p.setUnitsInStock(resultSet.getInt(7));
			p.setUnitsInOrder(resultSet.getInt(8));
			return p;
		}
		
	}

	@Override
	public void addProduct(final Product p) {
		String sql="insert into product values(?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setString(1,p.getProductId());
				ps.setString(2,p.getName());
				ps.setBigDecimal(3,p.getUnitPrice());
				ps.setString(4,p.getDescription());
				ps.setString(5,p.getManufacturer());
				ps.setString(6,p.getCategory());
				ps.setLong(7,p.getUnitsInStock());
				ps.setLong(8,p.getUnitsInOrder());
				ps.setBoolean(9,p.isDiscontinued());
				ps.setString(10,p.getCondition());
				return ps.execute();
			}
		});
		
	}
}
