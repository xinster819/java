package lx.springmvc.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;


/**
 * 这个是html版错误返回结果
 * @author alchemistli
 */
public class TestExceptionResolver extends SimpleMappingExceptionResolver {

    public TestExceptionResolver() {
        System.out.println("进来了");
    }
    
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {

        if (ex instanceof HttpMessageNotReadableException) {
            // HTTP CODE 400
            System.out.println("400");
        } else if (ex instanceof ConversionNotSupportedException) {
            // HTTP CODE 500
            System.out.println("500");
        } else if (ex instanceof NoHandlerFoundException) {
            // HTTP CODE 404
            System.out.println("404");
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("123", "456");
        // View view = new InternalResourceView("/error.jsp");
        // mv.setView(view);
        mv.setViewName("error");
        return mv;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        System.out.println("1234");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/error.jsp");
        return mv;
    }

}
