package com.sohu.mpV2.vo;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class MpNews {

    private long id;
    private String title;
    private String mobileTitle;
    private String brief;
    private String content;
    private String url;
    private int status;
    @JSONField(serialize = false)
    private int channelId;
    @JSONField(serialize = false)
    private String tags;
    private boolean imageNews;
    private String mpMediaId;
    private String originalSource;
    @JSONField(serialize = false)
    private Date gatherTime;
    private Date postTime;

    @JSONField(name="channel")
    private MpChannel mpChannel;
    @JSONField(name="tags")
    private List<MpTag> mpTags;

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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public MpChannel getMpChannel() {
        return mpChannel;
    }

    public void setMpChannel(MpChannel mpChannel) {
        this.mpChannel = mpChannel;
    }

    public List<MpTag> getMpTags() {
        return mpTags;
    }

    public void setMpTags(List<MpTag> mpTags) {
        this.mpTags = mpTags;
    }

    public enum MpNewsStatus {
        DRAFT(1, "草稿"), AUDITING(2, "审核中"), REJECT(3, "驳回"), PUBLISHED(4, "审核通过"), SCHEDULED(5, "定时发表");
        private int code;
        private String msg;
        
        private MpNewsStatus(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
