package com.sohu.mpV2.vo;

public class MpMediaProfile {

    private int id;
    private String mpMediaId;
    private String SiteUrl;
    private int mediaType;
    private int auditType;
    private boolean auditStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMpMediaId() {
        return mpMediaId;
    }

    public void setMpMediaId(String mpMediaId) {
        this.mpMediaId = mpMediaId;
    }

    public String getSiteUrl() {
        return SiteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        SiteUrl = siteUrl;
    }

    public int getMediaType() {
        return mediaType;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    public int getAuditType() {
        return auditType;
    }

    public void setAuditType(int auditType) {
        this.auditType = auditType;
    }

    public boolean isAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(boolean auditStatus) {
        this.auditStatus = auditStatus;
    }

}
