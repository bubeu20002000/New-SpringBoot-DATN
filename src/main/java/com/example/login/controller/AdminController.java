package com.example.login.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.login.model.Category;
import com.example.login.model.Order;
import com.example.login.model.Product;
import com.example.login.model.User;
import com.example.login.repo.CategoryRepo;
import com.example.login.repo.OrderRepo;
import com.example.login.repo.ProductRepo;
import com.example.login.repo.UserRepo;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admin")
public class AdminController {
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@GetMapping("/count")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Map<String, String>> getCountInfo() {
		Map<String, String> response = new HashMap<String, String>();
		response.put("CountProds", productRepo.countProds());
		response.put("CountProds_OutOfStock", productRepo.countProds_OutOfStock());
		response.put("CountOrders", orderRepo.countOrders());
		response.put("CountOrders_NotFinish", orderRepo.countOrdersNotDone());
		response.put("CountUsers", userRepo.countUsers());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<User>> getListOfUsers() {
		return new ResponseEntity<>(userRepo.findAll(), HttpStatus.OK);
	}

	@GetMapping("/product")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Product>> getListOfProds() {
		return new ResponseEntity<>(productRepo.findAll(), HttpStatus.OK);
	}

	@GetMapping("/order_uf")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Order>> getListOfOrdersUnfinish() {
		return new ResponseEntity<>(orderRepo.listofOrdersUnfinish(), HttpStatus.OK);
	}

	@GetMapping("/order_done")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Order>> getListOfOrdersDone() {
		return new ResponseEntity<>(orderRepo.listofOrdersDone(), HttpStatus.OK);
	}

	@PutMapping("/update_order/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Order> UpdateOrdertoDone(@PathVariable("id") Long id) {
		Optional<Order> order = orderRepo.findById(id);
		if (order.isPresent()) {
			Order _order = order.get();
			_order.setStatus(2);
			return new ResponseEntity<>(orderRepo.save(_order), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/update_prod/{id}/{cid}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Product> UpdateProd(@PathVariable("id") Long id, @PathVariable("cid") Long cid,
			@RequestBody Product product) {
		Optional<Product> prodOptional = productRepo.findById(id);
		Optional<Category> cateOptional = categoryRepo.findById(cid);
		if (prodOptional.isPresent()) {
			Product _product = prodOptional.get();
			Category category = cateOptional.get();
			_product.setSku(product.getSku());
			_product.setProdname(product.getProdname());
			_product.setProdtype(product.getProdtype());
			_product.setProdcolor(product.getProdcolor());
			_product.setProdsize(product.getProdsize());
			_product.setProdinstock(Integer.valueOf(product.getProdinstock()));
			_product.setProdprice(Double.valueOf(product.getProdprice()));
			_product.setProdstatus(Boolean.valueOf(product.getProdstatus()));
			_product.setProddescription(product.getProddescription());
			_product.setProdimg1(product.getProdimg1());
			_product.setProdimg2(product.getProdimg2());
			_product.setProddiscount(Integer.valueOf(product.getProddiscount()));
			_product.setCategories(category);
			return new ResponseEntity<>(productRepo.save(_product), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete_prod/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") Long id) {
		try {
			productRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/add_prod/{cid}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Product> createProd(@PathVariable("cid") Long cid, @RequestBody Product product) {
		try {
			Optional<Category> cateOptional = categoryRepo.findById(cid);
			Category category = cateOptional.get();
			Product product2 = productRepo.save(new Product(null, product.getSku(), product.getProdname(),
					product.getProdtype(), product.getProdcolor(), product.getProdsize(),
					Integer.valueOf(product.getProdinstock()), Double.valueOf(product.getProdprice()),
					Boolean.valueOf(product.getProdstatus()), product.getProddescription(), product.getProdimg1(),
					product.getProdimg2(), Integer.valueOf(product.getProddiscount()), category));
			return new ResponseEntity<>(product2, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
