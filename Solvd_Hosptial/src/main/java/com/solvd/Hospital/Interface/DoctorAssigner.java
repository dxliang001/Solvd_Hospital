package com.solvd.Hospital.Interface;

import com.solvd.Hospital.Classes.Appointment;
import com.solvd.Hospital.Classes.Doctor;

@FunctionalInterface
public interface DoctorAssigner {
    void assignDoctor(Appointment appointment, Doctor doctor);
}