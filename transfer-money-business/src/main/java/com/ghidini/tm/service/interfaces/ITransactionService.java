package com.ghidini.tm.service.interfaces;

import java.util.List;

import com.ghidini.tm.domain.dto.TransactionDTO;

public interface ITransactionService {
	
	public void addTransaction(TransactionDTO transaction);
	public List<TransactionDTO> getAllTransaction();

}
