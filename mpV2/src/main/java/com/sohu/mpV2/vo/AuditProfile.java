package com.sohu.mpV2.vo;

import java.util.zip.CRC32;

public class AuditProfile {

    private static int getRouteTableIndex(String key, int size) {
        try {
            CRC32 checksum = new CRC32();
            checksum.update(key.getBytes("utf-8"));
            int crc = (int) checksum.getValue();
            int r = Math.abs((crc >> 16) & 0x7fff) % size;
            return r;
        } catch (Exception e) {
            throw new RuntimeException("can not get route table index which " + key);
        }
    }
    
    public static void main(String[] args) {
        System.out.println(getRouteTableIndex("oSeHTsztv2xie3KxMGiDaFt2_Fbw@wechat.sohu.com", 256));
    }
}
