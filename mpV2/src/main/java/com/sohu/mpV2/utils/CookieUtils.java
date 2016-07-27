package com.sohu.mpV2.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

    public static boolean isExist(String key, HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            return false;
        }
        for (Cookie cook : cookies) {
            if (cook.getName().equals(key)) {
                return true;
            }
        }
        return false;
    }

    public static void addCookie(String key, String value, String domain, String path, int maxAge,
            HttpServletResponse resp) {
        Cookie cookie = new Cookie(key, value);
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        resp.addCookie(cookie);
    }

}
