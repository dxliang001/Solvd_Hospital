package com.solvd.Hospital.Classes;

import com.solvd.Hospital.Enums.Gender;

public abstract class Employee extends Person {
    private final String id;
    private int experience;
    private Department department;
    private static int employeeCounter = 0;

    private String jobTitle;

    static {
        employeeCounter = 0;
    }

    public Employee(String name, Gender gender, int age, int experience, Department department, String jobTitle) {
        super(name, gender, age);
        this.experience = experience;
        this.department = department;
        employeeCounter++;
        this.id = "EMP" + employeeCounter;
        this.jobTitle = jobTitle;
    }



    // Abstract method
    public abstract void work();

    // Getters and setters
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public final String getId() {
        return id;
    }
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }


    public void setExperience(int experience) {
        this.experience = experience;
    }


}
