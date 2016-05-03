package springmvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class AuthInterceptor implements HandlerInterceptor {

    LoadingCache<String, String> userCache = CacheBuilder.newBuilder().maximumSize(10)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String arg0) throws Exception {
                    return null;
                }

            });

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute("god");
        if ("god".equals(attribute)) {
            return true;
        } else {
            response.sendRedirect("/api/sock/login");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

}
