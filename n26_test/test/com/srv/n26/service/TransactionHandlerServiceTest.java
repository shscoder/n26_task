package com.srv.n26.service;


import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.srv.n26.component.DataStoreComponent;
import com.srv.n26.model.Statistics;
import com.srv.n26.model.Transaction;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TransactionHandlerServiceImpl.class,DataStoreComponent.class})
public class TransactionHandlerServiceTest {

	@Autowired
	private DataStoreComponent dataStoreComponent;

	@Autowired
	private TransactionHandlerService transactionHandlerService;

	private Statistics statistics;

	@Before
	public void setUp() {
		createTransactions();
		createOldTransactions();
		statistics = transactionHandlerService.getStatistics();

	}

	@Test
	public void testAvg() {
		assertTrue(statistics.getAvg() == 18.75D);
	}

	@Test
	public void testMax() {
		assertTrue(statistics.getMax() == 30D);
	}

	@Test
	public void testMin() {
		assertTrue(statistics.getMin() == 10D);
	}

	@Test
	public void testSum() {
		assertTrue(statistics.getSum() == 150D);
	}

	@Test
	public void testCount() {
		assertTrue(statistics.getCount() == 8L);
	}

	private void createOldTransactions() {
		List<Transaction> transactions = Arrays.asList(new Transaction(10D, timeAgo(64)),
				new Transaction(10D, timeAgo(100)), new Transaction(15D, timeAgo(64)),
				new Transaction(15D, timeAgo(67)), new Transaction(20D, timeAgo(78)), new Transaction(20D, timeAgo(90)),
				new Transaction(30D, timeAgo(89)), new Transaction(30D, timeAgo(88)));
		transactions.stream().forEach(t -> transactionHandlerService.addTransaction(t));
	}

	private void createTransactions() {
		List<Transaction> transactions = Arrays.asList(new Transaction(10D, timeAgo(20)),
				new Transaction(10D, timeAgo(11)), new Transaction(15D, timeAgo(22)), new Transaction(15D, timeAgo(22)),
				new Transaction(20D, timeAgo(44)), new Transaction(20D, timeAgo(44)), new Transaction(30D, timeAgo(55)),
				new Transaction(30D, timeAgo(55)));
		transactions.stream().forEach(t -> transactionHandlerService.addTransaction(t));
	}

	private long timeAgo(long time) {
		return Instant.now().minusSeconds(time).toEpochMilli();
	}

	@After
	public void coolDown() {
		dataStoreComponent.clearTransaction();
	}
}
