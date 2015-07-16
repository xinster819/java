package lx.springmvc.controller;

import javax.annotation.Resource;

import lx.spring.db.mybatis.CyMybatis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("cy")
public class CyTestController {

    @Resource
    CyMybatis cyMybatis;

    @RequestMapping("select")
    @ResponseBody
    public String db(@RequestParam("isvid") int isvid,
            @RequestParam(value = "encode", defaultValue = "utf-8") String encode) {
        cyMybatis.select(isvid, encode);
        return "dd";
    }
}
