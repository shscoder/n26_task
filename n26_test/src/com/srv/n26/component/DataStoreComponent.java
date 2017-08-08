package com.srv.n26.component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.springframework.stereotype.Component;

import com.srv.n26.model.Transaction;

@Component
public class DataStoreComponent {

	private ConcurrentNavigableMap<Long, List<Transaction>> transactionsStorage;

	public DataStoreComponent() {
		this.transactionsStorage = new ConcurrentSkipListMap<>();
	}

	public synchronized void addTransaction(Transaction transaction) {

		List<Transaction> transactionAtGivenTime = transactionsStorage.get(transaction.getTimestamp());
		if (transactionAtGivenTime == null) {
			transactionAtGivenTime = new ArrayList<Transaction>();
		}
		transactionAtGivenTime.add(transaction);
		transactionsStorage.put(transaction.getTimestamp(), transactionAtGivenTime);
	}

	public  ConcurrentNavigableMap<Long, List<Transaction>> getTransactions() {
		
		return transactionsStorage;
	}
	
	//used for unit testing
	public synchronized void clearTransaction()
	{
		transactionsStorage.clear();
	}

}
