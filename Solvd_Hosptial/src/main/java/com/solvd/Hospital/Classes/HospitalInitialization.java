package com.solvd.Hospital.Utils;

import com.solvd.Hospital.Classes.*;
import com.solvd.Hospital.Enums.DepartmentType;
import com.solvd.Hospital.Enums.Gender;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HospitalInitialization {

    public static Map<Integer, Doctor> initializeDoctors() {
        Department department1 = new Department(DepartmentType.EMERGENCY);
        Department department2 = new Department(DepartmentType.CARDIOLOGY);
        Department department3 = new Department(DepartmentType.RADIOLOGY);

        Doctor drSmith = new Doctor("Dr. Smith", Gender.MALE, 55, "Cardiologist", department2, 5);
        Doctor drJohnson = new Doctor("Dr. Johnson", Gender.FEMALE, 45, "Emergency", department1, 10);
        Doctor drWilliams = new Doctor("Dr. Williams", Gender.MALE, 40, "Radiology", department3, 8);

        int drSmithId = 1;
        int drJohnsonId = 2;
        int drWilliamsId = 3;

        Map<Integer, Doctor> doctorsById = new HashMap<>();
        doctorsById.put(drSmithId, drSmith);
        doctorsById.put(drJohnsonId, drJohnson);
        doctorsById.put(drWilliamsId, drWilliams);

        return doctorsById;
    }

    public static List<Appointment> initializeAppointments(Map<Integer, Doctor> doctorsById) {
        Doctor drSmith = doctorsById.get(1);
        Doctor drJohnson = doctorsById.get(2);
        Doctor drWilliams = doctorsById.get(3);

        Generator generator = new Generator();
        List<Patient> patients = generator.generatePatients();
        List<Appointment> appointments = generator.generateAppointments(drSmith, drJohnson, drWilliams, patients);

        return appointments;
    }
}
