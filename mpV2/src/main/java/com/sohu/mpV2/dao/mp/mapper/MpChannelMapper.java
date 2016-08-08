package com.sohu.mpV2.dao.mp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.sohu.mpV2.vo.MpChannel;

public interface MpChannelMapper {

    @Select("select * from mp_channel where id=#{id}")
    @Results(value = { @Result(column = "id", property = "id"), //
            @Result(column = "name", property = "name"), //
            @Result(column = "cms_channel_id", property = "cmsChannelId"), //
            @Result(column = "cms_pname", property = "cmsPname"), //
            @Result(column = "status", property = "status") })
    public MpChannel byId(int id);
    
    @Select("select * from mp_channel where status=#{status}")
    @Results(value = { @Result(column = "id", property = "id"), //
            @Result(column = "name", property = "name"), //
            @Result(column = "cms_channel_id", property = "cmsChannelId"), //
            @Result(column = "cms_pname", property = "cmsPname"), //
            @Result(column = "status", property = "status") })
    public List<MpChannel> byStatus(int status);
}
