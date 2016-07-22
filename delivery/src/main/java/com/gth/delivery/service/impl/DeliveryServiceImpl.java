package com.gth.delivery.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gth.delivery.dao.UserMapper;
import com.gth.delivery.model.User;
import com.gth.delivery.service.DeliveryService;

@Service
public class DeliveryServiceImpl implements DeliveryService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public User getUserById(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}
}
