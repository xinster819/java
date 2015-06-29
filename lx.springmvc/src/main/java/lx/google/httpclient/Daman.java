package lx.google.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;

public class Daman {

    static List<Character> num_word = new ArrayList<Character>();
    static {
        for (char i = '0'; i <= '9'; i++) {
            num_word.add(i);
        }
        for (char i = 'A'; i <= 'Z'; i++) {
            num_word.add(i);
        }
    }

    static ExecutorService executor = Executors.newFixedThreadPool(100);

    static NetHttpTransport nht = new NetHttpTransport();

    public static void main(String[] args) throws IOException {
        HttpRequestFactory hrf = nht.createRequestFactory(new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) throws IOException {
            }
        });
        String _url = "http://www.whxxn.com/guests/";
        while (true) {
            GenericUrl url = new GenericUrl(_url);
            HttpRequest hr = hrf.buildGetRequest(url);
            try {
                HttpResponse resp = hr.execute();
                if (resp.getStatusCode() != 404) {
                    System.out.println(_url);
                    executor.execute(new Download(_url, "10301.jpg"));
                }
            } catch (HttpResponseException e) {
                if (e.getStatusCode() != 404) {
                    System.out.println(e.getStatusCode());
                }
            }
        }
    }

    public static class Download implements Runnable {

        String img;
        String name;

        public Download(String img, String name) {
            this.img = img;
            this.name = name;
        }

        @Override
        public void run() {
            URL url;
            try {
                url = new URL(img);
                InputStream is = url.openStream();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
