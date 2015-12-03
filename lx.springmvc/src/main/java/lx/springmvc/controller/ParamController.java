package lx.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @ResponseBody
    public String user() {
        return "user";
    }
}
