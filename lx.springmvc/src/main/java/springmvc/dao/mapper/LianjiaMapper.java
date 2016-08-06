package springmvc.dao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import springmvc.vo.Lianjia;

public interface LianjiaMapper {

    @Select("select * from lianjia where id = #{id}")
    @Results(value = { //
            @Result(column = "plot_name", property = "plotName"), //
            @Result(column = "id", property = "id"), //
            @Result(column = "url", property = "url"), //
            @Result(column = "price", property = "price"), //
            @Result(column = "construct_year", property = "constructYear"), //
            @Result(column = "direction", property = "direction"), //
            @Result(column = "area", property = "area"), //
            @Result(column = "floor", property = "floor"), //
            @Result(column = "structure", property = "structure") })
    public Lianjia byid(int id);

    @Insert("insert into lianjia (price, plot_name, structure, area, direction, floor, construct_year, url) values (#{price}, #{plotName}, #{structure}, #{area}, #{direction}, #{floor}, #{constructYear}, #{url})")
    public boolean insert(Lianjia Lianjia);

    @Update("update lianjia set plot_name=#{plotName}  where id=#{id} ")
    public boolean updatePlotName(Lianjia Lianjia); 

    @Update("update lianjia set price=#{price}  where id=#{id} ")
    public boolean updatePrice(Lianjia Lianjia); 

    @Update("update lianjia set construct_year=#{constructYear}  where id=#{id} ")
    public boolean updateConstructYear(Lianjia Lianjia); 

    @Update("update lianjia set direction=#{direction}  where id=#{id} ")
    public boolean updateDirection(Lianjia Lianjia); 

    @Update("update lianjia set area=#{area}  where id=#{id} ")
    public boolean updateArea(Lianjia Lianjia); 

    @Update("update lianjia set floor=#{floor}  where id=#{id} ")
    public boolean updateFloor(Lianjia Lianjia); 

    @Update("update lianjia set structure=#{structure}  where id=#{id} ")
    public boolean updateStructure(Lianjia Lianjia); 

    @Update("update lianjia set plot_name=#{plotName} ,price=#{price} ,construct_year=#{constructYear} ,direction=#{direction} ,area=#{area} ,floor=#{floor} ,structure=#{structure}  where id=#{id} ")
    public boolean update(Lianjia Lianjia); 

    @Delete("delete from lianjia where id=#{id}")
    public int delete(Lianjia lianjia);
}