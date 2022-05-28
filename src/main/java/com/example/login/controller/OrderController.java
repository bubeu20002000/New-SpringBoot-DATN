package com.example.login.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.login.model.Order;
import com.example.login.model.OrderProduct;
import com.example.login.model.Product;
import com.example.login.model.User;
import com.example.login.repo.OrderProdRepo;
import com.example.login.repo.OrderRepo;
import com.example.login.repo.ProductRepo;
import com.example.login.repo.UserRepo;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private OrderProdRepo orderProdRepo;

	@PostMapping("/add/{id}/{sku}/{size}/{qty}")
	public ResponseEntity<?> addToCart(@PathVariable("id") Long id, @PathVariable("sku") String sku,
			@PathVariable("size") String size, @PathVariable("qty") int qty) {
		Optional<User> user = userRepo.findById(id);
		User _user = user.get();

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDateTime now = LocalDateTime.now();

		if (!orderRepo.existsByStatusAndUserId(0, id)) {
			orderRepo.save(new Order(null, null, null, null, null, null, null, null, null, null,
					dtf.format(now).toString(), null, 0, 0, 0, _user));
		}

		Optional<Order> order = orderRepo.findByStatusAndUserId(0, id);
		Order _order = order.get();

		Optional<Product> product = productRepo.findBySkuAndProdsize(sku, size);
		Product _product = product.get();

		OrderProduct orderProduct = orderProdRepo
				.save(new OrderProduct(qty, 0, dtf.format(now).toString(), _order, _product));
		return new ResponseEntity<>(orderProduct, HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<?> getItemsCart(@PathVariable("id") Long id) {
		List<OrderProduct> orderproduct = orderProdRepo.findByStatusAndOrderUserId(0, id);
		return new ResponseEntity<>(orderproduct, HttpStatus.OK);
	}

	@DeleteMapping("/del/{id}")
	public ResponseEntity<HttpStatus> deleteItemCart(@PathVariable("id") Long id) {
		orderProdRepo.deleteByProductId(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/update/{id}")
	public ResponseEntity<Order> updateOrder(@PathVariable("id") Long id, @RequestBody Order order) {
		// create new order with status 1
		// update status from order product
		// delete order with status 0
		Optional<User> user = userRepo.findById(id);
		User _user = user.get();

//		Optional<Order> orderOptional = orderRepo.findByUser_Id(id);
//		Order orderOption = orderOptional.get(); 
//		orderProdRepo.deleteByOrderId(orderOption.getId());

		Order _Order = orderRepo.save(new Order(order.getName(), order.getCompany(), order.getAddress1(),
				order.getAddress2(), order.getCity(), order.getDistrict(), order.getWard(), order.getZipcode(),
				order.getPhone(), order.getEmail(), order.getDate(), order.getNote(), order.getTotal(),
				order.getStatus(), order.getPaymentmethod(), _user));
		
		List<OrderProduct> orderproduct = orderProdRepo.findByStatusAndOrderUserId(0, id);
		for (OrderProduct orderProduct2 : orderproduct) {
			orderProduct2.setStatus(1);
			orderProduct2.setOrder(_Order);
			orderProdRepo.save(orderProduct2);
		}

//		orderRepo.deleteByStatusAndUserId(0, id);
		return new ResponseEntity<>(_Order, HttpStatus.CREATED);
	}
	
	@GetMapping("/get-order/{id}")
	public ResponseEntity<?> getOrder(@PathVariable("id") Long id) {
		List<Order> order = orderRepo.findByUserId(id);
		return new ResponseEntity<>(order, HttpStatus.OK);
	}
	
	@GetMapping("/get-prod-order/{id}")
	public ResponseEntity<?> getProdOrderbyId(@PathVariable("id") Long id) {
		List<OrderProduct> orderProducts = orderProdRepo.findByOrderId(id);
		return new ResponseEntity<>(orderProducts, HttpStatus.OK);
	}
	
}
