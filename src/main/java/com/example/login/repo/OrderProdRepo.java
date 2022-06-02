package com.example.login.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.login.model.OrderProduct;

public interface OrderProdRepo extends JpaRepository<OrderProduct, Long>{
	List<OrderProduct> findByStatusAndOrderUserId(int status,Long id);
	
	List<OrderProduct> findByOrderId(Long id);
	
	@Transactional
	@Modifying
	void deleteByProductId(Long id);
	
	@Transactional
	@Modifying
	void deleteByOrderId(Long id);
	
	@Transactional
	@Modifying
	@Query(value = "update shop.order_products set status = 1 where status = 0",nativeQuery = true)
	void updateCart();
}
