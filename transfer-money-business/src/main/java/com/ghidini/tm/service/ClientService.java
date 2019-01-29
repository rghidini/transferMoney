package com.ghidini.tm.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import com.ghidini.tm.dao.interfaces.IClientDAO;
import com.ghidini.tm.domain.Client;
import com.ghidini.tm.domain.dto.ClientDTO;
import com.ghidini.tm.exceptions.DBCommitException;
import com.ghidini.tm.exceptions.IdNotFoundException;
import com.ghidini.tm.service.interfaces.IClientService;

@Singleton
public class ClientService implements IClientService{

	private static final Logger logger = Logger.getLogger(ClientService.class);
	
	private static final String CLIENT_NOT_FOUND = "Client not found";
	private static final String ID_MUST_BE_A_NUMBER = "Client id can't be null and must be a positive number";
	private static final String NAME_CANT_BE_NULL = "Client name can't be null and can't be contains a numeric character";

	@Inject
	private IClientDAO clientDao;

	@Override
	public void addClient(ClientDTO client) {
		try {
			if(StringUtils.isBlank(client.getClientName())
					|| StringUtils.isAlphanumeric(client.getClientName())){
				throw new IllegalArgumentException(NAME_CANT_BE_NULL);
			} else {
				clientDao.insert(new Client(client.getClientName()));
			}
		} catch(Exception e) {
			logger.error(new DBCommitException(client));
		}
	}

	@Override
	public void updateClient(Long id, ClientDTO clientDto) {
		Client client = null;
		try {
			if(StringUtils.isBlank(clientDto.getClientName())
					|| StringUtils.isAlphanumeric(clientDto.getClientName())){
				throw new IllegalArgumentException(NAME_CANT_BE_NULL);
			} else if(Objects.isNull(id)
					|| !NumberUtils.isNumber(id.toString())
					|| Objects.equals(Long.signum(id), NumberUtils.INTEGER_MINUS_ONE)) {
				throw new IllegalArgumentException(ID_MUST_BE_A_NUMBER);
			} else {
				client = new Client(id, Optional.ofNullable(clientDao.findById(id))
						.orElseThrow(() -> new IdNotFoundException(CLIENT_NOT_FOUND))
						.getClientName());
				clientDao.update(client).getClientName();
			}
		} catch(Exception e) {
			logger.error(new DBCommitException(clientDto));
		}
	}

	@Override
	public ClientDTO findClientById(Long id) {
		ClientDTO clientDto = null;
		try {
			if(Objects.isNull(id)
					|| !NumberUtils.isNumber(id.toString())
					|| Objects.equals(Long.signum(id), NumberUtils.INTEGER_MINUS_ONE)){
				throw new IllegalArgumentException(ID_MUST_BE_A_NUMBER);
			} else {
				clientDto = new ClientDTO(Optional.ofNullable(clientDao.findById(id))
						.orElseThrow(() -> new IdNotFoundException(CLIENT_NOT_FOUND))
						.getClientName());
			}
		} catch(Exception e) {
			logger.error(e.getMessage());
		}
		return clientDto;
	}

	@Override
	public List<ClientDTO> getAllClients() {
		List<ClientDTO> listClientDto = new ArrayList<>();
		try {
			Optional.ofNullable(clientDao.findAll())
			.orElseGet(Collections::emptyList)
			.stream()
			.filter(Objects::nonNull)
			.forEach(client -> listClientDto.add(new ClientDTO(client.getClientName())));
		} catch(Exception e) {
			logger.error(e.getMessage());
		}
		return listClientDto;
	}

	@Override
	public void deleteClient(Long id) {
		try {
			if(Objects.isNull(id)
					|| !NumberUtils.isNumber(id.toString())
					|| Objects.equals(Long.signum(id), NumberUtils.INTEGER_MINUS_ONE)){
				throw new IllegalArgumentException(ID_MUST_BE_A_NUMBER);
			} else {
				clientDao.delete(Optional.ofNullable(clientDao.findById(id))
						.orElseThrow(() -> new IdNotFoundException(CLIENT_NOT_FOUND))
						.getClientId());
			}
		} catch(Exception e) {
			logger.error(e.getMessage());
		}
	}

}
