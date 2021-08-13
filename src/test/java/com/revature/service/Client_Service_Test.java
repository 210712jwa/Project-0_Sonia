package com.revature.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.revature.dao.AccountDAO;
import com.revature.dao.ClientDAO;
import com.revature.dto.AddOrEditClientDTO;
import com.revature.exception.BadParameterException;
import com.revature.exception.DatabaseException;
import com.revature.exception.ClientNotFoundException;
import com.revature.model.Account;
import com.revature.model.Client;

public class Client_Service_Test {

	private ClientService clientService;
	private ClientDAO clientDao;
	private AccountDAO accountDao;
	
	@Before
	public void setUp() throws Exception {
		this.clientDao = mock(ClientDAO.class); 
		this.accountDao = mock(AccountDAO.class);
		
		this.clientService = new ClientService(clientDao, accountDao); 
	}

	/*
	 * getAllClients
	 */
	@Test
	public void test_getAllClients_positive() throws DatabaseException, SQLException {
		
		List<Client> mockReturnValues = new ArrayList<>();
		mockReturnValues.add(new Client(1, "John", 40));
		mockReturnValues.add(new Client(2, "Nikki", 10));
		when(clientDao.getAllClients()).thenReturn(mockReturnValues);
		
		List<Account> revatureAccounts = new ArrayList<>();
		revatureAccounts.add(new Account(1, "Bruno", 28, 1));
		revatureAccounts.add(new Account(2, "Sam", 60, 1));
		when(accountDao.getAllAccountsFromClient(eq(1))).thenReturn(revatureAccounts);
		
		List<Account> royalFortuneAccounts = new ArrayList<>();
		royalFortuneAccounts.add(new Account(10, "test1", 100, 2));
		royalFortuneAccounts.add(new Account(53, "test2", 101, 2));
		when(accountDao.getAllAccountsFromClient(eq(2))).thenReturn(royalFortuneAccounts);
		
		
		List<Client> actual = clientService.getAllClients();
		
		System.out.println(actual);
		
		List<Client> expected = new ArrayList<>();
		Client s1 = new Client(1, "John", 40);
		s1.setAccounts(revatureAccounts);
		Client s2 = new Client(2, "Nikki", 10);
		s2.setAccounts(royalFortuneAccounts);
		
		expected.add(s1);
		expected.add(s2);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_getAllClients_negative() throws SQLException {
		when(clientDao.getAllClients()).thenThrow(SQLException.class);
		
		try {
			clientService.getAllClients();
			
			fail(); 
		} catch (DatabaseException e) {
			String exceptionMessage = e.getMessage();
			assertEquals("Something went wrong with our DAO operations", exceptionMessage);
		}
		
	}
	

	/*
	 * getClientById
	 */
	@Test
	public void test_getClientById_idStringIsNotAnInt() throws DatabaseException, ClientNotFoundException {
		try {
			clientService.getClientById("asdfasdf");
			
			fail();
		} catch (BadParameterException e) {
			assertEquals("asdfasdf was passed in by the user as the id, but it is not an int", e.getMessage());
		}
	}
	
	@Test
	public void test_getClientById_existingId() throws SQLException, DatabaseException, ClientNotFoundException, BadParameterException {
		when(clientDao.getClientById(eq(1))).thenReturn(new Client(1, "John", 1));
		
		Client actual = clientService.getClientById("1");
		
		Client expected = new Client(1, "John", 1);
		expected.setAccounts(new ArrayList<>());
		
		assertEquals(expected, actual);
	}
	
//	@Test
//	public void test_getClientById_clientDoesNotExist() throws DatabaseException, ClientNotFoundException, BadParameterException {
//		try {
//			clientService.getClientById("10"); 
//			
//			fail();
//		} catch (ClientNotFoundException e) {
//			assertEquals("Client with id 10 was not found", e.getMessage());
//		}
//	}
	
	@Test
	public void test_getClientById_sqlExceptionEncountered() throws SQLException, ClientNotFoundException, BadParameterException {
		try {
			when(clientDao.getClientById(anyInt())).thenThrow(SQLException.class);
			
			clientService.getClientById("1");
			
			fail();
		} catch (DatabaseException e) {
			assertEquals("Something went wrong with our DAO operations", e.getMessage());
		}
	}
	
	/*
	 * addClient
	 */
	@Test
	public void test_addClient_positivePath() throws SQLException, DatabaseException, BadParameterException {
		
		AddOrEditClientDTO dto = new AddOrEditClientDTO();
		dto.setName("John");
		dto.setAccount_number(10);
		
		when(clientDao.addClient(eq(dto))).thenReturn(new Client(1, "John", 10));
		
		Client actual = clientService.addClient(dto);
		
		Client expected = new Client(1, "John", 10);
		expected.setAccounts(new ArrayList<>());
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_addClient_blankName() throws DatabaseException {
		AddOrEditClientDTO dto = new AddOrEditClientDTO();
		dto.setName("");
		dto.setAccount_number(10);
		
		try {
			clientService.addClient(dto);
			
			fail();
		} catch (BadParameterException e) {
			assertEquals("Client name cannot be blank", e.getMessage());
		}
		
	}
	
	@Test
	public void test_addClient_blankNameWithSpaces() throws DatabaseException {
		AddOrEditClientDTO dto = new AddOrEditClientDTO();
		dto.setName("                        ");
		dto.setAccount_number(10);
		
		try {
			clientService.addClient(dto);
			
			fail();
		} catch (BadParameterException e) {
			assertEquals("Client name cannot be blank", e.getMessage());
		}
	}

//	@Test
//	public void test_addClient_negativeAge() throws DatabaseException {
//		AddOrEditClientDTO dto = new AddOrEditClientDTO();
//		dto.setName("Bach's Client");
//		dto.setAccount_number(-1);
//		
//		try {
//			clientService.addClient(dto);
//			
//			fail();
//		} catch (BadParameterException e) {
//			assertEquals("Client age cannot be less than 0", e.getMessage());
//		}
//		
//	}
	
	@Test
	public void test_addClient_negativeAgeAndBlankName() throws DatabaseException {
		AddOrEditClientDTO dto = new AddOrEditClientDTO();
		dto.setName("");
		dto.setAccount_number(-10);
		
		try {
			clientService.addClient(dto);
			
			fail();
		} catch (BadParameterException e) {
			assertEquals("Client name cannot be blank and age cannot be less than 0", e.getMessage());
		}
		
	}
	
	@Test(expected = DatabaseException.class)
	public void test_addClient_SQLExceptionEncountered() throws SQLException, DatabaseException, BadParameterException {
		when(clientDao.addClient(any())).thenThrow(SQLException.class);
		
		AddOrEditClientDTO dto = new AddOrEditClientDTO();
		dto.setName("John");
		dto.setAccount_number(10);
		clientService.addClient(dto);
	}
	
	/*
	 * editClient
	 */
	
	@Test
	public void test_editClient_positivePath() throws DatabaseException, ClientNotFoundException, BadParameterException, SQLException {
		AddOrEditClientDTO dto = new AddOrEditClientDTO();
		dto.setName("John");
		dto.setAccount_number(100);
		
		Client clientWithId10 = new Client(10, "Mary", 5);
		when(clientDao.getClientById(eq(10))).thenReturn(clientWithId10);
		
		when(clientDao.editClient(eq(10), eq(dto))).thenReturn(new Client(10, "John", 100));
		
		Client actual = clientService.editClient("10", dto);
		
		Client expected = new Client(10, "John", 100);
		expected.setAccounts(new ArrayList<>());
		
		assertEquals(expected, actual);
	}
	
//	@Test
//	public void test_editClient_clientDoesNotExist() throws DatabaseException, BadParameterException {
//		AddOrEditClientDTO dto = new AddOrEditClientDTO();
//		dto.setName("John");
//		dto.setAccount_number(100);
//		
//		try {
//			clientService.editClient("1000", dto);
//			
//			fail();
//		} catch (ClientNotFoundException e) {
//			assertEquals("Client with id 1000 was not found", e.getMessage());
//		}
//		
//	}
	
	@Test(expected = BadParameterException.class)
	public void test_editClient_invalidId() throws DatabaseException, ClientNotFoundException, BadParameterException {
		AddOrEditClientDTO dto = new AddOrEditClientDTO();
		dto.setName("John");
		dto.setAccount_number(100);
		
		clientService.editClient("abc", dto);
	}
	
	@Test(expected = DatabaseException.class)
	public void test_editClient_SQLExceptionEncountered() throws SQLException, DatabaseException, ClientNotFoundException, BadParameterException {
		AddOrEditClientDTO dto = new AddOrEditClientDTO();
		dto.setName("John");
		dto.setAccount_number(100);
		
		when(clientDao.getClientById(eq(10))).thenReturn(new Client(10, "Mary", 5));
		when(clientDao.editClient(eq(10), eq(dto))).thenThrow(SQLException.class);
		
		clientService.editClient("10", dto);
	}
	
//	@Test
//	public void test_deleteClient_clientDoesNotExist() throws DatabaseException, BadParameterException {
//		
//		try {
//			clientService.deleteClient("1000");
//			
//			fail();
//		} catch (ClientNotFoundException e) {
//			assertEquals("Trying to delete client with an id of 1000, but it does not exist", e.getMessage());
//		}
//		
//	}
	
	@Test(expected = BadParameterException.class)
	public void test_deleteClient_invalidId() throws DatabaseException, ClientNotFoundException, BadParameterException {
		
		
		clientService.deleteClient("abc");
	}
	
	
	
}
