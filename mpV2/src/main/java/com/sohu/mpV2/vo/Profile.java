package com.sohu.mpV2.vo;

import java.util.Date;

public class Profile {

    private int id;
    private int accountType;
    private String username;
    private String description;
    private String avatorUrl;
    private String passport;
    private int status;
    private Date createAt;
    private Date updateAt;
    private int fromWhere;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatorUrl() {
        return avatorUrl;
    }

    public void setAvatorUrl(String avatorUrl) {
        this.avatorUrl = avatorUrl;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public int getFromWhere() {
        return fromWhere;
    }

    public void setFromWhere(int fromWhere) {
        this.fromWhere = fromWhere;
    }

    public enum FromWhere {
        MP(0, "MP"), CLIENT(1, "客户端"), CLIENT_SPE(2, "客户端特殊用户"), CLIENT_VIDEO(3, "客户端视频媒体"), MOBILE(4, "手机"), AUTOGEN(5, "自动生成");

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
    }

    public enum Status {

        AUDITING(0, "待审核"), PASS(1, "通过"), FAILED(2, "驳回"), INVALID(3, "查封"), RESTRICT(4, "限制状态， 什么鬼，不知道!");

        private int code;
        private String value;

        private Status(int code, String value) {
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

    }
}
