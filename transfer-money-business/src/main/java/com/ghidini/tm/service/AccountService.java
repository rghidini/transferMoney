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

import com.ghidini.tm.dao.interfaces.IAccountDAO;
import com.ghidini.tm.domain.Account;
import com.ghidini.tm.domain.dto.AccountDTO;
import com.ghidini.tm.exceptions.DBCommitException;
import com.ghidini.tm.exceptions.IdNotFoundException;
import com.ghidini.tm.service.interfaces.IAccountService;

@Singleton
public class AccountService implements IAccountService{

	private static final Logger logger = Logger.getLogger(AccountService.class);

	private static final String ACCOUNT_NOT_FOUND = "Account not found";
	private static final String CLIENT_ID_MUST_BE_A_NUMBER = "Client id can't be null and must be a positive number";
	private static final String ACCOUNT_CANT_BE_NULL = "Account id can't be null and must be a positive number";
	private static final String AMOUNT_GREATER_THAN_ZERO = "Amount must be greater than or equal zero";

	@Inject
	private IAccountDAO accountDao;

	@Override
	public void addAccount(AccountDTO account) {
		try {
			if(Objects.isNull(account.getClientId())
					|| !NumberUtils.isNumber(account.getClientId().toString())
					|| Objects.equals(Long.signum(account.getClientId()), NumberUtils.INTEGER_MINUS_ONE)) {
				throw new IllegalArgumentException(CLIENT_ID_MUST_BE_A_NUMBER);
			} 
			verifyAmount(account.getAmount()); 
			accountDao.insert(new Account(account.getClientId(), account.getAmount()));
		} catch(Exception e) {
			logger.error(new DBCommitException(account));
		}
	}

	@Override
	public AccountDTO findAccountById(Long id) {
		AccountDTO accountDto = null;
		try {
			verifyAccountId(id); 
			Account account = verifyAccount(id);
			accountDto = new AccountDTO(account.getAccountId(), account.getClientId(), account.getAmount());
		} catch(Exception e) {
			logger.error(e.getMessage());
		}
		return accountDto;
	}

	@Override
	public List<AccountDTO> getAllAccounts() {
		List<AccountDTO> listAccountsDto = new ArrayList<>();
		try {
			Optional.ofNullable(accountDao.findAll())
			.orElseGet(Collections::emptyList)
			.stream()
			.filter(Objects::nonNull)
			.forEach(account -> listAccountsDto.add(new AccountDTO(account.getAccountId(), account.getClientId(), account.getAmount())));

		} catch(Exception e) {
			logger.error(e.getMessage());
		}
		return listAccountsDto;
	}

	@Override
	public void deleteAccount(Long id) {
		try {
			verifyAccountId(id); 
			accountDao.delete(verifyAccount(id).getAccountId());
		} catch(Exception e) {
			logger.error(e.getMessage());
		}	
	}

	@Override
	public void updateAccount(AccountDTO account) {
		try {
			verifyAccountId(account.getAccountId());
			Account verifyAccount = verifyAccount(account.getAccountId());
			accountDao.update(verifyAccount);		
		} catch(Exception e) {
			logger.error(e.getMessage());
		}
	}

	private void verifyAccountId(Long id) {
		if(Objects.isNull(id)
				|| !NumberUtils.isNumber(id.toString())
				|| Objects.equals(Long.signum(id), NumberUtils.INTEGER_MINUS_ONE)){
			throw new IllegalArgumentException(ACCOUNT_CANT_BE_NULL);
		}
	}

	private Account verifyAccount(Long id) {
		return Optional.ofNullable(accountDao.findById(id))
				.orElseThrow(() -> new IdNotFoundException(ACCOUNT_NOT_FOUND));
	}

	private void verifyAmount(BigDecimal amount) {
		if(Objects.isNull(amount) 
				|| !NumberUtils.isNumber(amount.toString())
				|| Objects.equals(amount.signum(), NumberUtils.INTEGER_MINUS_ONE)){
			throw new IllegalArgumentException(AMOUNT_GREATER_THAN_ZERO);
		}
	}


}
