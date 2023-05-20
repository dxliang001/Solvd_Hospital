package com.solvd.Hospital;

import java.lang.reflect.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;


import com.solvd.Hospital.Classes.*;
import com.solvd.Hospital.ConnectionPool.Connection;
import com.solvd.Hospital.ConnectionPool.ConnectionManager;
import com.solvd.Hospital.ConnectionPool.ConnectionPool;
import com.solvd.Hospital.Enums.DepartmentType;
import com.solvd.Hospital.Enums.Gender;
import com.solvd.Hospital.Exceptions.InvalidInputException;
import com.solvd.Hospital.Exceptions.InvalidMenuOptionException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);
    private static Scanner scanner = new Scanner(System.in);
    private static List<Appointment> appointments = new ArrayList<>();

    private static final HashMap<Integer, Doctor> doctorsById = new HashMap<>();
    private static final HashSet<String> patientIds = new HashSet<>();
    private static ArrayList<Appointment> checkUpAppointments = new ArrayList<>();
    private static CustomLinkedList<Appointment> sickAppointments = new CustomLinkedList<>();

    private static PriorityQueue<Appointment> upcomingAppointments = new PriorityQueue<>(
            Comparator.comparing(Appointment::getAppointmentDateTime));


    // Define lambda functions
    private static BiFunction<Map<Integer, Doctor>, String, Optional<Doctor>> findDoctorByName =
            (doctors, name) -> doctors.values().stream().filter(doctor -> doctor.getName().equalsIgnoreCase(name)).findFirst();
    static BiFunction<List<Appointment>, Doctor, List<Appointment>> getAppointmentsWithDoctor =
            (appointments, doctor) ->
                    appointments.stream()
                            .filter(appointment -> appointment.getDoctor().equals(doctor))
                            .collect(Collectors.toList());

    private static Consumer<Appointment> printAppointment = appointment -> logger.info(appointment.toString());

    private static Function<Map<Integer, Doctor>, Double> calculateAverageDoctorExperience =
            doctors -> doctors.values().stream().mapToInt(Doctor::getExperience).average().orElse(0);

    private static BiFunction<Map<Integer, Doctor>, Integer, List<Doctor>> getDoctorsWithMoreExperience =
            (doctors, experience) -> doctors.values().stream().filter(doctor -> doctor.getExperience() > experience).collect(Collectors.toList());


    //Initialize Connection Pool object of size 5. Load Connection Pool using single threads and Java Thread Pool (7 threads in total).
    // 5 threads should be able to get the connection. 2 Threads should wait for the next available connection.
    // The program should wait as well.
    private static void connectToDatabase() {
        final ConnectionPool connectionPool = new ConnectionPool(5);

        Runnable connectTask = () -> {
            try {
                Connection connection = connectionPool.getConnection();
                logger.info(Thread.currentThread().getName() + " obtained a connection.");
                Thread.sleep(2000); // Simulate work
                connectionPool.releaseConnection(connection);
                logger.info(Thread.currentThread().getName() + " released a connection.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(7);
        for (int i = 0; i < 7; i++) {
            executorService.execute(connectTask);
        }

        executorService.shutdown();
    }

    // initialize Connection Pool object of size 5.
    private static void initializeConnectionPool() {
        ConnectionManager.initializePool();
    }


    //Create 2 Threads using Runnable and Thread.
    private static void runHospitalOperations() {
        HospitalOperations hospitalOperations = new HospitalOperations();
        hospitalOperations.startOperations();
    }

    public static void main(String[] args) throws InterruptedException {

        Map<Integer, Doctor> doctorsById = com.solvd.Hospital.Utils.HospitalInitialization.initializeDoctors();
        List<Appointment> appointments = com.solvd.Hospital.Utils.HospitalInitialization.initializeAppointments(doctorsById);

        Generator generator = new Generator();
        List<Patient> patients = generator.generatePatients();

        runHospitalOperations();
        connectToDatabase();
        initializeConnectionPool();


        Thread.sleep(6000);
        Menu menu = new Menu();
        AppointmentHandler appointmentHandler = new AppointmentHandler();

        processUserChoice(menu, patients);

        scanner.close();
    }


    private static void createAndInspectAppointmentObjectWithReflection() {
        try {

            Class<?> appointmentClass = Class.forName("com.solvd.Hospital.Classes.Appointment");


            logger.info("\nFields:");
            Field[] fields = appointmentClass.getDeclaredFields();
            for (Field field : fields) {
                logger.info("Field: " + field.getName() + ", Type: " + field.getType());
            }


            logger.info("\nMethods:");
            Method[] methods = appointmentClass.getDeclaredMethods();
            for (Method method : methods) {
                logger.info("Method: " + method.getName() + ", Return Type: " + method.getReturnType() + ", Parameter Types: " + Arrays.toString(method.getParameterTypes()));
            }


            Class<?> doctorClass = Class.forName("com.solvd.Hospital.Classes.Doctor");
            Constructor<?> doctorConstructor = doctorClass.getConstructor(String.class, Gender.class, int.class, String.class, Department.class, int.class);
            Doctor doctor = (Doctor) doctorConstructor.newInstance("Dr. Reflection", Gender.MALE, 45, "Radiologist", new Department(DepartmentType.RADIOLOGY), 10);

            // Create a new instance of the Patient class
            Class<?> patientClass = Class.forName("com.solvd.Hospital.Classes.Patient");
            Constructor<?> patientConstructor = patientClass.getConstructor(String.class, Gender.class, int.class, String.class);
            Patient patient = (Patient) patientConstructor.newInstance("Patient Reflection", Gender.MALE, 30, "123456");

            // Create a new instance of the Appointment class
            Constructor<?> appointmentConstructor = appointmentClass.getConstructor(Doctor.class, Patient.class, String.class, LocalDateTime.class);
            Appointment appointment = (Appointment) appointmentConstructor.newInstance(doctor, patient, "Check-up", LocalDateTime.now());


            // Use reflection to call method
            Method getDoctorMethod = appointmentClass.getMethod("getDoctor");
            Doctor reflectedDoctor = (Doctor) getDoctorMethod.invoke(appointment);
            logger.info("Reflected Doctor from Appointment: " + reflectedDoctor);

        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException |
                 InvocationTargetException e) {
            logger.info(e.getMessage());
        }
    }

    private static void processUserChoice(Menu menu, List<Patient> patients) {
        String choice = "";

        do {
            try {
                choice = menu.displayMainMenu(scanner);

                switch (choice) {
                    case "a" -> {
                        Doctor chosenDoctor = DoctorService.chooseDoctor(doctorsById, scanner);
                        AppointmentHandler.makeAppointment(chosenDoctor);
                    }
                    case "b" -> {
                        Doctor chosenDoctor = DoctorService.chooseDoctor(doctorsById, scanner);
                        AppointmentHandler.givePatientDiagnosis(chosenDoctor);
                    }
                    case "c" -> {
                        Doctor chosenDoctor = DoctorService.chooseDoctor(doctorsById, scanner);
                        AppointmentHandler.prescribeTreatment(chosenDoctor);
                    }
                    case "d" -> {
                        Doctor chosenDoctor = DoctorService.chooseDoctor(doctorsById, scanner);
                        AppointmentHandler.writePrescription(chosenDoctor);
                    }
                    case "e" -> DoctorService.displayAllDoctors(doctorsById);

                    case "f" -> {
                        logger.info("Enter the name of the doctor:");
                        String name = scanner.nextLine();
                        Optional<Doctor> doctorOptional = findDoctorByName.apply(doctorsById, name);
                        if (doctorOptional.isPresent()) {
                            logger.info(doctorOptional.get().toString());
                        } else {
                            logger.warn("No doctor found with this name.");
                        }
                        break;
                    }

                    case "i" -> {
                        long patientCount = patients.stream().count();
                        logger.info("The total number of patients is: " + patientCount);
                    }
                    case "j" -> {
                        List<Patient> oldPatients = patients.stream().filter(patient -> patient.getAge() > 65).collect(Collectors.toList());
                        logger.info("Patients older than 65: ");
                        oldPatients.forEach(patient -> logger.info(patient.toString()));
                    }

                    case "k" -> {
                        List<Appointment> sortedAppointments = appointments.stream().sorted(Comparator.comparing(Appointment::getAppointmentDateTime)).collect(Collectors.toList());
                        logger.info("Appointments sorted by date: ");
                        sortedAppointments.forEach(appointment -> logger.info(appointment.toString()));
                    }
                    case "l" -> {
                        int years = 2;
                        boolean inexperiencedDoctorExists = doctorsById.values().stream().anyMatch(doctor -> doctor.getExperience() < years);
                        logger.info("Is there any doctor with less than " + years + " years of experience? " + (inexperiencedDoctorExists ? "Yes" : "No"));
                    }

                    case "m" -> {
                        createAndInspectAppointmentObjectWithReflection();
                    }


                    case "h" -> {
                        logger.info("Goodbye!");
                    }
                    default -> {
                        logger.warn("Invalid Input");
                        throw new InvalidMenuOptionException("Invalid choice. Please try again.");
                    }
                }
            } catch (InvalidInputException | InvalidMenuOptionException e) {
                logger.info(e.getMessage());
            }
        } while (!choice.equalsIgnoreCase("h"));

    }
}


