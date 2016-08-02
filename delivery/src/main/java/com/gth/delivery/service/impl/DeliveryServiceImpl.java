package com.gth.delivery.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gth.delivery.dao.CourierMapper;
import com.gth.delivery.dao.FriendMapper;
import com.gth.delivery.dao.IndentMapper;
import com.gth.delivery.dao.UserMapper;
import com.gth.delivery.model.Courier;
import com.gth.delivery.model.Friend;
import com.gth.delivery.model.Indent;
import com.gth.delivery.model.User;
import com.gth.delivery.service.DeliveryService;

@Service
public class DeliveryServiceImpl implements DeliveryService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private FriendMapper friendMapper;

	@Autowired
	private CourierMapper courierMapper;

	@Autowired
	private IndentMapper indentMapper;

	@Override
	public User findUserById(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public User findUserByUsername(String username) {
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

	@Override
	public Courier findCourierById(Integer id) {
		return courierMapper.selectByPrimaryKey(id);
	}

	@Override
	public Courier findCourierByUsername(String username) {
		List<Courier> result = courierMapper.selectByUserName(username);
		if (result == null || result.size() != 1)
			return null;
		return result.get(0);
	}

	@Override
	public void insertCourier(Courier courier) {
		courierMapper.insertSelective(courier);
	}

	@Override
	public void updateCourier(Courier courier) {
		courierMapper.updateByPrimaryKeySelective(courier);
	}

	@Override
	public void createIndent(Indent indent) {
		indentMapper.insertSelective(indent);
	}

	@Override
	public List<Indent> findIndentByFromUserId(Integer id) {
		return indentMapper.selectByFromUserId(id);
	}

	@Override
	public List<Indent> findIndentByToUserId(Integer id) {
		return indentMapper.selectByToUserId(id);
	}
	
	@Override
	public List<Indent> findIndentByCourierId(Integer id) {
		return indentMapper.selectByCourierId(id);
	}

	@Override
	public Indent findIndentById(Integer id) {
		return indentMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateIndent(Indent indent) {
		indentMapper.updateByPrimaryKeySelective(indent);
	}

	@Override
	public List<User> findUserPage(Integer offset, Integer page) {
		return userMapper.selectPage(offset, page);
	}

	@Override
	public List<Courier> findCourierPage(Integer offset, Integer page) {
		return courierMapper.selectPage(offset, page);
	}

	@Override
	public List<Friend> findFriendPage(Integer offset, Integer page) {
		return friendMapper.selectPage(offset, page);
	}

	@Override
	public List<Indent> findIndentPage(Integer offset, Integer page) {
		return indentMapper.selectPage(offset, page);
	}

	@Override
	public Integer getUserNumber() {
		return userMapper.selectCount();
	}

	@Override
	public Integer getCourierNumber() {
		return courierMapper.selectCount();
	}

	@Override
	public Integer getFriendNumber() {
		return friendMapper.selectCount();
	}

	@Override
	public Integer getIndentNumber() {
		return indentMapper.selectCount();
	}

}
