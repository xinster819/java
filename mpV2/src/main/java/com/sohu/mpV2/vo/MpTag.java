package com.sohu.mpV2.vo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class MpTag {

    private int id;
    private String name;
    private String url;
    @JSONField(serialize = false)
    private int status;
    @JSONField(serialize = false)
    private int categoryId;
    @JSONField(serialize = false)
    private int channelId;
    @JSONField(serialize = false)
    private boolean type;
    @JSONField(serialize = false)
    private Date createTime;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
