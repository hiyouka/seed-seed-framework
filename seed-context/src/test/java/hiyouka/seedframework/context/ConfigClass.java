package hiyouka.seedframework.context;

import hiyouka.seedframework.beans.annotation.Bean;
import hiyouka.seedframework.beans.annotation.Import;
import hiyouka.seedframework.beans.annotation.Lazy;
import hiyouka.seedframework.beans.annotation.Primary;
import hiyouka.seedframework.context.annotation.ComponentScan;
import hiyouka.seedframework.context.annotation.Configuration;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Configuration
@ComponentScan("hiyouka.seedframework.context")
@Import({ImportClass.class,ImportClass2.class})
public class ConfigClass {

    @Bean
    @Lazy
    @Primary
    public BeanClass beanClass(){
        return new BeanClass();
    }

}