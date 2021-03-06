package com.gth.delivery.service;

import java.util.List;

import com.gth.delivery.model.Courier;
import com.gth.delivery.model.Friend;
import com.gth.delivery.model.Indent;
import com.gth.delivery.model.User;

public interface DeliveryService {
	public User findUserById(Integer id);

	public User findUserByUsername(String username);

	public void insertUser(User user);

	public void updateUser(User user);

	public List<User> findUsersByIds(List<Integer> ids);

	public void insertFriend(Integer id1, Integer id2);

	public Friend findFriendById(Integer id1, Integer id2);

	public List<Friend> findFriendByOneId(Integer id);

	public Courier findCourierById(Integer id);

	public Courier findCourierByUsername(String username);

	public void insertCourier(Courier courier);

	public void updateCourier(Courier courier);

	public void createIndent(Indent indent);

	public List<Indent> findIndentByFromUserId(Integer id);

	public List<Indent> findIndentByToUserId(Integer id);

	public List<Indent> findIndentByCourierId(Integer id);

	public Indent findIndentById(Integer id);

	public void updateIndent(Indent indent);

	// this is for test
	public List<User> findUserPage(Integer offset, Integer page);

	public List<Courier> findCourierPage(Integer offset, Integer page);

	public List<Friend> findFriendPage(Integer offset, Integer page);

	public List<Indent> findIndentPage(Integer offset, Integer page);

	public Integer getUserNumber();

	public Integer getCourierNumber();

	public Integer getFriendNumber();

	public Integer getIndentNumber();
}
