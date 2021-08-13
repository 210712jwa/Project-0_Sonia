package com.revature.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dto.AddOrEditAccountDTO;
import com.revature.model.Account;
import com.revature.service.AccountService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AccountController implements Controller {

	private AccountService accountService;
	private static Logger logger = LoggerFactory.getLogger(ClientController.class);


	public AccountController() {
		this.accountService = new AccountService();

	}

	private Handler getAccountFromClient = (ctx) -> {
		String clientId = ctx.pathParam("clientid");

		String greaterThan = ctx.queryParam("amountGreaterThan");
		
		logger.info("Get all clients list of clients:["+clientId.toString()+"]");
		
		
		
		List<Account> accountsFromClient = accountService.getAllAccountsFromClient(clientId);
		ctx.json(accountsFromClient);

	};
	private Handler getAccountsLessThan = (ctx) -> {

		String lessThan = ctx.queryParam("amountLessThan");

		List<Account> getAccountsLessThan = accountService.getAccountsLessThan(lessThan);
		ctx.json(getAccountsLessThan);

	};
	private Handler getAccountsGreaterThan = (ctx) -> {

		String greaterThan = ctx.queryParam("amountGreaterThan");

		List<Account> getAccountsGreaterThan = accountService.getAllAccountsFromClient(greaterThan);
		ctx.json(getAccountsGreaterThan);

	};
	private Handler addAccountToClient = (ctx) -> {
		AddOrEditAccountDTO accountToAdd = ctx.bodyAsClass(AddOrEditAccountDTO.class);
		
		Account addedAccount = accountService.addAccount(accountToAdd);
		ctx.status(200);
		ctx.json(addedAccount);
		
	};
	private Handler getAccountById = (ctx) -> {
		Map<String, String> mPathParmaMap = ctx.pathParamMap();
		System.out.println("Context parameter map: [" + mPathParmaMap + "]");
		

		String clientId = ctx.pathParam("clientid");
		String id = ctx.pathParam("id");
		
		
		Account accountById = accountService.getAccountById(clientId,id);
		ctx.status(200);
		ctx.json(accountById);
	};
	private Handler editAccount = (ctx) -> {
		AddOrEditAccountDTO accountToEdit = ctx.bodyAsClass(AddOrEditAccountDTO.class);
		
		String clientId = ctx.pathParam("clientid");
		
		String id = ctx.pathParam("id");
		
		Account editedAccount = accountService.editAccount(clientId, id, accountToEdit);
		ctx.status(200);
		ctx.json(editedAccount);
		
	};
	private Handler deleteAccount = (ctx) -> {
		String clientId = ctx.pathParam("clientid");
		
		String id = ctx.pathParam("id");
		ctx.status(200);
		accountService.deleteAccount(clientId,id);
	};
	
	@Override
	public void mapEndpoints(Javalin app) {
		app.post("/client/:clientid/account", addAccountToClient);
		app.get("/client/:clientid/account", getAccountFromClient);
		app.get("/client/:clientid/account/:id", getAccountById);
		app.put("/client/:clientid/account/:id", editAccount);
		app.delete("/client/:clientid/account/:id", deleteAccount);
		app.get("/client/:clientid/account/amountLessThan",getAccountsLessThan);
		app.get("/client/:clientid/account/amuountGreaterThan",getAccountsGreaterThan);
				
	}
}
