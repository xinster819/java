package lx.springmvc.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lx.spring.db.SpringDb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

@Controller
public class TestController {

    @Resource
    SpringDb springDb;
    
    @RequestMapping("db")
    @ResponseBody
    public long db() {
        return springDb.go();
    }
    
    @RequestMapping("/dd")
    @ResponseBody
    public String test() {
        return "dd";
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
