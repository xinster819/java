package springmvc.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import lx.capcha.Capcha;
import lx.springmvc.response.StatusResponse;
import lx.springmvc.response.StatusResponse.Status;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import springmvc.dao.UserDao;
import springmvc.vo.User;
import utils.AES;
import utils.CookieUtils;

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
    public StatusResponse loginAjax(@RequestParam("email") String email, @RequestParam("pwd") String pwd,
            @CookieValue(value = "uinfo", required = false) String userInfo, HttpServletResponse resp) {
        if (userInfo != null) {
            System.out.println(AES.decrypt(userInfo));
        }
        User user = userDao.validate(email, pwd);
        if (user == null) {
            return new StatusResponse(Status.FAIL);
        }
        CookieUtils.addCookie("uinfo", AES.encrypt(email), ".sohu.com", "/", 3425324, resp);
        return new StatusResponse(Status.SUCC);
    }

    @RequestMapping()
    public String capcha() {
        return "capcha";
    }

    // eb045d78d273107348b0300c01d29b7552d622abbc6faf81b3ec55359aa9950c

    public static void main(String[] args) {
        try {
            byte[] s = "123456".getBytes("utf-8");
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(s);
            System.out.println(bytesToHexString(digest));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
