package com.sohu.mpV2.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sohu.mpV2.service.MpNewsService;
import com.sohu.mpV2.vo.MpNews;

@Controller
@RequestMapping("news")
public class MpNewsController {

    @Resource
    private MpNewsService mpNewsService;

    @RequestMapping("/{id}")
    @ResponseBody
    public String p(@RequestParam("p") String p) {
        MpNews n = mpNewsService.byId(101674, p);
        System.out.println(n.getContent());
        return "ok";
        
    }

}
