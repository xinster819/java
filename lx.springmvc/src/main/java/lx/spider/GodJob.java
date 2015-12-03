package lx.spider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GodJob implements InitializingBean {

    static Logger LOGGER = LoggerFactory.getLogger(GodJob.class);

    @Scheduled(cron = "10 10 10 * * ?")
    public void flushBlackIp() {
        ShadowSocks.singIn();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

}
