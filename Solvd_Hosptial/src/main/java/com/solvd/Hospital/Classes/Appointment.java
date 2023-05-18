package com.solvd.Hospital.Classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private LocalDateTime appointmentDateTime;
    private String reason;
    private Doctor doctor;
    private Patient patient;

    public Appointment(LocalDateTime appointmentDateTime, String reason, Doctor doctor, Patient patient) {
        this.appointmentDateTime = appointmentDateTime;
        this.reason = reason;
        this.doctor = doctor;
        this.patient = patient;
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
        String formattedDateTime = appointmentDateTime.format(formatter);
        return "Appointment{" +
                "dateTime='" + formattedDateTime + '\'' +
                ", reason='" + reason + '\'' +
                ", doctor=" + doctor +
                ", patient=" + patient +
                '}';
    }


}
