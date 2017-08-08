package com.srv.n26.service;

import com.srv.n26.model.Statistics;
import com.srv.n26.model.Transaction;

public interface TransactionHandlerService{
	
	boolean addTransaction(Transaction transaction);
	
	Statistics getStatistics();

}
