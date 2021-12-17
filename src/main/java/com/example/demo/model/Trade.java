package com.example.demo.model;

public class Trade {

    private String customer;
    private String ccyPair;
    private String type;
    private String direction;
    private String tradeDate;
    private double amount1;
    private double amount2;
    private double rate;
    private String valueDate;
    private String legalEntity;
    private String trader;
    private String style;
    private String strategy;
    private String deliveryDate;
    private String expiryDate;
    private String payCcy;
    private double premium;
    private String premiumCcy;
    private String premiumType;
    private String premiumDate;
    private String excerciseStartDate;

    public Trade() {

    }
    public String getValueDate() {
        return valueDate;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public String getCustomer() {
        return customer;
    }

    public String getType() {
        return type;
    }

    public String getStyle() {
        return style;
    }

    public String getExcerciseStartDate() {
        return excerciseStartDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getPremiumDate() {
        return premiumDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public Trade(String customer, String ccyPair, String type, String direction, String tradeDate, double amount1, double amount2,
                 double rate, String valueDate, String legalEntity, String trader) {
        this.customer = customer;
        this.ccyPair = ccyPair;
        this.type = type;
        this.direction = direction;
        this.tradeDate = tradeDate;
        this.amount1 = amount1;
        this.amount2 = amount2;
        this.rate = rate;
        this.valueDate = valueDate;
        this.legalEntity = legalEntity;
        this.trader = trader;
    }

    public Trade(String customer, String ccyPair, String type, String style, String direction, String strategy, String tradeDate, double amount1, double amount2,
                 double rate, String deliveryDate, String expiryDate, String payCcy, double premium, String premiumCcy, String premiumType, String premiumDate, String legalEntity, String trader) {
        this.customer = customer;
        this.ccyPair = ccyPair;
        this.type = type;
        this.style = style;
        this.direction = direction;
        this.tradeDate = tradeDate;
        this.amount1 = amount1;
        this.amount2 = amount2;
        this.rate = rate;
        this.deliveryDate = deliveryDate;
        this.legalEntity = legalEntity;
        this.expiryDate = expiryDate;
        this.payCcy = payCcy;
        this.strategy = strategy;
        this.premium = premium;
        this.premiumCcy = premiumCcy;
        this.premiumType = premiumType;
        this.premiumDate = premiumDate;
        this.trader = trader;
    }

    public Trade(String customer, String ccyPair, String type, String style, String direction, String strategy, String tradeDate, double amount1, double amount2,
                 double rate, String deliveryDate, String expiryDate, String excerciseStartDate, String payCcy, double premium, String premiumCcy, String premiumType, String premiumDate, String legalEntity, String trader) {
        this.customer = customer;
        this.ccyPair = ccyPair;
        this.type = type;
        this.style = style;
        this.direction = direction;
        this.tradeDate = tradeDate;
        this.amount1 = amount1;
        this.amount2 = amount2;
        this.rate = rate;
        this.deliveryDate = deliveryDate;
        this.legalEntity = legalEntity;
        this.expiryDate = expiryDate;
        this.payCcy = payCcy;
        this.strategy = strategy;
        this.premium = premium;
        this.premiumCcy = premiumCcy;
        this.premiumType = premiumType;
        this.premiumDate = premiumDate;
        this.trader = trader;
        this.excerciseStartDate = excerciseStartDate;
    }
}

