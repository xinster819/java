package com.sohu.mpV2.dao.news.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.sohu.mpV2.vo.MpNews;

public interface MpNewsMapper {

    @Select("select * from mp_news_${index} where id=#{id}")
    @Results(value = { @Result(column = "id", property = "id"), //
            @Result(column = "title", property = "title"), //
            @Result(column = "mobileTitle", property = "mobile_title"), //
            @Result(column = "brief", property = "brief"), //
            @Result(column = "content", property = "content"), //
            @Result(column = "url", property = "url"), //
            @Result(column = "image_news", property = "imageNews"), //
            @Result(column = "origin_source", property = "originSource"), //
            @Result(column = "mp_media_id", property = "mpMediaId"), //
            @Result(column = "gather_time", property = "gatherTime"), //
            @Result(column = "post_time", property = "postTime") })
    public MpNews byId(@Param("index") int index, @Param("id") long id);

}
