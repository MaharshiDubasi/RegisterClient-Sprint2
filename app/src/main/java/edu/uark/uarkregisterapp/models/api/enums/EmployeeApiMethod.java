package edu.uark.uarkregisterapp.models.api.enums;

import edu.uark.uarkregisterapp.models.api.interfaces.PathElementInterface;

public enum EmployeeApiMethod implements PathElementInterface {
    NONE(""),
    LOGIN("login"),
    ACTIVE_COUNTS("activecounts");

    @Override
    public String getPathValue() {
        return value;
    }

    private String value;

    EmployeeApiMethod(String value) {
        this.value = value;
    }
}
