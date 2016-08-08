package com.sohu.mpV2.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignatureUtil {

    private static final Logger log = LoggerFactory.getLogger(SignatureUtil.class);

    public static String getAppSignature(Map<String, ?> params, String secret) {
        List<String> list = new ArrayList<String>();
        for (Map.Entry<String, ?> e : params.entrySet()) {
            list.add(e.getKey() + "=" + String.valueOf(e.getValue()));
        }
        Collections.sort(list);
        return getAppSignature(list, secret);
    }

    public static String getAppSignature(List<String> params, String secret) {
        if (params == null || secret == null) {
            return null;
        }
        return generateSignature(params, secret);
    }

    public static String generateSignature(List<String> params, String secret) {

        if (params == null || secret == null) {
            log.warn("params or secret is null");
            return null;
        }
        Collections.sort(params);
        StringBuilder buf = new StringBuilder();
        for (String p : params) {
            buf.append(p);
        }
        buf.append(secret);
        return md5(buf.toString());
    }

    public static String md5(String s) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            StringBuilder result = new StringBuilder();
            byte[] bs = md.digest(s.getBytes("UTF-8"));
            for (int i = 0; i < bs.length; i++) {
                byte b = bs[i];
                result.append(Integer.toHexString((b & 0xf0) >>> 4));
                result.append(Integer.toHexString(b & 0x0f));
            }
            return result.toString();
        } catch (java.security.NoSuchAlgorithmException ex) {
            // ignore
        } catch (UnsupportedEncodingException uee) {
            // ignore
        }
        return "";
    }
}
