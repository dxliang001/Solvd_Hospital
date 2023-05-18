package com.solvd.Hospital.Classes;
import com.solvd.Hospital.Enums.Gender;
import com.solvd.Hospital.Interface.IMaintainable;

public class AdministrativeStaff extends Employee implements IMaintainable {

    public AdministrativeStaff(String name, Gender gender, int age, int experience, Department department, String jobTitle) {
        super(name, gender, age, experience, department, jobTitle);
    }

    @Override
    public void work() {
        System.out.println("The administrative staff is handling paperwork and managing resources.");
    }

    @Override
    public String maintainEquipment(MedicalEquipment equipment) {
        return "Maintaining equipment: " + equipment.getEquipmentName();
    }
}
