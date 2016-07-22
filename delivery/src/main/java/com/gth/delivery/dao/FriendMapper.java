package com.gth.delivery.dao;

import com.gth.delivery.model.Friend;
import org.apache.ibatis.annotations.Param;

public interface FriendMapper {
    int deleteByPrimaryKey(@Param("id1") Integer id1, @Param("id2") Integer id2);

    int insert(Friend record);

    int insertSelective(Friend record);

    Friend selectByPrimaryKey(@Param("id1") Integer id1, @Param("id2") Integer id2);

    int updateByPrimaryKeySelective(Friend record);

    int updateByPrimaryKey(Friend record);
}