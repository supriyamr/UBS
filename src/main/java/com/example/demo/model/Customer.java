package com.example.demo.model;

public enum Customer {
    YODA_1("YODA1"),
    YODA_2("YODA2");

    private final String name;

    Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
