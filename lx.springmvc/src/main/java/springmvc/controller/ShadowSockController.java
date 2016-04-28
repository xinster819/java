package springmvc.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("list")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView();
        List<ShadowSock> list = shadowSockService.all();
        mv.addObject("list", list);
        mv.setViewName("shadow/list");
        return mv;
    }
}
