package com.sohu.mpV2.vo;

public enum FromWhere {

    MP(0, "mp"), CLIENT(1, "新闻客户端"), MOBILE(4, "mobile"), AUTO(5, "自动生成");

    private int code;
    private String value;

    private FromWhere(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static boolean exist(int code) {
        for (FromWhere one : FromWhere.values()) {
            if (one.getCode() == code) {
                return true;
            }
        }
        return false;
    }

}
