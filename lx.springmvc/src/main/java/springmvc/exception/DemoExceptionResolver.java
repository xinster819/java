package springmvc.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * 这个是html版错误返回结果
 * 
 * @author alchemistli
 */
public class DemoExceptionResolver extends SimpleMappingExceptionResolver {

    static Logger LOGGER = LoggerFactory.getLogger(DemoExceptionResolver.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {

        if (ex instanceof HttpMessageNotReadableException) {
            // HTTP CODE 400, do something
            LOGGER.info("http_code: {}, msg: {}", 400, ex.getMessage());
        } else if (ex instanceof ConversionNotSupportedException) {
            // HTTP CODE 500, do something
            LOGGER.info("http_code: {}, msg: {}", 500, ex.getMessage());
        } else if (ex instanceof NoHandlerFoundException) {
            // HTTP CODE 404, do something
            LOGGER.info("http_code: {}, msg: {}", 404, ex.getMessage());
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg", ex.printStackTrace(););
        mv.setViewName("error");
        return mv;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        return new ModelAndView();
    }

}
