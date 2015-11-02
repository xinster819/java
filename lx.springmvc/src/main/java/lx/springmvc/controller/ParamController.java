package lx.springmvc.controller;

import java.util.List;

import lx.springmvc.vo.User;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用于测试spring mvc的接口传复杂参数
 * 
 * @author alchemist
 */
@Controller
@RequestMapping("user")
public class ParamController {

    @RequestMapping("u")
    public String user() {
        return "user";
    }

    @RequestMapping("a")
    @ResponseBody
    public String first(@ModelAttribute("user") User user, @RequestParam("id") int id, BindingResult bindingResult) {
        System.out.println(user == null ? "cao" : user.getName());
        for (String friend : user.getFriends()) {
            System.out.println(friend);
        }
        return "";
    }

    @RequestMapping("b")
    @ResponseBody
    public String second(@ModelAttribute("users") List<User> users) {
        for (User user : users) {
            System.out.println(user == null ? "cao" : user.getName());
            for (String friend : user.getFriends()) {
                System.out.println(friend);
            }
        }
        return "";
    }

    @RequestMapping("c")
    @ResponseBody
    public String third(@ModelAttribute("user") User user, @ModelAttribute("user2") User user2,
            @RequestParam("id") int id) {
        System.out.println(user.getId());
        System.out.println(user2.getId());
        System.out.println(id);
        return "";
    }

}
