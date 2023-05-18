package com.solvd.Hospital.Classes;

import com.solvd.Hospital.Classes.Doctor;
import com.solvd.Hospital.Exceptions.InvalidInputException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.Map;
import java.util.Scanner;

public class DoctorService {

    private static final Logger logger = LogManager.getLogger(DoctorService.class);

    public static void displayAllDoctors(Map<Integer, Doctor> doctorsById) {
        logger.info("List of all doctors:");
        for (Doctor doctor : doctorsById.values()) {
            logger.info(doctor.toString());
        }
    }

    public static Doctor chooseDoctor(Map<Integer, Doctor> doctorsById, Scanner scanner) {
        Doctor chosenDoctor = null;
        boolean validInput = false;

        do {
            try {
                logger.info("Choose a doctor:");
                for (Map.Entry<Integer, Doctor> entry : doctorsById.entrySet()) {
                    logger.info(entry.getKey() + ". " + entry.getValue());
                }
                logger.info("Enter the doctor's number: ");

                int doctorChoice = -1;
                if (scanner.hasNextInt()) {
                    doctorChoice = scanner.nextInt();
                } else {
                    throw new InvalidInputException("Invalid input. Please enter a valid doctor's number.");
                }

                chosenDoctor = doctorsById.get(doctorChoice);
                if (chosenDoctor == null) {
                    throw new InvalidInputException("Invalid choice. Please choose a valid doctor's number.");
                }

                validInput = true;
            } catch (InvalidInputException e) {
                logger.warn(e.getMessage());
                scanner.nextLine(); // Discard invalid input
            }
        } while (!validInput);

        scanner.nextLine(); // Consume the newline character left by nextInt()
        return chosenDoctor;
    }
}
