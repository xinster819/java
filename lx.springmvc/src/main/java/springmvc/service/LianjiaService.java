package springmvc.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;

import org.apache.commons.lang.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LianjiaService {
    static String NAN_SHA_TAN = "http://bj.lianjia.com/ershoufang/nanshatan1/pg%dtf1bp250ep330/";

    public void main() {
        try {
            for (int i = 0; i < 3; i++) {
                Document doc = Jsoup.connect(String.format(NAN_SHA_TAN, i)).get();
                Element houseList = doc.getElementById("house-lst");
                Elements list = houseList.getElementsByTag("li");
                ListIterator<Element> iter = list.listIterator();
                while (iter.hasNext()) {
                    Element li = iter.next();
                    String id = li.attr("data-id");
                    Elements infos = li.getElementsByClass("info-panel");
                    if (infos.iterator().hasNext()) {
                        Element info = infos.iterator().next();
                        System.out.println(id);
                        int price = NumberUtils.toInt(info.getElementsByClass("num").get(0).text());
                        String _desp = info.getElementsByClass("where").get(0).text();
                        String[] desp = _desp.split("  ");
                        String plotName = desp[0];
                        String structure = desp[1];
                        double area = NumberUtils.toDouble(desp[2].split("平米")[0], 0);
                        String direction = desp[3];
                        String _con = info.getElementsByClass("con").get(0).text();
                        String[] con = _con.split("/");
                        String floor = con[1];
                        int constructYear = NumberUtils.toInt(con[2].split("年")[0], 0);
                        String url = info.getElementsByTag("a").get(0).attr("href");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean validateIcp(String url) {
        try {
            String u = "http://beian.links.cn/beian.asp?beiantype=domain&keywords=" + URLEncoder.encode(url, "utf-8");
            Document doc = Jsoup.connect(u).get();
            Elements elements = doc.getElementsByClass("longrowcontent");
            if (elements == null || elements.size() == 0) {
                return false;
            }
            Element result = elements.get(0);
            if (result.toString().contains("备案已注销") || result.toString().contains("未备案")) {
                return false;
            }
            Elements trs = result.getElementsByTag("tr");
            if(trs.size() != 3 ) {
                return false;
            }
            Element tr = trs.get(2);
            Elements tds = tr.getElementsByTag("td");
            if(tds.size() != 8) {
                return false;
            }
            String cao = tds.get(4).getElementsByTag("a").get(0).text();
            System.out.println(cao);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Set<String> urls = new HashSet<String>();
        urls.add("http://www.hooxx.net");
        urls.add("http://www.haotor.com/");
        urls.add("http://www.haokongbu.com");
        urls.add("1235");
        for (String url : urls) {
            boolean r = validateIcp(url);
            System.out.println(url + ": " + r);
        }
    }
}
