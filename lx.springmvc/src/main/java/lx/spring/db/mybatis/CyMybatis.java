package lx.spring.db.mybatis;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import lx.spring.db.MultiDataSource;
import lx.spring.db.cyMapper.TopicMapper;
import lx.springmvc.vo.cy.Topic;

import org.springframework.stereotype.Service;

@Service
public class CyMybatis {
    
    @Resource
    MultiDataSource multiDataSource;
    
    @Resource
    TopicMapper topicMapper;

    public void select() {
        for (int start = 33; start <= 64; start++) {
            multiDataSource.put(Thread.currentThread().getId(), String.valueOf(start));
            List<Topic> topics = topicMapper.selectByIsvid(98765);
            if (topics != null) {
                for (Topic topic : topics) {
                    try {
                        lx.google.httpclient.spider.SpiderUtils.spider(topic.getTopicUrl());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        lx.google.httpclient.spider.SpiderUtils.print();
    }
}
