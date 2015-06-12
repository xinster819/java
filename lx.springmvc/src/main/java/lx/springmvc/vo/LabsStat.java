package lx.springmvc.vo;

public class LabsStat {

    private long id;
    private int date;
    private int type;
    private String uri;
    private int ms200;
    private int ms500;
    private int ms1000;
    private int ms2000;
    private int reqCount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getMs200() {
        return ms200;
    }

    public void setMs200(int ms200) {
        this.ms200 = ms200;
    }

    public int getMs500() {
        return ms500;
    }

    public void setMs500(int ms500) {
        this.ms500 = ms500;
    }

    public int getMs1000() {
        return ms1000;
    }

    public void setMs1000(int ms1000) {
        this.ms1000 = ms1000;
    }

    public int getMs2000() {
        return ms2000;
    }

    public void setMs2000(int ms2000) {
        this.ms2000 = ms2000;
    }

    public int getReqCount() {
        return reqCount;
    }

    public void setReqCount(int reqCount) {
        this.reqCount = reqCount;
    }

}
