package com.sohu.mpV2.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sohu.mpV2.response.BaseResponse;
import com.sohu.mpV2.response.CodeAreaResponse;
import com.sohu.mpV2.response.ResponseStatus;
import com.sohu.mpV2.service.CodeAreaService;

@Controller
@RequestMapping("c")
public class CodeAreaController {

    @Resource
    private CodeAreaService codeAreaService;

    @RequestMapping("all")
    @ResponseBody
    public BaseResponse all() {
        CodeAreaResponse resp = new CodeAreaResponse(ResponseStatus.SUCC);
        resp.setProvinces(codeAreaService.getAllProvince());
        resp.setCities(codeAreaService.getAllCity());
        return resp;
    }

}
