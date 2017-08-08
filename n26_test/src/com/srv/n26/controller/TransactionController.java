package com.srv.n26.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.srv.n26.model.Statistics;
import com.srv.n26.model.Transaction;
import com.srv.n26.service.TransactionHandlerService;

@RestController
public class TransactionController {

	@Autowired
	TransactionHandlerService transactionHandlerService;

	@RequestMapping(value = "/transactions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> addTransaction(@RequestBody Transaction transaction) {

		boolean status = transactionHandlerService.addTransaction(transaction);

		if (status) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}
		else
		{
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
	
	
	@RequestMapping(value = "/statistics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Statistics> getStatistics() {

		Statistics statistics = transactionHandlerService.getStatistics();

		return new ResponseEntity<Statistics>(statistics, HttpStatus.OK);
	}
	
	
	
}
