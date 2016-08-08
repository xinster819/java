package com.sohu.mpV2.vo;

import java.util.Date;

public class MpExpertCert {

    private long id;
    private String org;
    private String certInfo;
    private String certPicUrls;
    private String otherPicUrls;
    private int status;
    private int channelId;
    private int categoryId;
    private int modified;
    private int mediaProfileId;
    private Date createAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getCertInfo() {
        return certInfo;
    }

    public void setCertInfo(String certInfo) {
        this.certInfo = certInfo;
    }

    public String getCertPicUrls() {
        return certPicUrls;
    }

    public void setCertPicUrls(String certPicUrls) {
        this.certPicUrls = certPicUrls;
    }

    public String getOtherPicUrls() {
        return otherPicUrls;
    }

    public void setOtherPicUrls(String otherPicUrls) {
        this.otherPicUrls = otherPicUrls;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getModified() {
        return modified;
    }

    public void setModified(int modified) {
        this.modified = modified;
    }

    public int getMediaProfileId() {
        return mediaProfileId;
    }

    public void setMediaProfileId(int mediaProfileId) {
        this.mediaProfileId = mediaProfileId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

}
