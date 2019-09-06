package hiyouka.seedframework.beans.annotation;

import hiyouka.seedframework.beans.factory.AutowiredAnnotationPostProcessor;

import java.lang.annotation.*;

/**
 * @see AutowiredAnnotationPostProcessor
 * @author hiyouka
 * @since JDK 1.8
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {

    boolean required() default true;

}