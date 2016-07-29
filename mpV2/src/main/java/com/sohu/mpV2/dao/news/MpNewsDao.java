package com.sohu.mpV2.dao.news;

import java.util.zip.CRC32;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sohu.mpV2.dao.news.mapper.MpNewsMapper;
import com.sohu.mpV2.vo.MpNews;

@Component
public class MpNewsDao {

    @Resource
    private MpNewsMapper mpNewsMapper;

    public MpNews byId(long id, String mpMediaId) {
        return mpNewsMapper.byId(getRouteTableIndex(mpMediaId), id);
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

}
