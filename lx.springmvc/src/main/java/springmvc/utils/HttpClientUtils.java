package springmvc.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;

public class HttpClientUtils {

    static Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);

    static HttpRequestFactory HRF = new NetHttpTransport().createRequestFactory(new HttpRequestInitializer() {
        public void initialize(HttpRequest request) throws IOException {
            request.setConnectTimeout(4000);
            request.setReadTimeout(10000);
            request.setNumberOfRetries(1);
            request.setFollowRedirects(true);
            request.setLoggingEnabled(false);
        }
    });

    public static String getHtml(String url, Map<String, String> params) {
        HttpRequest req;
        try {
            GenericUrl u = new GenericUrl(url);
            for (Entry<String, String> param : params.entrySet()) {
                u.set(param.getKey(), param.getValue());
            }
            req = HRF.buildGetRequest(u);
            HttpResponse resp = req.execute();
            InputStream content = resp.getContent();
            if (content == null) {
                return null;
            }
            BufferedInputStream bis = new BufferedInputStream(content);
            String charset = resp.getContentCharset().name();
            if (StringUtils.isEmpty(charset) || "ISO-8859-1".equals(charset)) {
                charset = HtmlUtils.detect(bis);
            }
            return IOUtils.toString(bis, charset);
        } catch (IOException e) {
            LOGGER.error("get html from failed. url: {} reason: {}", url, e);
        }
        return null;
    }

    public static String getHtmlAsString(String url) {
        Html html = getHtml(url);
        try {
            if (html != null) {
                return IOUtils.toString(html.getBis(), html.getCharset());
            }
        } catch (Exception e) {
            LOGGER.error("get getHtmlAsString from failed. url: {} reason: {}", url, e);
        } finally {
            if (html != null) {
                html.close();
            }
        }
        return null;
    }

    private static Html getHtml(String url) {
        HttpRequest req;
        HttpResponse resp = null;
        InputStream in = null;
        try {
            req = HRF.buildGetRequest(new GenericUrl(url));
            resp = req.execute();
            if (resp == null) {
                return null;
            }
            in = resp.getContent();
            BufferedInputStream bis = new BufferedInputStream(in);
            String charset = resp.getContentCharset().name();
            if (StringUtils.isEmpty(charset) || "ISO-8859-1".equals(charset)) {
                charset = HtmlUtils.detect(bis);
            }
            return new Html(bis, charset);
        } catch (HttpResponseException e) {
            LOGGER.error("httpresponseException - get html from failed. url: {} reason: {}-{}", url, e.getStatusCode(),
                    e.getStatusMessage());
        } catch (IOException e) {
            LOGGER.error("get html from failed. url: {} reason: {}", url, e.getMessage());
        }
        if (in != null) {
            try {
                in.close();
                if (resp != null) {
                    resp.disconnect();
                }
            } catch (IOException e) {
                LOGGER.error("InputStream close failed. url: {} reason: {}", url, e);
            }
        }
        return null;
    }

    static class Html {
        BufferedInputStream bis;
        String charset;

        public Html(BufferedInputStream bis, String charset) {
            this.bis = bis;
            this.charset = charset;
        }

        public BufferedInputStream getBis() {
            return bis;
        }

        public void setBis(BufferedInputStream bis) {
            this.bis = bis;
        }

        public String getCharset() {
            return charset;
        }

        public void setCharset(String charset) {
            this.charset = charset;
        }

        public void close() {
            try {
                bis.close();
            } catch (IOException e) {
                // logger
            }
        }
    }
}
