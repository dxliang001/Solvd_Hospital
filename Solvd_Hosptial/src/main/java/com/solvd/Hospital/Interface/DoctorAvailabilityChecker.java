package com.solvd.Hospital.Interface;

import com.solvd.Hospital.Classes.Appointment;
import com.solvd.Hospital.Classes.Doctor;

interface CheckAppointmentForDoctor {
    boolean check(Appointment appointment, Doctor doctor);
}

interface PrintAppointmentDetails {
    void print(Appointment appointment);
}