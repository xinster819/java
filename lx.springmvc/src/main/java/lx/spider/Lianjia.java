package lx.spider;

import java.io.IOException;
import java.util.ListIterator;

import org.apache.commons.lang.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Lianjia {
    static String NAN_SHA_TAN = "http://bj.lianjia.com/ershoufang/nanshatan1/pg%dtf1bp250ep330/";

    public static void main(String[] args) {
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
}
