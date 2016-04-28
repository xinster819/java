package springmvc.job;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import springmvc.service.ShadowSockService;

@Component
public class ShadowJob {

    @Resource
    private ShadowSockService shadowSockService;

    @Scheduled(cron = "0 0 18 * * ?")
    public void checkin() {
        shadowSockService.checkin();
    }
}
