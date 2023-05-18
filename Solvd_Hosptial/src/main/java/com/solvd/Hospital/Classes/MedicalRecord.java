package com.solvd.Hospital.Classes;

import java.util.Objects;
public class MedicalRecord {
    private String medicalCondition;
    private String treatment;
    private String medication;
    // patient reports, financial reports, and performance reports. Attributes can include report ID, type, and generated data.
    public MedicalRecord(String medicalCondition, String treatment, String medication) {
        this.medicalCondition = medicalCondition;
        this.treatment = treatment;
        this.medication = medication;
    }

    // Getters and setters
    public String getMedicalCondition() {
        return medicalCondition;
    }

    public void setMedicalCondition(String medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    @Override
    public String toString() {
        return "MedicalRecord{" +
                "medicalCondition='" + medicalCondition + '\'' +
                ", treatment='" + treatment + '\'' +
                ", medication='" + medication + '\'' ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalRecord that = (MedicalRecord) o;
        return Objects.equals(medicalCondition, that.medicalCondition) &&
                Objects.equals(treatment, that.treatment) &&
                Objects.equals(medication, that.medication);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicalCondition, treatment, medication);
    }
}
