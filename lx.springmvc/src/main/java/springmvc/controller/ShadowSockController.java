package springmvc.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import springmvc.service.ShadowSockService;
import springmvc.vo.ShadowSock;

@Controller
@RequestMapping("sock")
public class ShadowSockController {

    @Resource
    ShadowSockService shadowSockService;

    @RequestMapping("checkin")
    @ResponseBody
    public String all() {
        shadowSockService.checkin();
        return "user";
    }

    @RequestMapping("checkinone")
    @ResponseBody
    public String checkinByUrl(@RequestParam("url") String url) {
        ShadowSock s = shadowSockService.byUrl(url);
        shadowSockService.checkinOne(s);
        return "user";
    }

    @RequestMapping("list")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView();
        List<ShadowSock> list = shadowSockService.all();
        mv.addObject("list", list);
        mv.setViewName("shadow/list");
        return mv;
    }

    @RequestMapping("off")
    @ResponseBody
    public String off(@RequestParam("url") String url) {
        ShadowSock s = shadowSockService.byUrl(url);
        shadowSockService.off(s);
        return "dd";
    }

    @RequestMapping(value = "add", method = { RequestMethod.POST })
    @ResponseBody
    public String add(@ModelAttribute("shadowSock") ShadowSock shadowSock) {
        shadowSockService.createNewOne(shadowSock);
        return "dd";
    }

    @RequestMapping(value = "login")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }

    @RequestMapping(value = "submit", method = { RequestMethod.POST })
    public ModelAndView login(@RequestParam("username") String username, @RequestParam("pwd") String pwd,
            HttpServletRequest req) {
        if ("alchemist".equals(username) && "~!@#$%qwer".equals(pwd)) {
            req.getSession().setAttribute("god", "god");
            req.getSession().setMaxInactiveInterval(1800);
            return list();
        }
        return login();
    }
}
