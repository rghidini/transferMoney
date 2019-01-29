package com.ghidini.tm.service.interfaces;

import java.util.List;

import com.ghidini.tm.domain.dto.ClientDTO;

public interface IClientService {
	
	public void addClient(ClientDTO client);
	public void updateClient(Long id, ClientDTO client);
	public void deleteClient(Long id);
	public ClientDTO findClientById(Long id);
	public List<ClientDTO> getAllClients();

}
