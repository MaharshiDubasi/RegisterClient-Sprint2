package edu.uark.uarkregisterapp.models.api.fields;

import edu.uark.uarkregisterapp.models.api.interfaces.FieldNameInterface;

public enum EmployeeFieldName implements FieldNameInterface {
    ID("id"),
    EMPLOYEE_ID("employeeId"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    PASSWORD("password"),
    ACTIVE("active"),
    CLASSIFICATION("classification"),
    MANAGER_ID("managerId"),
    CREATED_ON("createdOn");

    private String fieldName;
    public String getFieldName() {
        return this.fieldName;
    }

    EmployeeFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
