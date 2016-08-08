package com.sohu.mpV2.response;

import java.util.Collection;

import com.sohu.mpV2.vo.CodeArea;

public class CodeAreaResponse extends BaseResponse {

    private CodeArea area;
    private Collection<CodeArea> provinces;
    private Collection<CodeArea> cities;

    public CodeAreaResponse(ResponseStatus s) {
        super(s);
    }

    public CodeArea getArea() {
        return area;
    }

    public void setArea(CodeArea area) {
        this.area = area;
    }

    public Collection<CodeArea> getProvinces() {
        return provinces;
    }

    public void setProvinces(Collection<CodeArea> collection) {
        this.provinces = collection;
    }

    public Collection<CodeArea> getCities() {
        return cities;
    }

    public void setCities(Collection<CodeArea> cities) {
        this.cities = cities;
    }

}
