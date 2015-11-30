package lx.spider;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.client.http.HttpResponse;

import utils.HttpClientUtils;

public class Ssd {

    public static void main(String[] args) {
        System.setProperty("http.proxyHost", "localhost");
        System.setProperty("http.proxyPort", "8888");
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", "540818255@qq.com");
        params.put("passwd", "123456789");
        params.put("remember_me", "week");
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Origin", "http://www.ssd.la/");
        headers.put("Host", "ssd.la");
        headers.put("Referer", "http://www.ssd.la/user/login.php");

        StringBuilder sb = new StringBuilder();
        HttpResponse post = HttpClientUtils.post("http://www.ssd.la/user/_login.php", params, headers);
        List<String> cookies = (List<String>) post.getHeaders().get("set-cookie");
        List<HttpCookie> list = new ArrayList<HttpCookie>();
        for (String cookie : cookies) {
            List<HttpCookie> parse = HttpCookie.parse(cookie.toString());
            list.addAll(parse);
        }
        for (HttpCookie one : list) {
            sb.append(one.getName()).append("=").append(one.getValue()).append(";");
        }
        System.out.println(sb);
        String html = HttpClientUtils.getHtml("http://www.ssd.la/user/_checkin.php", new HashMap<String, String>(),
                sb);
        System.out.println(html);
        // String content =
        // HttpClientUtils.getHtmlAsString("http://camelidc.net/user/login.php");
        // System.out.println(content);
    }
}
