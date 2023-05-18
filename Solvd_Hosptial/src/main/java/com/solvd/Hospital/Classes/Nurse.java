package com.solvd.Hospital.Classes;

import com.solvd.Hospital.Enums.Gender;
import com.solvd.Hospital.Interface.IExaminable;

import java.util.Objects;

public class Nurse extends Employee implements IExaminable {

    private int experience;

    public Nurse(String name, Gender gender, int age, String jobTitle, int experience, Department department) {
        super(name, gender, age, experience, department, jobTitle);
        this.experience = experience;
    }

    @Override
    public void work() {
        System.out.println("The nurse is taking care of patients.");
    }

    // Getters and setters
    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public String toString() {
        return "Nurse: " +
                "experience=" + getExperience() +
                ", IDr='" + getId() + '\'' +
                ", jobTitle='" + getJobTitle() + '\'' +
                ", name='" + getName() + '\'' +
                ", gender='" + getGender().getShortForm() + '\'' +
                ", age=" + getAge();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Nurse nurse = (Nurse) o;
        return experience == nurse.experience;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), experience);
    }

    @Override
    public String examine(Patient patient) {
        return "Nurse examining patient: " + patient.getName();
    }
}
