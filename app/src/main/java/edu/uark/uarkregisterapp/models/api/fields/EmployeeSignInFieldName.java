package edu.uark.uarkregisterapp.models.api.fields;

import edu.uark.uarkregisterapp.models.api.interfaces.FieldNameInterface;

public enum EmployeeSignInFieldName implements FieldNameInterface {
    EMPLOYEE_ID("employeeId"),
    PASSWORD("password");

    private String fieldName;
    public String getFieldName() {
        return this.fieldName;
    }

    EmployeeSignInFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
