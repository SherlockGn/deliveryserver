package com.gth.delivery.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gth.delivery.model.Friend;

public interface FriendMapper {
	int deleteByPrimaryKey(@Param("id1") Integer id1, @Param("id2") Integer id2);

	int insert(Friend record);

	int insertSelective(Friend record);

	Friend selectByPrimaryKey(@Param("id1") Integer id1, @Param("id2") Integer id2);

	int updateByPrimaryKeySelective(Friend record);

	int updateByPrimaryKey(Friend record);

	List<Friend> selectFriendByOneId(Integer id);

	List<Friend> selectPage(@Param("offset") Integer offset, @Param("page") Integer page);

	Integer selectCount();
}