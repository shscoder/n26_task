package com.srv.n26.controller;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.*;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;

import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.srv.n26.model.Statistics;
import com.srv.n26.model.Transaction;
import com.srv.n26.service.TransactionHandlerServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {N26App.class,TransactionController.class,TransactionHandlerServiceImpl.class,DataStoreComponent.class}
,webEnvironment=WebEnvironment.RANDOM_PORT)
public class StatisticsControllerTest {

	@Value("${local.server.port}")
	private int port;

	@Autowired private DataStoreComponent dataStoreComponent;

	@Before
	public void setUp() {
		RestAssured.port = port;
		Arrays.asList(new Transaction(20D, timeAgo(20)), new Transaction(40D, timeAgo(30)))
				.stream()
				.forEach(t -> dataStoreComponent.addTransaction(t));
	}
	
	@Test
	public void testCallOk(){
		Response resp = callStatsEndpoint();
		assertTrue(resp.getStatusCode() == HttpStatus.SC_OK);
	}
	
	@Test
	public void testResponseAvg() throws JsonProcessingException, IOException{
		Response resp = callStatsEndpoint();
		Statistics result = new ObjectMapper().readValue(resp.getBody().asString(), Statistics.class);
		Assertions.assertThat(result.getAvg()).isEqualTo(30D);
	}
	
	@Test
	public void testResponseSum() throws JsonProcessingException, IOException{
		Response resp = callStatsEndpoint();
		Statistics result = new ObjectMapper().readValue(resp.getBody().asString(), Statistics.class);
		Assertions.assertThat(result.getSum()).isEqualTo(60D);
	}
	
	@Test
	public void testResponseCount() throws JsonProcessingException, IOException{
		Response resp = callStatsEndpoint();
		Statistics result = new ObjectMapper().readValue(resp.getBody().asString(), Statistics.class);
		Assertions.assertThat(result.getCount()).isEqualTo(2L);
	}
	
	@Test
	public void testResponseMax() throws JsonProcessingException, IOException{
		Response resp = callStatsEndpoint();
		Statistics result = new ObjectMapper().readValue(resp.getBody().asString(), Statistics.class);
		Assertions.assertThat(result.getMax()).isEqualTo(40D);
	}

	@Test
	public void testResponseMin() throws JsonProcessingException, IOException{
		Response resp = callStatsEndpoint();
		Statistics result = new ObjectMapper().readValue(resp.getBody().asString(), Statistics.class);
		Assertions.assertThat(result.getMin()).isEqualTo(20D);
	}
	
	@After
	public void tearDown() {
		dataStoreComponent.clearTransaction();
	}
	
	private Response callStatsEndpoint() {
		return given().contentType("application/json")
			.when().get("/statistics");
	}
	
	private long timeAgo(long time){
		return Instant.now().minusSeconds(time).toEpochMilli();
	}
}