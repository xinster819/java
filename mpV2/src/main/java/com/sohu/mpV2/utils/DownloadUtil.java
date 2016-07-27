package com.sohu.mpV2.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * 下载指定地址内容，主要是图片
 * 
 * @author alchemist
 */
public class DownloadUtil {

    public static void fileDownload(String des, String _url) {
        URL url;
        try {
            url = new URL(_url);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(des);

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
