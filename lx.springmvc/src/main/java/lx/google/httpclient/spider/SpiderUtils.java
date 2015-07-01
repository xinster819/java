package lx.google.httpclient.spider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;

public class SpiderUtils {

    static AtomicInteger total = new AtomicInteger();
    static AtomicInteger succ = new AtomicInteger();

    static String src = "E:\\spider\\evil\\";
    static Set<String> urls = new HashSet<String>();
    static File failedPath = new File("E:\\failed\\failed.txt");

    static ExecutorService executor = Executors.newFixedThreadPool(100);
    static NetHttpTransport nht = new NetHttpTransport();
    static HttpRequestFactory hrf = nht.createRequestFactory(new HttpRequestInitializer() {
        @Override
        public void initialize(HttpRequest request) throws IOException {
        }
    });

    static String _prefix = "http://www.lmkuspg.com/";

    public static void spider(String _url) throws IOException {
        if (!_url.startsWith(_prefix) || urls.contains(_url)) {
            return;
        }
        urls.add(_url);
        total.addAndGet(1);
        boolean isOk = false;
        GenericUrl url = new GenericUrl(_url);
        HttpRequest hr = hrf.buildGetRequest(url);
        try {
            HttpResponse resp = hr.execute();
            if (resp.getStatusCode() != 404) {
                String p = resp.parseAsString();
                if (p.contains("wz-picrrc")) {
                    String a = p.split("wz-picrrc")[1];
                    if (a.contains("src=\"")) {
                        a = a.split("src=\"")[1];
                        a = a.split("\"")[0];
                        if (a.endsWith(".jpg") || a.endsWith(".JPG")) {
                            isOk = true;
                            String[] names = a.split("/");
                            executor.execute(new Download(src + "jpg\\", _prefix + a, names[names.length - 1]));
                            succ.addAndGet(1);
                        } else if (a.endsWith(".png")) {
                            isOk = true;
                            String[] names = a.split("/");
                            executor.execute(new Download(src + "png\\", _prefix + a, names[names.length - 1]));
                            succ.addAndGet(1);
                        } else if (a.endsWith(".gif")) {
                            isOk = true;
                            String[] names = a.split("/");
                            executor.execute(new Download(src + "gif\\", _prefix + a, names[names.length - 1]));
                            succ.addAndGet(1);
                        }
                    }
                }
            }
        } catch (HttpResponseException e) {
            if (e.getStatusCode() != 404) {
                System.out.println(e.getStatusCode());
            }
        } catch (SocketTimeoutException e) {
            System.out.println(_url);
        }
        if (!isOk) {
            FileUtils.write(failedPath, _url + "\r\n", true);
        }
    }

    public static void print() {
        System.out.println(total.get() + "--" + succ.get());
    }

    public static class Download implements Runnable {

        String src;
        String img;
        String name;

        public Download(String src, String img, String name) {
            this.img = img;
            this.name = name;
            this.src = src + name;
        }

        @Override
        public void run() {
            URL url;
            try {
                url = new URL(img);
                InputStream is = url.openStream();
                OutputStream os = new FileOutputStream(src);
                byte[] b = new byte[2048];
                int length;
                while ((length = is.read(b)) != -1) {
                    os.write(b, 0, length);
                }
                is.close();
                os.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        
        String _url = "http://changyan.sohu.com/api/labs/barrage/load?end_time=199999&topic_source_id=1862509&show_types=1,2,3&start_time=100000&client_id=cyqyBluaj&topic_title=航海王第652集";
        HttpClient hc = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(_url);
        try {
            org.apache.http.HttpResponse response = hc.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            HttpEntity entity = response.getEntity();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(entity, "utf-8");
                System.out.println(result);
            } else {
                httpGet.abort();
            }
        } catch (Exception e) {
            if (httpGet != null) {
                httpGet.abort();
            }
        } finally {
        }
//        GenericUrl url = new GenericUrl(_url);
//        HttpRequest hr = hrf.buildGetRequest(url);
//        HttpResponse resp = hr.execute();
//        String p = resp.parseAsString();
//        System.out.println(p);
    }
}
