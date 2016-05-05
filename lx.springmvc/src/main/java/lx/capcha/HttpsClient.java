package lx.capcha;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
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
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport.Builder;

import utils.HtmlUtils;
import utils.HttpClientUtils;

public class HttpsClient {

    public static void main(String[] args) throws GeneralSecurityException {
        X509TrustManager tm = new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkServerTrusted(X509Certificate[] ax509certificate, String s) throws CertificateException {
            }

            @Override
            public void checkClientTrusted(X509Certificate[] ax509certificate, String s) throws CertificateException {
            }
        };
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(null, new TrustManager[] { tm }, null);
        // 创建SSLSocketFactory
        SSLSocketFactory socketFactory = new SSLSocketFactory(ctx, null);

        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", socketFactory, 443));
        HttpRequestFactory HRF = new ApacheHttpTransport.Builder().setSocketFactory(socketFactory)
                .doNotValidateCertificate().build().createRequestFactory(new HttpRequestInitializer() {
                    public void initialize(HttpRequest request) throws IOException {
                        request.setConnectTimeout(4000);
                        request.setReadTimeout(10000);
                        request.setNumberOfRetries(1);
                        request.setFollowRedirects(true);
                        request.setLoggingEnabled(false);
                        request.setSuppressUserAgentSuffix(false);
                    }
                });

        Map<String, String> params = new HashMap<String, String>();
        params.put("email", "540818255@qq.com");
        params.put("passwd", "123456789");
        params.put("remember_me", "week");

        String htmlAsString = HttpClientUtils.postAsString("https://huaile.me/auth/login", params,
                new HashMap<String, String>(), "");
        System.out.println(htmlAsString);
    }

    public static HttpResponse post(HttpRequestFactory HRF, String url, Map<String, String> param,
            Map<String, String> _headers, Object cookie) {
        UrlEncodedContent hc = new UrlEncodedContent(param);
        try {
            HttpRequest req = HRF.buildPostRequest(new GenericUrl(url), hc);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
            headers.setUserAgent(
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36");
            headers.setCookie(cookie.toString());
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

    public static String postAsString(HttpRequestFactory HRF, String url, Map<String, String> param,
            Map<String, String> _headers, Object cookie) {
        try {
            HttpResponse resp = post(HRF, url, param, _headers, cookie);
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
            e.printStackTrace();
        }
        return null;
    }

}
