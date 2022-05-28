package com.example.login.payload.response;

import java.util.List;

import lombok.Data;

@Data
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
    private String username;
	private String email;
	private List<String> roles;
	
	private String phone_number;
	private String address_1;
	private String city;
	private String district;
	private String ward;
	
	public JwtResponse(String accessToken, Long id, String username, String email, 
			String phone_number,
			String address_1,
			String city,
			String district,
			String ward,
			List<String> roles) {
	    this.token = accessToken;
	    this.id = id;
	    this.username = username;
	    this.email = email;
	    this.phone_number = phone_number;
	    this.address_1 = address_1;
	    this.city = city;
	    this.district = district;
	    this.ward = ward;
	    this.roles = roles;
	}
}
