package com.solvd.Hospital.Classes;

import java.util.Objects;
public class Hospital {
    private String name;
    private String location;
    private String phoneNumber;
    private String address;

    public Hospital(String name, String location, String phoneNumber, String address) {
        this.name = name;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Hospital: " +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\''
                ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hospital hospital = (Hospital) o;
        return Objects.equals(name, hospital.name) &&
                Objects.equals(location, hospital.location) &&
                Objects.equals(phoneNumber, hospital.phoneNumber) &&
                Objects.equals(address, hospital.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, location, phoneNumber, address);
    }
}
