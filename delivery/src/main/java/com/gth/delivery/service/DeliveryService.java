package com.gth.delivery.service;

import com.gth.delivery.model.User;

public interface DeliveryService {
	public User getUserById(Integer id);

	public User getUserByUsername(String username);
	
	public void insertUser(User user);
}
