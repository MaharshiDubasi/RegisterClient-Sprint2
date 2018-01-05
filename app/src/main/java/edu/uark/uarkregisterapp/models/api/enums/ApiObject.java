package edu.uark.uarkregisterapp.models.api.enums;

import edu.uark.uarkregisterapp.models.api.interfaces.PathElementInterface;

public enum ApiObject implements PathElementInterface {
    NONE(""),
    PRODUCT("product/");

    @Override
    public String getPathValue() {
        return value;
    }

    private String value;

    ApiObject(String value) {
        this.value = value;
    }
}
