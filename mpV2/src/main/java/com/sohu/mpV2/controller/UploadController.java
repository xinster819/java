package com.sohu.mpV2.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import com.sohu.mpV2.utils.ImageUtil;

@Controller
@RequestMapping("upload")
public class UploadController {

    @RequestMapping(value = "1", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String r(@RequestParam("image")MultipartFile f) {
        try {
            ImageUtil.test1(f.getBytes());
            ImageUtil.test(f.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ImageProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MetadataException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "ok";
    }
    
    @RequestMapping("3")
    public ModelAndView u() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("show");
        return mv;
    }
    
}
