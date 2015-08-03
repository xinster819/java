package lx.springmvc.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lx.jedis.KeyUtils;
import lx.spring.db.MybatisService;
import lx.spring.db.jdbc.SpringDb;
import lx.spring.db.mybatis.Mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

@Controller
public class TestController {

    @Resource
    SpringDb springDb;

    @Resource
    KeyUtils keyUtils;

    @Autowired
    Mybatis mybatis;

    @Resource
    MybatisService mybatisService;

    @RequestMapping("db")
    @ResponseBody
    public String db() {
//        return String.valueOf(springDb.go());
        return "dd";
    }

    @RequestMapping("mybatis")
    @ResponseBody
    public String mybatis() {
        mybatisService.print();
        return "123";
    }

    @RequestMapping("insert")
    @ResponseBody
    public String mybatisInsert() throws Exception {
        mybatisService.insert();
        return "123";
    }

    @RequestMapping("insert2")
    @ResponseBody
    public String mybatisInsert2() {
        mybatis.insert();
        return "123";
    }

    @RequestMapping("insert3")
    @ResponseBody
    public String mybatisInsert3() {
        springDb.insert();
        return "123";
    }

    @RequestMapping("/jedis")
    @ResponseBody
    public String test() {
        keyUtils.getIsvCmtLatestKey(3910, "hot");
        return "dd";
    }

    @RequestMapping("/count")
    public ModelAndView hello(@RequestParam("date") int date) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("datas", springDb.select(date));
        mv.setViewName("hello");
        return mv;
    }

    @RequestMapping("/ee")
    @ResponseBody
    public String ee(@RequestParam("tag") String tag, HttpServletRequest req)
            throws NoSuchRequestHandlingMethodException {
        if ("error".equals(tag)) {
            throw new NoSuchRequestHandlingMethodException(req);
        }
        return "succ";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                InputStream is = file.getInputStream();
                InputStreamReader r = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(r);
                while (reader.ready()) {
                    System.out.println(reader.readLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "dd";
    }
}
