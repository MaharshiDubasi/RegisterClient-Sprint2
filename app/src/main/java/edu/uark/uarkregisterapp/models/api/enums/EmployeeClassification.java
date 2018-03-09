package edu.uark.uarkregisterapp.models.api.enums;

import android.util.SparseArray;

import java.util.HashMap;
import java.util.Map;

public enum EmployeeClassification {
    NOT_DEFINED(0),
    GENERAL_MANAGER(1),
    SHIFT_MANAGER(2),
    CASHIER(3);

    public int getValue() {
        return value;
    }

    public static EmployeeClassification mapValue(int key) {
        if (valueMap == null) {
            valueMap = new SparseArray<>();

            for (EmployeeClassification status : EmployeeClassification.values()) {
                valueMap.put(status.getValue(), status);
            }
        }

        return ((valueMap.indexOfKey(key) >= 0) ? valueMap.get(key) : EmployeeClassification.NOT_DEFINED);
    }

    public static EmployeeClassification mapName(String name) {
        if (nameMap == null) {
            nameMap = new HashMap<>();

            for (EmployeeClassification status : EmployeeClassification.values()) {
                nameMap.put(status.name(), status);
            }
        }

        return (nameMap.containsKey(name) ? nameMap.get(name) : EmployeeClassification.NOT_DEFINED);
    }

    private int value;

    private static Map<String, EmployeeClassification> nameMap = null;
    private static SparseArray<EmployeeClassification> valueMap = null;

    private EmployeeClassification(int value) {
        this.value = value;
    }
}
