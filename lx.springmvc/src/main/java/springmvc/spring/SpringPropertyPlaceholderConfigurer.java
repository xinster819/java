package springmvc.spring;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class SpringPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    static Logger LOGGER = LoggerFactory.getLogger(SpringPropertyPlaceholderConfigurer.class);

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
            throws BeansException {
        super.processProperties(beanFactory, props);

        LOGGER.info("loading properties....\n {}", props.toString());
    }

}
