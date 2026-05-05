package com.example.back.constant;

public enum StudentsGender {
    MALE("男"),
    FEMALE("女"),
    OTHER("其他");

    private final String label;

    StudentsGender(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
