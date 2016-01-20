package springmvc.dao;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import springmvc.dao.mapper.UserMapper;
import springmvc.vo.User;

@Repository
public class UserDao {

    static Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

    @Resource
    private UserMapper userMapper;

    public User byUserId(int userId) {
        try {
            return userMapper.byUserId(userId);
        } catch (Exception e) {
            LOGGER.error("failed", e);
        }
        return null;
    }

    public User validate(String email, String pwd) {
        User user = userMapper.byEmail(email);
        if (user != null && pwd.equals(user.getPassword())) {
            return user;
        }
        return null;
    }

    public User byEmail(String email) {
        try {
            return userMapper.byEmail(email);
        } catch (Exception e) {
            LOGGER.error("failed", e);
        }
        return null;
    }
}
