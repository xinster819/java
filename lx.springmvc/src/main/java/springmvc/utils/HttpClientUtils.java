package springmvc.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport.Builder;

public class HttpClientUtils {

    static Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);

    static HttpRequestFactory HRF = null;

    static {
        try {
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkServerTrusted(X509Certificate[] ax509certificate, String s)
                        throws CertificateException {
                }

                @Override
                public void checkClientTrusted(X509Certificate[] ax509certificate, String s)
                        throws CertificateException {
                }
            };
            SSLContext ssl = SSLContext.getInstance("SSL");
            ssl.init(null, new TrustManager[] { tm }, null);
            Builder builder = new NetHttpTransport.Builder();
            builder.setSslSocketFactory(ssl.getSocketFactory());
            HRF = builder.build().createRequestFactory(new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                    request.setConnectTimeout(4000);
                    request.setReadTimeout(10000);
                    request.setNumberOfRetries(1);
                    request.setFollowRedirects(true);
                    request.setLoggingEnabled(false);
                    request.setSuppressUserAgentSuffix(false);
                }
            });
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public static HttpResponse post(String url, Map<String, String> param, Map<String, String> _headers) {
        UrlEncodedContent hc = new UrlEncodedContent(param);
        try {
            HttpRequest req = HRF.buildPostRequest(new GenericUrl(url), hc);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
            headers.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36");
            for (Entry<String, String> _header : _headers.entrySet()) {
                headers.put(_header.getKey(), _header.getValue());
            }
            req.setHeaders(headers);
            return req.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void printInformation(HttpResponse resp) {
        HttpHeaders headers = resp.getHeaders();
        for (String key : headers.keySet()) {
            System.out.println(key + " -- " + headers.get(key));
        }
        String cookie = resp.getHeaders().getCookie();
        System.out.println(resp.getStatusCode());
        System.out.println(cookie);
    }

    public static String getHtml(String url, Map<String, String> params, Object cookie) {
        HttpRequest req;
        try {
            GenericUrl u = new GenericUrl(url);
            for (Entry<String, String> param : params.entrySet()) {
                u.set(param.getKey(), param.getValue());
            }
            req = HRF.buildGetRequest(u);
            HttpHeaders header = new HttpHeaders();
            header.setCookie(cookie.toString());
            req.setHeaders(header);
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
            printInformation(resp);
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
