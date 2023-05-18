package com.solvd.Hospital.Classes;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.Scanner;



public class Menu {
    private static final Logger logger = LogManager.getLogger(Menu.class);
    public String displayMainMenu(Scanner scanner) {
        logger.info("------------------------------------------------------");
        logger.info("Main Menu:");
        logger.info("a. Make an appointment");
        logger.info("b. Give a patient a diagnosis");
        logger.info("c. Prescribe treatment");
        logger.info("d. Write a prescription");
        logger.info("e. Display all doctors");
        logger.info("f. Find doctor information");
        logger.info("i. Total number of patient");
        logger.info("j. Patient older than 65");
        logger.info("k. Appointments sorted by date");
        logger.info("l. Check to see doctor if there is doctor less than 2 year experience");
        logger.info("m. Using reflection extract information");
        logger.info("h. Exit");
        logger.info("------------------------------------------------------");
        logger.info("Enter your choice: ");
        return scanner.nextLine();
    }



}
