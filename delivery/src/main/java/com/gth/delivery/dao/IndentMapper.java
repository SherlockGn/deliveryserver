package com.gth.delivery.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gth.delivery.model.Indent;

public interface IndentMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Indent record);

	int insertSelective(Indent record);

	Indent selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Indent record);

	int updateByPrimaryKeyWithBLOBs(Indent record);

	int updateByPrimaryKey(Indent record);

	List<Indent> selectByFromUserId(Integer id);

	List<Indent> selectByToUserId(Integer id);

	List<Indent> selectByCourierId(Integer id);

	List<Indent> selectPage(@Param("offset") Integer offset, @Param("page") Integer page);

	Integer selectCount();
}