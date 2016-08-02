package com.gth.delivery.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gth.delivery.model.User;

public interface UserMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKeyWithBLOBs(User record);

	int updateByPrimaryKey(User record);

	List<User> selectByUsername(String username);

	List<User> selectByUserIds(List<Integer> ids);

	List<User> selectPage(@Param("offset") Integer offset, @Param("page") Integer page);
	
	Integer selectCount();
}