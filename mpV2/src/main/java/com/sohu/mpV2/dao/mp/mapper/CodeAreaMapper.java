package com.sohu.mpV2.dao.mp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.sohu.mpV2.vo.CodeArea;

public interface CodeAreaMapper {

    @Select("select * from code_area where id <= 650 ")
    @Results(value = { @Result(column = "id", property = "id"), //
            @Result(column = "code", property = "code"), //
            @Result(column = "name", property = "name")})
    public List<CodeArea> getAllProvince();
    
    @Select("select * from code_area where id > 650 ")
    @Results(value = { @Result(column = "id", property = "id"), //
            @Result(column = "code", property = "code"), //
            @Result(column = "name", property = "name")})
    public List<CodeArea> getAllCity();
}
