package com.example.login.repo;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.example.login.model.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
	Optional<Order> findByStatusAndUserId(int status,Long id);
	boolean existsByStatusAndUserId(int status,Long id);
	@Transactional
	@Modifying
	void deleteByStatusAndUserId(int status,Long id);
	List<Order> findByUserId(Long id);
}
