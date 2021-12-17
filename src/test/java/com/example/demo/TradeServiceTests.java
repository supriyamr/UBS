package com.example.demo;

import com.example.demo.model.Trade;
import com.example.demo.service.TradeService;
import com.example.demo.service.TradeValidations;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TradeServiceTests {

	@Autowired
	TradeService tradeService;

	@BeforeEach
	public void setUp() {
		tradeService = new TradeService();
		TradeValidations.errorMap = new HashMap<>();
	}

	@AfterEach
	public void tearDown() {
		tradeService = null;
		TradeValidations.errorMap = new HashMap<>();
	}

	@Test
	public void TestValueDateOnWeekendForYoda1() throws Exception {

		Trade trade = new Trade("YODA1","EURUSD","Spot","BUY","2020-08- 11",1000000.00,1120000.00,1.12,"2020-08-15",
				"UBS AG","Josef Schoenberger");

		String expected = "Trade value date cannot be on weekend";
		Map<Integer, String> actual = tradeService.tradeValidaton(trade);

		assertEquals(1, actual.size());
		assertEquals(expected, actual.get(2));
	}

	@Test
	public void TestValueDateOnWeekendForYoda2() throws Exception {

		Trade trade = new Trade("YODA2","EURUSD","Forward","SELL","2020-08- 11",1000000.00,1120000.00,1.12,"2020-08-22",
				"UBS AG","Josef Schoenberger");

		String expected = "Trade value date cannot be on weekend";
		Map<Integer, String> actual = tradeService.tradeValidaton(trade);

		assertEquals(1, actual.size());
		assertEquals(expected, actual.get(2));
	}

	@Test
	public void TestValueDateOnWeekendAndBeforeTradeDate() throws Exception {

		Trade trade = new Trade("YODA2","EURUSD","Forward","BUY","2020-08- 11",1000000.00,1120000.00,1.12,"2020-08-08",
				"UBS AG","Josef Schoenberger");

		String expectedWeekendError = "Trade value date cannot be on weekend";
		String expectedBeforeTradeDateError = "Trade value date Sat Aug 08 00:00:00 BST 2020 cannot be before trade date Tue Aug 11 00:00:00 BST 2020";
		Map<Integer, String> actual = tradeService.tradeValidaton(trade);

		assertEquals(2, actual.size());
		assertEquals(expectedBeforeTradeDateError, actual.get(1));
		assertEquals(expectedWeekendError, actual.get(2));
	}

	@Test
	public void TestValueDateOnWeekendAndBeforeTradeDateAndInvalidCustomer() throws Exception {

		Trade trade = new Trade("PLUT02","EURUSD","Forward","BUY","2020-08- 11",1000000.00,1120000.00,1.12,"2020-08-08",
				"UBS AG","Josef Schoenberger");

		String expectedWeekendError = "Trade value date cannot be on weekend";
		String expectedCustomerError = "Customer must be either YODA1 or YODA2, but is: PLUT02";
		String expectedBeforeTradeDateError = "Trade value date Sat Aug 08 00:00:00 BST 2020 cannot be before trade date Tue Aug 11 00:00:00 BST 2020";
		Map<Integer, String> actual = tradeService.tradeValidaton(trade);

		assertEquals(3, actual.size());
		assertEquals(expectedBeforeTradeDateError, actual.get(1));
		assertEquals(expectedWeekendError, actual.get(2));
		assertEquals(expectedCustomerError, actual.get(3));
	}

	@Test
	public void TestValidEuropeanVanillaOptionTrade() throws Exception {

		Trade trade = new Trade("YODA1","EURUSD","VanillaOption","EUROPEAN","BUY", "CALL","2020-08-11",1000000.00,1120000.00,1.12,"2020-08-22","2020-08-19","USD",0.20,"USD",
				"%USD","2020-08-12", "UBS AG","Josef Schoenberger");

		Map<Integer, String> actual = tradeService.tradeValidaton(trade);

		assertEquals(0, actual.size());
	}

	@Test
	public void TestVanillaOptionWithExpiryDateAfterDeliveryDate() throws Exception {

		Trade trade = new Trade("YODA1","EURUSD","VanillaOption","EUROPEAN","BUY", "CALL","2020-08-11",1000000.00,1120000.00,1.12,"2020-08-22","2020-08-25","USD",0.20,"USD",
				"%USD","2020-08-12", "UBS AG","Josef Schoenberger");

		String expectedMessage = "Expiry date Tue Aug 25 00:00:00 BST 2020 and Premium date Wed Aug 12 00:00:00 BST 2020 shall be before delivery date Sat Aug 22 00:00:00 BST 2020";
		Map<Integer, String> actual = tradeService.tradeValidaton(trade);

		assertEquals(1, actual.size());
		assertEquals(expectedMessage, actual.get(6));
	}

	@Test
	public void TestValidAmericanVanillaOptionTrade() throws Exception {

		Trade trade = new Trade("YODA2","EURUSD","VanillaOption","AMERICAN","SELL", "CALL","2020-08-11",1000000.00,1120000.00,1.12,"2020-08-22","2020-08-21","2020-08-12","USD",0.20,"USD",
				"%USD","2020-08-12", "UBS AG","Josef Schoenberger");

		Map<Integer, String> actual = tradeService.tradeValidaton(trade);

		assertEquals(0, actual.size());
	}

	@Test
	public void TestVanillaOptionTradeExcerciseStartDate() throws Exception {

		Trade trade = new Trade("YODA1","EURUSD","VanillaOption","AMERICAN","BUY", "CALL","2020-08-11",1000000.00,1120000.00,1.12,"2020-08-22","2020-08-19",
				"2020-08-10","USD",0.20,"USD", "%USD","2020-08-12", "UBS AG","Josef Schoenberger");

		String expectedMessage = "ExcerciseStartDate Mon Aug 10 00:00:00 BST 2020 has to be after the trade date Tue Aug 11 00:00:00 BST 2020 but before the expiry date Wed Aug 19 00:00:00 BST 2020";
		Map<Integer, String> actual = tradeService.tradeValidaton(trade);

		assertEquals(1, actual.size());
		assertEquals(expectedMessage, actual.get(5));
	}

	@Test
	public void TestInvalidExcerciseStartDateAndCustomer() throws Exception {

		Trade trade = new Trade("PLUTO3","EURUSD","VanillaOption","AMERICAN","SELL", "CALL","2020-08-11",1000000.00,1120000.00,1.12,"2020-08-22","2020-08-19",
				"2020-08-10","USD",0.20,"USD", "%USD","2020-08-12", "UBS AG","Josef Schoenberger");

		String expectedExcerciseStartDateMessage = "ExcerciseStartDate Mon Aug 10 00:00:00 BST 2020 has to be after the trade date Tue Aug 11 00:00:00 BST 2020 but before the expiry date Wed Aug 19 00:00:00 BST 2020";
		String expectedCustomerMessage = "Customer must be either YODA1 or YODA2, but is: PLUTO3";
		Map<Integer, String> actual = tradeService.tradeValidaton(trade);

		assertEquals(2, actual.size());
		assertEquals(expectedExcerciseStartDateMessage, actual.get(5));
		assertEquals(expectedCustomerMessage, actual.get(3));
	}

	@Test
	public void TestInValidVanillaOptionTradeStyle() throws Exception {

		Trade trade = new Trade("YODA2","EURUSD","VanillaOption","XYZ","SELL", "CALL","2020-08-11",1000000.00,1120000.00,1.12,"2020-08-22","2020-08-21","2020-08-12","USD",0.20,"USD",
				"%USD","2020-08-12", "UBS AG","Josef Schoenberger");

		String expectedMessage = "VanillaOption trade's style must be either American or European, but is: XYZ";
		Map<Integer, String> actual = tradeService.tradeValidaton(trade);

		assertEquals(1, actual.size());
		assertEquals(expectedMessage, actual.get(4));
	}
}
