package com.solvd.Hospital.Enums;

public enum Gender {
    MALE("M"),
    FEMALE("F"),
    OTHER("O");

    private final String shortForm;

    Gender(String shortForm) {
        this.shortForm = shortForm;
    }

    public String getShortForm() {
        return shortForm;
    }

    public static Gender getGenderShortForm(String shortForm) {
        for (Gender gender : Gender.values()) {
            if (gender.getShortForm().equals(shortForm)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Invalid short form for gender: " + shortForm);
    }
}