package com.srv.n26.controller;


import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.*;

import java.time.Instant;

import org.assertj.core.api.Assertions;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.srv.n26.component.DataStoreComponent;
import com.srv.n26.main.N26App;
import com.srv.n26.model.Transaction;
import com.srv.n26.service.TransactionHandlerServiceImpl;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = {N26App.class,TransactionController.class,TransactionHandlerServiceImpl.class,DataStoreComponent.class}
,webEnvironment=WebEnvironment.RANDOM_PORT)
public class TransactionControllerTest {

	
	
	@Value("${local.server.port}")
	private int port;
	

	@Test
	public void testSuccess(){
		
		String jsonStr = null;
		try {
			 jsonStr = new ObjectMapper().writeValueAsString(new Transaction(10D, timeAgo(20)));
			RestAssured.port = port;
		} catch (JsonProcessingException e) {
			Assertions.fail(e.getMessage());
		}
		
		Response resp = given().contentType("application/json")
			.body(jsonStr).when().post("/transactions");
		
		System.out.println("success code::"+resp.getStatusCode());
		assertTrue(resp.getStatusCode() == 201);
	}
	
	
	@Test
	public void testOlderTransaction(){
		
		String jsonStr = null;
		try {
			 jsonStr = new ObjectMapper().writeValueAsString(new Transaction(10D, timeAgo(70)));
			RestAssured.port = port;
		} catch (JsonProcessingException e) {
			Assertions.fail(e.getMessage());
		}
		
		Response resp = given().contentType("application/json")
			.body(jsonStr).when().post("/transactions");
		
		System.out.println("no content code::"+resp.getStatusCode());
		assertTrue(resp.getStatusCode() == 204);
	}
	
	private long timeAgo(long time){
		return Instant.now().minusSeconds(time).toEpochMilli();
	}
}
