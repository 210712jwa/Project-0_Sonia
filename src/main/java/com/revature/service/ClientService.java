package com.revature.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dao.AccountDAO;
import com.revature.dao.AccountDAOImpl;
import com.revature.dao.ClientDAO;
import com.revature.dao.ClientDAOImpl;
import com.revature.dto.AddOrEditClientDTO;
import com.revature.exception.BadParameterException;
import com.revature.exception.ClientNotFoundException;
import com.revature.exception.DatabaseException;
import com.revature.model.Account;
import com.revature.model.Client;

public class ClientService {

	private Logger logger = LoggerFactory.getLogger(ClientService.class);

	private ClientDAO clientDao;
	private AccountDAO accountDao;

	public ClientService() {
		this.clientDao = new ClientDAOImpl();
		this.accountDao = new AccountDAOImpl();

	}
	
	public ClientService(ClientDAO mockedClientDaoObject, AccountDAO mockedAccountDaoObject) {
		this.clientDao = mockedClientDaoObject;
		this.accountDao = mockedAccountDaoObject;
	}

	public List<Client> getAllClients() throws DatabaseException {
		
		try {
			List<Client> clients = clientDao.getAllClients();

			//for (Client client : clients) {
				
				//List<Account> accounts = accountDao.getAllAccountsFromClient(client.getId());
				//client.setAccounts(accounts);
			
			//}
			return clients;
		} catch (SQLException e) {
			throw new DatabaseException("Something went wrong with our DAO operations");
		}
		
	}

	public Client getClientById(String stringId) throws DatabaseException, ClientNotFoundException, BadParameterException {
		try {
			int id = Integer.parseInt(stringId);
			Client client = clientDao.getClientById(id);
			if (client == null) {
				throw new ClientNotFoundException("Client with id " + id + "was not found");
			}
//			List<Account> accounts = accountDao.getAllAccountsFromClient(id);
//			client.setAccounts(accounts);
			return client;
		}

		catch (SQLException e) {
			throw new DatabaseException("Something went wrong with our DAO operations");
		} catch (NumberFormatException e) {
			throw new BadParameterException(
					stringId + " was passed in by the user as the id, " + "but it is not an int");
		}

	}

	public Client addClient(AddOrEditClientDTO client) throws DatabaseException, BadParameterException {
		if (client.getName().trim().equals("") && client.getAccount_number() < 0) {
			throw new BadParameterException("Client name cannot be blank and age cannot be less than 0");
		}
		if (client.getName().trim().equals("")) {
			throw new BadParameterException("Client name cannot be blank");
		}
		if (client.getAccount_number() < 0) {
			throw new BadParameterException("Client Account number cannot be less than 0");
		}
		try {
			Client addedclient = clientDao.addClient(client);
			addedclient.setAccounts(new ArrayList<>());
			return addedclient;
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public Client editClient(String stringId, AddOrEditClientDTO client)
			throws DatabaseException, ClientNotFoundException, BadParameterException {
		try {
			int clientId = Integer.parseInt(stringId);
			if (clientDao.getClientById(clientId) == null) {
				throw new ClientNotFoundException("Client with id " + clientId + "was not found");
			}
			Client editedClient = clientDao.editClient(clientId, client);

			List<Account> accounts = accountDao.getAllAccountsFromClient(clientId);
			editedClient.setAccounts(accounts);

			return editedClient;
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		} catch (NumberFormatException e) {
			throw new BadParameterException(
					stringId + " was passed in by the user as the id, " + "but it is not an int");
		}
	}

	public void deleteClient(String clientId) throws BadParameterException, DatabaseException, ClientNotFoundException {

		try {
			int id = Integer.parseInt(clientId);

			Client client = clientDao.getClientById(id);
			if (client == null) {
				throw new ClientNotFoundException("Trying to delete ship with an id of " + id + ", but it does not exist");
			}
			clientDao.deleteClient(id);
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		} catch (NumberFormatException e) {
			throw new BadParameterException(
					clientId + " was passed in by the user as the id, " + "but it is not an int");
		}

	}

	public void getClientsWithAccount_numberBetween(int i, String queryParam) {

	}



}
