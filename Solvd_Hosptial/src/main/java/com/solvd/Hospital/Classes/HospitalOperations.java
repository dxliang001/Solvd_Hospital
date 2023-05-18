package com.solvd.Hospital.Classes;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class HospitalOperations {

    private static final Logger logger = LogManager.getLogger(Menu.class);
    public void startOperations() {

        Runnable checkInPatient = new Runnable() {
            @Override
            public void run() {
                Runnable checkInPatient = new Runnable() {
                    @Override
                    public void run() {
                        logger.info("Check in patient thread started.");

                        for (int i = 1; i <= 5; i++) {
                            logger.info("Checking in patient " + i);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        logger.info("Check in patient thread finished.");
                    }
                };

                new Thread(checkInPatient).start();
            }
        };
        new Thread(checkInPatient).start();

        Runnable administerTreatment = new Runnable() {
            @Override
            public void run() {
                Runnable administerTreatment = new Runnable() {
                    @Override
                    public void run() {
                        logger.info("Administer treatment thread started.");

                        for (int i = 1; i <= 5; i++) {
                            logger.info("Administering treatment to patient " + i);
                            try {
                                // Let's simulate some delay
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        logger.info("Administer treatment thread finished.");
                    }
                };

                new Thread(administerTreatment).start();
            }
        };
        new Thread(administerTreatment).start();
    }
}
