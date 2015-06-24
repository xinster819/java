package lx.spring.db.mapper;

import java.util.List;

import lx.springmvc.vo.LabsStat;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface LabsStatMapper {

    @Select("select * from labs_stat order by date desc")
    List<LabsStat> select();

    @Insert("insert into article (url, content) values (#{url},#{content})")
    public void insert(@Param("url") String url, @Param("content") String content);

    @Update("update article set url=#{url}, content=#{content}")
    public void update(@Param("url") String url, @Param("content") String content);
}
