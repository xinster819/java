package springmvc.service;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.api.client.http.HttpResponse;

import springmvc.dao.ShadowSockDao;
import springmvc.vo.ShadowSock;
import utils.HtmlUtils;
import utils.HttpClientUtils;

@Service
public class ShadowSockService {

    static Logger LOGGER = LoggerFactory.getLogger(ShadowSockService.class);

    @Resource
    ShadowSockDao shadowSockDao;

    public ShadowSock byUrl(String url) {
        return shadowSockDao.byUrl(url);
    }

    public List<ShadowSock> all() {
        try {
            return shadowSockDao.all();
        } catch (Exception e) {
            LOGGER.error("select all failed", e);
        }
        return null;
    }

    public void off(ShadowSock s) {
        s.setStatus(ShadowSock.OFF);
        shadowSockDao.updateStatus(s);
    }

    public void createNewOne(ShadowSock s) {
        shadowSockDao.insert(s);
        checkinOne(s);
    }

    public void checkin() {
        List<ShadowSock> list = shadowSockDao.all();
        for (ShadowSock one : list) {
            checkinOne(one);
        }
    }

    public static void main(String[] args) {
        ShadowSock s = new ShadowSock();
        s.setUrl("https://huaile.me:443");
        s.setLoginUri("/auth/login");
        s.setCheckInUri("/user/checkin");
        new ShadowSockService().checkinOne(s);
    }

    public void checkinOne(ShadowSock one) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", "540818255@qq.com");
        params.put("passwd", "123456789");
        params.put("remember_me", "week");
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Origin", one.getUrl());
        headers.put("Host", HtmlUtils.getDomainName(one.getUrl()));
        StringBuilder sb = new StringBuilder();
        try {
            HttpResponse post = HttpClientUtils.post(one.getUrl() + one.getLoginUri(), params, headers, "");
            @SuppressWarnings("unchecked")
            List<String> _cookies = (List<String>) post.getHeaders().get("set-cookie");
            List<HttpCookie> cookies = new ArrayList<HttpCookie>();
            for (String _cookie : _cookies) {
                List<HttpCookie> parse = HttpCookie.parse(_cookie.toString());
                cookies.addAll(parse);
            }
            for (HttpCookie cookie : cookies) {
                sb.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
            }
            String html = HttpClientUtils.postAsString(one.getUrl() + one.getCheckInUri(),
                    new HashMap<String, String>(), new HashMap<String, String>(), sb);
            one.setStatus(ShadowSock.ON);
            one.setCheckInTime(new Date());
            shadowSockDao.checkIn(one);
            LOGGER.info("url: {} result: {}", one.getUrl(), unicodeToChinese(html));
        } catch (Exception e) {
            LOGGER.error("sth wrong. site: {}", one.getUrl(), e);
            one.setStatus(ShadowSock.ERROR);
            one.setCheckInTime(new Date());
            shadowSockDao.checkIn(one);
        }
    }

    public static String unicodeToChinese(String str) {
        if (str.indexOf("\\u") == -1 || str == null || "".equals(str.trim())) {
            return str.replaceAll("\\\\ ", " ");

        }
        StringBuffer sb = new StringBuffer();
        if (!str.startsWith("\\u")) {
            int index = str.indexOf("\\u");
            sb.append(str.substring(0, index));
            str = str.substring(index);
        }
        if (str.endsWith(":")) {
            str = str.substring(0, str.length() - 1);
        }
        String[] chs = str.trim().split("\\\\u");

        for (int i = 0; i < chs.length; i++) {
            String ch = chs[i].trim();
            if (ch != null && !"".equals(ch)) {

                sb.append((char) Integer.parseInt(ch.substring(0, 4), 16));
                if (ch.length() > 4) {
                    sb.append(ch.substring(4));
                }
            }
        }
        return sb.toString();
    }

}
