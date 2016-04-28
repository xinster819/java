package springmvc.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShadowSock {

    public static short ON = 1;
    public static short OFF = 0;
    public static short ERROR = -1;
    public SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String url;
    private String loginUri;
    private String checkInUri;
    private short status;
    private Date checkInTime;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLoginUri() {
        return loginUri;
    }

    public void setLoginUri(String loginUri) {
        this.loginUri = loginUri;
    }

    public String getCheckInUri() {
        return checkInUri;
    }

    public void setCheckInUri(String checkInUri) {
        this.checkInUri = checkInUri;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public String getFormatDate() {
        return SDF.format(checkInTime);
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    @Override
    public String toString() {
        return url + " | " + loginUri + " | " + checkInUri;
    }

}
