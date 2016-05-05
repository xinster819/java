package springmvc.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import springmvc.dao.UserDao;
import springmvc.vo.User;

@Service
public class UserService {

    @Resource
    private UserDao userDao;

    public void userDao() {
        userDao.byId(1);
    }

    public void createUser() {
        User user = new User();
        user.setEmail("5408182445@qq.com");
        user.setNick("god");
        user.setPassword("123456");
        user.setPortrait("por");
        user.setCtime(new Date());
        userDao.insert(user);
    }
}
