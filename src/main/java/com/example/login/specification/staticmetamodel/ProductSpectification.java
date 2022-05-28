package com.example.login.specification.staticmetamodel;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.example.login.model.Product;

@Component
public class ProductSpectification {

	public Specification<Product> getProds(ProductRequest request) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (request.getProdName() != null && !request.getProdName().isEmpty()) {
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("prodname")),
						"%" + request.getProdName().toLowerCase() + "%"));
			}
			if (request.getProdSize() != null && !request.getProdSize().isEmpty()) {
				predicates.add(criteriaBuilder.equal(root.get("prodsize"), request.getProdSize()));
			}
			if (request.getProdType() != null && !request.getProdType().isEmpty()) {
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("prodtype")),
						"%" + request.getProdType().toLowerCase() + "%"));
			}
			if (request.getCatName() != null && !request.getCatName().isEmpty()) {
				predicates.add(criteriaBuilder.equal(root.get("categories").get("catname"), request.getCatName()));
			}
			if (request.getSort().matches("asc") && request.getSort() != null && !request.getSort().isEmpty()) {
				query.orderBy(criteriaBuilder.asc(root.get("prodprice")));
			}
			if (request.getSort().matches("desc") && request.getSort() != null && !request.getSort().isEmpty()) {
				query.orderBy(criteriaBuilder.desc(root.get("prodprice")));
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
