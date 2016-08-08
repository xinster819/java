package com.sohu.mpV2.vo;

import com.alibaba.fastjson.annotation.JSONField;

public class MpChannel {

    private int id;
    private String name;
    @JSONField(serialize = false)
    private int cmsChannelId;
    @JSONField(serialize = false)
    private String cmsPname;
    @JSONField(serialize = false)
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCmsChannelId() {
        return cmsChannelId;
    }

    public void setCmsChannelId(int cmsChannelId) {
        this.cmsChannelId = cmsChannelId;
    }

    public String getCmsPname() {
        return cmsPname;
    }

    public void setCmsPname(String cmsPname) {
        this.cmsPname = cmsPname;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
