package springmvc.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import springmvc.dao.UserDao;
import springmvc.response.BaseResponse;
import springmvc.response.ResponseStatus;
import springmvc.vo.User;
import utils.AES;
import utils.CookieUtils;

@Controller
@RequestMapping("capcha")
public class LoginController {

    @Resource
    private UserDao userDao;

    @RequestMapping(value = "login", method = { RequestMethod.GET, RequestMethod.POST })
    public String login(@RequestParam("user_id") int userId, @RequestParam("pwd") String pwd) {
        User user = userDao.byUserId(userId);
        if (user == null) {
            return "capcha";
        }
        return "hello";
    }

    @RequestMapping(value = "loginAjax", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public BaseResponse loginAjax(@RequestParam("email") String email, @RequestParam("pwd") String pwd,
            @CookieValue(value = "uinfo", required = false) String userInfo, HttpServletResponse resp) {
        if (userInfo != null) {
            System.out.println(AES.decrypt(userInfo));
        }
        User user = userDao.validate(email, pwd);
        if (user == null) {
            return new BaseResponse(ResponseStatus.FAIL);
        }
        CookieUtils.addCookie("uinfo", AES.encrypt(email), ".sohu.com", "/", 3425324, resp);
        return new BaseResponse(ResponseStatus.SUCC);
    }

    @RequestMapping()
    public String capcha() {
        return "capcha";
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv + " ");
        }
        return stringBuilder.toString();
    }
}
