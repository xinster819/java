package com.sohu.mpV2.dao.news;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Component;

import com.sohu.mpV2.dao.mp.MpChannelDao;
import com.sohu.mpV2.dao.mp.MpTagDao;
import com.sohu.mpV2.dao.news.mapper.MpNewsMapper;
import com.sohu.mpV2.vo.MpChannel;
import com.sohu.mpV2.vo.MpNews;
import com.sohu.mpV2.vo.MpNews.MpNewsStatus;
import com.sohu.mpV2.vo.MpTag;

@Component
public class MpNewsDao {

    @Resource
    private MpNewsMapper mpNewsMapper;
    
    @Resource
    private MpChannelDao mpChannelDao;

    @Resource
    private MpTagDao mpTagDao;
    
    public MpNews byId(long id, String mpMediaId) {
        MpNews news = mpNewsMapper.byId(getRouteTableIndex(mpMediaId), id);
        if (news == null || news.getStatus() != MpNewsStatus.PUBLISHED.getCode()) {
            return null;
        }
        MpChannel mpChannel = mpChannelDao.byId(news.getChannelId());
        if (mpChannel == null) {
            return null;
        }
        if (StringUtils.isNotEmpty(news.getTags())) {
            String[] tags = news.getTags().split(";");
            List<MpTag> list = new ArrayList<MpTag>();
            for(String tag: tags) {
                MpTag mpTag = mpTagDao.byId(NumberUtils.toInt(tag));
                if (mpTag != null) {
                    list.add(mpTag);
                }
            }
            news.setMpTags(list);
        }
        news.setMpChannel(mpChannel);
        return news;
    }

    private static int getRouteTableIndex(String key) {
        try {
            CRC32 checksum = new CRC32();
            checksum.update(key.getBytes("utf-8"));
            int crc = (int) checksum.getValue();
            int r = Math.abs((crc >> 16) & 0x7fff) % 256;
            return r;
        } catch (Exception e) {
            throw new RuntimeException("can not get route table index which " + key);
        }
    }

    public static void main(String[] args) {
        int index = getRouteTableIndex("866478573@renren.sohu.com");
        System.out.println(index);
    }
}
