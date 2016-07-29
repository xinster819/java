package com.sohu.mpV2.vo;

import java.util.Date;

public class MpNews {

    private long id;
    private String title;
    private String mobileTitle;
    private String brief;
    private String content;
    private String url;
    private boolean imageNews;
    private String mpMediaId;
    private String originalSource;
    private Date gatherTime;
    private Date postTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMobileTitle() {
        return mobileTitle;
    }

    public void setMobileTitle(String mobileTitle) {
        this.mobileTitle = mobileTitle;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isImageNews() {
        return imageNews;
    }

    public void setImageNews(boolean imageNews) {
        this.imageNews = imageNews;
    }

    public String getMpMediaId() {
        return mpMediaId;
    }

    public void setMpMediaId(String mpMediaId) {
        this.mpMediaId = mpMediaId;
    }

    public Date getGatherTime() {
        return gatherTime;
    }

    public void setGatherTime(Date gatherTime) {
        this.gatherTime = gatherTime;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public String getOriginalSource() {
        return originalSource;
    }

    public void setOriginalSource(String originalSource) {
        this.originalSource = originalSource;
    }

}
