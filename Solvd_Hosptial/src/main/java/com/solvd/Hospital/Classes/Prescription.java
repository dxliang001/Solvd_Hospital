package com.solvd.Hospital.Classes;

public class Prescription {
    private String medication;
    private String dosage;
    private String instructions;
    // treatment ID, name , description, cost
    public Prescription(String medication, String dosage, String instructions) {
        this.medication = medication;
        this.dosage = dosage;
        this.instructions = instructions;
    }

    // Getters and setters
    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "medication='" + medication + '\'' +
                ", dosage='" + dosage + '\'' +
                ", instructions='" + instructions + '\'' +
                '}';
    }
}
