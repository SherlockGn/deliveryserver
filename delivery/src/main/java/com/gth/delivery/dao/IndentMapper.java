package com.gth.delivery.dao;

import com.gth.delivery.model.Indent;

public interface IndentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Indent record);

    int insertSelective(Indent record);

    Indent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Indent record);

    int updateByPrimaryKeyWithBLOBs(Indent record);

    int updateByPrimaryKey(Indent record);
}