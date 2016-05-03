package springmvc.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.DateTypeHandler;
import org.apache.ibatis.type.JdbcType;

import springmvc.vo.ShadowSock;

public interface ShadowSockMapper {

    @Select("select * from shadow where url = '${url}'")
    @Results(value = { @Result(column = "url", property = "url"), //
            @Result(column = "login_uri", property = "loginUri"), //
            @Result(column = "check_in_uri", property = "checkInUri"), //
            @Result(column = "portrait", property = "portrait"), //
            @Result(column = "status", property = "status"), //
            @Result(column = "check_in_time", typeHandler = DateTypeHandler.class, jdbcType = JdbcType.TIMESTAMP, javaType = Date.class, property = "checkInTime") })
    public ShadowSock byUrl(@Param("url") String url);

    @Select("select * from shadow where status != 0")
    @Results(value = { @Result(column = "url", property = "url"), //
            @Result(column = "login_uri", property = "loginUri"), //
            @Result(column = "check_in_uri", property = "checkInUri"), //
            @Result(column = "portrait", property = "portrait"), //
            @Result(column = "status", property = "status"), //
            @Result(column = "check_in_time", typeHandler = DateTypeHandler.class, jdbcType = JdbcType.TIMESTAMP, javaType = Date.class, property = "checkInTime") })
    public List<ShadowSock> all();

    @Update("update shadow set status=${status}, check_in_time='${checkInTime.toLocaleString()}' where url='${url}'")
    public void check_in(ShadowSock s);

    @Update("update shadow set status=${status} where url='${url}'")
    public void updateStatus(ShadowSock s);

}
