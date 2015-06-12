package lx.jedis;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedisPool;

@Service
public class KeyUtils {

    @Resource
    ShardedJedisPool shardedJedisPool;

    public void getIsvCmtLatestKey(int isvId, String order) {
        String key = new StringBuilder().append("ic:").append(isvId + "-" + order).append(":aid").toString();
        Set<String> zrange = shardedJedisPool.getResource().zrange(key, 0, 30);
        for (String one : zrange) {
            System.out.println(one);
        }
    }
}
