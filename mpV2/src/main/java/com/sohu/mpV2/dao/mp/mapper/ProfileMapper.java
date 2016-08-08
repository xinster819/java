package com.sohu.mpV2.dao.mp.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sohu.mpV2.vo.MpMediaProfile;

public interface ProfileMapper {

    @Select("select * from mp_media_profile where id=#{id}")
    @Results(value = { @Result(column = "id", property = "id"), //
            @Result(column = "mp_media_id", property = "mpMediaId"), //
            @Result(column = "site_url", property = "SiteUrl"), //
            @Result(column = "media_type", property = "mediaType"), //
            @Result(column = "audit_type", property = "auditType"), //
            @Result(column = "from_where", property = "fromWhere"), //
            @Result(column = "grade", property = "grade"), //
            @Result(column = "mp_channel_id", property = "mpChannelId"), //
            @Result(column = "audit_status", property = "auditStatus") })
    public MpMediaProfile byId(int id);

    @Select("select * from mp_media_profile where mp_media_id=#{mpMediaId}")
    @Results(value = { @Result(column = "id", property = "id"), //
            @Result(column = "mp_media_id", property = "mpMediaId"), //
            @Result(column = "site_url", property = "SiteUrl"), //
            @Result(column = "media_type", property = "mediaType"), //
            @Result(column = "audit_type", property = "auditType"), //
            @Result(column = "from_where", property = "fromWhere"), //
            @Result(column = "grade", property = "grade"), //
            @Result(column = "mp_channel_id", property = "mpChannelId"), //
            @Result(column = "audit_status", property = "auditStatus") })
    public MpMediaProfile byMpMediaId(String mpMediaId);
    
    @Update("update mp_media_profile set weibo_url=#{weiboUrl} where id=#{id}")
    public void updateWeiboUrl(MpMediaProfile one);
}
