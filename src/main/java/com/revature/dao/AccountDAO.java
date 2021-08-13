package com.revature.dao;

import java.sql.SQLException;
import java.util.List;

import com.revature.dto.AddOrEditAccountDTO;
import com.revature.model.Account;

public interface AccountDAO {

	public abstract List<Account> getAllAccountsFromClient(int clientId) throws SQLException;

	public abstract Account addAccount(AddOrEditAccountDTO account) throws SQLException;

	public abstract Account editAccount(int clientId, int id, AddOrEditAccountDTO account) throws SQLException;

	public abstract Account getAccountById(int clientId, int id) throws SQLException;

	public abstract void deleteAccount(int clientId, int id) throws SQLException;

	List<Account> getAccountsBetween(int clientId, int lessThan, int greaterThan) throws SQLException;

	List<Account> getAccountsLessThan(int clientId, int lessThan) throws SQLException;

	List<Account> getAccountsGreaterThan(int clientId, int greaterThan) throws SQLException;

}
