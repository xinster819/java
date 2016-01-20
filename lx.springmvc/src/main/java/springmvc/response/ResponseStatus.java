package springmvc.response;

public enum ResponseStatus {

    SUCC(200, "SUCC"), FAIL(500, "FAIL");

    private int code;
    private String msg;

    private ResponseStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
