package springmvc.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import springmvc.service.DemoService;

@Controller
@RequestMapping("demo")
public class DemoController {

    @Resource
    private DemoService demoService;

    @RequestMapping("show/{id}")
    public ModelAndView show(@PathVariable("id") int id) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("name", demoService.byId(id));
        mv.setViewName("show");
        return mv;
    }
}
