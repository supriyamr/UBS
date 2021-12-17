package com.example.demo.model;

public enum Style {
    AMERICAN("AMERICAN"),
    EUROPEAN("EUROPEAN");

    private final String style;

    Style(String style) {
            this.style = style;
        }

    public String getStyle() {
        return style;
    }

}
