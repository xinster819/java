package lx.google.httpclient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.IOUtils;

public class HttpClientUtils {
    static HttpRequestFactory HRF = new NetHttpTransport().createRequestFactory();

    public static String get(String _url, String encode, Map<String, String> _params, Map<String, String> _headers) {
        try {
            GenericUrl url = new GenericUrl(_url);
            url.putAll(_params);
            HttpRequest hr = HRF.buildGetRequest(url);
            HttpHeaders headers = new HttpHeaders();
            for (Entry<String, String> entry : _headers.entrySet()) {
                headers.set(entry.getKey(), entry.getValue());
            }
            hr.setHeaders(headers);
            HttpResponse resp = hr.execute();
            if (resp.getStatusCode() == HttpStatusCodes.STATUS_CODE_OK) {
                return parseAsString(resp, encode);
            }
        } catch (HttpResponseException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean download(String _url, String encode, Map<String, String> _params, Map<String, String> _headers) {
        String html = get(_url, encode, _params, _headers);
        jsoup(html);
        return true;
    }

    public static String parseAsString(HttpResponse resp, String encode) {
        InputStream content;
        try {
            content = resp.getContent();
            if (content == null) {
                return "";
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            IOUtils.copy(content, out);
            return out.toString(encode);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void jsoup(String html) {
        Document doc = Jsoup.parse(html);
        Elements list = doc.getElementsByClass("gif-n-box");
        Iterator<Element> iterator = list.iterator();
        while (iterator.hasNext()) {
            Elements imgs = iterator.next().getElementsByTag("img");
            Iterator<Element> i = imgs.iterator();
            while (i.hasNext()) {
                Element img = i.next();
                String src = img.attr("src");
                System.out.println(src);
            }
        }
    }

    public static void main(String[] args) {
        // String url = "http://changyan.sohu.com/api/labs/barrage/submit";
        // Map<String, String> headers = new HashMap<String, String>();
        // headers.put("X-Forwarded-For", "111.111.111.111");
        // Map<String, String> params = new HashMap<String, String>();
        // params.put("client_id", "cyqRdz1DK");
        // params.put("topic_id", "135630068");
        // params.put("content", "发个弹幕234");
        // params.put("start_time", "9000");
        // params.put("show_type", "1");
        // params.put("color", "#fffff");
        // params.put("font_size", "1");
        // params.put("access_token", "5900558ada336096843eefc4142a7700");
        String url = "http://www.gaoxiaogif.com/dongwugif/3136.html";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> params = new HashMap<String, String>();
        download(url, "gb2312", params, headers);
    }
}
