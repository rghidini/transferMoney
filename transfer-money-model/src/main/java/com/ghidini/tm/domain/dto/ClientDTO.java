package com.ghidini.tm.domain.dto;

import java.io.Serializable;

public class ClientDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String clientName;
	

	public ClientDTO(String clientName) {
		this.clientName = clientName;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

}
