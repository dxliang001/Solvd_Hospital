package com.solvd.Hospital.Classes;
import com.solvd.Hospital.Enums.Gender;
import com.solvd.Hospital.Interface.IDiagnosable;
import com.solvd.Hospital.Interface.IExaminable;
import com.solvd.Hospital.Interface.ITreatable;

import java.util.Objects;

import com.solvd.Hospital.Main;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Doctor extends Employee implements IDiagnosable, ITreatable, IExaminable {
    private int experience;
    private final int doctorId;
    private static int idCounter = 1;
    private static final Logger logger = LogManager.getLogger(Main.class);

    public Doctor(String name, Gender gender, int age, String jobTitle, Department department, int experience) {
        super(name, gender , age, experience, department, jobTitle);
        this.experience = experience;
        this.doctorId = idCounter++;
    }

    @Override
    public void work() {
        logger.info("The doctor is diagnosing and treating patients.");
    }

    // Getters and setters
    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getDoctorId() {
        return doctorId;
    }

    @Override
    public String toString() {
        return "Doctor: " +
                "Experience='" + getExperience() + '\'' +
                ", IDr='" + getId() + '\'' +
                ", doctorId='" + getDoctorId() + '\'' +
                ", jobTitle='" + getJobTitle() + '\'' +
                ", name='" + getName() + '\'' +
                ", gender='" + getGender().getShortForm() + '\'' +
                ", age=" + getAge();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(doctorId, doctor.doctorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctorId);
    }

    @Override
    public String diagnose(Patient patient) {
        return "Diagnosing patient: " + patient.getName();
    }

    @Override
    public String treat(Patient patient) {
        return "Treating patient: " + patient.getName();
    }

    @Override
    public String examine(Patient patient) {
        return "Examining patient: " + patient.getName();
    }


}
