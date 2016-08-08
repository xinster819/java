package com.sohu.mpV2.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sohu.mpV2.response.BaseResponse;
import com.sohu.mpV2.response.ObjectResponse;
import com.sohu.mpV2.response.ResponseStatus;
import com.sohu.mpV2.service.MpChannelService;

@Controller
@RequestMapping("ch")
public class MpChannelController {

    @Resource
    private MpChannelService mpChannelService;

    @RequestMapping("all")
    @ResponseBody
    public BaseResponse getAllChannels() {
        ObjectResponse resp = new ObjectResponse(ResponseStatus.SUCC);
        resp.setObject(mpChannelService.activeChannels());
        return resp;
    }

}
