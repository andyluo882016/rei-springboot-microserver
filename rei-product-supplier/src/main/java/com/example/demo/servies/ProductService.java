package com.example.demo.servies;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.module.Product;
import com.example.demo.repository.ProductRepository;

@Service
@Transactional
public class ProductService {

	 @Autowired
		ProductRepository productRepository;
	    
	    
	    public List<Product> findalls() {
	    	
	    	return productRepository.findAll();
	    }
	    
	    public Product saveoneProduct(Product product) {
	    	
	       return	productRepository.insert(product);
	    	
	    }
	    
	    public Map<String, Object> getallProductsInPage(int Pagenum, int pagesize, String sortBy) {
	    	Map<String, Object> response =new HashMap<String, Object>();
	    	Sort sort =Sort.by(sortBy);
	    	Pageable page =PageRequest.of(Pagenum, pagesize, sort);
	    	Page<Product> productPage = productRepository.findAll(page);
	    	response.put("data", productPage.getContent());
	    	response.put("totalof number page ", productPage.getTotalPages());
	    	response.put("Total No of Elements ", productPage.getTotalElements());
	    	response.put("Current Page No: ", productPage.getNumber());

	    	return response;
	    }
	    
	    public Product findProductById(String id) {
	    	
	    	return productRepository.findProductByProductId(id);
	    }
	    
	    public boolean existsofProductbyId(String id) {
	    	
	    	return productRepository.existsById(id);
	    }
	    
	    public void deleteById(@PathVariable("id") final String id) {
	    	
	    	productRepository.deleteById(id);
	    	
	    	//return "The Product with: "+id+" has been deleted";
	    }
	    
	    public Product updatedProduct(Product product) {
	    	
	    	return productRepository.save(product);
	    }
	    
	    public List<Product> findproductsByBrand(String brand) {
	    	
	    	  return productRepository.findProductsByBrand(brand);
	    }
}
