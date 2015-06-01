package lx.springmvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class TraceLoggerInterceptor implements HandlerInterceptor {

    LoadingCache<String, Long> cache = CacheBuilder.newBuilder().maximumSize(500).build(new CacheLoader<String,Long>() {
        @Override
        public Long load(String key) throws Exception {
            return -1L;
        }
    });

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String key = Thread.currentThread().getId() + "#" + request.getRequestURI() + ":";
        cache.put(key, System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        String key = Thread.currentThread().getId() + "#" + request.getRequestURI() + ":";
        Long start = cache.getIfPresent(key);
        if (start != null && start > 0) {
            System.out.println(System.currentTimeMillis() - start);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

}
