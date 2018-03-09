package edu.uark.uarkregisterapp.models.api;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import edu.uark.uarkregisterapp.models.api.fields.EmployeeLoginFieldName;
import edu.uark.uarkregisterapp.models.api.interfaces.ConvertToJsonInterface;

public class EmployeeLogin implements ConvertToJsonInterface {
    private String employeeId;
    public String getEmployeeId() {
        return this.employeeId;
    }
    public EmployeeLogin setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
        return this;
    }
    private String password;
    public String getPassword() {
        return this.password;
    }
    public EmployeeLogin setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public JSONObject convertToJson() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(EmployeeLoginFieldName.EMPLOYEE_ID.getFieldName(), this.employeeId);
            jsonObject.put(EmployeeLoginFieldName.PASSWORD.getFieldName(), this.password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public EmployeeLogin() {
        this.password = StringUtils.EMPTY;
        this.employeeId = StringUtils.EMPTY;
    }
}
