package com.sohu.mpV2.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sohu.mpV2.response.BaseResponse;
import com.sohu.mpV2.response.ProfileResponse;
import com.sohu.mpV2.response.ResponseStatus;
import com.sohu.mpV2.service.MpMediaProfileService;
import com.sohu.mpV2.vo.MpMediaProfile;

@Controller
@RequestMapping("p")
public class MpMediaProfileController {

    @Resource
    private MpMediaProfileService mpMediaProfileService;

    @RequestMapping("{id}")
    @ResponseBody
    public String show(@PathVariable("id") int id) {
        MpMediaProfile byId = mpMediaProfileService.byId(id);
        System.out.println(byId.getMpMediaId());
        return byId.getSiteUrl();
    }

    @RequestMapping("/full")
    @ResponseBody
    public BaseResponse fullProfile(@RequestParam("passport") String passport) {
        MpMediaProfile profile = mpMediaProfileService.byMpMediaId(passport);
        if (profile == null) {
            return new BaseResponse(ResponseStatus.FAIL);
        }
        ProfileResponse resp = new ProfileResponse(ResponseStatus.SUCC);
        resp.setMpMediaProfile(profile);
        return resp;
    }
}
