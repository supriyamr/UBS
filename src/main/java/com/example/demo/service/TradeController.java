package com.example.demo.service;

import com.example.demo.model.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Map;

@RestController
public class TradeController {

    @Autowired
    TradeService tradeService;

    @PostMapping("/trade/validate")
    public ResponseEntity validateTrade(@RequestBody Trade trade) throws ParseException {

        Map<Integer, String> errorResponseMap = tradeService.tradeValidaton(trade);

        if (errorResponseMap.size() > 0) {
            return new ResponseEntity<>(errorResponseMap, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(trade, HttpStatus.OK);
    }
}
