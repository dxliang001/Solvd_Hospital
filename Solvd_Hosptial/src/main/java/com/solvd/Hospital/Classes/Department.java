package com.solvd.Hospital.Classes;

import com.solvd.Hospital.Enums.DepartmentType;

import java.util.Objects;
public class Department {
    private DepartmentType type;

    public Department(DepartmentType type) {
        this.type = type;
    }

    // getter and setter for type

    @Override
    public String toString() {
        return "Department{" +
                "type=" + type.getDisplayName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

}
