package springmvc.controller;

import javax.annotation.Resource;

import lx.capcha.Capcha;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import springmvc.dao.UserDao;
import springmvc.vo.User;

@Controller
@RequestMapping("capcha")
public class CapchaController {

    @Resource
    private UserDao userDao;

    @RequestMapping("get")
    public ResponseEntity<byte[]> get() {
        byte[] image = Capcha.getCapcha();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<byte[]>(image, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@RequestParam("user_id") int userId, @RequestParam("pwd") String pwd) {
        User user = userDao.byUserId(userId);
        if (user == null) {
            return "capcha";
        }
        return "hello";
    }

    @RequestMapping()
    public String capcha() {
        return "capcha";
    }
}
