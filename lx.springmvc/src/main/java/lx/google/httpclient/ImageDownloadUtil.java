package lx.google.httpclient;

import static lx.google.httpclient.HttpClientUtils.get;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;

public class ImageDownloadUtil {

    static AtomicInteger total = new AtomicInteger();
    static AtomicInteger succ = new AtomicInteger();

    static String src = "E:\\spider\\test\\";
    static Set<String> urls = new HashSet<String>();
    static File failedPath = new File("E:\\failed\\failed.txt");

    static ExecutorService executor = Executors.newFixedThreadPool(100);
    static HttpRequestFactory hrf = new NetHttpTransport().createRequestFactory();

    // static String _prefix = "http://www.lmkuspg.com/";

    public static void spider(String _url, String encode) throws IOException {
        // if (!_url.startsWith(_prefix) || urls.contains(_url)) {
        // return;
        // }
        urls.add(_url);
        total.addAndGet(1);
        String html = get(_url, encode, new HashMap<String, String>(), new HashMap<String, String>());
        if (StringUtils.isNotEmpty(html)) {
            jsoup(html);
        }
    }

    public static boolean jsoup(String html) {
        Document doc = Jsoup.parse(html);
        Elements list = doc.getElementsByClass("gif-n-box");
        Iterator<Element> iterator = list.iterator();
        while (iterator.hasNext()) {
            Elements imgs = iterator.next().getElementsByTag("img");
            Iterator<Element> i = imgs.iterator();
            while (i.hasNext()) {
                Element img = i.next();
                String imageUrl = img.attr("src");
                if (imageUrl.endsWith(".jpg") || imageUrl.endsWith(".JPG")) {
                    String[] names = imageUrl.split("/");
                    executor.execute(new Download(src + "jpg\\", imageUrl, names[names.length - 1]));
                    succ.addAndGet(1);
                } else if (imageUrl.endsWith(".png")) {
                    String[] names = imageUrl.split("/");
                    executor.execute(new Download(src + "png\\", imageUrl, names[names.length - 1]));
                    succ.addAndGet(1);
                } else if (imageUrl.endsWith(".gif")) {
                    String[] names = imageUrl.split("/");
                    executor.execute(new Download(src + "gif\\", imageUrl, names[names.length - 1]));
                    succ.addAndGet(1);
                }
            }
        }
        return false;
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
        spider("http://www.gaoxiaogif.com/zhenrengif/2847.html", "gb2312");
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
        }
        System.exit(0);
    }

}
