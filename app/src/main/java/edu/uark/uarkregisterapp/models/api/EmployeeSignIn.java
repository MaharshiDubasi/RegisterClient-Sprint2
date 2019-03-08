package edu.uark.uarkregisterapp.models.api;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import edu.uark.uarkregisterapp.models.api.fields.EmployeeSignInFieldName;
import edu.uark.uarkregisterapp.models.api.interfaces.ConvertToJsonInterface;

public class EmployeeSignIn implements ConvertToJsonInterface {
    private String employeeId;
    public String getEmployeeId() {
        return this.employeeId;
    }
    public EmployeeSignIn setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
        return this;
    }
    private String password;
    public String getPassword() {
        return this.password;
    }
    public EmployeeSignIn setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public JSONObject convertToJson() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(EmployeeSignInFieldName.EMPLOYEE_ID.getFieldName(), this.employeeId);
            jsonObject.put(EmployeeSignInFieldName.PASSWORD.getFieldName(), this.password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public EmployeeSignIn() {
        this.password = StringUtils.EMPTY;
        this.employeeId = StringUtils.EMPTY;
    }
}
