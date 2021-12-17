package com.example.demo.service;

import com.example.demo.exception.InvalidInputException;
import com.example.demo.model.Trade;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Service
public class TradeService {

    TradeValidations tradeValidations = new TradeValidations();
    Map<Integer, String> validMap = new HashMap<>();

    public Map<Integer, String> tradeValidaton(Trade trade) throws InvalidInputException, ParseException {

        boolean isTradeValueDateValid = tradeValidations.isTradeValueDateValid(trade.getValueDate(), trade.getTradeDate());
        boolean isCustomerValid = tradeValidations.isCustomerValid(trade.getCustomer());

        boolean hasTradeValueDate = false;
        if (trade.getType().equalsIgnoreCase("Spot") || trade.getType().equalsIgnoreCase("Forward")) {
            hasTradeValueDate = tradeValidations.hasTradeValueDate(trade.getValueDate());
        }

        boolean isTradeStyleValid = false;
        boolean isExpiryAndPremiumDateValid = false;
        boolean isExcerciseStartDateValid = false;
        if ("VanillaOption".equalsIgnoreCase(trade.getType())) {
            isTradeStyleValid =  tradeValidations.isTradeStyleValid(trade.getStyle());

            if (isTradeStyleValid) {
                if ("American".equalsIgnoreCase(trade.getStyle())) {
                    isExcerciseStartDateValid = tradeValidations.isExcerciseStartDateValid(trade.getExcerciseStartDate(), trade.getTradeDate(), trade.getExpiryDate());
                }
                isExpiryAndPremiumDateValid = tradeValidations.isExpiryAndPremiumDateValid(trade.getPremiumDate(), trade.getExpiryDate(), trade.getDeliveryDate());
            }
        }

        if (isTradeValueDateValid && isCustomerValid) {
            if (trade.getType().equalsIgnoreCase("Spot") || trade.getType().equalsIgnoreCase("Forward")) {
                if (hasTradeValueDate) {
                    return validMap;
                }
            } else if ("VanillaOption".equalsIgnoreCase(trade.getType())) {
                if (isTradeStyleValid && isExpiryAndPremiumDateValid) {
                    if ("American".equalsIgnoreCase(trade.getStyle())) {
                        if (isExcerciseStartDateValid) {
                            return validMap;
                        } else {
                            return TradeValidations.errorMap;
                        }
                    }
                    return validMap;
                }
            }
        }

        return TradeValidations.errorMap;
    }
}
