package com.example.login.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.login.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	
	Optional<User> findById(Long id);
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByPasstoken(String token);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
	
	@Query(value = "select count(u.id) from shop.users u inner join shop.user_roles ul on u.id = ul.user_id where ul.role_id = 1; ",nativeQuery = true)
	String countUsers();
	
	@Query(value = "select * from shop.users u where u.id <> :id ;",nativeQuery = true)
	List<User> findAll(@Param("id") Long id);
}
