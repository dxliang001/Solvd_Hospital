package com.solvd.Hospital.Classes;

import com.solvd.Hospital.Enums.Gender;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

public class Patient extends Person {
    private MedicalRecord medicalRecord;
    private String patientId;

    private static final HashSet<String> patientIds = new HashSet<>();

    public Patient(String name, Gender gender, int age, MedicalRecord medicalRecord) {
        super(name, gender, age);
        this.medicalRecord = medicalRecord;
        this.patientId = generateUniqueId();
    }

    // Getters and setters
    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public String getPatientId() {
        return patientId;
    }

    private String generateUniqueId() {
        String id;
        do {
            id = UUID.randomUUID().toString();
        } while (patientIds.contains(id));
        patientIds.add(id);
        return id;
    }

    @Override
    public String toString() {
        return "Patient: " +
                "medicalRecord: " + medicalRecord +
                ", patientId='" + getPatientId() + '\'' +
                ", name='" + getName() + '\'' +
                ", gender='" + getGender().getShortForm() + '\'' +
                ", age=" + getAge()
                ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Patient patient = (Patient) o;
        return Objects.equals(medicalRecord, patient.medicalRecord) &&
                Objects.equals(patientId, patient.patientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), medicalRecord, patientId);
    }
}
