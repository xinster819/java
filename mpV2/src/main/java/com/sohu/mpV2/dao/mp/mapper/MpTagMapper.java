package com.sohu.mpV2.dao.mp.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.sohu.mpV2.vo.MpTag;

public interface MpTagMapper {

    @Select("select * from mp_tag where id=#{id}")
    @Results(value = { @Result(column = "id", property = "id"), //
            @Result(column = "name", property = "name"), //
            @Result(column = "url", property = "url"), //
            @Result(column = "category_id", property = "categoryId"), //
            @Result(column = "status", property = "status"), //
            @Result(column = "type", property = "type"), //
            @Result(column = "create_time", property = "createTime") })
    public MpTag byId(int id);
}
