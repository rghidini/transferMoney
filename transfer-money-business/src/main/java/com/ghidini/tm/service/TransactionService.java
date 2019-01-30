package com.ghidini.tm.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import com.ghidini.tm.dao.interfaces.ITransactionDAO;
import com.ghidini.tm.domain.dto.AccountDTO;
import com.ghidini.tm.domain.dto.TransactionDTO;
import com.ghidini.tm.service.interfaces.IAccountService;
import com.ghidini.tm.service.interfaces.ITransactionService;

@Singleton
public class TransactionService implements ITransactionService{

	private static final Logger logger = Logger.getLogger(TransactionService.class);

	private static final String SAME_ACCOUNTS = "The accounts need to be different";
	private static final String TRANSFER_VALUE = "Transfer value exceeds what is available on account - ";
	private static final String AMOUNT_GREATER_THAN_ZERO = "Amount must be greater than or equal zero";

	@Inject
	private IAccountService accountService;

	@Inject
	private ITransactionDAO transactionDao;


	@Override
	public void addTransaction(TransactionDTO transaction) {

		try {

			if(Objects.equals(transaction.getFromAccountId(), transaction.getToAccountId())) {
				throw new IllegalArgumentException(SAME_ACCOUNTS);
			}

			BigDecimal amount = Optional.of(transaction.getAmount())
					.filter(Objects::nonNull)
					.filter(a -> NumberUtils.isNumber(a.toString()))
					.filter(a -> Objects.equals(a.signum(), NumberUtils.INTEGER_ONE))
					.orElseThrow(() -> new IllegalArgumentException(AMOUNT_GREATER_THAN_ZERO));

			AccountDTO fromAccount = accountService.findAccountById(transaction.getFromAccountId());
			AccountDTO toAccount = accountService.findAccountById(transaction.getToAccountId());

			BigDecimal subtract = fromAccount.getAmount().subtract(amount);
			if(Objects.equals(subtract.signum(), NumberUtils.INTEGER_MINUS_ONE)) {
				throw new IllegalArgumentException(TRANSFER_VALUE + fromAccount.getAmount());
			}

			fromAccount.setAmount(subtract);
			accountService.updateAccount(fromAccount);

			BigDecimal add = toAccount.getAmount().add(amount);
			toAccount.setAmount(add);
			accountService.updateAccount(toAccount);

		} catch(Exception e) {
			logger.error(e.getMessage());
		}

	}

	@Override
	public List<TransactionDTO> getAllTransaction() {
		List<TransactionDTO> listTransactionsDto = new ArrayList<>();
		try {
			Optional.ofNullable(transactionDao.findAll())
			.orElseGet(Collections::emptyList)
			.stream()
			.filter(Objects::nonNull)
			.forEach(transaction -> listTransactionsDto.add(new TransactionDTO(transaction.getFromAccountId(), transaction.getToAccountId(), 
					transaction.getAmount(), transaction.getTransactionDate())));
		} catch(Exception e) {
			logger.error(e.getMessage());
		}
		return listTransactionsDto;
	}

}
