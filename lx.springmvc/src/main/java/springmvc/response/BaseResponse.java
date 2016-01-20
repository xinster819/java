package springmvc.response;

public class BaseResponse {

    private int code;
    private String msg;

    public BaseResponse(ResponseStatus s) {
        this.code = s.getCode();
        this.msg = s.getMsg();
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
