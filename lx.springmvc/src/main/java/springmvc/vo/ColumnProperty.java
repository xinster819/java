package springmvc.vo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnProperty {
    String value();

    boolean isPk() default false;
    
    boolean isIncrement() default false;
    
    boolean isSelectKey() default false;

    boolean isUpdateKey() default false;

    boolean needUpdate() default true;
    
    boolean isDeleteKey() default false;
}
