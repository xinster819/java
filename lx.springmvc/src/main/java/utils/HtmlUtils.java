package utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 负责解析页面中指定标签中的数据。 例： title， charset // *
 */
public class HtmlUtils {

    private static final int META_TAG_BUFFER_SIZE = 8192;

    // (?s)是Dotall mode开启、 (?i) 是case_insensitive开启
    // 匹配 <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    private static final Pattern HTTP_EQUIV_PATTERN = Pattern
            .compile(
                    "<meta\\s+http-equiv\\s*=\\s*['\\\"]*\\s*" + "Content-Type['\\\"]*\\s+content\\s*=\\s*['\\\"]"
                            + "text/html;\\s*charset\\s*=\\s*([^'\\\"]+)['\\\"]",
                    Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

    // 匹配<meta charset="utf-8">
    private static final Pattern META_CHARSET_PATTERN = Pattern
            .compile("<meta\\s+charset\\s*=\\s*['\\\"]([^'\\\"]+)['\\\"]", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

    private static final Pattern TITLE_PATTERN = Pattern.compile("\\<title>(.*)\\</title>",
            Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    private static Pattern ALL_LINKS = Pattern.compile("<\\s*?a.*?href=\"(.*?)\".*?>",
            Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

    private static final Charset ASCII = Charset.forName("US-ASCII");

    public static String detect(InputStream input) throws IOException {
        if (input == null) {
            return null;
        }

        // Read enough of the text stream to capture possible meta tags
        input.mark(META_TAG_BUFFER_SIZE);
        byte[] buffer = new byte[META_TAG_BUFFER_SIZE];
        int n = 0;
        int m = input.read(buffer);
        while (m != -1 && n < buffer.length) {
            n += m;
            m = input.read(buffer, n, buffer.length - n);
        }
        input.reset();

        // Interpret the head as ASCII and try to spot a meta tag with
        // a possible character encoding hint
        String charset = null;
        String head = ASCII.decode(ByteBuffer.wrap(buffer, 0, n)).toString();
        Matcher equiv = HTTP_EQUIV_PATTERN.matcher(head);
        if (equiv.find()) {
            charset = equiv.group(1);
        }
        if (charset == null) {
            Matcher meta = META_CHARSET_PATTERN.matcher(head);
            if (meta.find()) {
                charset = meta.group(1);
            }
        }
        return charset;
    }

    public static String getPageTitle(InputStream input, String charset) throws IOException {
        if (input == null) {
            return null;
        }

        // Read enough of the text stream to capture possible meta tags
        input.mark(META_TAG_BUFFER_SIZE);
        byte[] buffer = new byte[META_TAG_BUFFER_SIZE];
        int n = 0;
        int m = input.read(buffer);
        while (m != -1 && n < buffer.length) {
            n += m;
            m = input.read(buffer, n, buffer.length - n);
        }
        input.reset();
        String content = new String(buffer, charset);
        // extract the title
        Matcher matcher = TITLE_PATTERN.matcher(content);
        if (matcher.find()) {
            return matcher.group(1).replaceAll("[\\s\\<>]+", " ").trim();
        } else {
            return null;
        }
    }

    public static List<String> getLinks(String content) {
        Matcher matcher = ALL_LINKS.matcher(content);
        List<String> list = new ArrayList<String>();
        while (matcher.find()) {
            if (matcher.group(1).startsWith("http://")) {
                list.add(matcher.group(1));
            }
        }
        return list;
    }

    public static List<String> getLinks(String content, String host) {
        Matcher matcher = ALL_LINKS.matcher(content);
        List<String> list = new ArrayList<String>();
        while (matcher.find()) {
            if (matcher.group(1).startsWith("http://")) {
                if (matcher.group(1).contains(host)) {
                    list.add(matcher.group(1));
                }
            }
        }
        return list;
    }

    public static String getDomainName(String url) {
        try {
            URI uri = new URI(url);
            String domain = uri.getHost();
            return domain.startsWith("www.") ? domain.substring(4) : domain;
        } catch (Exception e) {
        }
        return "";
    }
}
