package com.gth.delivery.dao;

import com.gth.delivery.model.Courier;

public interface CourierMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Courier record);

    int insertSelective(Courier record);

    Courier selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Courier record);

    int updateByPrimaryKeyWithBLOBs(Courier record);

    int updateByPrimaryKey(Courier record);
}