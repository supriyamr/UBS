package com.example.demo.model;

public enum Day {
    SATURDAY("SATURDAY"),
    SUNDAY("SUNDAY");

    private final String day;

    Day(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }

}
