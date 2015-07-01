package lx.spring.db.cyMapper;

import java.util.List;

import lx.springmvc.vo.cy.Topic;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface TopicMapper {

    @Select("select * from topic where isv_id = #{isvid}")
    @Results(value = { @Result(property = "topicid", column = "topic_id"),//
            @Result(property = "isvid", column = "isv_id"), //
            @Result(property = "topicUrl", column = "topic_urls") })
    List<Topic> selectByIsvid(int isvid);

}
