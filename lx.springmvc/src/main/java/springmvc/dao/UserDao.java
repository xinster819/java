package springmvc.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import springmvc.dao.mapper.UserMapper;
import springmvc.vo.User;

@Repository
public class UserDao {

    @Resource
    private UserMapper userMapper;

    public User byUserId(int userId) {
        return userMapper.byUserId(userId);
    }
}
