package com.example.login.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String sku;
	@Column(name = "prod_name", nullable = false)
	private String prodname;
	@Column(name = "prod_type", nullable = false)
	private String prodtype;
	@Column(name = "prod_size", nullable = false)
	private String prodsize;
	@Column(name = "prod_description")
	private String proddescription;
	@Column(name = "prod_instock", nullable = false)
	private int prodinstock;
	@Column(name = "prod_price", nullable = false)
	private double prodprice;
	@Column(name = "prod_status", nullable = false)
	private Boolean prodstatus;
	@Column(name = "prod_color", nullable = false)
	private String prodcolor;
	@Column(name = "prod_img1")
	private String prodimg1;
	@Column(name = "prod_img2")
	private String prodimg2;
	@ManyToOne
	private Category categories;
	
}
