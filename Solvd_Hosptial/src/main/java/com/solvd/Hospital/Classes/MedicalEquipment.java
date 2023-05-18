package com.solvd.Hospital.Classes;

import com.solvd.Hospital.Interface.IOperatable;

import java.util.Objects;
public class MedicalEquipment implements IOperatable {
    private String equipmentName;
    private String manufacturer;
    private String model;
    // change to inventory, contain medical supplies, equipment, and medications.
    // Attributes can include item ID, name, quantity, and expiration date.
    public MedicalEquipment(String equipmentName, String manufacturer, String model) {
        this.equipmentName = equipmentName;
        this.manufacturer = manufacturer;
        this.model = model;
    }

    // Getters and setters
    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "MedicalEquipment{" +
                "name='" + equipmentName + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalEquipment that = (MedicalEquipment) o;
        return Objects.equals(equipmentName, that.equipmentName) &&
                Objects.equals(manufacturer, that.manufacturer) &&
                Objects.equals(model, that.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(equipmentName, manufacturer, model);
    }

    @Override
    public String operate(String operation) {
        return "Operating equipment: " + getEquipmentName() + " with operation " + operation;
    }
}
