package com.sohu.mpV2.response;

public class ObjectResponse extends BaseResponse {

    private Object object;

    public ObjectResponse(ResponseStatus s) {
        super(s);
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
