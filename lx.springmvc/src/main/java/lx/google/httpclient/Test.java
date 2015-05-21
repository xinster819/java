package lx.google.httpclient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.http.javanet.NetHttpTransport;

public class Test {

    static NetHttpTransport nht = new NetHttpTransport();

    public static void main(String[] args) throws IOException {
        HttpRequestFactory hrf = nht.createRequestFactory(new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) throws IOException {
            }
        });
        GenericUrl url = new GenericUrl("http://changyan.sohu.com/api/labs/hotnewswall/load");
        url.set("client_id", "cyqemw6s1");
        HttpRequest hr = hrf.buildGetRequest(url);
        Map<String, String> map = new HashMap<String, String>();
        map.put("client", "ssss");
        HttpContent hc = new UrlEncodedContent(map);
        HttpResponse resp = hr.execute();
        String s = resp.parseAsString();
        System.out.println(s);
    }
}
