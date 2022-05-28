package com.example.login.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_products")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class OrderProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int quantity;
	
	private int status;
	
	private String date;
	
	@ManyToOne
	private Order order;
	
	@ManyToOne
	private Product product;

	public OrderProduct(int quantity, int status, String date, Order order, Product product) {
		super();
		this.quantity = quantity;
		this.status = status;
		this.date = date;
		this.order = order;
		this.product = product;
	}



}
