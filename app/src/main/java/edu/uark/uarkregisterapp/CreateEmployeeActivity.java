package edu.uark.uarkregisterapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;

import edu.uark.uarkregisterapp.models.api.ApiResponse;
import edu.uark.uarkregisterapp.models.api.Employee;
import edu.uark.uarkregisterapp.models.api.enums.EmployeeClassification;
import edu.uark.uarkregisterapp.models.api.services.EmployeeService;
import edu.uark.uarkregisterapp.models.transition.EmployeeTransition;

public class CreateEmployeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_employee);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  // Respond to the action bar's Up/Home button
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveButtonOnClick(View view) {
        if (!this.validateInput()) {
            return;
        }

        (new CreateEmployeeTask()).execute(
            (new Employee())
                .setActive(true)
                .setFirstName(this.getFirstNameEditText().getText().toString())
                .setLastName(this.getLastNameEditText().getText().toString())
                .setPassword(this.getPasswordEditText().getText().toString())
                .setClassification(EmployeeClassification.GENERAL_MANAGER)
        );
    }

    private EditText getFirstNameEditText() {
        return (EditText) this.findViewById(R.id.edit_text_employee_create_first_name);
    }

    private EditText getLastNameEditText() {
        return (EditText) this.findViewById(R.id.edit_text_employee_create_last_name);
    }

    private EditText getPasswordEditText() {
        return (EditText) this.findViewById(R.id.edit_text_employee_create_password);
    }

    private EditText getPasswordConfirmEditText() {
        return (EditText) this.findViewById(R.id.edit_text_employee_create_password_confirm);
    }

    private boolean validateInput() {
        boolean validInput = true;

        if (StringUtils.isBlank(this.getFirstNameEditText().getText().toString())) {
            this.displayValidationAlert(R.string.alert_dialog_employee_create_validation_first_name);
            this.getFirstNameEditText().requestFocus();
            validInput = false;
        }
        if (validInput && StringUtils.isBlank(this.getLastNameEditText().getText().toString())) {
            this.displayValidationAlert(R.string.alert_dialog_employee_create_validation_last_name);
            this.getLastNameEditText().requestFocus();
            validInput = false;
        }
        if (validInput && StringUtils.isBlank(this.getPasswordEditText().getText().toString())) {
            this.displayValidationAlert(R.string.alert_dialog_employee_create_validation_password);
            this.getLastNameEditText().requestFocus();
            validInput = false;
        }
        if (validInput && !this.getPasswordEditText().getText().toString().equals(this.getPasswordConfirmEditText().getText().toString())) {
            this.displayValidationAlert(R.string.alert_dialog_employee_create_validation_password_invalid);
            this.getLastNameEditText().requestFocus();
            validInput = false;
        }

        return validInput;
    }

    private void displayValidationAlert(int stringId) {
        new AlertDialog.Builder(this)
            .setMessage(stringId)
            .create()
            .show();
    }

    private class CreateEmployeeTask extends AsyncTask<Employee, Void, ApiResponse<Employee>> {
        @Override
        protected void onPreExecute() {
            this.createEmployeeAlert = new AlertDialog.Builder(CreateEmployeeActivity.this)
                .setMessage(R.string.alert_dialog_employee_create)
                .create();
            this.createEmployeeAlert.show();
        }

        @Override
        protected ApiResponse<Employee> doInBackground(Employee... employees) {
            if (employees.length > 0) {
                return (new EmployeeService()).createEmployee(employees[0]);
            } else {
                return (new ApiResponse<Employee>())
                    .setValidResponse(false);
            }
        }

        @Override
        protected void onPostExecute(ApiResponse<Employee> apiResponse) {
            this.createEmployeeAlert.dismiss();

            if (!apiResponse.isValidResponse()) {
                new AlertDialog.Builder(CreateEmployeeActivity.this)
                    .setMessage(R.string.alert_dialog_employee_create_failed)
                    .create()
                    .show();
                return;
            }

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

            intent.putExtra(
                getString(R.string.intent_extra_employee)
                , new EmployeeTransition(apiResponse.getData())
            );

            startActivity(intent);
        }

        private AlertDialog createEmployeeAlert;
    }
}
