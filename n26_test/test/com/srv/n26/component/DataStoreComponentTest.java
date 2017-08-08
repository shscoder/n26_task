package com.srv.n26.component;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.srv.n26.model.Transaction;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataStoreComponent.class)
public class DataStoreComponentTest {

	@Autowired
	private DataStoreComponent dataStoreComponent;
	private Transaction transaction;

	@Before
	public void setUp() {
		transaction = new Transaction(22D, 1236781678L);
		dataStoreComponent.addTransaction(transaction);
	}

	@Test
	public void testTrasactionsNotEmpty() {

		assertTrue(dataStoreComponent.getTransactions().size() == 1);
	}

	@Test
	public void testTransactionListSize() {
		assertTrue(dataStoreComponent.getTransactions().size() == 1);
	}

	@Test
	public void testAddTransactionSameTimestamp() {
		Transaction transactionTwenty = new Transaction(20D, 1236781678L);
		dataStoreComponent.addTransaction(transactionTwenty);

		assertTrue(dataStoreComponent.getTransactions().size() == 1);
	}

	@Test
	public void testAddTransactionSameTimestampAddToList() {
		Transaction transactionTwenty = new Transaction(20D, 1236781678L);
		dataStoreComponent.addTransaction(transactionTwenty);

		assertTrue(dataStoreComponent.getTransactions().get(1236781678L).size() == 2);
	}

	@After
	public void tearDown() throws Exception {
		dataStoreComponent.clearTransaction();
	}
}
