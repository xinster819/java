package springmvc.controller.root;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import springmvc.spring.SpringPropertyPlaceholderConfigurer;

@Controller
@RequestMapping("root")
public class ProjectStatusController {

    @Resource
    private SpringPropertyPlaceholderConfigurer springPropertyPlaceholderConfigurer;

    @RequestMapping("props")
    @ResponseBody
    public String pros() {
        return springPropertyPlaceholderConfigurer.printProps();
    }

}
