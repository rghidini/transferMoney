package com.ghidini.tm.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class AccountDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long accountId;
	
	private Long clientId;
	
	private BigDecimal amount;

	/**
	 * @param accountId
	 * @param clientId
	 * @param amount
	 */
	public AccountDTO(Long accountId, Long clientId, BigDecimal amount) {
		super();
		this.accountId = accountId;
		this.clientId = clientId;
		this.amount = amount;
	}

	/**
	 * @return the accountId
	 */
	public Long getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the clientId
	 */
	public Long getClientId() {
		return clientId;
	}

	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}
