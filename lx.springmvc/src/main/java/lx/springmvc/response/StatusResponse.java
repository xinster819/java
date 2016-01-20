package lx.springmvc.response;

public class StatusResponse {

    private int code;
    private String msg;

    public StatusResponse() {
    }

    public StatusResponse(Status status) {
        code = status.code;
        msg = status.msg;
    }

    public StatusResponse(int code, String msg) {
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

    public static enum Status {
        SUCC(200, "succ"), FAIL(500, "fail");
        private int code;
        private String msg;

        private Status(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

}
