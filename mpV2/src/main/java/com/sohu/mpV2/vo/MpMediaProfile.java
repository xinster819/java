package com.sohu.mpV2.vo;

import java.io.Serializable;

public class MpMediaProfile implements Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    private String mpMediaId;
    private String SiteUrl;
    private int copyRight;
    private int mediaType;
    private int auditType;
    private boolean auditStatus;
    private int fromWhere;
    private int grade;
    private boolean isGrab;
    private boolean pass;
    private int mpChannelId;
    private int cmsMediaId;
    private int channelModified;
    private String weiboUrl;

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

    public int getFromWhere() {
        return fromWhere;
    }

    public void setFromWhere(int fromWhere) {
        this.fromWhere = fromWhere;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public int getMpChannelId() {
        return mpChannelId;
    }

    public void setMpChannelId(int mpChannelId) {
        this.mpChannelId = mpChannelId;
    }

    public int getCopyRight() {
        return copyRight;
    }

    public void setCopyRight(int copyRight) {
        this.copyRight = copyRight;
    }

    public boolean isGrab() {
        return isGrab;
    }

    public void setGrab(boolean isGrab) {
        this.isGrab = isGrab;
    }

    public int getCmsMediaId() {
        return cmsMediaId;
    }

    public void setCmsMediaId(int cmsMediaId) {
        this.cmsMediaId = cmsMediaId;
    }

    public int getChannelModified() {
        return channelModified;
    }

    public void setChannelModified(int channelModified) {
        this.channelModified = channelModified;
    }

    public String getWeiboUrl() {
        return weiboUrl;
    }

    public void setWeiboUrl(String weiboUrl) {
        this.weiboUrl = weiboUrl;
    }
}
