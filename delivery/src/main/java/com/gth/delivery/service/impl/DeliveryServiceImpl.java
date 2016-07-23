package com.gth.delivery.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gth.delivery.dao.FriendMapper;
import com.gth.delivery.dao.UserMapper;
import com.gth.delivery.model.Friend;
import com.gth.delivery.model.User;
import com.gth.delivery.service.DeliveryService;

@Service
public class DeliveryServiceImpl implements DeliveryService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private FriendMapper friendMapper;

	@Override
	public User getUserById(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public User getUserByUsername(String username) {
		List<User> lstUser = userMapper.selectByUsername(username);
		if (lstUser == null || lstUser.size() != 1)
			return null;
		return lstUser.get(0);
	}

	@Override
	public void insertUser(User user) {
		userMapper.insertSelective(user);
	}

	@Override
	public void updateUser(User user) {
		userMapper.updateByPrimaryKeySelective(user);
	}
	
	@Override
	public List<User> findUsersByIds(List<Integer> ids) {
		return userMapper.selectByUserIds(ids);
	}

	@Override
	public void insertFriend(Integer id1, Integer id2) {
		friendMapper.insert(new Friend(id1, id2, new Date()));
	}

	@Override
	public Friend findFriendById(Integer id1, Integer id2) {
		return friendMapper.selectByPrimaryKey(id1, id2);
	}

	@Override
	public List<Friend> findFriendByOneId(Integer id) {
		return friendMapper.selectFriendByOneId(id);
	}
}
