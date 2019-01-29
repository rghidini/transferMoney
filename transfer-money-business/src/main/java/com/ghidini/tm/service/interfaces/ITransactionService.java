package com.ghidini.tm.service.interfaces;

import java.util.List;

import com.ghidini.tm.domain.Transaction;

public interface ITransactionService {
	
	public void addTransaction(Transaction transaction);
	public List<Transaction> getAllTransaction();

}
