package com.solvd.Hospital.Classes;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import com.solvd.Hospital.Enums.Gender;
import com.solvd.Hospital.Exceptions.InvalidInputException;

import com.solvd.Hospital.Main;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.function.Predicate;
import java.util.stream.Collectors;


public class AppointmentHandler {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static Scanner scanner = new Scanner(System.in);

    private static List<Appointment> checkUpAppointments = new ArrayList<>();
    private static CustomLinkedList<Appointment> sickAppointments = new CustomLinkedList<>();

    private static PriorityQueue<Appointment> upcomingAppointments = new PriorityQueue<>(
            Comparator.comparing(Appointment::getAppointmentDateTime));


    private static List<Appointment> appointments = new ArrayList<>();



    public static void makeAppointment(Doctor chosenDoctor) throws InvalidInputException {
        logger.info("Enter your name:");
        String patientName = scanner.nextLine();

        logger.info("Enter your gender (MALE/FEMALE/OTHER):");
        String patientGender = scanner.nextLine().toUpperCase();
        while (!Arrays.stream(Gender.values()).map(Enum::name).collect(Collectors.toList()).contains(patientGender)) {
            logger.warn("Invalid gender input. Please enter MALE, FEMALE,or OTHER.");
            patientGender = scanner.nextLine().toUpperCase();
        }

        logger.info("Enter patient age:");
        int patientAge = -1;
        while (patientAge == -1) {
            if (scanner.hasNextInt()) {
                patientAge = scanner.nextInt();
                if (patientAge < 0) {
                    logger.warn("Age cannot be negative. Please enter a valid age.");
                    patientAge = -1;
                }
            } else {
                logger.warn("Invalid input. Please enter a valid age (integer).");
                scanner.next(); // Discard invalid input
            }
        }
        scanner.nextLine();

        Patient currentPatient = new Patient(patientName, Gender.valueOf(patientGender.toUpperCase()), patientAge, new MedicalRecord("", "", ""));

        List<LocalTime> availableTimeSlots = generateAvailableTimeSlots(chosenDoctor);

        logger.info("Available time slots:");
        for (int i = 0; i < availableTimeSlots.size(); i++) {
            logger.info((i + 1) + ". " + availableTimeSlots.get(i).format(DateTimeFormatter.ofPattern("hh:mm a")));
        }

        int chosenTimeSlotIndex = -1;
        while (chosenTimeSlotIndex < 0 || chosenTimeSlotIndex >= availableTimeSlots.size()) {
            logger.info("Choose a time slot by entering the corresponding number:");
            if (scanner.hasNextInt()) {
                chosenTimeSlotIndex = scanner.nextInt() - 1;
                if (chosenTimeSlotIndex < 0 || chosenTimeSlotIndex >= availableTimeSlots.size()) {
                    logger.warn("Invalid choice. Please enter a number corresponding to an available time slot.");
                }
            } else {
                logger.warn("Invalid input. Please enter a number corresponding to an available time slot.");
                scanner.next();
            }
        }
        scanner.nextLine();

        LocalTime chosenTime = availableTimeSlots.get(chosenTimeSlotIndex);

        logger.info("Enter the appointment date (MM/DD/YYYY): ");
        String appointmentDate = scanner.nextLine();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        LocalDateTime appointmentDateTime = null;
        try {
            LocalDate localDate = LocalDate.parse(appointmentDate, dateFormatter);
            appointmentDateTime = LocalDateTime.of(localDate, chosenTime);
        } catch (DateTimeParseException e) {
            logger.error("Invalid date format. Please enter the date in MM/DD/YYYY format.", e);
        }

        logger.info("Enter the reason for the appointment (1. Sick, 2. Check up, 3. Other): ");
        int reasonChoice = -1;
        while (reasonChoice < 1 || reasonChoice > 3) {
            if (scanner.hasNextInt()) {
                reasonChoice = scanner.nextInt();
                if (reasonChoice < 1 || reasonChoice > 3) {
                    logger.warn("Invalid reason choice. Please choose 1, 2, or 3.");
                }
            } else {
                logger.warn("Invalid input. Please enter a valid reason choice (integer between 1 and 3).");
                scanner.next(); // Discard invalid input
            }
        }
        scanner.nextLine();

        String appointmentReason;
        switch (reasonChoice) {
            case 1:
                appointmentReason = "Sick";
                break;
            case 2:
                appointmentReason = "Check up";
                break;
            case 3:
                appointmentReason = "Other";
                break;
            default:
                throw new InvalidInputException("Invalid reason choice. Please choose 1, 2, or 3.");
        }

        Appointment appointment = new Appointment(appointmentDateTime, appointmentReason, chosenDoctor, currentPatient);
        appointments.add(appointment);
        logger.info("Appointment created: " + appointment);

        switch (appointmentReason) {
            case "Check up":
                checkUpAppointments.add(appointment);
                break;
            case "Sick":
                sickAppointments.add(appointment);
                break;
            case "Other":
                upcomingAppointments.add(appointment);
                break;
            default:

        }

        logger.info("All appointments:");
        List<Appointment> allAppointments = new ArrayList<>();
        allAppointments.addAll(checkUpAppointments);
        allAppointments.addAll(sickAppointments.toArrayList());
        allAppointments.addAll(upcomingAppointments);

        for (Appointment appt : upcomingAppointments) {
            allAppointments.add(appt);
        }

        if (allAppointments.isEmpty()) {
            logger.info("No appointments scheduled.");
        } else {
            for (Appointment appt : allAppointments) {
                logger.info(appt.toString());
            }
        }
    }

    private static List<LocalTime> generateAvailableTimeSlots(Doctor chosenDoctor) {
        List<LocalTime> timeSlots = new ArrayList<>();

        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(17, 0);
        Duration appointmentDuration = Duration.ofMinutes(30);

        for (LocalTime time = startTime; time.isBefore(endTime); time = time.plus(appointmentDuration)) {
            boolean slotAvailable = true;

            for (Appointment appointment : appointments) {
                if (appointment.getDoctor().equals(chosenDoctor)
                        && appointment.getAppointmentDateTime().toLocalTime().equals(time)
                        && appointment.getAppointmentDateTime().toLocalDate().equals(LocalDate.now())) {
                    slotAvailable = false;
                    break;
                }
            }

            if (slotAvailable) {
                timeSlots.add(time);
            }
        }

        return timeSlots;
    }

    public static void givePatientDiagnosis(Doctor chosenDoctor)throws InvalidInputException {
        logger.info("Enter your name:");
        String patientName = scanner.nextLine();

        logger.info("Enter your gender (MALE/FEMALE/OTHER):");
        String patientGender = scanner.nextLine().toUpperCase();
        while (!Arrays.stream(Gender.values()).map(Enum::name).collect(Collectors.toList()).contains(patientGender)) {
            logger.warn("Invalid gender input. Please enter MALE, FEMALE,or OTHER.");
            patientGender = scanner.nextLine().toUpperCase();
        }

        logger.info("Enter patient age:");
        int patientAge = -1;
        while (patientAge == -1) {
            if (scanner.hasNextInt()) {
                patientAge = scanner.nextInt();
                if (patientAge < 0) {
                    logger.warn("Age cannot be negative. Please enter a valid age.");
                    patientAge = -1;
                }
            } else {
                logger.warn("Invalid input. Please enter a valid age (integer).");
                scanner.next(); // Discard invalid input
            }
        }
        scanner.nextLine();

        Patient currentPatient = new Patient(patientName, Gender.valueOf(patientGender.toUpperCase()), patientAge, new MedicalRecord("", "", ""));

        logger.info("Enter the patient's diagnosis: ");
        String diagnosis = scanner.nextLine();
        currentPatient.getMedicalRecord().setMedicalCondition(diagnosis);
        logger.info("Diagnosis by " + chosenDoctor.getName() + ": " + diagnosis + " to: " + currentPatient);
    }


    public static void prescribeTreatment(Doctor chosenDoctor) throws InvalidInputException {
        logger.info("Enter patient name:");
        String patientName = scanner.nextLine();

        logger.info("Enter your gender (MALE/FEMALE/OTHER):");
        String patientGender = scanner.nextLine().toUpperCase();
        while (!Arrays.stream(Gender.values()).map(Enum::name).collect(Collectors.toList()).contains(patientGender)) {
            logger.warn("Invalid gender input. Please enter MALE, FEMALE,or OTHER.");
            patientGender = scanner.nextLine().toUpperCase();
        }

        logger.info("Enter patient age:");
        int patientAge = -1;
        while (patientAge == -1) {
            if (scanner.hasNextInt()) {
                patientAge = scanner.nextInt();
                if (patientAge < 0) {
                    logger.warn("Age cannot be negative. Please enter a valid age.");
                    patientAge = -1;
                }
            } else {
                logger.warn("Invalid input. Please enter a valid age (integer).");
                scanner.next(); // Discard invalid input
            }
        }
        scanner.nextLine();

        Patient currentPatient = new Patient(patientName, Gender.valueOf(patientGender.toUpperCase()), patientAge, new MedicalRecord("", "", ""));

        logger.info("Select a treatment from the following options:");
        logger.info("1. Medication");
        logger.info("2. Surgery");
        logger.info("3. Physical therapy");
        int treatmentChoice = -1;
        while (treatmentChoice == -1) {
            if (scanner.hasNextInt()) {
                treatmentChoice = scanner.nextInt();
                if (treatmentChoice < 1 || treatmentChoice > 3) {
                    logger.warn("Invalid input. Please select a valid treatment option (1-3).");
                    treatmentChoice = -1;
                }
            } else {
                logger.warn("Invalid input. Please select a valid treatment option (1-3).");
                scanner.next(); // Discard invalid input
            }
        }
        scanner.nextLine();

        String treatment = switch (treatmentChoice) {
            case 1 -> "Medication";
            case 2 -> "Surgery";
            case 3 -> "Physical therapy";
            default -> throw new InvalidInputException("Invalid treatment choice.");
        };

        currentPatient.getMedicalRecord().setTreatment(treatment);
        logger.info("Treatment prescribed by " + chosenDoctor.getName() + ": " + treatment + " to: " + currentPatient);
    }


    public static void writePrescription(Doctor chosenDoctor) throws InvalidInputException {
        logger.info("Enter patient name:");
        String patientName = scanner.nextLine();

        logger.info("Enter your gender (MALE/FEMALE/OTHER):");
        String patientGender = scanner.nextLine().toUpperCase();
        while (!Arrays.stream(Gender.values()).map(Enum::name).collect(Collectors.toList()).contains(patientGender)) {
            logger.warn("Invalid gender input. Please enter MALE, FEMALE,or OTHER.");
            patientGender = scanner.nextLine().toUpperCase();
        }

        logger.info("Enter patient age:");
        int patientAge = -1;
        while (patientAge == -1) {
            if (scanner.hasNextInt()) {
                patientAge = scanner.nextInt();
                if (patientAge < 0) {
                    logger.warn("Age cannot be negative. Please enter a valid age.");
                    patientAge = -1;
                }
            } else {
                logger.warn("Invalid input. Please enter a valid age (integer).");
                scanner.next(); // Discard invalid input
            }
        }
        scanner.nextLine();

        Patient currentPatient = new Patient(patientName, Gender.valueOf(patientGender.toUpperCase()), patientAge, new MedicalRecord("", "", ""));

        logger.info("Select a medication from the following options:");
        logger.info("1. Acetaminophen 500mg tablet");
        logger.info("2. Chlorpheniramine 4mg tablet");
        logger.info("3. Guaifenesin 400mg tablet");
        int medicationChoice = -1;
        while (medicationChoice == -1) {
            if (scanner.hasNextInt()) {
                medicationChoice = scanner.nextInt();
                if (medicationChoice < 1 || medicationChoice > 3) {
                    logger.warn("Invalid input. Please select a valid medication option (1-3).");
                    medicationChoice = -1;
                }
            } else {
                logger.warn("Invalid input. Please select a valid medication option (1-3).");
                scanner.next(); // Discard invalid input
            }
        }
        scanner.nextLine();

        String medication;
        String dosage;
        switch (medicationChoice) {
            case 1 -> {
                medication = "Acetaminophen";
                dosage = "500mg tablet, take 2 tablets every 6 hours as needed for pain and fever";
            }
            case 2 -> {
                medication = "Chlorpheniramine";
                dosage = "4mg tablet, take 1 tablet every 12 hours as needed for congestion and runny nose";
            }
            case 3 -> {
                medication = "Guaifenesin";
                dosage = "400mg tablet, take 1 tablet every 12 hours as needed for cough";
            }
            default -> throw new InvalidInputException("Invalid medication choice.");
        }

        Prescription prescription = new Prescription(medication, dosage, "");
        currentPatient.getMedicalRecord().setMedication(prescription.getMedication());
        logger.info("Prescription written by " + chosenDoctor.getName() + ": " + prescription + " to: " + currentPatient);
    }

    public List<Appointment> filterAppointments(Predicate<Appointment> condition) {
        return appointments.stream().filter(condition).collect(Collectors.toList());
    }

}