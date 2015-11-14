package com.packt.webstore.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.Request;
import org.apache.coyote.http11.Http11AprProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.packt.webstore.domain.Product;

import com.packt.webstore.service.ProductService;


@RequestMapping("/products")
@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@RequestMapping("/{category}")
	public String list(Model model,@PathVariable("category") String category) {
		model.addAttribute("products",productService.getProductsByCategory(category));
		return "product";
	}
	@RequestMapping("/all")
	public String allProduct(Model model) {
		model.addAttribute("products",productService.getAllProducts());
		return "product";
	}
	@RequestMapping("/")
	public String Products(Model model) {
		model.addAttribute("products",productService.getAllProducts());
		return "product";
	}
	@RequestMapping("/filter/{byCategory}")
	public String getProductByFilter(Model model,@MatrixVariable(pathVar="byCategory") Map<String,List<String>> filterParam)
	{
		System.out.println("hie");
		System.out.println(productService.getProductByFilter(filterParam));
		model.addAttribute("products",productService.getProductByFilter(filterParam));
		return "product";
	}
	@RequestMapping("/product")
	public String getProductById(@RequestParam("id") String ProductId,Model model){
		System.out.println("..........."+productService.getProductById(ProductId));
		model.addAttribute("product",productService.getProductById(ProductId));
		return "products_detail";
	}
	@RequestMapping("/tablet/{price}")
	public String filterProduct(@MatrixVariable(pathVar="price") Map<String,List<String>> filterParam,@RequestParam("manufacturer") String manufacturer,Model model){
		Set<Product> product=productService.getProductByFilter(filterParam);
		List<Product> productmanu=productService.getProductsByManufacturer(manufacturer);
		product.retainAll(productmanu);
		System.out.println("jdfkjsdkfbsdk"+product);
		model.addAttribute("products",product);
		return "product";
	}
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String getAddNewForm(Model model){
		Product p=new Product();
		model.addAttribute("product", p);
		return "add_product";
	}
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String processAddNewForm(@ModelAttribute("product") Product p,HttpServletRequest request){
		System.out.println(p.toString());
		MultipartFile image=p.getProductImage();
		String rootDirectory=request.getSession().getServletContext().getRealPath("/");
		if(image!=null&&!image.isEmpty()){
		try {
			image.transferTo(new File(rootDirectory+"resources\\images\\"+p.getProductId()+".jpg"));
		} catch (IllegalStateException e) {
			System.out.println("location not found");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		productService.addProduct(p);
		return "redirect:/products/all";
	}
	
	
}
