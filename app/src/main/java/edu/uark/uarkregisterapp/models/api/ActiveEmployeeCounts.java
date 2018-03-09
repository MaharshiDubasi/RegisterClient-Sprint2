package edu.uark.uarkregisterapp.models.api;

import org.json.JSONObject;

import edu.uark.uarkregisterapp.models.api.fields.ActiveEmployeeCountsFieldName;
import edu.uark.uarkregisterapp.models.api.interfaces.LoadFromJsonInterface;

public class ActiveEmployeeCounts implements LoadFromJsonInterface<ActiveEmployeeCounts> {
    private int activeCashierCount;
    public int getActiveCashierCount() {
        return this.activeCashierCount;
    }
    public ActiveEmployeeCounts setActiveCashierCount(int activeCashierCount) {
        this.activeCashierCount = activeCashierCount;
        return this;
    }

    private int activeShiftManagerCount;
    public int getActiveShiftManagerCount() {
        return this.activeShiftManagerCount;
    }
    public ActiveEmployeeCounts setActiveShiftManagerCount(int activeShiftManagerCount) {
        this.activeShiftManagerCount = activeShiftManagerCount;
        return this;
    }

    private int activeGeneralManagerCount;
    public int getActiveGeneralManagerCount() {
        return this.activeGeneralManagerCount;
    }
    public ActiveEmployeeCounts setActiveGeneralManagerCount(int activeGeneralManagerCount) {
        this.activeGeneralManagerCount = activeGeneralManagerCount;
        return this;
    }

    @Override
    public ActiveEmployeeCounts loadFromJson(JSONObject rawJsonObject) {
        this.activeCashierCount = rawJsonObject.optInt(ActiveEmployeeCountsFieldName.CASHIER_COUNT.getFieldName());
        this.activeShiftManagerCount = rawJsonObject.optInt(ActiveEmployeeCountsFieldName.SHIFT_MANAGER_COUNT.getFieldName());
        this.activeGeneralManagerCount = rawJsonObject.optInt(ActiveEmployeeCountsFieldName.GENERAL_MANAGER_COUNT.getFieldName());

        return this;
    }

    public ActiveEmployeeCounts() {
        this.activeCashierCount = 0;
        this.activeShiftManagerCount = 0;
        this.activeGeneralManagerCount = 0;
    }
}
