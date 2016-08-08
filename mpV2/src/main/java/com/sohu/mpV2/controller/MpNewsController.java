package com.sohu.mpV2.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sohu.mpV2.response.BaseResponse;
import com.sohu.mpV2.response.NewsResponse;
import com.sohu.mpV2.response.ResponseStatus;
import com.sohu.mpV2.service.MpMediaProfileService;
import com.sohu.mpV2.service.MpNewsService;
import com.sohu.mpV2.vo.MpMediaProfile;
import com.sohu.mpV2.vo.MpNews;

@Controller
@RequestMapping("news")
public class MpNewsController {

    @Resource
    private MpNewsService mpNewsService;

    @Resource
    private MpMediaProfileService mpMediaProfileService;

    @RequestMapping("/{id}")
    @ResponseBody
    public BaseResponse p(@PathVariable("id") int id, @RequestParam("p") String p, HttpServletResponse response) {
        response.setContentType("application/json");
        MpNews n = mpNewsService.byId(id, p);
        if (n == null) {
            return new BaseResponse(ResponseStatus.FAIL);
        }
        MpMediaProfile profile = mpMediaProfileService.byMpMediaId(p);
        if (profile == null) {
            return new BaseResponse(ResponseStatus.FAIL);
        }
        NewsResponse resp = new NewsResponse(ResponseStatus.SUCC);
        // n.setContent(StringEscapeUtils.escapeHtml(n.getContent()));
        resp.setMpNews(n);
        resp.setExpert(mpMediaProfileService.isExpert(profile.getId()));
        return resp;
    }
}
