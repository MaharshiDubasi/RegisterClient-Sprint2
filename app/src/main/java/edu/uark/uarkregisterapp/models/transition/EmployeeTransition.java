package edu.uark.uarkregisterapp.models.transition;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.UUID;

import edu.uark.uarkregisterapp.commands.converters.ByteToUUIDConverterCommand;
import edu.uark.uarkregisterapp.commands.converters.UUIDToByteConverterCommand;
import edu.uark.uarkregisterapp.models.api.Employee;
import edu.uark.uarkregisterapp.models.api.enums.EmployeeClassification;

public class EmployeeTransition implements Parcelable {
    private UUID id;
    public UUID getId() {
        return this.id;
    }
    public EmployeeTransition setId(UUID id) {
        this.id = id;
        return this;
    }

    private String employeeId;
    public String getEmployeeId() {
        return this.employeeId;
    }
    public EmployeeTransition setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    private String firstName;
    public String getFirstName() {
        return this.firstName;
    }
    public EmployeeTransition setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    private String lastName;
    public String getLastName() {
        return this.lastName;
    }
    public EmployeeTransition setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    private String password;
    public String getPassword() {
        return this.password;
    }
    public EmployeeTransition setPassword(String password) {
        this.password = password;
        return this;
    }

    private boolean active;
    public boolean getActive() {
        return this.active;
    }
    public EmployeeTransition setActive(boolean active) {
        this.active = active;
        return this;
    }

    private EmployeeClassification classification;
    public EmployeeClassification getClassification() {
        return this.classification;
    }
    public EmployeeTransition setClassification(EmployeeClassification classification) {
        this.classification = classification;
        return this;
    }

    private UUID managerId;
    public UUID getManagerId() {
        return this.managerId;
    }
    public EmployeeTransition setManagerId(UUID managerId) {
        this.managerId = managerId;
        return this;
    }

    private Date createdOn;
    public Date getCreatedOn() {
        return this.createdOn;
    }
    public EmployeeTransition setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
        return this;
    }
    @Override
    public void writeToParcel(Parcel destination, int flags) {
        destination.writeByteArray((new UUIDToByteConverterCommand()).setValueToConvert(this.id).execute());
        destination.writeString(this.employeeId);
        destination.writeString(this.firstName);
        destination.writeString(this.lastName);
        destination.writeString(this.password);
        destination.writeInt(this.active ? 1 : 0);
        destination.writeInt(this.classification.getValue());
        destination.writeByteArray((new UUIDToByteConverterCommand()).setValueToConvert(this.managerId).execute());
        destination.writeLong(this.createdOn.getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<EmployeeTransition> CREATOR = new Parcelable.Creator<EmployeeTransition>() {
        public EmployeeTransition createFromParcel(Parcel employeeTransitionParcel) {
            return new EmployeeTransition(employeeTransitionParcel);
        }

        public EmployeeTransition[] newArray(int size) {
            return new EmployeeTransition[size];
        }
    };

    public EmployeeTransition() {
        this.active = false;
        this.id = new UUID(0, 0);
        this.createdOn = new Date();
        this.managerId = new UUID(0, 0);
        this.lastName = StringUtils.EMPTY;
        this.password = StringUtils.EMPTY;
        this.firstName = StringUtils.EMPTY;
        this.employeeId = StringUtils.EMPTY;
        this.classification = EmployeeClassification.NOT_DEFINED;
    }

    public EmployeeTransition(Employee employee) {
        this.id = employee.getId();
        this.active = employee.getActive();
        this.lastName = employee.getLastName();
        this.password = employee.getPassword();
        this.createdOn = employee.getCreatedOn();
        this.firstName = employee.getFirstName();
        this.managerId = employee.getManagerId();
        this.employeeId = employee.getEmployeeId();
        this.classification = employee.getClassification();
    }

    public EmployeeTransition(Parcel employeeTransitionParcel) {
        this.id = (new ByteToUUIDConverterCommand()).setValueToConvert(employeeTransitionParcel.createByteArray()).execute();
        this.employeeId = employeeTransitionParcel.readString();
        this.firstName = employeeTransitionParcel.readString();
        this.lastName = employeeTransitionParcel.readString();
        this.password = employeeTransitionParcel.readString();
        this.active = (employeeTransitionParcel.readInt() != 0);
        this.classification = EmployeeClassification.mapValue(employeeTransitionParcel.readInt());
        this.managerId = (new ByteToUUIDConverterCommand()).setValueToConvert(employeeTransitionParcel.createByteArray()).execute();

        this.createdOn = new Date();
        this.createdOn.setTime(employeeTransitionParcel.readLong());
    }
}
