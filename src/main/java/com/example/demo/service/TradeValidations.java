package com.example.demo.service;

import com.example.demo.exception.InvalidInputException;
import com.example.demo.model.Customer;
import com.example.demo.model.Day;
import com.example.demo.model.Style;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TradeValidations {
    Logger logger = LoggerFactory.getLogger(TradeValidations.class);
    public static Map<Integer, String> errorMap = new HashMap<>();

    public boolean isTradeValueDateValid(String valueDateString, String tradeDateString) throws InvalidInputException, ParseException {
        boolean isValid = true;

        if (valueDateString != null) {
            Date valueDate = new SimpleDateFormat("yyyy-MM-dd").parse(valueDateString);
            Date tradeDate = new SimpleDateFormat("yyyy-MM-dd").parse(tradeDateString);

            try {
                if (valueDate.before(tradeDate)) {
                    throw new InvalidInputException("Trade value date " + valueDate  + " cannot be before trade date " + tradeDate);
                }
            } catch (InvalidInputException ex) {
                logger.error("Trade value date " + valueDate + " cannot be before trade date " + tradeDate);
                errorMap.put(1, ex.getMessage());
            }

            Calendar c = Calendar.getInstance();
            c.setTime(valueDate);
            String dayOfWeek = new SimpleDateFormat("EEEE").format(valueDate);

            try {
                if (Day.SATURDAY.getDay().equalsIgnoreCase(dayOfWeek) || Day.SUNDAY.getDay().equals(dayOfWeek)) {
                    throw new InvalidInputException("Trade value date cannot be on weekend");
                }
            } catch (InvalidInputException ex) {
                logger.error("Trade value date cannot be on weekend");
                errorMap.put(2, ex.getMessage());
                isValid = false;
            }
        }

        return isValid;
    }

    public boolean hasTradeValueDate(String valueDate) {
        return valueDate != null;
    }

    public boolean isCustomerValid(String customer) throws InvalidInputException {
        try {
            if (Customer.YODA_1.getName().equalsIgnoreCase(customer) || Customer.YODA_2.getName().equalsIgnoreCase(customer)) {
                return true;
            }
            throw new InvalidInputException("Customer must be either YODA1 or YODA2, but is: " + customer);
        } catch (InvalidInputException ex) {
            logger.error("Customer must be either YODA1 or YODA2, but is: " + customer);
            errorMap.put(3, ex.getMessage());
            return false;
        }
    }

    public boolean isTradeStyleValid(String style) throws InvalidInputException {
        try {
            if (Style.AMERICAN.getStyle().equalsIgnoreCase(style) || Style.EUROPEAN.getStyle().equalsIgnoreCase(style)) {
                return true;
            }
            throw new InvalidInputException("VanillaOption trade's style must be either American or European, but is: " + style);
        } catch (InvalidInputException ex) {
            logger.error("VanillaOption trade's style must be either American or European, but is: " + style);
            errorMap.put(4, ex.getMessage());
            return false;
        }
    }

    public boolean isExcerciseStartDateValid(String excerciseStartDateString, String tradeDateString, String expiryDateString) throws InvalidInputException, ParseException {
        Date excerciseStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(excerciseStartDateString);
        Date tradeDate = new SimpleDateFormat("yyyy-MM-dd").parse(tradeDateString);
        Date expiryDate = new SimpleDateFormat("yyyy-MM-dd").parse(expiryDateString);

        try {
            if (excerciseStartDate.after(tradeDate) && excerciseStartDate.before(expiryDate)) {
                return true;
            }
            throw new InvalidInputException("ExcerciseStartDate " + excerciseStartDate + " has to be after the trade date " + tradeDate + " but before the expiry date " + expiryDate);
        } catch (InvalidInputException ex) {
            logger.error("ExcerciseStartDate " + excerciseStartDate + " has to be after the trade date " + tradeDate + " but before the expiry date " + expiryDate);
            errorMap.put(5, ex.getMessage());
            return false;
        }
    }

    public boolean isExpiryAndPremiumDateValid(String premiumDateString, String expiryDateString, String deliveryDateString) throws InvalidInputException, ParseException {
        Date premiumDate = new SimpleDateFormat("yyyy-MM-dd").parse(premiumDateString);
        Date deliveryDate = new SimpleDateFormat("yyyy-MM-dd").parse(deliveryDateString);
        Date expiryDate = new SimpleDateFormat("yyyy-MM-dd").parse(expiryDateString);

        try {
            if (premiumDate.before(deliveryDate) && expiryDate.before(deliveryDate)) {
                return true;
            }
            throw new InvalidInputException("Expiry date " + expiryDate + " and Premium date " + premiumDate + " shall be before delivery date " + deliveryDate);
        } catch (InvalidInputException ex) {
            logger.error("Expiry date " + expiryDate + " and Premium date " + premiumDate + " shall be before delivery date " + deliveryDate);
            errorMap.put(6, ex.getMessage());
            return false;
        }

    }

}
