package com.sohu.mpV2.response;

import com.sohu.mpV2.vo.MpMediaProfile;

public class ProfileResponse extends BaseResponse {

    public ProfileResponse(ResponseStatus s) {
        super(s);
    }

    private MpMediaProfile mpMediaProfile;

    private boolean isExpert;

    public MpMediaProfile getMpMediaProfile() {
        return mpMediaProfile;
    }

    public void setMpMediaProfile(MpMediaProfile mpMediaProfile) {
        this.mpMediaProfile = mpMediaProfile;
    }

    public boolean isExpert() {
        return isExpert;
    }

    public void setExpert(boolean isExpert) {
        this.isExpert = isExpert;
    }

}
