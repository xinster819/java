package lx.springmvc.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class TestExceptionResolver extends SimpleMappingExceptionResolver {

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {

        System.out.println("1234");
        ModelAndView mv = new ModelAndView();
//        View view = new InternalResourceView("/error.jsp");
//        mv.setView(view);
        mv.setViewName("/error.jsp");
        return mv;
    }

}
