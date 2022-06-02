package com.example.login.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.login.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>{
	@Query("select u.prodsize, u.prodinstock from Product u where u.sku = ?1")
	List<String> customQuery(String sku);
	
	@Query("select u.prodsize,u.prodprice, u.prodprice-(u.prodprice*(u.proddiscount/100)) from Product u where u.sku = ?1")
	List<String> customQuery2(String sku);
	
	@Query(value = "select * from products u where u.prod_status = 1", nativeQuery = true)
	List<Product> customQueryNewPros(Pageable pageable);
	
	Optional<Product> findBySkuAndProdsize(String sku, String size);
	
	@Query("select count(u.id) from Product u")
	String countProds();
	
	@Query("select count(u.id) from Product u where u.prodinstock <= 0")
	String countProds_OutOfStock();
	
}
