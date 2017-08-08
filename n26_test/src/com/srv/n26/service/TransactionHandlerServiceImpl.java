package com.srv.n26.service;

import java.time.Instant;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srv.n26.component.DataStoreComponent;
import com.srv.n26.model.Statistics;
import com.srv.n26.model.Transaction;

@Service
public class TransactionHandlerServiceImpl implements TransactionHandlerService {

	@Autowired
	DataStoreComponent dataStoreComponent;

	@Override
	public boolean addTransaction(Transaction transaction) {

		Long sixtySecondAgoEpochTime = Instant.now().minusSeconds(60L)
				.toEpochMilli();

		if (transaction.getTimestamp() < sixtySecondAgoEpochTime) {
			return false;
		} else {
			dataStoreComponent.addTransaction(transaction);
			return true;
		}

	}

	@Override
	public Statistics getStatistics() {

		Long sixtySecondAgoEpochTime = Instant.now().minusSeconds(60L)
				.toEpochMilli();

		List<Transaction> validTransactions = dataStoreComponent
				.getTransactions().tailMap(sixtySecondAgoEpochTime).values()
				.stream().flatMap(t -> t.stream()).collect(Collectors.toList());

		Statistics statistics = new Statistics();
		if (validTransactions == null || validTransactions.isEmpty()) {

			return statistics;
		}

		
		DoubleSummaryStatistics stats = validTransactions.parallelStream()
				.collect(Collectors.summarizingDouble(Transaction::getAmount));

		statistics.setAvg(stats.getAverage());
		statistics.setCount(stats.getCount());
		statistics.setMax(stats.getMax());
		statistics.setMin(stats.getMin());
		statistics.setSum(stats.getSum());
		
		return statistics;
	}

}
