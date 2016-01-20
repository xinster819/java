package springmvc.dao.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import springmvc.vo.User;

public interface UserMapper {

    @Select("select * from user where user_id = #{userId}")
    @Results(value = { @Result(column = "user_id", property = "userId"),//
            @Result(column = "nick", property = "nick"),//
            @Result(column = "email", property = "email"),//
            @Result(column = "portrait", property = "portrait"),//
            @Result(column = "password", property = "password") })
    public User byUserId(int userId);

    @Select("select * from user where email = #{email}")
    @Results(value = { @Result(column = "user_id", property = "userId"),//
            @Result(column = "nick", property = "nick"),//
            @Result(column = "email", property = "email"),//
            @Result(column = "portrait", property = "portrait"),//
            @Result(column = "password", property = "password") })
    public User byEmail(String email);
}
