package lx.spider;

import java.net.HttpCookie;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.HttpClientUtils;

import com.google.api.client.http.HttpResponse;

public class ShadowSocks {

    static Logger LOGGER = LoggerFactory.getLogger(ShadowSocks.class);

    public static List<String> SITES = new ArrayList<String>();

    static {
        SITES.add("http://www.ssd.la");
        SITES.add("http://camelidc.net");
        SITES.add("http://ss.noteofstudy.com");
    }

    public static void singIn() {
        // System.setProperty("http.proxyHost", "localhost");
        // System.setProperty("http.proxyPort", "8888");
        for (String site : SITES) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("email", "540818255@qq.com");
            params.put("passwd", "123456789");
            params.put("remember_me", "week");
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("Origin", site);
            headers.put("Host", getDomainName(site));
            headers.put("Referer", site + "/user/login.php");
            StringBuilder sb = new StringBuilder();
            HttpResponse post = HttpClientUtils.post(site + "/user/_login.php", params, headers);
            @SuppressWarnings("unchecked")
            List<String> cookies = (List<String>) post.getHeaders().get("set-cookie");
            List<HttpCookie> list = new ArrayList<HttpCookie>();
            for (String cookie : cookies) {
                List<HttpCookie> parse = HttpCookie.parse(cookie.toString());
                list.addAll(parse);
            }
            for (HttpCookie one : list) {
                sb.append(one.getName()).append("=").append(one.getValue()).append(";");
            }
            String html = HttpClientUtils.getHtml(site + "/user/_checkin.php", new HashMap<String, String>(), sb);
            LOGGER.info("sign . result: {}", html);
        }
    }
    
    public static String getDomainName(String url) {
        try {
            URI uri = new URI(url);
            String domain = uri.getHost();
            return domain.startsWith("www.") ? domain.substring(4) : domain;
        } catch (Exception e) {
            LOGGER.error("get domain error. url: {}", url);
        }
        return "";
    }
}
