package com.gth.delivery.service;

import java.util.List;

import com.gth.delivery.model.Friend;
import com.gth.delivery.model.User;

public interface DeliveryService {
	public User getUserById(Integer id);

	public User getUserByUsername(String username);

	public void insertUser(User user);

	public void updateUser(User user);

	public List<User> findUsersByIds(List<Integer> ids);

	public void insertFriend(Integer id1, Integer id2);

	public Friend findFriendById(Integer id1, Integer id2);

	public List<Friend> findFriendByOneId(Integer id);
}
