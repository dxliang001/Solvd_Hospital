package com.solvd.Hospital.Enums;

public enum DepartmentType {
    EMERGENCY("Emergency"),
    CARDIOLOGY("Cardiology"),
    RADIOLOGY("Radiology");

    private String getDepartmentName;

    DepartmentType(String departmentName) {
        this.getDepartmentName = departmentName;
    }

    public String getDisplayName() {
        return getDepartmentName;
    }
}