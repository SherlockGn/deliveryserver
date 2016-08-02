package com.gth.delivery.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gth.delivery.model.Courier;

public interface CourierMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Courier record);

	int insertSelective(Courier record);

	Courier selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Courier record);

	int updateByPrimaryKeyWithBLOBs(Courier record);

	int updateByPrimaryKey(Courier record);

	List<Courier> selectByUserName(String username);

	List<Courier> selectPage(@Param("offset") Integer offset, @Param("page") Integer page);

	Integer selectCount();
}