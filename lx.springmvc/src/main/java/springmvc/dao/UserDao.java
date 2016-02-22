package springmvc.dao;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import springmvc.dao.mapper.UserMapper;
import springmvc.vo.User;

@Repository
public class UserDao implements BaseDao<User> {

    static Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public boolean update(User one) {
        return false;
    }

    @Override
    public boolean delete(User one) {
        return false;
    }

    @Override
    public User byId(long id) {
        try {
            return userMapper.byUserId((int) id);
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
