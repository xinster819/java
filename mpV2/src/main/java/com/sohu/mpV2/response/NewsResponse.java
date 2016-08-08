package com.sohu.mpV2.response;

import com.sohu.mpV2.vo.MpNews;

public class NewsResponse extends BaseResponse {

    private MpNews mpNews;
    private boolean isExpert;

    public NewsResponse(ResponseStatus s) {
        super(s);
    }

    public MpNews getMpNews() {
        return mpNews;
    }

    public void setMpNews(MpNews mpNews) {
        this.mpNews = mpNews;
    }

    public boolean isExpert() {
        return isExpert;
    }

    public void setExpert(boolean isExpert) {
        this.isExpert = isExpert;
    }

}
