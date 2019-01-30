package com.ghidini.tm.service.interfaces;

import java.util.List;

import com.ghidini.tm.domain.dto.AccountDTO;

public interface IAccountService {
	
	public void addAccount(AccountDTO account);
	public void deleteAccount(Long id);
	public void updateAccount(AccountDTO account);
	public AccountDTO findAccountById(Long id);
	public List<AccountDTO> getAllAccounts();

}
