package com.example.login.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String company;

	private String address1;

	private String address2;

	private String city;

	private String district;

	private String ward;

	private String zipcode;

	private String phone;

	private String email;

	private String date;

	private String note;

	private double total;

	private int status;

	@Column(name = "payment_method")
	private int paymentmethod;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

//	@ManyToMany
//	@JoinColumn(name = "prod_id")
//	private List<Product> products;

	public Order(String name, String company, String address1, String address2, String city, String district,
			String ward, String zipcode, String phone, String email, String date, String note, double total, int status,
			int paymentmethod, User user) {
		super();
		this.name = name;
		this.company = company;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.district = district;
		this.ward = ward;
		this.zipcode = zipcode;
		this.phone = phone;
		this.email = email;
		this.date = date;
		this.note = note;
		this.total = total;
		this.status = status;
		this.paymentmethod = paymentmethod;
		this.user = user;
	}

}
