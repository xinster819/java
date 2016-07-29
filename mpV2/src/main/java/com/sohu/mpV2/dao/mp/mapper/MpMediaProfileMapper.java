package com.sohu.mpV2.dao.mp.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.sohu.mpV2.vo.MpMediaProfile;

public interface MpMediaProfileMapper {

    @Select("select * from mp_media_profile where id=#{id}")
    @Results(value = { @Result(column = "id", property = "id"), //
            @Result(column = "mp_media_id", property = "mpMediaId"), //
            @Result(column = "site_url", property = "SiteUrl"), //
            @Result(column = "media_type", property = "mediaType"), //
            @Result(column = "audit_type", property = "auditType"), //
            @Result(column = "audit_status", property = "auditStatus") })
    public MpMediaProfile byId(int id);

}
