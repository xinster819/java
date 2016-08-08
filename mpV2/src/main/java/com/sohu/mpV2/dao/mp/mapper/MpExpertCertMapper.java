package com.sohu.mpV2.dao.mp.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.sohu.mpV2.vo.MpExpertCert;

public interface MpExpertCertMapper {

    @Select("select * from mp_expert_cert where media_profile_id=#{mpMediaProfileId}")
    @Results(value = { @Result(column = "id", property = "id"), //
            @Result(column = "media_profile_id", property = "mediaProfileId"), //
            @Result(column = "channel_id", property = "channelId"), //
            @Result(column = "category_id", property = "categoryId"), //
            @Result(column = "org", property = "org"), //
            @Result(column = "cert_info", property = "certInfo"), //
            @Result(column = "cert_pic_urls", property = "certPicUrls"), //
            @Result(column = "other_pic_urls", property = "otherPicUrls"), //
            @Result(column = "status", property = "status"), //
            @Result(column = "modified", property = "modified"), //
            @Result(column = "create_at", property = "createAt") })
    public MpExpertCert byMediaProfieId(int mpMediaProfileId);
}
