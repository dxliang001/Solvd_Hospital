package com.solvd.Hospital.Classes;

import com.solvd.Hospital.Enums.Gender;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Generator {

    public List<Patient> generatePatients() {
        List<Patient> patients = new ArrayList<>();

        patients.add(new Patient("John Doe", Gender.MALE, 30, new MedicalRecord("Flu", "Paracetamol", "None")));
        patients.add(new Patient("Jane Smith", Gender.FEMALE, 45, new MedicalRecord("High Blood Pressure", "Lisinopril", "None")));
        patients.add(new Patient("Bob Johnson", Gender.MALE, 25, new MedicalRecord("Sprained Ankle", "Ibuprofen", "None")));
        patients.add(new Patient("Alice Lee", Gender.FEMALE, 55, new MedicalRecord("Diabetes", "Metformin", "Insulin")));
        patients.add(new Patient("Mike Miller", Gender.MALE, 40, new MedicalRecord("Back Pain", "Naproxen", "Physical Therapy")));

        return patients;
    }

    public List<Appointment> generateAppointments(Doctor drSmith, Doctor drJohnson, Doctor drWilliams, List<Patient> patients) {
        List<Appointment> appointments = new ArrayList<>();

        appointments.add(new Appointment(LocalDateTime.of(2023, 5, 11, 9, 0), "Check up", drSmith, patients.get(0)));
        appointments.add(new Appointment(LocalDateTime.of(2023, 5, 12, 10, 30), "Sick", drSmith, patients.get(1)));
        appointments.add(new Appointment(LocalDateTime.of(2023, 5, 13, 14, 0), "Other", drJohnson, patients.get(2)));
        appointments.add(new Appointment(LocalDateTime.of(2023, 5, 14, 11, 15), "Other", drJohnson, patients.get(3)));
        appointments.add(new Appointment(LocalDateTime.of(2023, 5, 15, 15, 30), "Check up", drWilliams, patients.get(4)));

        return appointments;
    }


}