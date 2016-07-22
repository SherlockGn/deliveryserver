package com.gth.delivery.model;

import java.util.Date;

public class User {
	private Integer id;

	private Boolean gender;

	private Date time;

	private String name;

	private String username;

	private String password;

	private String phone;

	private String address;

	public User() {
	}

	public User(Integer id, Boolean gender, Date time, String name, String username, String password, String phone,
			String address) {
		this.id = id;
		this.gender = gender;
		this.time = time;
		this.name = name;
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.address = address;
	}

	public User(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getGender() {
		return gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}
}