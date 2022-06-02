package com.example.login.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.login.model.Order;
import com.example.login.model.Product;
import com.example.login.model.User;
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

//	@Autowired
//	private OrderProdRepo orderProdRepo;
	
	@GetMapping("/count")
	public ResponseEntity<Map<String, String>> getCountInfo(){
		Map<String, String> response = new HashMap<String, String>();
		response.put("CountProds", productRepo.countProds());
		response.put("CountProds_OutOfStock", productRepo.countProds_OutOfStock());
		response.put("CountOrders", orderRepo.countOrders());
		response.put("CountOrders_NotFinish", orderRepo.countOrdersNotDone());
		response.put("CountUsers", userRepo.countUsers());
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<User>> getListOfUsers(){
		return new ResponseEntity<>(userRepo.findAll(),HttpStatus.OK);
	}
	@GetMapping("/product")
	public ResponseEntity<List<Product>> getListOfProds(){
		return new ResponseEntity<>(productRepo.findAll(),HttpStatus.OK);
	}
	@GetMapping("/order_uf")
	public ResponseEntity<List<Order>> getListOfOrdersUnfinish(){
		return new ResponseEntity<>(orderRepo.listofOrdersUnfinish(),HttpStatus.OK);
	}
	@GetMapping("/order_done")
	public ResponseEntity<List<Order>> getListOfOrdersDone(){
		return new ResponseEntity<>(orderRepo.listofOrdersDone(),HttpStatus.OK);
	}
}
