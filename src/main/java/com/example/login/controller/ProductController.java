package com.example.login.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.login.model.Product;
import com.example.login.repo.ProductRepo;
import com.example.login.specification.staticmetamodel.ProductRequest;
import com.example.login.specification.staticmetamodel.ProductSpectification;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/product")
public class ProductController {
	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private ProductSpectification productSpectification;

	@GetMapping("/products-info")
	public List<Product> getInfo(){
		return productRepo.findAll();
	}

	@PostMapping("/products/{page}/{size}")
	public ResponseEntity<Map<String, Object>> test2(@Valid @RequestBody(required = false) ProductRequest productRequest,
			@PathVariable("page") int page, @PathVariable("size") int size) {
		PageRequest paging = PageRequest.of(page, size);
		List<Product> products = new ArrayList<Product>();
		
		List<Product> output = new ArrayList<>();

		
		if (productRequest == null) {
			products = productRepo.findAll();
		} else {
			products = productRepo.findAll(productSpectification.getProds(productRequest));
		}
		if (products.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		HashSet<Object> seen=new HashSet<>();
		products.removeIf(c -> !seen.add(Arrays.asList(c.getSku())));
		int total = products.size();
		int start = (int)paging.getOffset();
		int end = Math.min((start + paging.getPageSize()), total);
		if (start <= end) {
		    output = products.subList(start, end);
		}
		Page<Product> page_filter = new PageImpl<>(output, paging, total);
		
		Map<String, Object> response = new HashMap<>();
		response.put("products",output);
		response.put("currentPage", page_filter.getNumber());
		response.put("totalItems", page_filter.getTotalElements());
		response.put("totalPages", page_filter.getTotalPages());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/product-details/{id}")
	public Optional<Product> getDetails(@PathVariable("id") Long id){
		return productRepo.findById(id);
	}
	
	@GetMapping("/product-details-sku/{sku}")
	public Map<String, String> getSizes(@PathVariable("sku") String sku){
		Map<String, String> response = new HashMap<>();
		List<String> data =  productRepo.customQuery(sku);
		for (int i=0;i<data.size();i++) {
			response.put(data.get(i).split(",")[0], data.get(i).split(",")[1]);
		}
		return response;
	}
	
	@GetMapping("/new-products")
	public List<Product> getNewProds(){
		Pageable pageable = PageRequest.of(0, 15, Direction.DESC, "id");
		List<Product> data = productRepo.customQueryNewPros(pageable);
		HashSet<Object> seen=new HashSet<>();
		data.removeIf(c -> !seen.add(Arrays.asList(c.getSku())));
		return data;
	}
}
