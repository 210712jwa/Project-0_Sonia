package com.revature.service;

import java.sql.SQLException;
import java.util.List;

import com.revature.dao.AccountDAO;
import com.revature.dao.AccountDAOImpl;
import com.revature.dao.ClientDAO;
import com.revature.dao.ClientDAOImpl;
import com.revature.dto.AddOrEditAccountDTO;
import com.revature.exception.BadParameterException;
import com.revature.exception.ClientNotFoundException;
import com.revature.exception.DatabaseException;
import com.revature.model.Account;
import com.revature.model.Client;

public class AccountService {
	private AccountDAO accountDao;
	private ClientDAO clientDao;

	public AccountService() {
		this.accountDao = new AccountDAOImpl();
		this.clientDao = new ClientDAOImpl();

	}

	public AccountService(ClientDAO clientDao, AccountDAO accountDao) {
		this.clientDao = clientDao;
		this.accountDao = accountDao;

	}

	public List<Account> getAllAccountsFromClient(String clientIdString)
			throws BadParameterException, DatabaseException, ClientNotFoundException {
		try {

			int clientId = Integer.parseInt(clientIdString);

			if (clientDao.getClientById(clientId) == null) {
				throw new ClientNotFoundException("Client with id " + clientId + " was not found");
			}
			List<Account> accounts = accountDao.getAllAccountsFromClient(clientId);

			return accounts;
		} catch (SQLException e) {
			e.printStackTrace();

			throw new DatabaseException(e.getMessage());
		} catch (NumberFormatException e) {
			throw new BadParameterException(
					clientIdString + " was passed in by the user as the id, " + "but it is not an int");
		}
	}

	public Account getAccountById(String clientIdString, String IdString)
			throws ClientNotFoundException, DatabaseException, BadParameterException {
		try {

			int clientId = Integer.parseInt(clientIdString);

			if (clientDao.getClientById(clientId) == null) {
				throw new ClientNotFoundException("Client with id " + clientId + " was not found");
			}

			try {

				int id = Integer.parseInt(IdString);

				if (accountDao.getAccountById(clientId, id) == null) {
					throw new ClientNotFoundException("Account with id " + id + " was not found");
				}

				Account targetAccount = accountDao.getAccountById(clientId, id);
				return targetAccount;

			} catch (SQLException e) {
				throw new DatabaseException(e.getMessage());
			} catch (NumberFormatException e) {
				throw new BadParameterException(
						IdString + " was passed in by the user as the id, " + "but it is not an int");
			}

		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		} catch (NumberFormatException e) {
			throw new BadParameterException(
					clientIdString + " was passed in by the user as the id, " + "but it is not an int");
		}
	}

	public Account addAccount(AddOrEditAccountDTO account) throws DatabaseException, BadParameterException {

		if (account.getType_of_account().trim().equals("") && account.getAccount_balance() < 0) {
			throw new BadParameterException("Account name cannot be blank and balance cannot be less than 0");
		}

		if (account.getType_of_account().trim().equals("")) {
			throw new BadParameterException("Account name cannot be blank");
		}

		if (account.getAccount_balance() < 0) {
			throw new BadParameterException("Account balance cannot be less than 0");
		}

		try {
			Account addedAccount = accountDao.addAccount(account);

			return addedAccount;
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public Account editAccount(String clientIdString, String IdString, AddOrEditAccountDTO accountToEdit)
			throws ClientNotFoundException, BadParameterException, DatabaseException {
		try {

			int clientId = Integer.parseInt(clientIdString);

			if (clientDao.getClientById(clientId) == null) {
				throw new ClientNotFoundException("Client with id " + clientId + " was not found");
			}

			try {

				int id = Integer.parseInt(IdString);

				if (accountDao.getAccountById(clientId, id) == null) {
					throw new ClientNotFoundException("Account with id " + id + " was not found");
				}
				Account targetAccount = accountDao.editAccount(clientId, id, accountToEdit);

				return targetAccount;

			} catch (SQLException e) {
				throw new DatabaseException(e.getMessage());
			} catch (NumberFormatException e) {
				throw new BadParameterException(IdString + " was passed in by the user as the id, " + "but it is not an int");
			}

		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		} catch (NumberFormatException e) {
			throw new BadParameterException(
					clientIdString + " was passed in by the user as the id, " + "but it is not an int");
		}
	}

	public void deleteAccount(String clientIdString, String IdString)
			throws ClientNotFoundException, DatabaseException, BadParameterException {
		try {

			int clientId = Integer.parseInt(clientIdString);

			if (clientDao.getClientById(clientId) == null) {
				throw new ClientNotFoundException("Client with id " + clientId + " was not found");
			}

			try {

				int id = Integer.parseInt(IdString);

				if (accountDao.getAccountById(clientId, id) == null) {
					throw new ClientNotFoundException("Account with id " + id + " was not found");
				}
				accountDao.deleteAccount(clientId, id);

			} catch (SQLException e) {
				throw new DatabaseException(e.getMessage());
			} catch (NumberFormatException e) {
				throw new BadParameterException(
						IdString + " was passed in by the user as the id, " + "but it is not an int");
			}

		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		} catch (NumberFormatException e) {
			throw new BadParameterException(
					clientIdString + " was passed in by the user as the id, " + "but it is not an int");
		}

	}

	public List<Account> getAccountsLessThan(String lessThan) {
		// TODO Auto-generated method stub
		return null;
	}


//	public List<Account> getAllAccountsFromClient(String clientIdString, String lessThanString, String greaterThanString) throws BadParameterException, DatabaseException, ClientNotFoundException {
//		boolean lessThanValue = false;
//		boolean greaterThanValue = false;
//		int greaterThan = 0;
//		int lessThan = 0;
//		List<Account> accounts = null;
//		try {
//			
//			int clientId = Integer.parseInt(clientIdString);
//			
//			if(clientDao.getClientById(clientId) == null) {
//				throw new ClientNotFoundException("Client with id " + clientId + " was not found");
//			}
//			try {
//				if(lessThanString != null)
//				{
//					lessThan = Integer.parseInt(lessThanString);
//					lessThanValue = true;
//				}
//				
//			} catch (NumberFormatException e) {
//				throw new BadParameterException(lessThanString + " was passed in by the user as the less than value, " + "but it is not an int");
//			}
//			try {
//				if(greaterThanString != null)
//				{
//					greaterThan = Integer.parseInt(greaterThanString);
//					greaterThanValue = true;
//				}
//				
//			} catch (NumberFormatException e) {
//				throw new BadParameterException(greaterThanString + " was passed in by the user as the less than value, " + "but it is not an int");
//			}
//			
//			if(lessThanValue && greaterThanValue) {
//				accounts = accountDao.getAccountsBetween(clientId, lessThan, greaterThan);
//				
//			}
//			else if(lessThanValue) {
//				accounts = accountDao.getAccountsLessThan(clientId, lessThan);
//				
//			}
//			else if(greaterThanValue) {
//				accounts = accountDao.getAccountsGreaterThan(clientId, greaterThan);
//				
//			}
//			else {
//			
//				accounts = accountDao.getAllAccountsFromClient(clientId);	
//			}
//			if(accounts == null) {
//				throw new ClientNotFoundException("No Accounts found for client");
//			}
//			
//			
//			return accounts;
//			
//		} catch (SQLException e) {
//			throw new DatabaseException(e.getMessage());
//		} catch (NumberFormatException e) {
//			throw new BadParameterException(clientIdString + " was passed in by the user as the id, " + "but it is not an int");
//		}
//	}

}