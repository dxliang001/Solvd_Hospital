package com.solvd.Hospital.Classes;
import com.solvd.Hospital.Enums.Gender;
import com.solvd.Hospital.Interface.IMaintainable;

import com.solvd.Hospital.Main;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class AdministrativeStaff extends Employee implements IMaintainable {

    private static final Logger logger = LogManager.getLogger(Main.class);
    public AdministrativeStaff(String name, Gender gender, int age, int experience, Department department, String jobTitle) {
        super(name, gender, age, experience, department, jobTitle);
    }

    @Override
    public void work() {
        logger.info("The administrative staff is handling paperwork and managing resources.");
    }

    @Override
    public String maintainEquipment(MedicalEquipment equipment) {
        return "Maintaining equipment: " + equipment.getEquipmentName();
    }
}
